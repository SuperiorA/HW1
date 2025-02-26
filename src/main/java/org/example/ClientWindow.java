package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWindow extends JFrame {

    private static final int POS_X = 1000;
    private static final int POS_Y = 50;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final JTextArea chatArea = new JTextArea();
    private final JTextField inputField = new JTextField();
    private PrintWriter out;

    ClientWindow() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(true);
        setTitle("Чат клиента");

        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        Font font = new Font("Arial", Font.PLAIN, 32);
        chatArea.setFont(font);
        inputField.setFont(font);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatArea.setBackground(Color.CYAN);
        inputField.setBackground(Color.WHITE);
        sendButton.setBackground(Color.GREEN);
        sendButton.setFont(font);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);

        connectToServerWithRetry();
    }

    private void connectToServerWithRetry() {
        new Thread(() -> {
            int retryCount = 0;
            while (retryCount < 5) {
                try {
                    Thread.sleep(5000);
                    Socket socket = new Socket("localhost", 12345);
                    out = new PrintWriter(socket.getOutputStream(), true);

                    new Thread(() -> {
                        try (Scanner in = new Scanner(socket.getInputStream())) {
                            while (in.hasNextLine()) {
                                String message = in.nextLine();
                                chatArea.append("Сервер: " + message + "\n");
                            }
                        } catch (IOException e) {
                            chatArea.append("Ошибка при чтении сообщений от сервера: " + e.getMessage() + "\n");
                        }
                    }).start();

                    chatArea.append("Подключение к серверу успешно установлено.\n");
                    return;
                } catch (IOException e) {
                    retryCount++;
                    chatArea.append("Попытка подключения " + retryCount + " не удалась. Повторная попытка...\n");
                } catch (InterruptedException e) {
                    chatArea.append("Ошибка при ожидании подключения: " + e.getMessage() + "\n");
                }
            }
            chatArea.append("Не удалось подключиться к серверу после 5 попыток.\n");
        }).start();
    }

    private void sendMessage() {
        if (out == null) {
            chatArea.append("Ошибка: нет подключения к серверу.\n");
            return;
        }

        String message = inputField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            chatArea.append("Вы: " + message + "\n");
            inputField.setText("");
        }
    }
}