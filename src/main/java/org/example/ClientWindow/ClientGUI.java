package org.example.ClientWindow;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame implements ClientView {
    private JTextArea chatArea = new JTextArea();
    private JTextField inputField = new JTextField();
    private JButton sendButton = new JButton("Отправить");
    private ClientController controller;

    public ClientGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(1000, 0, 800, 600);
        setResizable(true);
        setTitle("Чат клиента");

        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        Font font = new Font("Arial", Font.PLAIN, 25);
        chatArea.setFont(font);
        inputField.setFont(font);
        sendButton.setFont(font);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    @Override
    public void setSendButtonEnabled(String отправить, boolean enabled) {
        sendButton.setEnabled(enabled);
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty() && controller != null) {
            controller.sendMessage(message);
            inputField.setText("");
        }
    }
}