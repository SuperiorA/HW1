package org.example.ClientWindow;

public interface ClientView {
    void appendMessage(String message);
    void setSendButtonEnabled(String отправить, boolean enabled);
}