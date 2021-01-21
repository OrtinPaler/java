package courseJava;

import java.awt.event.KeyEvent; // подключаем класс для обработки нажатий

public class Constants {
    // название игры
    public static final String TITLE = "Very good pong";
    // размер игрового поля
    public static final int BOARD_WIDTH = 720;
    public static final int BOARD_HIGHT = 480;
    // размер ракетки
    public static final int PLAYER_HIGHT = 50;
    public static final int PLAYER_WIDTH = 10;
    // размер мяча
    public static final int BALL_HIGHT = 10;
    public static final int BALL_WIDTH = 10;
    // скорость игры в миллисекундах
    public static final int GAME_SPEED = 10;
    // управление игрока 1
    public static final int FIRST_PLAYER_UP = KeyEvent.VK_UP;
    public static final int FIRST_PLAYER_DOWN = KeyEvent.VK_DOWN;
    // управление игрока 2
    public static final double SECOND_PLAYER_UP = KeyEvent.VK_W;
    public static final double SECOND_PLAYER_DOWN = KeyEvent.VK_S;
    // управление игрой
    public static final int ESCAPE_BUTTON = KeyEvent.VK_ESCAPE;
    public static final int RESTART_GAME_BUTTON = KeyEvent.VK_R;
    // стартовая позиция игрока 1
    public static final int START_COORDINATE_X_1PLAYER = 660;
    public static final int START_COORDINATE_Y_1PLAYER = 200;
    // стартовая позиция игрока 2
    public static final int START_COORDINATE_X_2PLAYER = 30;
    public static final int START_COORDINATE_Y_2PLAYER = 200;
    // максимальная скорость мяча
    public static final double BALL_SPEED_X = 6;
    public static final double BALL_SPEED_Y = 6;
    // скорость игроков
    public static final double PLAYER_SPEED = 7;
    // максимальное кол-во очков
    public static final int MAX_SCORE = 11;
}
