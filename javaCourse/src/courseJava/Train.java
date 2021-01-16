package courseJava;

import java.io.Serializable;  // подключаем класс для работы с сериализацией
import java.util.ArrayList;   // подключаем класс для работы со списками
import java.util.Scanner;     // подключаем класс для обработки ввода

public class Train implements Serializable {
    private final String number_train;              // номер поезда
    private ArrayList<RailwayCarriage> carriages;   // массив вагонов

    // конструктор по умолчанию
    Train() {
        Scanner in_0 = new Scanner(System.in);
        System.out.print("Введите номер поезда: ");
        this.number_train = in_0.nextLine();

        this.carriages = new ArrayList<>();
    }

    // свойство для установки вагона в поезд
    public void set_carriages(ArrayList<RailwayCarriage> carriages) { this.carriages = carriages; }

    // свойство для вывода номера поезда
    public String get_number_train() { return this.number_train; }

    // свойство для вывода массива вагонов
    public ArrayList<RailwayCarriage> get_carriages() { return this.carriages; }

    // метод для вывода информации о поезде
    public void train_info() {
        System.out.print("\n  Количество вагонов: " + (this.carriages.size()) + "\n");
        for (RailwayCarriage carriage : this.carriages)
            carriage.rail_car_info();
        System.out.print("\n");
    }
}
