package org.stepanov.handler;

/**
 * Класс `AdminHandler` представляет собой обработчик для взаимодействия администратора
 * с консольным интерфейсом приложения. Он предоставляет функциональность, связанную с
 * отображением меню.
 */
public class AdminHandler {
    public void displayAdminMenu() {
        System.out.println("╔═════════════════════════════════════════════════════════════╗");
        System.out.println("║ Привет, Админ. Рад видеть вас!                              ║");
        System.out.println("║ Выберите действие:                                          ║");
        System.out.println("║ 1. Просмотр аудитов                                         ║");
        System.out.println("║ 2. Выйти из аккаунта                                        ║");
        System.out.println("║ 3. Выйти из приложения                                      ║");
        System.out.println("╚═════════════════════════════════════════════════════════════╝");
    }
}
