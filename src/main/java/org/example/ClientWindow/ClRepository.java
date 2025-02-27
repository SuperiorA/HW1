package org.example.ClientWindow;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClRepository implements ClientRepository {
    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    private boolean isConnected = false;

    @Override
    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new Scanner(socket.getInputStream());
        isConnected = true;
    }

    @Override
    public void send(String message) throws IOException {
        if (out != null) {
            out.println(message);
        }
    }

    @Override
    public String receive() throws IOException {
        if (in != null && in.hasNextLine()) {
            return in.nextLine();
        }
        return null;
    }

    @Override
    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            isConnected = false;
        } catch (IOException e) {
            System.err.println("Ошибка при отключении: " + e.getMessage());
        }
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }
}