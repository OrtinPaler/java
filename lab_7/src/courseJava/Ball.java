package courseJava;

import javax.swing.*;           // подключаем класс для работы с графикой
import java.awt.event.KeyEvent; // подключаем класс для обработки нажатий
import java.util.Random;        // подключаем класс для использования случайных чисел

public class Ball extends GameObject implements Runnable {
    Player player_1;        // игрок 1
    Player player_2;        // игрок 2
    GamePanel game_panel;   // игровая панель

    // конструктор с параметрами player_1 (игрок 1), player_2 (игрок 2) и game_panel (игровая панель)
    public Ball(Player player_1, Player player_2, GamePanel game_panel) {
        this.player_1 = player_1;
        this.player_2 = player_2;
        this.game_panel = game_panel;
        initialize(game_panel);
    }

    // метод для инициализации партии
    private void initialize(GamePanel game_panel) {
        restart(game_panel);
    }

    // переопределенный метод для управления ракетками
    @Override
    public void move() {
        sendCoordinatesToPlayers();
        checkCollision();
        setX(getX() + getxSpeed());
        setY(getY() + getySpeed());
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

    // переопределенный метод для начала партии сначала
    @Override
    public void restart(GamePanel game_panel) {
        Random random = new Random();
        if (getX() <= 0)
            setXSpeed(-Constants.BALL_SPEED_X);
        else if (getX() >= Constants.BOARD_WIDTH)
            setXSpeed(Constants.BALL_SPEED_X);
        else
            setXSpeed(Constants.BALL_SPEED_X);
        if (Score.checkEndGame()) {
            String result;
            if (Score.getScore_1() > Score.getScore_2())
                result = "Победил игрок 1";
            else
                result = "Победил игрок 2";

            JOptionPane.showMessageDialog(game_panel, result);
            setXSpeed(Constants.BALL_SPEED_X);
            Score.clearScore();
        }
        int start_x = Constants.BOARD_WIDTH / 2;
        setX(start_x - Constants.PLAYER_WIDTH);
        setY(random.nextInt((300 - 50) + 1) + 50);
        setYSpeed(Constants.BALL_SPEED_Y);
        player_2.restart(game_panel);
        player_1.restart(game_panel);
    }

    // метод для обработки удара
    private double[] hitFactor(Player player) {
        double Value = (getY() - player.getY()) / Constants.PLAYER_HIGHT;
        double[] speeds = new double[]{getxSpeed(), getySpeed()};
        System.out.println("hitFactor = " + Value);

        if (Value == 1) {
            System.out.println("hitFactor 0");
            speeds[1] = 0;
        }
        if (Value < 0.5 && Value >= 0) {
            System.out.println("-hitFactor45");
            speeds[0] = -speeds[0];
            speeds[1] = -Constants.BALL_SPEED_Y;
            if (player.getCurrentPlayer() == 1) {
                setX(getX() - Math.abs(0.1));
                System.out.println("1pl");
            }
            else if (player.getCurrentPlayer() == 2) {
                setX(getX() + Math.abs(0.1));
                System.out.println("2pl");
            }
        }
        if (Value >= 0.5 && Value <= 1) {
            System.out.println("hitFactor 45");
            speeds[0] = -speeds[0];
            speeds[1] = Constants.BALL_SPEED_Y;

            if (player.getCurrentPlayer() == 1) {
                setX(getX() - Math.abs(0.1));
                System.out.println("1pl");
            }
            else if (player.getCurrentPlayer() == 2) {
                setX(getX() + Math.abs(0.1));
                System.out.println("2pl");
            }
        }
        return speeds;
    }

    // метод для обработки коллизий
    private void checkCollision() {
        if (getX() <= 0) {
            player_1.restart(game_panel);
            player_2.restart(game_panel);
            Score.setScore_1(Score.getScore_1() + 1);
            restart(game_panel);
        }
        if (getX() >= Constants.BOARD_WIDTH) {
            player_1.restart(game_panel);
            player_2.restart(game_panel);
            Score.setScore_2(Score.getScore_2() + 1);
            restart(game_panel);
        }
        else {
            if (getY() <= 2) {
                setYSpeed(-getySpeed());
            }
            if (getY() >= (Constants.BOARD_HIGHT - Constants.BALL_HIGHT - 40)) {
                setYSpeed(-getySpeed());
                setY(Constants.BOARD_HIGHT - Constants.BALL_HIGHT - 41);
            }
            if ((getX() <= player_2.getX() + Constants.PLAYER_WIDTH) && (getX() >=
                    player_2.getX() - Constants.PLAYER_WIDTH)) {
                double[] speeds = hitFactor(player_2);
                setXSpeed(speeds[0]);
                setYSpeed(speeds[1]);
            }
            if ((getX() >= player_1.getX() - Constants.BALL_WIDTH) && (getX() <=
                    player_1.getX() - Constants.BALL_WIDTH + Constants.PLAYER_WIDTH)) {
                double[] speeds = hitFactor(player_1);
                setXSpeed(speeds[0]);
                setYSpeed(speeds[1]);
            }
        }
    }

    // метод для начала новой игры
    private void newGame() {
        Random random = new Random();
        setXSpeed(Constants.BALL_SPEED_X);
        Score.clearScore();
        int start_x = Constants.BOARD_WIDTH / 2;
        setX(start_x - Constants.PLAYER_WIDTH);
        setY(random.nextInt((300 - 50) + 1) + 50);
        setYSpeed(Constants.BALL_SPEED_Y);
    }

    // метод для отправки координат мяча игрокам
    private void sendCoordinatesToPlayers() {
        player_2.setBallX(getX());
        player_2.setBallY(getY());

        player_1.setBallX(getX());
        player_1.setBallY(getY());
    }

    // метод при нажатии клавиши
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == Constants.RESTART_GAME_BUTTON) {
            newGame();
            restart(game_panel);
        }
    }
}
