import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainApp extends JFrame {

    private static final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
    static { DF.setLenient(false); }

    private final TournamentManager tournamentManager = new TournamentManager("tournaments.dat");
    private final PlayerManager playerManager = new PlayerManager("players.dat");
    private final TournamentTypeManager typeManager = new TournamentTypeManager("types.dat");

    private JTable tblTournaments, tblPlayers, tblTypes;
    private DefaultTableModel tmTournaments, tmPlayers, tmTypes;

    private JTextField tId, tName, tStart, tEnd, tRounds, tTypeId, tSurface;

    private JTextField pId, pFirst, pLast, pDob, pCountry, pTournamentId;
    private JComboBox<String> pGender;

    private JTextField ttId, ttName;

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    
    private final Color DARK_BG = new Color(30, 30, 30);
    private final Color INPUT_BG = new Color(50, 50, 50);
    private final Color TEXT_COLOR = new Color(220, 220, 220); 
    private final Color BORDER_COLOR = new Color(80, 80, 80);
    private final Color HEADER_BG = new Color(60, 60, 60);

    public MainApp() {
        setTitle("Tournament Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        UIManager.put("control", DARK_BG);
        UIManager.put("text", TEXT_COLOR);
        UIManager.put("nimbusBase", new Color(40, 40, 40));
        UIManager.put("nimbusBlueGrey", DARK_BG);
        UIManager.put("ComboBox:background", INPUT_BG);
        UIManager.put("TextField:background", INPUT_BG);
        UIManager.put("TextArea:background", INPUT_BG);

        mainPanel.add(buildHomePanel(), "Home");
        mainPanel.add(buildTournamentTab(), "Tournaments");
        mainPanel.add(buildPlayerTab(), "Players");
        mainPanel.add(buildTypeTab(), "Tournament Types");

        add(mainPanel, BorderLayout.CENTER);

        refreshAllTables();
    }

    private void refreshAllTables() {
        refreshTournamentTable(tournamentManager.getAll());
        refreshPlayerTable(playerManager.getAll());
        refreshTypeTable();
    }

    private JPanel buildHomePanel() {
        JPanel panel = new GradientPanel(new Color(0, 0, 50), new Color(0, 0, 0));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        ImageIcon imageIcon = new ImageIcon("atp_tour.jpg");
        Image image = imageIcon.getImage().getScaledInstance(300, -1, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        panel.add(imageLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 25, 0));
        buttonPanel.setOpaque(false);

        ImageIcon tournamentIcon = new ImageIcon(new ImageIcon("tennis_racket.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        ImageIcon playerIcon = new ImageIcon(new ImageIcon("tennis_player.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        ImageIcon typeIcon = new ImageIcon(new ImageIcon("trophy.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));

        JButton tournamentsBtn = new ThemedIconButton("Tournaments", tournamentIcon);
        JButton playersBtn = new ThemedIconButton("Players", playerIcon);
        JButton typesBtn = new ThemedIconButton("Tournament Types", typeIcon);

        buttonPanel.add(tournamentsBtn);
        buttonPanel.add(playersBtn);
        buttonPanel.add(typesBtn);

        panel.add(buttonPanel, gbc);

        tournamentsBtn.addActionListener(e -> cardLayout.show(mainPanel, "Tournaments"));
        playersBtn.addActionListener(e -> cardLayout.show(mainPanel, "Players"));
        typesBtn.addActionListener(e -> cardLayout.show(mainPanel, "Tournament Types"));

        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private void setupInput(JComponent comp) {
        comp.setBackground(INPUT_BG);
        comp.setForeground(TEXT_COLOR);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void setupTable(JTable table, JScrollPane scrollPane) {
        table.setBackground(new Color(230, 230, 230)); 
        table.setForeground(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setGridColor(BORDER_COLOR);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(80, 100, 150));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(100, 32));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(HEADER_BG);
                setForeground(TEXT_COLOR);
                setFont(new Font("Segoe UI", Font.BOLD, 14));
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, BORDER_COLOR));
                setHorizontalAlignment(JLabel.CENTER);
                return this;
            }
        });

        scrollPane.getViewport().setBackground(DARK_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(new Color(230, 230, 230)); 
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        });
    }

    private JPanel buildTournamentTab() {
        JPanel root = new JPanel(new BorderLayout(20,20));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        root.setBackground(DARK_BG);

        tmTournaments = new DefaultTableModel(new String[]{"ID","Name","Start Date","End Date","Rounds","Type ID","Surface"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTournaments = new JTable(tmTournaments);
        JScrollPane scrollPane = new JScrollPane(tblTournaments);
        setupTable(tblTournaments, scrollPane);
        root.add(scrollPane, BorderLayout.CENTER);
        
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setOpaque(false);
        tId = new JTextField(); tName = new JTextField(); tStart = new JTextField();
        tEnd = new JTextField(); tRounds = new JTextField(); tTypeId = new JTextField();
        tSurface = new JTextField();
        setupInput(tId); setupInput(tName); setupInput(tStart); setupInput(tEnd);
        setupInput(tRounds); setupInput(tTypeId); setupInput(tSurface);
        form.add(createLabel("ID:")); form.add(tId);
        form.add(createLabel("Name:")); form.add(tName);
        form.add(createLabel("Start (dd/MM/yyyy):")); form.add(tStart);
        form.add(createLabel("End (dd/MM/yyyy):")); form.add(tEnd);
        form.add(createLabel("Rounds:")); form.add(tRounds);
        form.add(createLabel("Type ID:")); form.add(tTypeId);
        form.add(createLabel("Surface:")); form.add(tSurface);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btns.setOpaque(false);
        JButton add = new ThemedButton("Add");
        JButton upd = new ThemedButton("Update");
        JButton del = new ThemedButton("Delete");
        JButton refresh = new ThemedButton("Clear Form");
        JButton back = new ThemedButton("Back to Home");
        btns.add(add); btns.add(upd); btns.add(del); btns.add(refresh);
        btns.add(back);

        JPanel south = new JPanel(new BorderLayout(15, 15));
        south.setOpaque(false);
        south.add(form, BorderLayout.CENTER);
        south.add(btns, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            try {
                Tournament t = readTournamentForm(false);
                tournamentManager.add(t);
                refreshTournamentTable(tournamentManager.getAll());
                clearTournamentForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        upd.addActionListener(e -> {
            int row = tblTournaments.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to update.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
            try {
                Tournament t = readTournamentForm(true);
                int modelIndex = tblTournaments.convertRowIndexToModel(row);
                tournamentManager.update(modelIndex, t);
                refreshTournamentTable(tournamentManager.getAll());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        del.addActionListener(e -> {
            int row = tblTournaments.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to delete.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this tournament?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                int modelIndex = tblTournaments.convertRowIndexToModel(row);
                tournamentManager.delete(modelIndex);
                refreshTournamentTable(tournamentManager.getAll());
                clearTournamentForm();
            }
        });

        refresh.addActionListener(e -> clearTournamentForm());
        back.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        tblTournaments.getSelectionModel().addListSelectionListener(this::onTournamentRowSelect);
        return root;
    }
    
    private void onTournamentRowSelect(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        int row = tblTournaments.getSelectedRow();
        if (row >= 0) {
            int modelIndex = tblTournaments.convertRowIndexToModel(row);
            Tournament t = tournamentManager.getAll().get(modelIndex);
            tId.setText(String.valueOf(t.getId()));
            tName.setText(t.getName());
            tStart.setText(DF.format(t.getStartDate()));
            tEnd.setText(DF.format(t.getEndDate()));
            tRounds.setText(String.valueOf(t.getNumOfRounds()));
            tTypeId.setText(String.valueOf(t.getTypeId()));
            tSurface.setText(t.getSurface());
            tId.setEditable(false);
        }
    }
    
    private Tournament readTournamentForm(boolean isUpdate) throws Exception {
        if (tName.getText().trim().isEmpty() || tStart.getText().trim().isEmpty() || 
            tEnd.getText().trim().isEmpty() || tRounds.getText().trim().isEmpty() ||
            tTypeId.getText().trim().isEmpty() || tSurface.getText().trim().isEmpty()) {
            throw new Exception("All fields must be filled.");
        }
        if (!isUpdate && tId.getText().trim().isEmpty()) {
            throw new Exception("ID must be provided for a new entry.");
        }
        try {
            int id = Integer.parseInt(tId.getText().trim());
            String name = tName.getText().trim();
            Date start = DF.parse(tStart.getText().trim());
            Date end = DF.parse(tEnd.getText().trim());
            int rounds = Integer.parseInt(tRounds.getText().trim());
            int typeId = Integer.parseInt(tTypeId.getText().trim());
            String surface = tSurface.getText().trim();
            return new Tournament(id, name, start, end, rounds, typeId, surface);
        } catch (NumberFormatException nfe) {
            throw new Exception("ID, Rounds, and Type ID must be valid numbers.");
        } catch (ParseException pe) {
            throw new Exception("Dates must be in dd/MM/yyyy format.");
        }
    }
    
    private void clearTournamentForm() {
        tId.setText(""); tId.setEditable(true);
        tName.setText(""); tStart.setText(""); tEnd.setText("");
        tRounds.setText(""); tTypeId.setText(""); tSurface.setText("");
        tblTournaments.clearSelection();
    }
    
    private void refreshTournamentTable(List<Tournament> tournaments) {
        tmTournaments.setRowCount(0);
        for (Tournament t : tournaments) {
            tmTournaments.addRow(new Object[]{ t.getId(), t.getName(), DF.format(t.getStartDate()), DF.format(t.getEndDate()), t.getNumOfRounds(), t.getTypeId(), t.getSurface() });
        }
    }

    private JPanel buildPlayerTab() {
        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        root.setBackground(DARK_BG);

        tmPlayers = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Gender", "DOB", "Country", "Tournament ID"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPlayers = new JTable(tmPlayers);
        JScrollPane scrollPane = new JScrollPane(tblPlayers);
        setupTable(tblPlayers, scrollPane);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setOpaque(false);
        pId = new JTextField(); pFirst = new JTextField(); pLast = new JTextField();
        pGender = new JComboBox<>(new String[]{"MALE", "FEMALE"});
        pDob = new JTextField(); pCountry = new JTextField(); pTournamentId = new JTextField();
        setupInput(pId); setupInput(pFirst); setupInput(pLast); setupInput(pGender);
        setupInput(pDob); setupInput(pCountry); setupInput(pTournamentId);
        form.add(createLabel("ID:")); form.add(pId);
        form.add(createLabel("First Name:")); form.add(pFirst);
        form.add(createLabel("Last Name:")); form.add(pLast);
        form.add(createLabel("Gender:")); form.add(pGender);
        form.add(createLabel("DOB (dd/MM/yyyy):")); form.add(pDob);
        form.add(createLabel("Country:")); form.add(pCountry);
        form.add(createLabel("Tournament ID:")); form.add(pTournamentId);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btns.setOpaque(false);
        JButton add = new ThemedButton("Add");
        JButton upd = new ThemedButton("Update");
        JButton del = new ThemedButton("Delete");
        JButton refresh = new ThemedButton("Clear Form");
        JButton back = new ThemedButton("Back to Home");
        btns.add(add); btns.add(upd); btns.add(del); btns.add(refresh); btns.add(back);

        JPanel south = new JPanel(new BorderLayout(15, 15));
        south.setOpaque(false);
        south.add(form, BorderLayout.CENTER);
        south.add(btns, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            try {
                Player p = readPlayerForm(false);
                playerManager.add(p);
                refreshPlayerTable(playerManager.getAll());
                clearPlayerForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        upd.addActionListener(e -> {
            int row = tblPlayers.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to update.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
            try {
                Player p = readPlayerForm(true);
                int modelIndex = tblPlayers.convertRowIndexToModel(row);
                playerManager.update(modelIndex, p);
                refreshPlayerTable(playerManager.getAll());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        del.addActionListener(e -> {
            int row = tblPlayers.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to delete.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this player?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                int modelIndex = tblPlayers.convertRowIndexToModel(row);
                playerManager.delete(modelIndex);
                refreshPlayerTable(playerManager.getAll());
                clearPlayerForm();
            }
        });
        refresh.addActionListener(e -> clearPlayerForm());
        back.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        tblPlayers.getSelectionModel().addListSelectionListener(this::onPlayerRowSelect);
        return root;
    }

    private void onPlayerRowSelect(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        int row = tblPlayers.getSelectedRow();
        if (row >= 0) {
            int modelIndex = tblPlayers.convertRowIndexToModel(row);
            Player p = playerManager.getAll().get(modelIndex);
            pId.setText(String.valueOf(p.getId()));
            pFirst.setText(p.getFirstName());
            pLast.setText(p.getLastName());
            pGender.setSelectedItem(p.getGender().name());
            pDob.setText(DF.format(p.getDateOfBirth()));
            pCountry.setText(p.getCountry());
            pTournamentId.setText(String.valueOf(p.getTournamentId()));
            pId.setEditable(false);
        }
    }
    
    private Player readPlayerForm(boolean isUpdate) throws Exception {
        if (pFirst.getText().trim().isEmpty() || pLast.getText().trim().isEmpty() ||
            pDob.getText().trim().isEmpty() || pCountry.getText().trim().isEmpty() ||
            pTournamentId.getText().trim().isEmpty()) {
            throw new Exception("All fields must be filled.");
        }
        if (!isUpdate && pId.getText().trim().isEmpty()) {
            throw new Exception("ID must be provided for a new entry.");
        }
        try {
            int id = Integer.parseInt(pId.getText().trim());
            int tournamentId = Integer.parseInt(pTournamentId.getText().trim());
            if (tournamentManager.findById(tournamentId) == null) {
                throw new Exception("Tournament with ID " + tournamentId + " does not exist.");
            }
            String first = pFirst.getText().trim();
            String last = pLast.getText().trim();
            Player.Gender gender = Player.Gender.valueOf(((String)pGender.getSelectedItem()).toUpperCase());
            Date dob = DF.parse(pDob.getText().trim());
            String country = pCountry.getText().trim();
            return new Player(id, first, last, gender, dob, country, tournamentId);
        } catch (NumberFormatException nfe) {
            throw new Exception("ID and Tournament ID must be valid numbers.");
        } catch (ParseException pe) {
            throw new Exception("Date of Birth must be in dd/MM/yyyy format.");
        }
    }

    private void clearPlayerForm() {
        pId.setText(""); pId.setEditable(true);
        pFirst.setText(""); pLast.setText(""); pGender.setSelectedIndex(0);
        pDob.setText(""); pCountry.setText(""); pTournamentId.setText("");
        tblPlayers.clearSelection();
    }
    
    private void refreshPlayerTable(List<Player> players) {
        tmPlayers.setRowCount(0);
        for (Player p : players) {
            tmPlayers.addRow(new Object[]{ p.getId(), p.getFirstName(), p.getLastName(), p.getGender().name(), DF.format(p.getDateOfBirth()), p.getCountry(), p.getTournamentId() });
        }
    }
    
    private JPanel buildTypeTab() {
        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        root.setBackground(DARK_BG);

        tmTypes = new DefaultTableModel(new String[]{"Type ID", "Type Name"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTypes = new JTable(tmTypes);
        JScrollPane scrollPane = new JScrollPane(tblTypes);
        setupTable(tblTypes, scrollPane);
        root.add(scrollPane, BorderLayout.CENTER);
        
        JPanel form = new JPanel(new GridLayout(2, 2, 15, 15));
        form.setOpaque(false);
        ttId = new JTextField(); ttName = new JTextField();
        setupInput(ttId); setupInput(ttName);
        form.add(createLabel("Type ID:")); form.add(ttId);
        form.add(createLabel("Type Name:")); form.add(ttName);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btns.setOpaque(false);
        JButton add = new ThemedButton("Add");
        JButton upd = new ThemedButton("Update");
        JButton del = new ThemedButton("Delete");
        JButton refresh = new ThemedButton("Clear Form");
        JButton back = new ThemedButton("Back to Home");
        btns.add(add); btns.add(upd); btns.add(del); btns.add(refresh); btns.add(back);
        
        JPanel south = new JPanel(new BorderLayout(15, 15));
        south.setOpaque(false);
        south.add(form, BorderLayout.CENTER);
        south.add(btns, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            try {
                TournamentType t = readTypeForm();
                typeManager.add(t);
                refreshTypeTable();
                clearTypeForm();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
        upd.addActionListener(e -> {
            int row = tblTypes.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to update.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
            try {
                TournamentType t = readTypeForm();
                typeManager.update(row, t);
                refreshTypeTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
        del.addActionListener(e -> {
            int row = tblTypes.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to delete.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this type?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                typeManager.delete(row);
                refreshTypeTable();
                clearTypeForm();
            }
        });
        refresh.addActionListener(e -> clearTypeForm());
        back.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        tblTypes.getSelectionModel().addListSelectionListener(this::onTypeRowSelect);
        return root;
    }
    
    private void onTypeRowSelect(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        int row = tblTypes.getSelectedRow();
        if (row >= 0) {
            TournamentType t = typeManager.getAll().get(row);
            ttId.setText(String.valueOf(t.getTypeId()));
            ttName.setText(t.getTypeName());
            ttId.setEditable(false);
        }
    }

    private TournamentType readTypeForm() throws Exception {
        if (ttId.getText().trim().isEmpty() || ttName.getText().trim().isEmpty()) {
            throw new Exception("Both ID and Name fields must be filled.");
        }
        try {
            int id = Integer.parseInt(ttId.getText().trim());
            String name = ttName.getText().trim();
            return new TournamentType(id, name);
        } catch (NumberFormatException nfe) {
            throw new Exception("ID must be a valid number.");
        }
    }

    private void clearTypeForm() {
        ttId.setText(""); ttId.setEditable(true);
        ttName.setText("");
        tblTypes.clearSelection();
    }
    
    private void refreshTypeTable() {
        tmTypes.setRowCount(0);
        for (TournamentType t : typeManager.getAll()) {
            tmTypes.addRow(new Object[]{t.getTypeId(), t.getTypeName()});
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}

class ThemedIconButton extends JButton {
    public ThemedIconButton(String text, ImageIcon icon) {
        super(text, icon);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFont(new Font("Segoe UI", Font.BOLD, 18));
        setForeground(Color.WHITE);
        setBackground(new Color(70, 70, 70, 180));
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        setContentAreaFilled(false);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(100, 100, 100, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(70, 70, 70, 180));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        g2.dispose();
        super.paintComponent(g);
    }
}

class ThemedButton extends JButton {
    public ThemedButton(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setBackground(new Color(80, 80, 80));
        setForeground(Color.WHITE);
        setOpaque(true);
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(100, 100, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(80, 80, 80));
            }
        });
    }
}

class GradientPanel extends JPanel {
    private final Color color1;
    private final Color color2;
    public GradientPanel(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}