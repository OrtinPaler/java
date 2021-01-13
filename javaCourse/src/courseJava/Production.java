package courseJava;

import java.io.Serializable;

// типы продукции
enum ProductionType {
    undefined,      // тип неопределен
    containers,     // контейнеры
    liquids,        // жидкости
    bulk_cargo,     // сыпучие грузы
    cars            // автомобили
}

public class Production implements Serializable {
    private String name_product;            // название товара
    private ProductionType type_production; // тип продукции
    private CarriageType required_type_car; // необходимый тип вагона

    // конструктор по умолчанию
    Production() {
        this.name_product = "Undefined";
        this.type_production = ProductionType.undefined;
        this.required_type_car = CarriageType.undefined;
    }

    // свойство для установки имени продукта
    public void set_name_product(String name_product) { this.name_product = name_product; }

    // свойство для установки типа продукции и соответствующего ему типа вагона
    public void set_type_production(ProductionType type_production) {
        this.type_production = type_production;

        // в зависимости от типа продукции устнавливается значение на тип вагонов
        if (type_production == ProductionType.containers)
            this.required_type_car = CarriageType.platform_for_containers;
        else if (type_production == ProductionType.liquids)
            this.required_type_car = CarriageType.tanks_for_liquids;
        else if (type_production == ProductionType.bulk_cargo)
            this.required_type_car = CarriageType.for_bulk_cargo;
        else if (type_production == ProductionType.cars)
            this.required_type_car = CarriageType.car_platform;
        else
            this.required_type_car = CarriageType.undefined;
    }

    // свойство для вывода названия товара
    public String get_name_product() { return this.name_product; }

    // свойство для вывода необходимого типа вагона
    public CarriageType get_required_type() { return this.required_type_car; }

    // метод для вывода информации о продукии
    public void production_info() {
        System.out.println("\n    Название продукции: " + this.name_product
                + "\n    Тип продукции: " + this.type_production
                + "\n    Необходимый тип вагона: " + this.required_type_car);
    }
}
