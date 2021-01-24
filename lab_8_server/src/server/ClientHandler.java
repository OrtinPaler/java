package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Server server;                              // экземпляр сервера
    private Scanner in_message;                         // входящее собщение
    private PrintWriter out_message;                    // исходящее сообщение
    private static int number_clients = 0;              // количество участников в чате, статичное поле

    // конструктор с параметрами client_socket (клиентский сокет) и server (сервер)
    public ClientHandler(Socket client_socket, Server server) {
        try {
            number_clients++;
            this.server = server;
            this.out_message = new PrintWriter(client_socket.getOutputStream());
            this.in_message = new Scanner(client_socket.getInputStream());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // сервер отправляет сообщение
                server.send_message_all("Новый участник вошёл в чат!");
                server.send_message_all("Клиентов в чате = " + number_clients);
                break;
            }

            while (true) {
                // Если от клиента пришло сообщение
                if (in_message.hasNext()) {
                    String client_message = in_message.nextLine();
                    // если клиент отправляет данное сообщение, то цикл прерывается и клиент выходит из чата
                    if (client_message.equalsIgnoreCase("Конец сессии"))
                        break;
                    System.out.println(client_message);      // выводим в консоль сообщение
                    server.send_message_all(client_message); // отправляем данное сообщение всем клиентам
                }
                Thread.sleep(100);                      // останавливаем выполнение потока на 100 мс
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            this.close();
        }
    }

    // метод для отправки сообщения
    public void send_message(String msg) {
        try {
            out_message.println(msg);
            out_message.flush();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // метод при выходе клиента из чата
    public void close() {
        server.remove_client(this);  // удаляем клиента из списка
        number_clients--;
        server.send_message_all("Клиентов в чате = " + number_clients);
    }
}
