package courseJava;

import java.awt.*;  // подключаем класс для отрисовки приложения

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(GameMainFrame::new);
    }
}
