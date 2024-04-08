package org.stepanov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionServiceTest {
    private TransactionService transactionService;
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        playerService = new PlayerService();
        transactionService = new TransactionService();
    }

    @Test
    public void testViewTransactionHistory() {
        assertTrue(playerService.registerPlayer("user1", "password1"));
        playerService.creditWithTransactionId("user1", "6453452", BigDecimal.valueOf(100));
        assertEquals(1, transactionService.viewTransactionHistory("user1"));
        assertEquals(0, transactionService.viewTransactionHistory("nonexistentUser"));
    }

    @Test
    public void testViewAllAudits() throws Exception {
        assertTrue(playerService.registerPlayer("user1", "password1"));
        playerService.authenticatePlayer("user1", "password1");
        playerService.creditWithoutTransactionId("user1", BigDecimal.valueOf(50.0));
        assertEquals(3, transactionService.viewAllAudits());
    }
}
