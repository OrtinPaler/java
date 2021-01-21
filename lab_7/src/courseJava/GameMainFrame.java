package courseJava;

// подключаем графические библиотеки
import javax.swing.*;
import java.awt.*;

import java.awt.event.WindowAdapter;    // подключаем класс для получения оконных событий
import java.awt.event.WindowEvent;      // подлючаем класс для управления оконными событиями

public class GameMainFrame extends JFrame {
    // конструктор по умолчанию
    public GameMainFrame() {
        InitializeLayout();
    }

    // метод для инициализации игрового поля
    private void InitializeLayout() {
        GamePanel game_panel = new GamePanel();
        add(game_panel);
        setTitle(Constants.TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HIGHT));
        setLocationRelativeTo(null);    // устанавливаем окно приложения по центру экрана
        setResizable(false);            // запрещаем изменять размер окна приложения
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game_panel.setInGame(false);
                super.windowClosing(e);
            }
        });
    }
}
