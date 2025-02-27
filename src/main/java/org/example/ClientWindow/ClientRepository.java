package org.example.ClientWindow;

import java.io.IOException;

public interface ClientRepository {
    void connect(String host, int port) throws IOException;
    void send(String message) throws IOException;
    String receive() throws IOException;
    void disconnect();
    boolean isConnected();
}