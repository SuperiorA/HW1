package org.example.ClientWindow;

public interface ClientController {
    void sendMessage(String message);
    void connectToServer(String host, int port);
}