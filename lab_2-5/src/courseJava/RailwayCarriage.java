package courseJava;

import java.io.Serializable;  // подключаем класс для работы с сериализацией

// перечисление для типов вагонов
enum CarriageType {
    undefined,                  // тип неопределен
    platform_for_containers,    // платформа для контейнеров
    tanks_for_liquids,          // цистерны для жидкостей
    for_bulk_cargo,             // вагоны для сыпучих грузов
    car_platform                // платформа для автомобилей
}

public class RailwayCarriage implements Serializable {
    private int number_carriage;        // номер вагона
    private CarriageType type_carriage; // тип груза вагона
    private Production product;         // товар, который загружен в вагон
    private boolean full;               // статус вагона

    // конструктор по умолчанию
    public RailwayCarriage() {
        this.number_carriage = 0;
        this.type_carriage = CarriageType.undefined;
        this.full = false;
    }

    // свойство для установки типа вагона
    public void set_type_car(CarriageType type_carriage) { this.type_carriage = type_carriage; }

    // свойство для установки номера вагона
    public void set_number_carriage(int number) { this.number_carriage = number; }

    // свойство для установки товаров в вагон
    public void set_product(Production product) { this.product = product; }

    // свойство для установки статуса вагона
    public void set_full(boolean value) { this.full = value; }

    // свойство для вывода номера вагона
    public int get_number_carriage() { return this.number_carriage; }

    // свойство для вывода типа груза вагона
    public CarriageType get_type_carriage() { return this.type_carriage; }

    // свойство для вывода статуса вагона
    public boolean get_full() { return !this.full; }

    // метод для вывода информации о вагоне
    public void rail_car_info() {
        System.out.println("    Номер вагона: " + this.number_carriage
                + "\n      Тип груза: " + this.type_carriage
                + "\n      Статус вагона: " + this.full);
    }
}
