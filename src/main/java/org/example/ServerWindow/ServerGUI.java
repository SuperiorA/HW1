package org.example.ServerWindow;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame implements ServerView {
    private JButton btnStart = new JButton("Старт");
    private JButton btnStop = new JButton("Стоп");
    private JTextArea log = new JTextArea();
    private ServerController controller;

    public ServerGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(1000, 700, 800, 600);
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

        btnStart.addActionListener(e -> {
            if (controller != null) {
                controller.startServer();
            }
        });

        btnStop.addActionListener(e -> {
            if (controller != null) {
                controller.stopServer();
            }
        });

        setVisible(true);
    }

    public void setController(ServerController controller) {
        this.controller = controller;
    }

    @Override
    public void appendLog(String message) {
        log.append(message + "\n");
    }

    @Override
    public void setStartButtonEnabled(boolean enabled) {
        btnStart.setEnabled(enabled);
    }

    @Override
    public void setStopButtonEnabled(boolean enabled) {
        btnStop.setEnabled(enabled);
    }
}