import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThemedButton extends JButton {

    private Color normalColor = new Color(50, 50, 50);
    private Color hoverColor = new Color(80, 80, 80);
    private Color pressedColor = new Color(30, 30, 30);

    public ThemedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(normalColor);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        setFont(new Font("Arial", Font.BOLD, 14));
        setPreferredSize(new Dimension(150, 40));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }
}
