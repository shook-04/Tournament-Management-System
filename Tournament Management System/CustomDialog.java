import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomDialog extends JDialog {

    public enum Type {
        INFO, WARNING, ERROR
    }

    public CustomDialog(Frame owner, String title, String message, Type type) {
        super(owner, title, true);
        
        setBackground(Color.BLACK);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.BLACK);
        
        JLabel iconLabel = new JLabel();
        switch(type) {
            case INFO:
                iconLabel.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
                break;
            case WARNING:
                iconLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
                break;
            case ERROR:
                iconLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
                break;
        }
        mainPanel.add(iconLabel, BorderLayout.WEST);
        
        JTextArea messageArea = new JTextArea(message);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        messageArea.setForeground(Color.WHITE);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(messageArea, BorderLayout.CENTER);

        JButton okButton = new ThemedButton("OK");
        okButton.setPreferredSize(new Dimension(80, 30));
        okButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
}
