package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerWindow extends JFrame {

    private static final int POS_X = 1000;
    private static final int POS_Y = 700;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JButton btnStart = new JButton("Старт");
    private JButton btnStop = new JButton("Стоп");
    private boolean isServerWorking;
    private final JTextArea log = new JTextArea();
    private ServerSocket serverSocket;

    ServerWindow() {

        isServerWorking = false;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(true);
        setTitle("Чат сервера");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 80, 80));
        buttonPanel.add(btnStart);
        buttonPanel.add(btnStop);

        log.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(log);
        log.setBackground(Color.CYAN);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        Font font = new Font("Arial", Font.PLAIN, 32);
        btnStart.setFont(font);
        btnStop.setFont(font);
        log.setFont(font);
        buttonPanel.setBackground(Color.WHITE);
        btnStart.setBackground(Color.GREEN);
        btnStop.setBackground(Color.RED);
        btnStart.setForeground(Color.BLUE);
        btnStop.setForeground(Color.BLUE);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    startServer();
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    stopServer();
                }
            }
        });

        setVisible(true);
    }

    private void startServer() {
        isServerWorking = true;
        log.append("СЕРВЕР ЗАПУЩЕН\n");

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(12345);
                log.append("Сервер ожидает подключений на порту 12345...\n");

                while (isServerWorking) {
                    Socket clientSocket = serverSocket.accept();
                    log.append("Подключен клиент: " + clientSocket.getInetAddress() + "\n");
                    new Thread(() -> handleClient(clientSocket)).start();
                }
            } catch (IOException e) {
                log.append("Ошибка сервера: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }).start();
    }

    private void stopServer() {
        isServerWorking = false;
        log.append("СЕРВЕР ОСТАНОВЛЕН\n");

        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.append("Ошибка при остановке сервера: " + e.getMessage() + "\n");
        }
    }

    private void handleClient(Socket clientSocket) {
        try (Scanner in = new Scanner(clientSocket.getInputStream());
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            log.append("Клиент подключён: " + clientSocket.getInetAddress() + "\n");

            while (isServerWorking) {
                if (in.hasNextLine()) {
                    String message = in.nextLine();
                    log.append("Сообщение от клиента: " + message + "\n");
                    out.println("Сервер получил: " + message);
                }
            }
        } catch (IOException e) {
            log.append("Ошибка при работе с клиентом: " + e.getMessage() + "\n");
        }
    }
}