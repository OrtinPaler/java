package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final int PORT = 3443;                                           // прослушиваемый порт
    private final ArrayList<ClientHandler> clients = new ArrayList<>();     // список клиентов

    public Server() {
        Socket client_socket = null;         // сокет клиента
        ServerSocket server_socket = null;   // серверный сокет

        try {
            server_socket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            while (true) {
                client_socket = server_socket.accept(); // ждём подключений от сервера
                ClientHandler client = new ClientHandler(client_socket, this);  // создаём обработчик клиента
                clients.add(client);
                new Thread(client).start(); // каждое подключение клиента обрабатываем в новом потоке
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                assert client_socket != null;               // если клиентский сокет не null
                client_socket.close();                      // закрываем клиентский сокет
                System.out.println("Сервер остановлен");
                server_socket.close();                      // закрываем сервер
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // метод для отправки сообщений всем
    public void send_message_all(String message) {
        for (ClientHandler o : clients)
            o.send_message(message);
    }

    // метод для удаления клиента из коллекции при выходе из чата
    public void remove_client(ClientHandler client) {
        clients.remove(client);
    }
}
