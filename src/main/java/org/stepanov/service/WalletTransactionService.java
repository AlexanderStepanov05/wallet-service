package org.stepanov.service;

import org.stepanov.entity.Player;
import org.stepanov.entity.Transaction;
import org.stepanov.entity.types.ActionType;
import org.stepanov.entity.types.AuditType;

import java.util.List;
import java.util.Map;

/**
 * Класс WalletTransactionsService представляет собой сервис для управления транзакциями.
 * Он позволяет выполнять дебетовые и кредитные транзакции,  а также просматривать историю транзакций.
 */
public class WalletTransactionService {

    private final WalletPlayerService walletPlayerService;
    private final List<String> audits;
    private final Map<String, Player> players;

    public WalletTransactionService(WalletPlayerService walletPlayerService) {
        this.walletPlayerService = walletPlayerService;
        this.audits = walletPlayerService.getAudits();
        this.players = walletPlayerService.getPlayers();
    }

    /**
     * Метод просматривает аудит действий игроков.
     *
     * @return Количество записей в аудите.
     */
    public int viewAllAudits() {
        for (String audit : audits) {
            System.out.println(audit);
        }
        return audits.size();
    }

    /**
     * Метод просматривает историю транзакций для игрока с указанным именем.
     *
     * @param username Имя игрока.
     * @return Количество транзакций в истории.
     */
    public int viewTransactionHistory(String username) {
        Player player = players.get(username);

        if (player != null) {
            List<Transaction> history = player.getTransactions();
            if (history != null) {
                if (history.isEmpty()) {
                    System.out.println("У пользователя " + username + " нет истории транзакций.");
                    walletPlayerService.audit(username, ActionType.VIEW_TRANSACTION_HISTORY, AuditType.FAIL);
                    return 0;
                } else {
                    System.out.println("История транзакций для пользователя " + username + ":");
                    for (Transaction tx : history) {
                        System.out.println("Идентификатор транзакции: " +
                                tx.getId() +
                                " | " +
                                tx.getType() +
                                " " +
                                tx.getAmount());
                    }
                    walletPlayerService.audit(username, ActionType.VIEW_TRANSACTION_HISTORY, AuditType.SUCCESS);
                    return history.size();
                }
            } else {
                System.out.println("История транзакций игрока " + username + " не инициализирована.");
                walletPlayerService.audit(username, ActionType.VIEW_TRANSACTION_HISTORY, AuditType.FAIL);
                return -1;
            }
        } else {
            System.out.println("Пользователь с именем " + username + " не найден.");
            walletPlayerService.audit(username, ActionType.VIEW_TRANSACTION_HISTORY, AuditType.FAIL);
            return 0;
        }
    }
}
