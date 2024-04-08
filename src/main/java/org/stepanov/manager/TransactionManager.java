package org.stepanov.manager;

import org.stepanov.entity.Audit;
import org.stepanov.entity.Player;
import org.stepanov.entity.Transaction;
import org.stepanov.service.AuditService;
import org.stepanov.service.PlayerService;
import org.stepanov.service.TransactionService;

import java.util.List;

import static org.stepanov.entity.types.ActionType.VIEW_TRANSACTION_HISTORY;
import static org.stepanov.entity.types.AuditType.FAIL;
import static org.stepanov.entity.types.AuditType.SUCCESS;

public class TransactionManager {
    private PlayerManager playerManager;

    private final PlayerService playerService = PlayerService.getInstance();
    private final TransactionService transactionsService = TransactionService.getInstance();
    private final AuditService auditService = AuditService.getInstance();

    public TransactionManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public int viewAllAudits() {
        List<Audit> audits = auditService.findAll();

        for (Audit audit : audits) {
            String formattedOutput = String.format("%-20s | %-15s | %-15s | %d",
                    audit.getPlayerFullName(),
                    audit.getAuditType(),
                    audit.getActionType(),
                    audit.getId());
            System.out.println(formattedOutput);
        }
        return audits.size();
    }

    public int viewTransactionHistory(String username) {
        var optionalPlayer = playerService.findByUsername(username);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            List<Transaction> transactionsList = transactionsService.findByPlayerId(player.getId());

            if (transactionsList.isEmpty()) {
                System.out.println("У пользователя " + username + " нет истории транзакций.");
                playerManager.audit(username, VIEW_TRANSACTION_HISTORY, FAIL);
                return 0;
            }

            System.out.println("Custom ID | Transaction Type  | Amount");
            System.out.println("-----------------------------------");

            for (Transaction transaction : transactionsList) {
                System.out.printf("%-10s | %-16s | %.2f%n",
                        transaction.getTransactionId(),
                        transaction.getType(),
                        transaction.getAmount());
            }
            playerManager.audit(username, VIEW_TRANSACTION_HISTORY, SUCCESS);
            return transactionsList.size();

        } else {
            System.out.println("Пользователь с именем " + username + " не найден.");
            playerManager.audit(username, VIEW_TRANSACTION_HISTORY, FAIL);
            return 0;
        }
    }
}
