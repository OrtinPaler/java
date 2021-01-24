package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends JFrame {
    private static final String SERVER_HOST = "localhost";  // адрес сервера
    private static final int SERVER_PORT = 3443;            // порт
    private Socket client_socket;                           // клиентский сокет
    private Scanner in_message;                             // входящее сообщение
    private PrintWriter out_message;                        // исходящее сообщение
    private final JTextField input_message;                 // поле ввода сообщения
    private final JTextField input_name;                    // поле ввода имени
    private final JTextArea dialogues;                      // поле диалогов
    private String client_name = "";                        // имя клиента

    // конструктор по умолчанию
    public Client() {
        //ArrayList<String> users = new ArrayList<>();
        //Entrance.loading_user(users);

        try {
            // подключаемся к серверу
            client_socket = new Socket(SERVER_HOST, SERVER_PORT);
            in_message = new Scanner(client_socket.getInputStream());
            out_message = new PrintWriter(client_socket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        setBounds(600, 300, 600, 500);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dialogues = new JTextArea();
        dialogues.setEditable(false);                                           // запрещаем вводить в поле диалогов текст
        dialogues.setLineWrap(true);                                            // разрешаем перенос строки
        JScrollPane slider = new JScrollPane(dialogues);                        // делаем страницу прокручиваемой
        add(slider, BorderLayout.CENTER);

        JLabel number_clients = new JLabel("Количество клиентов в чате: "); // выводим количество клиентов в чате
        add(number_clients, BorderLayout.NORTH);

        JPanel bottom_panel = new JPanel(new BorderLayout());                   // выводим панель с кнопками
        add(bottom_panel, BorderLayout.SOUTH);

        JButton send_message = new JButton("Отправить");                   // добавлям в панель кнопку "отправить"
        bottom_panel.add(send_message, BorderLayout.EAST);

        input_message = new JTextField("Введите ваше сообщение: ");             // добавляем поля для ввода
        bottom_panel.add(input_message, BorderLayout.CENTER);
        input_name = new JTextField("Введите ваше имя: "/*users.get(users.size() - 1)*/);
        bottom_panel.add(input_name, BorderLayout.WEST);
        input_name.setEditable(true);

        // обработчик события нажатия кнопки "отправить"
        send_message.addActionListener(e -> {
            if (!input_message.getText().trim().isEmpty() && !input_name.getText().trim().isEmpty()) {
                client_name = input_name.getText();
                send_message();
                input_message.grabFocus();  // фокус на текстовое поле с сообщением
            }
        });

        // при фокусе поле сообщения очищается
        input_message.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                input_message.setText("");
            }
        });

        // при фокусе поле имя очищается
        input_name.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                input_name.setText("");
            }
        });

        // в отдельном потоке начинаем работу с сервером
        new Thread(() -> {
            try {
                // если есть входящее сообщение
                while (true) if (in_message.hasNext()) {
                    String transmitted_message = in_message.nextLine();
                    String clients = "Клиентов в чате = ";
                    if (transmitted_message.indexOf(clients) == 0)
                        number_clients.setText(transmitted_message);
                    else
                        dialogues.append(transmitted_message + "\n");  // выводим сообщение
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // добавляем обработчик события закрытия окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    client_name = input_name.getText();
                    if (!client_name.isEmpty() && !client_name.equals("Введите ваше имя: ")) {
                        out_message.println(client_name + " вышел из чата!");
                        /*users.remove(client_name);
                        Entrance.saving_user(users);*/
                    }
                    else
                        out_message.println("Участник вышел из чата, так и не представившись!");

                    out_message.println("Конец сессии");
                    out_message.flush();                        // выбрасываем всё из буфера
                    out_message.close();                        // закрываем поток вывода
                    in_message.close();                         // закрываем поток ввода
                    client_socket.close();                      // закрываем поток клиента
                }
                catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
        setVisible(true);   // отображаем форму
    }

    // метод для отправки сообщения
    public void send_message() {
        String messageStr = input_name.getText() + ": " + input_message.getText();  // формируем сообщение для отправки
        out_message.println(messageStr);                                            // отправляем сообщение
        out_message.flush();                                                        // выбрасываем всё из буфера
        input_message.setText("");
    }
}

/*class Entrance extends JFrame {
    public static void create_entrance() {
        ArrayList<String> users = new ArrayList<>();
        loading_user(users);
        JFrame frame = new JFrame("Вход");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(5, 0, 0, 0));
        fieldsPanel.setMaximumSize(new Dimension(800,  100));
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        JLabel server = new JLabel("Введите сервер:");
        server.setFont(new Font("Calibri", Font.BOLD, 14));
        fieldsPanel.add(server);
        JTextField address_server = new JTextField();
        fieldsPanel.add(address_server);

        JLabel name = new JLabel("Введите имя:");
        server.setFont(new Font("Calibri", Font.BOLD, 14));
        fieldsPanel.add(name);
        JTextField name_field = new JTextField();
        fieldsPanel.add(name_field);

        JButton SendButton = new JButton("ОК");

        SendButton.addActionListener(e -> {
            if (address_server.getText().isEmpty() || name_field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Заполните все поля...");
            }
            else if (!address_server.getText().equalsIgnoreCase("localhost")) {
                JOptionPane.showMessageDialog(frame, "Неправильный сервер...");
            }
            else if (!name_field.getText().isEmpty()) {
                boolean unique = true;
                for (String user : users) {
                    if (name_field.getText().equalsIgnoreCase(user)) {
                        unique = false;
                        break;
                    }
                }

                if (!unique) {
                    JOptionPane.showMessageDialog(frame, "Имя уже занято...");
                }
                else {
                    users.add(name_field.getText());
                    saving_user(users);
                    frame.dispose();
                    new Client();
                }
            }
            else {
                frame.dispose();
                new Client();
            }
        });

        address_server.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!Character.isLetter(c))
                    e.consume();
            }
        });

        fieldsPanel.add(SendButton);
        mainPanel.add(fieldsPanel);
        frame.getContentPane().add(mainPanel);
        frame.setPreferredSize(new Dimension(300, 200));
        frame.pack();
        frame.setVisible(true);
    }

    public static void saving_user(ArrayList<String> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:/5_semester/java/lab_8_client/data/users.bin"))) {
            // записываем новые данные в файл
            oos.writeObject(users);
        }
        catch (Exception ex) {
            System.out.print("ОШИБКА!\nДанные о пользователях не сохранены...\n\n");
        }
    }

    public static void loading_user(ArrayList<String> users) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:/5_semester/java/lab_8_client/data/users.bin"))) {
            // читаем из файла
            ArrayList<?> new_objects = (ArrayList<?>)ois.readObject();
            ArrayList<String> new_users = new ArrayList<>();
            assert new_objects != null;
            for(Object element : new_objects)
                new_users.add((String) element);
            users.addAll(new_users);
        }
        catch (Exception ex) {
            System.out.print("ОШИБКА!\nДанные о пользователях не загружены...\n\n");
        }
    }
}*/
