package org.example.ServerWindow;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SerRepository implements ServerRepository {
    private boolean isServerWorking = false;
    private ServerSocket serverSocket;

    @Override
    public void start(int port) throws IOException {
        isServerWorking = true;
        serverSocket = new ServerSocket(port);

        new Thread(() -> {
            while (isServerWorking) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (IOException e) {
                    if (isServerWorking) {
                        System.err.println("Ошибка при подключении клиента: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        isServerWorking = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при остановке сервера: " + e.getMessage());
        }
    }

    @Override
    public boolean isServerWorking() {
        return isServerWorking;
    }

    @Override
    public void handleClient(Socket clientSocket) {
        try (Scanner in = new Scanner(clientSocket.getInputStream());
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            while (isServerWorking) {
                if (in.hasNextLine()) {
                    String message = in.nextLine();
                    System.out.println("Сообщение от клиента: " + message);
                    out.println("Сервер получил: " + message);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при работе с клиентом: " + e.getMessage());
        }
    }
}
