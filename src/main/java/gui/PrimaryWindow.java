package gui;

import javax.swing.*;
import java.awt.*;

public class PrimaryWindow {

    public void show() {
        SwingUtilities.invokeLater(() -> {
            // 1. Create the main window frame
            JFrame frame = new JFrame("Project jProjectiles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null); // Centre

            JLabel label = new JLabel("Available Operations", SwingConstants.CENTER);
            JPanel buttonPanel = new JPanel(new FlowLayout());

            JButton button1 = new JButton("Option: SUVAT 1D");
            JButton button2 = new JButton("Option: Projectile 2D");
            JButton button3 = new JButton("Reset");

            button1.addActionListener(e -> label.setText("You clicked: SUVAT 1D"));
            button2.addActionListener(e -> label.setText("You clicked: Projectile 2D"));
            button3.addActionListener(e -> label.setText("Available Operations"));

            buttonPanel.add(button1);
            buttonPanel.add(button2);
            buttonPanel.add(button3);

            frame.setLayout(new BorderLayout());
            frame.add(label, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.setVisible(true);


        });
    }
}
