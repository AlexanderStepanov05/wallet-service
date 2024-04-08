package org.stepanov.manager;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.stepanov.entity.Audit;
import org.stepanov.entity.Player;
import org.stepanov.entity.Transaction;
import org.stepanov.entity.types.ActionType;
import org.stepanov.entity.types.AuditType;
import org.stepanov.exception.TransactionException;
import org.stepanov.service.WalletAuditService;
import org.stepanov.service.WalletPlayerService;
import org.stepanov.service.WalletTransactionService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import static org.stepanov.entity.types.ActionType.*;
import static org.stepanov.entity.types.AuditType.*;
import static org.stepanov.entity.types.TransactionType.CREDIT;
import static org.stepanov.entity.types.TransactionType.DEBIT;

@NoArgsConstructor
public class PlayerManager {
    private final WalletPlayerService playerService = WalletPlayerService.getInstance();
    private final WalletTransactionService transactionService = WalletTransactionService.getInstance();
    private final WalletAuditService auditService = WalletAuditService.getInstance();


    public boolean registerPlayer(String username, String password) {
        Optional<Player> maybePlayer = playerService.findByUsername(username);

        if (maybePlayer.isEmpty()) {
            Player newPlayer = Player.builder()
                    .username(username)
                    .password(password)
                    .balance(BigDecimal.ZERO)
                    .build();

            playerService.save(newPlayer);
            System.out.println("Привет, " + username + ". Вы успешно зарегистрировались.");
            audit(username, REGISTRATION, SUCCESS);
            return true;
        } else {
            System.out.println("Пользователь с именем " + username + " уже существует. Повторите попытку с другим именем пользователя!");
            audit(username, REGISTRATION, FAIL);
            return false;
        }
    }

    public boolean authenticatePlayer(String username, String password) {
        var player = playerService.findByUsername(username).orElse(null);

        if (player != null && player.getPassword().equals(password)) {
            System.out.println("Вы успешно вошли в систему");
            audit(username, AUTHORIZATION, SUCCESS);
            return true;
        } else {
            System.out.println("Ошибка авторизации. Пожалуйста, проверьте имя пользователя и пароль.");
            audit(username, AUTHORIZATION, FAIL);
            return false;
        }
    }

    public BigDecimal getBalance(String username) {
        var player = playerService.findByUsername(username).orElse(null);

        if (player != null) {
            audit(username, GETTING_BALANCE, SUCCESS);
            return player.getBalance();
        } else {
            System.out.println("Пользователь с именем " + username + " не найден");
            audit(username, GETTING_BALANCE, FAIL);
            return BigDecimal.valueOf(-1.0);
        }
    }

    public boolean creditWithTransactionId(String username, Integer customId, BigDecimal amount) throws TransactionException {
        if(customId > Integer.MAX_VALUE || customId <= 0) {
            System.out.println("Введите корректное число для идентификатороа транзакции.");
            audit(username, CREDIT_TRANSACTION, FAIL);
            return false;
        }

        if(amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            System.out.println("Введите корректное число для выполнения кредитной транзакции.");
            audit(username, CREDIT_TRANSACTION, FAIL);
            return false;
        }

        Optional<Transaction> existingTransaction = transactionService.findById(customId);

        if (existingTransaction.isPresent()) {
            System.out.println("Транзакция с таким идентификатором уже существует.");
            audit(username, CREDIT_TRANSACTION, FAIL);
            throw new TransactionException("Транзакция с таким идентификатором уже существует, пожалуйста, введите другой идентификатор");
        }

        Optional<Player> playerOptional = playerService.findByUsername(username);

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            Transaction transaction = Transaction.createTransaction(customId, CREDIT, amount, player.getId());
            player.setBalance(player.getBalance().add(amount));

            transactionService.save(transaction);
            playerService.update(player);

            System.out.println("Кредитная транзакция успешно выполнена.");
            audit(username, CREDIT_TRANSACTION, SUCCESS);
            return true;
        } else {
            System.out.println("Пользователь с именем " + username + " не найден.");
            audit(username, CREDIT_TRANSACTION, FAIL);
            return false;
        }
    }

    public boolean debitWithTransactionId(String username, Integer customId, BigDecimal amount) throws TransactionException {
        if(customId > Integer.MAX_VALUE || customId <= 0) {
            System.out.println("Введите корректное число для идентификатороа транзакции.");
            audit(username, DEBIT_TRANSACTION, FAIL);
            return false;
        }

        if(amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            System.out.println("Введите корректное число для выполнения дебетовой транзакции.");
            audit(username, DEBIT_TRANSACTION, FAIL);
            return false;
        }
        Optional<Player> playerOptional = null;

        try {
            playerOptional = playerService.findByUsername(username);
        } catch (Exception e) {
            audit(username, DEBIT_TRANSACTION, FAIL);
            System.err.println("ERROR: " + e);
        }

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            Optional<Transaction> existingTransaction = transactionService.findById(customId);
            if (existingTransaction.isPresent()) {
                audit(username, DEBIT_TRANSACTION, FAIL);
                throw new TransactionException("Транзакция с таким идентификатором уже существует, пожалуйста, введите другой идентификатор");
            }

            if (player.getBalance().compareTo(amount) >= 0) {
                Transaction transaction = Transaction.createTransaction(customId, DEBIT, amount, player.getId());
                player.setBalance(player.getBalance().subtract(amount));
                playerService.update(player);
                transactionService.save(transaction);
                audit(username, DEBIT_TRANSACTION, SUCCESS);
                System.out.println("Дебетовая транзакция успешно выполнена.");
                return true;
            } else {
                System.out.println("Недостаточно средств для выполнения дебетовой транзакции.");
                audit(username, DEBIT_TRANSACTION, FAIL);
                return false;
            }
        } else {
            audit(username, DEBIT_TRANSACTION, FAIL);
            System.out.println("Пользователь с именем " + username + " не найден.");
            return false;
        }
    }

    public boolean creditWithoutTransactionId(String username, BigDecimal amount) throws TransactionException {
        Random random = new Random();
        long randomLong = random.nextLong();
        int randomInt = (int) (randomLong & 0x7FFFFFFF);

        return creditWithTransactionId(username, randomInt, amount);
    }

    @SneakyThrows
    public boolean debitWithoutTransactionId(String username, BigDecimal amount) {
        Random random = new Random();
        long randomLong = random.nextLong();
        int randomInt = (int) (randomLong & 0x7FFFFFFF);

        return debitWithTransactionId(username, randomInt, amount);
    }

    public void audit(String username, ActionType actionType, AuditType auditType) {
        Audit audit = Audit.builder()
                .playerFullName(username)
                .actionType(actionType)
                .auditType(auditType)
                .build();
        auditService.save(audit);
    }
}
