package courseJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Form {

    public static void create_form() {
        JFrame frame = new JFrame("Заказ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 25));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel panelRadioButtons = new JPanel();
        JPanel sendButtonPanel = new JPanel();
        JPanel fieldsPanel = new JPanel();

        JRadioButton high = new JRadioButton("Высокий");
        JRadioButton second = new JRadioButton("Средний");
        JRadioButton last = new JRadioButton("Низкий");

        change_color(new JPanel[]{mainPanel, fieldsPanel, panelRadioButtons, sendButtonPanel}, Color.RED);
        change_color_button(new JRadioButton[]{high, second, last}, Color.RED);

        fieldsPanel.setLayout(new GridLayout(9, 0, 0, 0));
        fieldsPanel.setMaximumSize(new Dimension(800, 250));

        JLabel last_name = new JLabel("Фамилия");
        last_name.setFont(new Font("Calibri", Font.BOLD, 14));
        fieldsPanel.add(last_name);
        JTextField lastNameTextField = new JTextField();
        fieldsPanel.add(lastNameTextField);

        lastNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!Character.isLetter(c))
                    e.consume();
            }
        });

        JLabel first_name = new JLabel("Имя");
        first_name.setFont(new Font("Calibri", Font.BOLD, 14));
        fieldsPanel.add(first_name);
        JTextField firstNameTextField = new JTextField();
        fieldsPanel.add(firstNameTextField);

        firstNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!Character.isLetter(c))
                    e.consume();
            }
        });

        JLabel patronymic = new JLabel("Отчество");
        patronymic.setFont(new Font("Calibri", Font.BOLD, 14));
        fieldsPanel.add(patronymic);
        JTextField patronymicTextField = new JTextField();
        fieldsPanel.add(patronymicTextField);

        patronymicTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!Character.isLetter(c))
                    e.consume();
            }
        });

        JLabel phone_number = new JLabel("Телефон");
        phone_number.setFont(new Font("Calibri", Font.BOLD, 14));
        fieldsPanel.add(phone_number);
        JTextField phoneTextField = new JTextField();
        fieldsPanel.add(phoneTextField);

        phoneTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || phoneTextField.getText().length() > 13)
                    e.consume();
            }
        });

        JLabel importance = new JLabel("Приоритет заказа");
        importance.setFont(new Font("Calibri", Font.BOLD, 14));
        importance.setHorizontalAlignment(SwingConstants.CENTER);
        fieldsPanel.add(importance);

        high.addActionListener(e -> {
            change_color(new JPanel[]{mainPanel, fieldsPanel, panelRadioButtons, sendButtonPanel}, Color.RED);
            change_color_button(new JRadioButton[]{high, second, last}, Color.RED);
        });

        high.setSelected(true);

        second.addActionListener(e -> {
            change_color(new JPanel[]{mainPanel, fieldsPanel, panelRadioButtons, sendButtonPanel}, Color.YELLOW);
            change_color_button(new JRadioButton[]{high, second, last}, Color.YELLOW);
        });

        last.addActionListener(e -> {
            change_color(new JPanel[]{mainPanel, fieldsPanel, panelRadioButtons, sendButtonPanel}, Color.GREEN);
            change_color_button(new JRadioButton[]{high, second, last}, Color.GREEN);
        });

        ButtonGroup group = new ButtonGroup();
        group.add(high);
        group.add(second);
        group.add(last);

        panelRadioButtons.setLayout(new BoxLayout(panelRadioButtons, BoxLayout.Y_AXIS));
        panelRadioButtons.add(high);
        panelRadioButtons.add(second);
        panelRadioButtons.add(last);

        sendButtonPanel.setLayout(new BoxLayout(sendButtonPanel, BoxLayout.Y_AXIS));
        JButton SendButton = new JButton("Отправить");
        SendButton.addActionListener(e -> {
            if (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty()
                    || patronymicTextField.getText().isEmpty() || phoneTextField.getText().isEmpty())
                JOptionPane.showMessageDialog(frame, "Заполните все поля...");
            else {
                String result = "";
                result += "Фамилия: " + lastNameTextField.getText()
                        + "\nИмя: " + firstNameTextField.getText()
                        + "\nОтчетсво: " + patronymicTextField.getText()
                        + "\nНомер телефона: "+ phoneTextField.getText()
                        + "\nПриоритет заказа: ";
                if (high.isSelected())
                    result += "Высокий";
                else if (second.isSelected())
                    result += "Средний";
                else if (last.isSelected())
                    result += "Низкий";

                JOptionPane.showMessageDialog(frame, result);
            }
        });

        sendButtonPanel.add(SendButton);
        mainPanel.add(fieldsPanel);
        mainPanel.add(panelRadioButtons);
        mainPanel.add(sendButtonPanel);
        frame.getContentPane().add(mainPanel);
        frame.setPreferredSize(new Dimension(300, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static void change_color(JPanel[] jpanels, Color color) {
        for (JPanel i : jpanels)
            i.setBackground(color);
    }

    private static void change_color_button(JRadioButton[] jradiobuttons, Color color) {
        for (JRadioButton i : jradiobuttons)
            i.setBackground(color);
    }
}
