package org.stepanov.manager;

import org.stepanov.service.WalletAuditService;
import org.stepanov.service.WalletPlayerService;
import org.stepanov.service.WalletTransactionService;

public class TransactionManager {
    private PlayerManager playerManager;

    private final WalletPlayerService playerService = WalletPlayerService.getInstance();
    private final WalletTransactionService transactionsService = WalletTransactionService.getInstance();
    private final WalletAuditService auditService = WalletAuditService.getInstance();

    public TransactionManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }
}
