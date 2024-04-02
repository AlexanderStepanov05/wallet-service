package org.stepanov.in;

import lombok.NoArgsConstructor;
import org.stepanov.handler.AdminHandler;
import org.stepanov.handler.MainHandler;
import org.stepanov.handler.UserHandler;
import org.stepanov.service.WalletPlayerService;
import org.stepanov.service.WalletTransactionService;

import java.math.BigDecimal;
import java.util.Scanner;

public class WalletConsole {
    WalletPlayerService walletPlayerService;
    WalletTransactionService walletTransactionService;

    static String loggedInUsername = null;
    static boolean loggedIn = false;
    Scanner scanner = new Scanner(System.in);

    public WalletConsole() {
        walletPlayerService = new WalletPlayerService();
        walletTransactionService = new WalletTransactionService(walletPlayerService);
    }

    public void start(MainHandler mainHandler, AdminHandler adminHandler, UserHandler userHandler) {
        while (true) {
            if (!loggedIn) {
                mainHandler.displayMainMenu();
                int choice = userHandler.readChoice();
                switch (choice) {
                    case 1:
                        System.out.print("Введите имя пользователя: ");
                        String username = scanner.nextLine();
                        System.out.print("Введите пароль: ");
                        String password = scanner.nextLine();
                        walletPlayerService.registerPlayer(username, password);
                        break;
                    case 2:
                        System.out.print("Введите имя пользователя: ");
                        String authenticateUsername = scanner.nextLine();
                        System.out.print("Введите пароль: ");
                        String authenticatePassword = scanner.nextLine();
                        if (walletPlayerService.authenticatePlayer(authenticateUsername, authenticatePassword)) {
                            loggedIn = true;
                            loggedInUsername = authenticateUsername;
                        }
                        break;
                    case 3:
                        mainHandler.exitApplication();
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите снова.");
                }
            } else if ("admin".equals(loggedInUsername)) {
                adminHandler.displayAdminMenu();
                int choice = userHandler.readChoice();
                switch (choice) {
                    case 1:
                        walletTransactionService.viewAllAudits();
                        break;
                    case 2:
                        userHandler.logout();
                        break;
                    case 3:
                        mainHandler.exitApplication();
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите снова.");
                }
            } else if (loggedInUsername != null) {
                userHandler.displayUserMenu();
                int choice = userHandler.readChoice();
                switch (choice) {
                    case 1:
                        BigDecimal balance = walletPlayerService.getBalance(loggedInUsername);
                        System.out.println("Баланс игрока " + loggedInUsername + ": " + balance);
                        break;
                    case 2:
                        userHandler.displayUserCreditAndDebit();
                        int choiceDebetTransactionId = userHandler.readChoice();
                        switch (choiceDebetTransactionId) {
                            case 1:
                                System.out.print("Введите сумму дебета: ");
                                BigDecimal debetAmountTransactionId = scanner.nextBigDecimal();
                                scanner.nextLine();
                                System.out.print("Введите идентификатор транзакции: ");
                                String transactionIdForCredit = scanner.nextLine();
                                walletPlayerService.debitWithTransactionId(loggedInUsername, transactionIdForCredit, debetAmountTransactionId);
                                break;
                            case 2:
                                System.out.print("Введите сумму дебета: ");
                                BigDecimal debetAmount = scanner.nextBigDecimal();
                                scanner.nextLine();
                                walletPlayerService.debitWithoutTransactionId(loggedInUsername, debetAmount);
                                break;
                        }
                        break;
                    case 3:
                        userHandler.displayUserCreditAndDebit();
                        int choiceCreditTransactionId = userHandler.readChoice();
                        switch (choiceCreditTransactionId) {
                            case 1:
                                System.out.print("Введите сумму кредита: ");
                                BigDecimal creditAmountTransactionId = scanner.nextBigDecimal();
                                scanner.nextLine();
                                System.out.print("Введите идентификатор транзакции: ");
                                String transactionIdForCredit = scanner.nextLine();
                                walletPlayerService.creditWithTransactionId(loggedInUsername, transactionIdForCredit, creditAmountTransactionId);
                                break;
                            case 2:
                                System.out.print("Введите сумму кредита: ");
                                BigDecimal creditAmount = scanner.nextBigDecimal();
                                scanner.nextLine();
                                walletPlayerService.creditWithoutTransactionId(loggedInUsername, creditAmount);
                                break;
                        }
                        break;
                    case 4:
                        walletTransactionService.viewTransactionHistory(loggedInUsername);
                        break;
                    case 5:
                        userHandler.logout();
                        break;
                    case 6:
                        mainHandler.exitApplication();
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите снова.");
                }
            } else {
                System.out.println("Вы не авторизованы. Войдите сначала в аккаунт.");
            }
        }
    }

    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
