package courseJava;

// подключаем графические библиотеки
import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyEvent; // подключаем класс для обработки нажатий

// перечисление для определения типа игры
enum TypeOfGame {
    single,
    multiplayer,
    computerOnly
}

public class GamePanel extends JPanel {
    private Timer timer;                // таймер для управления работой потоков
    private Player player_1;            // игрок 1
    private Player player_2;            // игрок 2
    private Ball ball;                  // мяч
    private boolean inGame = false;     // в игре
    private boolean inMenu = true;      // в меню
    private boolean inPause = false;    // на паузе
    private Thread thread_player_1;     // поток игрока 1
    private Thread thread_player_2;     // поток игрока 2
    private Thread thread_ball;         // поток мяча

    // конструктор по умолчанию
    public GamePanel() {
        initializedVariables();
    }

    // свойство для установки состояния "в игре"
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    // свойство для установки состояния "на паузе"
    public void setInPause(boolean inPause) {
        this.inPause = inPause;
    }

    // свойство для вывода состояния "в игре"
    public synchronized boolean isInGame() {
        return inGame;
    }

    // свойство для вывода состояния "на паузе"
    public synchronized boolean isInPause() {
        return !inPause;
    }

    // свойство для установки типа игры
    private void setTypeOfGame(TypeOfGame typeOfGame) {
        switch (typeOfGame) {
            case single -> {
                player_2.setAIPlayer(true);
                player_1.setAIPlayer(false);
            }
            case multiplayer -> {
                player_1.setAIPlayer(false);
                player_2.setAIPlayer(false);
            }
            case computerOnly -> {
                player_1.setAIPlayer(true);
                player_2.setAIPlayer(true);
            }
        }
    }

    // метод для инициализации игры
    private void initializedVariables() {
        player_1 = new Player(this);
        player_2 = new Player(this);
        ball = new Ball(player_1, player_2, this);
        thread_player_1 = new Thread(player_1);
        thread_player_2 = new Thread(player_2);
        thread_ball = new Thread(ball);
        addKeyListener(new GameEventListener(this));
        setFocusable(true);
        this.timer = new Timer(Constants.GAME_SPEED, new GameLoop(this));
        this.timer.start();
    }

    // метод для отрисовки игроков
    private void drawPlayer(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int)player_1.getX(), (int)player_1.getY(), Constants.PLAYER_WIDTH, Constants.PLAYER_HIGHT);
        g.setColor(Color.RED);
        g.fillRect((int)player_2.getX(), (int)player_2.getY(), Constants.PLAYER_WIDTH, Constants.PLAYER_HIGHT);
    }

    // метод для отрисовки сетки
    private void drawGUI(Graphics g) {
        g.setColor(Color.GREEN);
        int y = 0;
        for (int i = 0; i < 21; i++) {
            g.fillRect(Constants.BOARD_WIDTH / 2 + 9, y + 15, 2, 10);
            y += 20;
        }
    }

    // метод для отрисовки мяча
    private void drawBall(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int)ball.getX(), (int)ball.getY(), Constants.BALL_WIDTH, Constants.BALL_HIGHT);
    }

    // метод для отрисовки табло
    private void drawScore(Graphics g) {
        g.setFont(g.getFont().deriveFont(40f));
        g.setColor(Color.BLUE);
        g.drawString(Integer.toString(Score.getScore_1()), Constants.BOARD_WIDTH / 2 + 50, 30);
        g.setColor(Color.RED);
        g.drawString(Integer.toString(Score.getScore_2()), Constants.BOARD_WIDTH / 2 - 50, 30);
    }

    // переопределенный метод для отрисовки игрового поля
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        doDrawing(g);
    }

    // метод для отрисовки меню
    private void drawMenu() {
        System.out.println("draw menu");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        JPanel NamePanel = new JPanel();
        NamePanel.setBackground(Color.BLACK);
        JLabel NameJLabel = new JLabel("Very good pong");
        NameJLabel.setFont(new Font("Calibri", Font.PLAIN, 60));
        NameJLabel.setForeground(Color.BLUE);
        NamePanel.add(NameJLabel);
        NameJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(NamePanel);

        JPanel ButtonPanel = new JPanel();

        ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
        JButton OnePlrBtn = new JButton("Single player");
        JButton TwoPlrBtn = new JButton("Multiplayer");
        JButton OnlyPcBtn = new JButton("Computer game");
        OnePlrBtn.setOpaque(false);
        OnePlrBtn.setContentAreaFilled(false);
        OnePlrBtn.setBorderPainted(false);
        OnePlrBtn.setFocusPainted(false);
        OnePlrBtn.setFont(new Font("Calibri", Font.PLAIN, 30));
        OnePlrBtn.setForeground(Color.GREEN);

        TwoPlrBtn.setOpaque(false);
        TwoPlrBtn.setContentAreaFilled(false);
        TwoPlrBtn.setBorderPainted(false);
        TwoPlrBtn.setFocusPainted(false);
        TwoPlrBtn.setFont(new Font("Calibri", Font.PLAIN, 30));
        TwoPlrBtn.setForeground(Color.YELLOW);

        OnlyPcBtn.setOpaque(false);
        OnlyPcBtn.setContentAreaFilled(false);
        OnlyPcBtn.setBorderPainted(false);
        OnlyPcBtn.setFocusPainted(false);
        OnlyPcBtn.setFont(new Font("Calibri", Font.PLAIN, 30));
        OnlyPcBtn.setForeground(Color.CYAN);

        ButtonPanel.add(OnePlrBtn);
        ButtonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ButtonPanel.add(TwoPlrBtn);
        ButtonPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        ButtonPanel.add(OnlyPcBtn);
        ButtonPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        ButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ButtonPanel.setBackground(Color.BLACK);

        add(ButtonPanel);

        OnePlrBtn.addActionListener(e -> {
            setTypeOfGame(TypeOfGame.single);
            inGame = true;
            thread_player_1.start();
            thread_player_2.start();
            thread_ball.start();
            timer.start();
            inMenu = false;
        });

        TwoPlrBtn.addActionListener(e -> {
            setTypeOfGame(TypeOfGame.multiplayer);
            inGame = true;
            thread_player_1.start();
            thread_player_2.start();
            thread_ball.start();
            timer.start();
            inMenu = false;
        });

        OnlyPcBtn.addActionListener(e -> {
            setTypeOfGame(TypeOfGame.computerOnly);
            inGame = true;
            thread_player_1.start();
            thread_player_2.start();
            thread_ball.start();
            timer.start();
            inMenu = false;
        });
    }

    // метод для отрисовки всего
    private void doDrawing(Graphics g) {
        if (inGame && !inPause) {
            removeAll();
            drawPlayer(g);
            drawBall(g);
            drawGUI(g);
            drawScore(g);
        }
        else if (timer.isRunning()) {
            drawMenu();
            timer.stop();
            System.out.println("timer stop");
        }
        else if (!timer.isRunning()) {
            drawMenu();
            System.out.println("timer stop 2");
        }
        Toolkit.getDefaultToolkit().sync();
    }

    // метод алгоритм работы цикла
    public void doOneLoop() {
        repaint();
    }

    // метод при нажатии клавиши
    public void keyPressed(KeyEvent e) {
        this.player_1.keyPressed(e);
        this.player_2.keyPressed(e);
        this.ball.keyPressed(e);
        checkMenuButton(e);
        System.out.println("Pressed button");
    }

    // метод при отпускании клавиши
    public void keyReleased(KeyEvent e) {
        this.player_1.keyReleased(e);
        this.player_2.keyReleased(e);
    }

    // метод для обработки нажатий кнопок меню
    private void checkMenuButton(KeyEvent event) {
        int key = event.getKeyCode();

        if (key == Constants.ESCAPE_BUTTON && !inMenu) {
            System.out.println("ESCAPE");
            if (!inPause) {
                timer.stop();
                setInPause(true);
            }
            else {
                timer.start();
                setInPause(false);
            }
        }
    }
}
