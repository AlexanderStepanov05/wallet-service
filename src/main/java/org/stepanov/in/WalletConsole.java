package org.stepanov.in;

import lombok.NoArgsConstructor;
import org.stepanov.handler.AdminHandler;
import org.stepanov.handler.MainHandler;
import org.stepanov.handler.UserHandler;
import org.stepanov.service.WalletPlayerService;
import org.stepanov.service.WalletTransactionService;

public class WalletConsole {
    WalletPlayerService walletPlayerService;
    WalletTransactionService walletTransactionService;

    public WalletConsole() {
        walletPlayerService = new WalletPlayerService();
        walletTransactionService = new WalletTransactionService(walletPlayerService);
    }

    public void start(MainHandler mainHandler, AdminHandler adminHandler, UserHandler userHandler) {
    }

    public void setLoggedInUsername(Object o) {
    }

    public void setLoggedIn(boolean b) {
    }
}
