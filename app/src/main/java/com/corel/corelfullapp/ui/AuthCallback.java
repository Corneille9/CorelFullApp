package com.corel.corelfullapp.ui;

public interface AuthCallback {
    void sendMessage(String phoneNumber);
    void verification(String code);
}
