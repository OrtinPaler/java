package courseJava;

import java.awt.event.KeyAdapter;   // подключаем класс для того, чтобы получить события клавиатуры
import java.awt.event.KeyEvent;     // подключаем класс для обработки нажатий

public class GameEventListener extends KeyAdapter {
    private final GamePanel board;  // игровое поле

    // конструктор с параметром board (игровое поле)
    public GameEventListener(GamePanel board){
        this.board = board;
    }

    // переопределенный метод при нажатии клавиши
    @Override
    public void keyPressed(KeyEvent e) {
        this.board.keyPressed(e);
    }

    // переопределенный метод при отпускании клавиши
    @Override
    public void keyReleased(KeyEvent e) {
        this.board.keyReleased(e);
    }
}
