package gui;

import javax.swing.*;

public class PrimaryWindow {

    public void show() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Project jProjectiles");


            frame.setSize(400, 300);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JLabel label = new JLabel("Hello, Swing World!", JLabel.CENTER);
                    frame.add(label);

                    JButton button = new JButton("Click Me!");
                    frame.add(button);

                    frame.setVisible(true);

                });

    }
}
