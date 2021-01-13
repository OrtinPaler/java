package courseJava;
import courseJava.Production;

// типы вагонов
enum CarriageType {

    undefined,                  // тип неопределен
    platform_for_containers,    // платформа для контейнеров
    tanks_for_liquids,          // цистерны для жидкостей
    for_bulk_cargo,             // вагоны для сыпучих грузов
    car_platform                // платформа для автомобилей
}

public class RailwayCarriage {

    private int number_carriage;        // номер вагона
    private CarriageType type_carriage; // тип груза вагона
    private Production product;         // товар, который загружен в вагон

    // конструктор по умолчанию
    public RailwayCarriage() {

        this.number_carriage = 0;
        this.type_carriage = CarriageType.undefined;
    }

    // конструктор с параметром type_carriage (тип вагона)
    public RailwayCarriage(CarriageType type_carriage) {

        this.number_carriage = 0;
        this.type_carriage = type_carriage;
    }

    // свойство для установки номера вагона
    public void set_num_car(int number_carriage) {

        this.number_carriage = number_carriage;
    }

    // свойство для установки типа вагона
    public void set_type_car(CarriageType type_carriage) {

        this.type_carriage = type_carriage;
    }

    // свойство для установки продукта в вагон
    public void set_product(Production product) {

        this.product = product;
    }

    // свойство для вывода номера вагона
    public int get_num_car() {

        return this.number_carriage;
    }

    // свойство для вывода типа вагона
    public CarriageType get_type_car() {

        return this.type_carriage;
    }

    // свойство для вывода продукта, перевозимого в вагоне
    public Production get_product() {

        return this.product;
    }

    // метод для вывода информации о вагоне
    public void rail_car_info() {

        System.out.println("\nНомер вагона: " + this.number_carriage + "\nТип груза: " + this.type_carriage + "\n");
    }
}
