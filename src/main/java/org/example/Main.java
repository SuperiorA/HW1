package org.example;

import org.example.ClientWindow.*;
import org.example.ServerWindow.*;

public class Main {
    public static void main(String[] args) {

        startServer();
        startClient();
    }

    private static void startServer() {
        ServerGUI serverView = new ServerGUI();
        ServerRepository serverRepository = new SerRepository();
        ServerController serverController = new SerController(serverView, serverRepository);

        serverView.setController(serverController);
    }

    private static void startClient() {
        ClientGUI clientView = new ClientGUI();
        ClientRepository clientRepository = new ClRepository();
        ClientController clientController = new ClController(clientView, clientRepository);

        clientView.setController(clientController);
        clientController.connectToServer("localhost", 12345);
    }
}