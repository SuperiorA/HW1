package org.example.ServerWindow;

public interface ServerView {
    void appendLog(String message);
    void setStartButtonEnabled(boolean enabled);
    void setStopButtonEnabled(boolean enabled);
}