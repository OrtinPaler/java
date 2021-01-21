package courseJava;

import java.awt.event.ActionEvent;      // подключаем класс для обработки событий
import java.awt.event.ActionListener;   // подключаем класс для получения событий действия

public class GameLoop implements ActionListener {
    private final GamePanel game_panel; // игровое поле

    // конструктор с параметром game_panel (игровое поле)
    public GameLoop(GamePanel game_panel) {
        this.game_panel = game_panel;
    }

    // переопределенный метод для определения, что произошло значимое действие
    @Override
    public void actionPerformed(ActionEvent e) {
        this.game_panel.doOneLoop();
    }
}
