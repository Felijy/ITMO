import ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Запускаем GUI в потоке событий
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null); // Центрируем окно
            frame.setVisible(true);
        });
    }
}