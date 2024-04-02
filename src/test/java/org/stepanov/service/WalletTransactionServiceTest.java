package org.stepanov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalletTransactionServiceTest {
    private WalletTransactionService walletTransactionService;
    private WalletPlayerService walletPlayerService;

    @BeforeEach
    public void setUp() {
        walletPlayerService = new WalletPlayerService();
        walletTransactionService = new WalletTransactionService(walletPlayerService);
    }

    @Test
    public void testViewTransactionHistory() {
        assertTrue(walletPlayerService.registerPlayer("user1", "password1"));
        walletPlayerService.creditWithTransactionId("user1", "6453452", BigDecimal.valueOf(100));
        assertEquals(1, walletTransactionService.viewTransactionHistory("user1"));
        assertEquals(0, walletTransactionService.viewTransactionHistory("nonexistentUser"));
    }

    @Test
    public void testViewAllAudits() throws Exception {
        assertTrue(walletPlayerService.registerPlayer("user1", "password1"));
        walletPlayerService.authenticatePlayer("user1", "password1");
        walletPlayerService.creditWithoutTransactionId("user1", BigDecimal.valueOf(50.0));
        assertEquals(3, walletTransactionService.viewAllAudits());
    }
}
