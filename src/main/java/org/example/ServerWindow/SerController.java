package org.example.ServerWindow;

public class SerController implements ServerController {
    private ServerView view;
    private ServerRepository repository;

    public SerController(ServerView view, ServerRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void startServer() {
        try {
            repository.start(12345);
            view.appendLog("СЕРВЕР ЗАПУЩЕН");
            view.setStartButtonEnabled(false);
            view.setStopButtonEnabled(true);
        } catch (Exception e) {
            view.appendLog("Ошибка при запуске сервера: " + e.getMessage());
        }
    }

    @Override
    public void stopServer() {
        repository.stop();
        view.appendLog("СЕРВЕР ОСТАНОВЛЕН");
        view.setStartButtonEnabled(true);
        view.setStopButtonEnabled(false);
    }
}