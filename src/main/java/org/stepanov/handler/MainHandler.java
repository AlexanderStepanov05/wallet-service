package org.stepanov.handler;


/**
 * Класс `MainHandler` представляет собой обработчик для взаимодействия с главным меню
 * консольного интерфейса приложения. Он предоставляет функциональность, связанную с
 * отображением главного меню и обработкой действий, связанных с ним.
 */
public class MainHandler {
    public void displayMainMenu() {
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║         Выберите действие:                      ║");
        System.out.println("║ 1. Регистрация                                  ║");
        System.out.println("║ 2. Авторизация                                  ║");
        System.out.println("║ 3. Выйти из приложения                          ║");
        System.out.println("╚═════════════════════════════════════════════════╝");

    }

    public void exitApplication() {
        System.out.println("Выход из приложения.");
        System.exit(0);
    }
}
