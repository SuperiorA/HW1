package org.example.ClientWindow;

import java.io.IOException;

public class ClController implements ClientController {
    private ClientView view;
    private ClientRepository repository;

    public ClController(ClientView view, ClientRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void sendMessage(String message) {
        try {
            repository.send(message);
            view.appendMessage("Вы: " + message);
        } catch (IOException e) {
            view.appendMessage("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }

    @Override
    public void connectToServer(String host, int port) {
        int retryCount = 0;
        final int maxRetries = 5;
        final int retryDelay = 5000;

        while (retryCount < maxRetries) {
            try {
                repository.connect(host, port);
                view.appendMessage("Подключение к серверу успешно установлено.");
                view.setSendButtonEnabled ("Отправить", true);

                new Thread(() -> {
                    while (repository.isConnected()) {
                        try {
                            String message = repository.receive();
                            view.appendMessage("Сервер: " + message);
                        } catch (IOException e) {
                            view.appendMessage("Ошибка при получении сообщения: " + e.getMessage());
                        }
                    }
                }).start();

                return;
            } catch (IOException e) {
                retryCount++;
                view.appendMessage("Попытка подключения " + retryCount + " не удалась. Повторная попытка...");

                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ex) {
                    view.appendMessage("Ошибка при ожидании подключения: " + ex.getMessage());
                }
            }
        }

        view.appendMessage("Не удалось подключиться к серверу после " + maxRetries + " попыток.");
    }
}