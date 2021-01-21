package courseJava;

// абстрактный класс для описания игровых объектов
public abstract class GameObject {
    protected double x = 0;        // координата x
    protected double y = 0;        // координата y
    protected double dy = 0;       // измение координаты y
    protected double xSpeed = 0;   // скорость по оси Ox
    protected double ySpeed = 0;   // скорость по оси Oy

    // свойство для установки значения x (выполняется одним потоком)
    public synchronized void setX(double x) {
        this.x = x;
    }

    // свойство для установки значения y (выполняется одним потоком)
    public synchronized void setY(double y) {
        this.y = y;
    }

    // свойство для установки скорости по оси Ox
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    // свойство для установки скорости по оси Oy
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    // свойство для вывода значения x (выполняется одним потоком)
    public synchronized double getX() {
        return x;
    }

    // свойство для вывода значения y (выполняется одним потоком)
    public synchronized double getY() {
        return y;
    }

    // свойство для вывода скорости по оси Ox
    public double getxSpeed() {
        return xSpeed;
    }

    // свойство для вывода скорости по оси Oy
    public double getySpeed() {
        return ySpeed;
    }

    // абстрактный метод для управления ракетками
    public abstract void move();

    // абстрактный метод для начала партии сначала
    public abstract void restart(GamePanel game_panel);
}
