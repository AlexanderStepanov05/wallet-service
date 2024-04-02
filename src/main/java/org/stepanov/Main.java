package org.stepanov;

import org.stepanov.handler.AdminHandler;
import org.stepanov.handler.MainHandler;
import org.stepanov.handler.UserHandler;
import org.stepanov.in.WalletConsole;

public class Main {
    public static void main(String[] args) {
        MainHandler mainHandler = new MainHandler();
        AdminHandler adminHandler = new AdminHandler();
        UserHandler userHandler = new UserHandler();

        WalletConsole walletConsole = new WalletConsole();
        walletConsole.start(mainHandler, adminHandler, userHandler);
    }
}