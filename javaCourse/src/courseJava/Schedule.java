package courseJava;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class Point {
    public final double point_x;
    public final double point_y;

    Point(double point_x, double point_y) {
        this.point_x = point_x;
        this.point_y = point_y;
    }
}

public class Schedule {

    public static void create_schedule(ArrayList<Point> arrayList_addTotal_points,
                                       ArrayList<Point> arrayList_removeTotal_points,
                                       ArrayList<Point> arrayList_addMedian_points,
                                       ArrayList<Point> arrayList_removeMedian_points,
                                       ArrayList<Point> linkedList_addTotal_points,
                                       ArrayList<Point> linkedList_removeTotal_points,
                                       ArrayList<Point> linkedList_addMedian_points,
                                       ArrayList<Point> linkedList_removeMedian_points)
    {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();                            // создаем новый фрейм
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);           // устанавливаем окно во весь экран
            frame.setTitle("График");                               // устанавливаем заголовок окна
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // устнавливаем значение по умолчанию при закрытии окна
            frame.setResizable(false);                              // запрещаем изменять размер окна
            frame.setVisible(true);                                 // отображаем окно

            JPanel p = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    Line2D axisX = new Line2D.Double(50, 810, 1533, 810);
                    Line2D axisY = new Line2D.Double(50, 10, 50, 810);
                    Rectangle backgrounds = new Rectangle(0, 0, 1535, 840);

                    g2.setFont(new Font("Calibri", Font.BOLD, 12));
                    g2.setStroke(new BasicStroke(1.5f));

                    // устанавливаем фон
                    g2.setColor(Color.WHITE);
                    g2.fill(backgrounds);

                    // четрим оси координат
                    g2.setColor(Color.BLACK);
                    g2.draw(axisX); // чертим ось OX
                    g2.draw(axisY); // чертим ось OY

                    double x = axisY.getX1();
                    for (int i = 0; i < 5; i++) {
                        x += 282.0;
                        Line2D label = new Line2D.Double(x, axisX.getY1() - 5, x, axisX.getY1() + 5);
                        g2.draw(label);
                        g2.drawString(Integer.toString((int)Math.pow(10, (i + 1))), (int)x - 5 * (i + 1), (int)axisX.getY1() + 20);
                    }
                    g2.drawString("X, шт.", backgrounds.width - 30, backgrounds.height - 10);

                    int vertexX_1 = (int)axisY.getX1();
                    int vertexX_2 = vertexX_1 + 8;
                    int vertexX_3 = vertexX_1 - 8;

                    int vertexY_1 = (int)axisY.getY1() - 10;
                    int vertexY_2 = (int)axisY.getY1(); // vertexY_3 = vertexY_2

                    Polygon poly = new Polygon(new int[]{vertexX_1, vertexX_2, vertexX_3},
                            new int[]{vertexY_1, vertexY_2, vertexY_2}, 3);
                    g2.fill(poly);

                    double y = axisX.getY1();
                    for (int i = 0; i < 39; i++) {
                        y -= 20.0;
                        Line2D label = new Line2D.Double(axisY.getX1() - 5, y, axisY.getX1() + 5, y);
                        g2.draw(label);
                        g2.drawString(Integer.toString(274 + (274 * i)), (int)axisY.getX1() - 35, (int)y + 5);
                    }
                    g2.drawString("Y, 10 нс", backgrounds.x + 5, backgrounds.y + 12);
                    g2.setFont(new Font("Calibri", Font.BOLD, 8));
                    g2.drawString("6", backgrounds.x + 27, backgrounds.y + 5);
                    g2.setFont(new Font("Calibri", Font.BOLD, 12));

                    vertexX_1 = (int)axisX.getX2() - 10; // vertexX_1 = vertexX_3
                    vertexX_2 = (int)axisX.getX2() + 2;

                    vertexY_1 = (int)axisX.getY1() - 7;
                    vertexY_2 = (int)axisX.getY1();
                    int vertexY_3 = (int)axisX.getY1() + 7;

                    poly = new Polygon(new int[]{vertexX_1, vertexX_2, vertexX_1},
                            new int[]{vertexY_1, vertexY_2, vertexY_3}, 3);
                    g2.fill(poly);

                    double array_x_start = axisX.getX1();
                    double array_y_start =  axisX.getY1();
                    double array_x_finish;
                    double array_y_finish;

                    double link_x_start = axisX.getX1();
                    double link_y_start =  axisX.getY1();
                    double link_x_finish;
                    double link_y_finish;

                    for (int i = 0; i < 5; i++) {
                        g2.setColor(Color.RED);
                        array_x_finish = axisX.getX1() + arrayList_addTotal_points.get(i).point_x * 282;
                        array_y_finish = axisY.getY2() - arrayList_addTotal_points.get(i).point_y / 1000000;
                        g2.draw(new Line2D.Double(array_x_start, array_y_start, array_x_finish, array_y_finish));
                        array_x_start = array_x_finish;
                        array_y_start = array_y_finish;

                        g2.setColor(Color.BLUE);
                        link_x_finish = axisX.getX1() + linkedList_addTotal_points.get(i).point_x * 282;
                        link_y_finish = axisY.getY2() - linkedList_addTotal_points.get(i).point_y / 1000000;
                        g2.draw(new Line2D.Double(link_x_start, link_y_start, link_x_finish, link_y_finish));
                        link_x_start = link_x_finish;
                        link_y_start = link_y_finish;
                    }
                    g2.setColor(Color.BLACK);
                }
            };
            frame.getContentPane().add(p);
        });
    }
}
