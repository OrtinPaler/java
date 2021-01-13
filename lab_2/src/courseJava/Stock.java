package courseJava;
import courseJava.Production;

public class Stock {

    private int quantity;           // кол-во товара на складе
    private Production[] products;  // массив продукции

    // конструктор с параметром quantity (вместимость)
    Stock(int quantity) {

        this.quantity = quantity;
        this.products = new Production[this.quantity];
    }

    // свойство для установки вместимости склада
    public void set_quantity(int quantity) {

        this.quantity = quantity;
    }

    // свойство для установки продукта
    public void set_product(Production product, int number) {

        this.products[number] = product;
    }

    // свойство для установки продуктов в null (иными словами выгрузка продуктов со склада)
    public void set_product_zero(int number) {

        this.products[number] = null;
    }

    // свойство для установки продуктов
    public void set_products(Production[] products) {

        this.products = products;
    }

    // свойство для вывода вместимости склада
    public int get_quantity() {

        return this.quantity;
    }

    // свойство для вывода продуктов, хранимых на складе
    public Production get_product(int number) {

        return this.products[number];
    }

    // метод для вывода информации о складе
    public void stock_info() {

        System.out.println("\n@ИНФОРМАЦИЯ ПО СКЛАДУ\n\nКол-во товара на складе: " + this.quantity);
        
        for (int i = 0; i < this.quantity; i++) {
            if (products[i] != null) {
                System.out.print("Товар №" + (i + 1) + ": " + products[i].get_name_product() + "\n");
            }
        }
    }
}
