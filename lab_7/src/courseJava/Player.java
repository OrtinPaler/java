package courseJava;

import java.awt.event.KeyEvent; // подключаем класс для обработки нажатий

public class Player extends GameObject implements Runnable {
    private static int count = 0;       // номер игрока
    private int current_player = 0;     // текущий игрок
    private double up_button = 0;          // кнопка "вверх"
    private double down_button = 0;         // кнопка "вниз"
    private boolean ai_player = false;   //

    private double ball_x = 0;                 // координата мяча по оси Ox
    private double ball_y = 0;                 // координата мяча по оси Oy
    private final GamePanel game_panel;     // игровое поле

    // конструктор с параметром game_panel (игровое поле)
    public Player(GamePanel game_panel) {
        this.game_panel = game_panel;
        initialize();
    }

    // свойство для установки координат мяча по оси Ox
    public void setBallX(double ball_x) {
        this.ball_x = ball_x;
    }

    // свойство для установки координат мяча по оси Oy
    public void setBallY(double ball_y) {
        this.ball_y = ball_y;
    }

    // свойство для установки исскуственного интеллекта
    public void setAIPlayer(boolean ai_player) {
        this.ai_player = ai_player;
    }

    // свойство для вывода текущего игрока
    public int getCurrentPlayer() {
        return current_player;
    }

    // переопределенный метод для управления ракетками
    @Override
    public void move() {
        // передаем управление исскуственному интеллекту
        if (ai_player)
            AIControl();

        // устанавливаем новые координаты ракетки
        setY(getY() + dy);

        // устанавливаем границы движения ракетки
        if (getY() > Constants.BOARD_HIGHT - Constants.PLAYER_HIGHT - 40)
            setY(Constants.BOARD_HIGHT - Constants.PLAYER_HIGHT - 40);
        else if (getY() < 0)
            setY(0);
    }

    // переопределенный метод для начала партии сначала
    @Override
    public void restart(GamePanel game_panel) {
        System.out.println("player restart " + count);
        if (current_player == 1) {
            System.out.println("player1 restart");
            setX(Constants.START_COORDINATE_X_1PLAYER);
            setY(Constants.START_COORDINATE_Y_1PLAYER);
        }
        else if (current_player == 2) {
            System.out.println("player2 restart");
            setX(Constants.START_COORDINATE_X_2PLAYER);
            setY(Constants.START_COORDINATE_Y_2PLAYER);
        }
        dy = 0;
    }

    // переопределенный метод для работы окна приложения
    @Override
    public void run() {
        while (game_panel.isInGame()) {
            while (game_panel.isInPause()) {
                try {
                    move();
                    Thread.sleep(Constants.GAME_SPEED);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // метод для инициализации партии
    private void initialize() {
        int start_x = 0;
        int start_y = 0;

        System.out.println(count);

        if (count == 0) {
            up_button = Constants.FIRST_PLAYER_UP;
            down_button = Constants.FIRST_PLAYER_DOWN;
            start_x = Constants.START_COORDINATE_X_1PLAYER;
            start_y = Constants.START_COORDINATE_Y_1PLAYER;
            current_player = 1;
        }
        else if (count == 1) {
            up_button = Constants.SECOND_PLAYER_UP;
            down_button = Constants.SECOND_PLAYER_DOWN;
            start_x = Constants.START_COORDINATE_X_2PLAYER;
            start_y = Constants.START_COORDINATE_Y_2PLAYER;
            current_player = 2;
        }
        count++;

        setYSpeed(Constants.PLAYER_SPEED);
        setX(start_x);
        setY(start_y);
    }

    // метод для работы исскуственного интеллекта
    private void AIControl() {
        if ((current_player == 1) && (ball_x > Constants.BOARD_WIDTH / 2.0)) {
            if ((getY() + Constants.PLAYER_HIGHT / 2.0) < ball_y)
                dy = getySpeed();
            else
                dy = -getySpeed();
        }
        else if ((current_player == 2) && (ball_x < Constants.BOARD_WIDTH / 2.0)) {
            if ((getY() + Constants.PLAYER_HIGHT / 2.0) < ball_y)
                dy = getySpeed();
            else
                dy = -getySpeed();
        }
        else
            dy = 0;
    }

    // метод при нажатии клавиши
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == up_button)
            dy = -getySpeed();
        else if (key == down_button)
            dy = getySpeed();
    }

    // метод при отпускании клавиши
    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == up_button)
            dy = 0;
        else if (key == down_button)
            dy = 0;
    }
}
