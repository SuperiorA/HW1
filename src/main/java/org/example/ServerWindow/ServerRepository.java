package org.example.ServerWindow;

import java.io.IOException;
import java.net.Socket;

public interface ServerRepository {
    void start(int port) throws IOException;
    void stop();
    boolean isServerWorking();
    void handleClient(Socket clientSocket);
}