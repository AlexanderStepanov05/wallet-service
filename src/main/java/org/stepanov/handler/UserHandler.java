package org.stepanov.handler;

import org.stepanov.in.WalletConsole;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс `UserHandler` представляет собой обработчик для взаимодействия пользователя
 * с консольным интерфейсом приложения. Он предоставляет функциональность, связанную с
 * отображением меню пользователя, чтением ввода и управлением пользовательскими действиями.
 */
public class UserHandler {

    Scanner scanner = new Scanner(System.in);
    WalletConsole walletConsole = new WalletConsole();

    /**
     * Считывает выбор пользователя из консоли.
     *
     * @return Выбор пользователя.
     */
    public int readChoice() {
        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }
        return choice;
    }

    public void logout() {
        walletConsole.setLoggedInUsername(null);
        walletConsole.setLoggedIn(false);
        System.out.println("Выход из аккаунта.");
    }

    public void displayUserMenu() {
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║       Выберите действие:      ║");
        System.out.println("║  1. Просмотр баланса          ║");
        System.out.println("║  2. Дебет                     ║");
        System.out.println("║  3. Кредит                    ║");
        System.out.println("║  4. История транзакций        ║");
        System.out.println("║  5. Выйти из аккаунта         ║");
        System.out.println("║  6. Выйти из приложения       ║");
        System.out.println("╚═══════════════════════════════╝");

    }

    public void displayUserCreditAndDebit() {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║ Желаете ли вы ввести идентификатор для транзакции? ║");
        System.out.println("║ 1. Да                                              ║");
        System.out.println("║ 2. Нет                                             ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

}
