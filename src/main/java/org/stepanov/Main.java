package org.stepanov;

import org.stepanov.exception.TransactionException;
import org.stepanov.handler.AdminHandler;
import org.stepanov.handler.MainHandler;
import org.stepanov.handler.UserHandler;
//import org.stepanov.in.WalletConsole;
import org.stepanov.in.WalletConsole;
import org.stepanov.liquibase.LiquibaseDemo;

public class Main {
    public static void main(String[] args) throws TransactionException {
        MainHandler mainHandler = new MainHandler();
        AdminHandler adminHandler = new AdminHandler();
        UserHandler userHandler = new UserHandler();

        LiquibaseDemo liquibaseDemo = LiquibaseDemo.getInstance();
        liquibaseDemo.runMigrations();

        WalletConsole walletConsole = new WalletConsole();
        walletConsole.start(mainHandler, userHandler, adminHandler);
    }
}