package org.stepanov.service;

import lombok.Getter;
import org.stepanov.entity.Player;
import org.stepanov.entity.Transaction;
import org.stepanov.types.ActionType;
import org.stepanov.types.AuditType;
import org.stepanov.types.TransactionType;

import java.math.BigDecimal;
import java.util.*;

@Getter
public class WalletPlayerService {
    private final Map<String, Player> players;
    private final Map<String, Transaction> transactions;
    private final List<String> audits;

    public WalletPlayerService() {
        this.players = new HashMap<>();
        players.put("admin", new Player("admin", "admin"));
        this.transactions = new HashMap<>();
        this.audits = new ArrayList<>();
    }

    public boolean registerPlayer(String username, String password) {
        if (!players.containsKey(username)) {
            Player newPlayer = new Player(username, password);
            players.put(username, newPlayer);
            System.out.println("Привет, " + username + ". Вы успешно зарегистрировались.");
            audit(username, ActionType.REGISTRATION, AuditType.SUCCESS);
            return true;
        } else {
            System.out.println("Пользователь с именем " + username + " уже существует. Повторите попытку, вызовите заново команду регистрации!");
            audit(username, ActionType.REGISTRATION, AuditType.FAIL);
            return false;
        }
    }

    public boolean authenticatePlayer(String username, String password) {
        Player player = players.get(username);
        if (player != null && player.getPassword().equals(password)) {
            System.out.println("Вы успешно вошли в систему.");
            audit(username, ActionType.AUTHORIZATION, AuditType.SUCCESS);
            return true;
        } else {
            System.out.println("Ошибка авторизации. Пожалуйста, проверьте имя пользователя и пароль.");
            audit(username, ActionType.AUTHORIZATION, AuditType.FAIL);
            return false;
        }
    }

    public BigDecimal getBalance(String username) {
        Player player = players.get(username);
        if (player != null) {
            audit(username, ActionType.GETTING_BALANCE, AuditType.SUCCESS);
            return player.getBalance();
        } else {
            System.out.println("Пользователь с именем " + username + " не найден.");
            audit(username, ActionType.GETTING_BALANCE, AuditType.FAIL);
            return BigDecimal.valueOf(-1.0);
        }
    }

    public boolean creditWithTransactionId(String username, String transactionId, BigDecimal amount) {
        if (transactions.containsKey(transactionId) && transactionId != null) {
            System.out.println("Транзакция с таким идентификатором уже существует.");
            audit(username, ActionType.CREDIT_TRANSACTION, AuditType.FAIL);
            return false;
        } else {
            Transaction transaction = new Transaction(transactionId, amount, TransactionType.CREDIT);
            Player player = players.get(username);

            if (player != null) {
                player.getTransactions().add(transaction);
                player.setBalance(player.getBalance().add(amount));
                transactions.put(transactionId, transaction);
                System.out.println("Кредитная транзакция успешно выполнена.");
                audit(username, ActionType.CREDIT_TRANSACTION, AuditType.SUCCESS);
                return true;
            } else {
                System.out.println("Пользователь с именем " + username + " не найден.");
                audit(username, ActionType.CREDIT_TRANSACTION, AuditType.FAIL);
                return false;
            }
        }
    }

    public boolean debitWithTransactionId(String username, String transactionId, BigDecimal amount) {
        Player player = players.get(username);
        if (player != null) {
            if (player.getBalance().compareTo(amount) >= 0) {
                if (transactions.containsKey(transactionId)) {
                    System.out.println("Транзакция с таким идентификатором уже существует.");
                    audit(username, ActionType.DEBIT_TRANSACTION, AuditType.FAIL);
                    return false;
                }
                Transaction transaction = new Transaction(transactionId, amount, TransactionType.DEBIT);
                player.getTransactions().add(transaction);
                player.setBalance(player.getBalance().subtract(amount));
                transactions.put(transactionId, transaction);
                audit(username, ActionType.DEBIT_TRANSACTION, AuditType.SUCCESS);
                System.out.println("Дебетовая транзакция успешно выполнена.");
                return true;
            } else {
                System.out.println("Недостаточно средств для выполнения дебетовой транзакции.");
                audit(username, ActionType.DEBIT_TRANSACTION, AuditType.FAIL);
                return false;
            }
        } else {
            System.out.println("Пользователь с именем " + username + " не найден.");
            audit(username, ActionType.DEBIT_TRANSACTION, AuditType.FAIL);
            return false;
        }
    }

    public boolean creditWithoutTransactionId(String username, BigDecimal amount) {
        UUID transactionId = UUID.randomUUID();
        String transactionIdString = transactionId.toString();

        return creditWithTransactionId(username, transactionIdString, amount);
    }

    public boolean debitWithoutTransactionId(String username, BigDecimal amount) {
        UUID transactionId = UUID.randomUUID();
        String transactionIdString = transactionId.toString();

        return debitWithTransactionId(username, transactionIdString, amount);
    }

    public void audit(String username, ActionType actionType, AuditType auditType) {
        String s = "Пользователь " + username + ": " + actionType + " | " + auditType;
        audits.add(s);
    }
}
