package org.stepanov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PlayerServiceTest {
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        playerService = new PlayerService();
    }

    @Test
    public void testRegisterPlayer() {
        assertTrue(playerService.registerPlayer("newUser", "newPassword"));
        assertFalse(playerService.registerPlayer("admin", "admin"));
        assertFalse(playerService.registerPlayer("newUser", "newPassword"));
    }

    @Test
    public void testAuthenticatePlayer() {
        assertTrue(playerService.authenticatePlayer("admin", "admin"));
        assertFalse(playerService.authenticatePlayer("nonexistentUser", "password"));
    }

    @Test
    public void testGetBalance() {
        assertEquals(BigDecimal.valueOf(0.0), playerService.getBalance("admin"));
        assertEquals(BigDecimal.valueOf(-1.0), playerService.getBalance("nonexistentUser"));
    }

    @Test
    public void testCreditWithoutTransactionId() {
        assertTrue(playerService.registerPlayer("user1", "password1"));
        playerService.creditWithoutTransactionId("user1", BigDecimal.valueOf(100.0));
        assertFalse(playerService.creditWithoutTransactionId("user2", BigDecimal.valueOf(100.0)));
        assertEquals(BigDecimal.valueOf(100.0), playerService.getBalance("user1"));
    }

    @Test
    public void testDebitWithoutTransactionId() {
        assertTrue(playerService.registerPlayer("user1", "password1"));
        assertTrue(playerService.creditWithoutTransactionId("user1", BigDecimal.valueOf(100.0)));
        assertTrue(playerService.debitWithoutTransactionId("user1", BigDecimal.valueOf(100.0)));
        assertFalse(playerService.debitWithoutTransactionId("user2", BigDecimal.valueOf(100.0)));
    }

    @Test
    public void testCreditWithTransactionId() {
        assertTrue(playerService.registerPlayer("user1", "password1"));
        playerService.creditWithTransactionId("user1", "1012994912041", BigDecimal.valueOf(100.0));
        assertFalse(playerService.creditWithTransactionId("user2", "10103039144", BigDecimal.valueOf(100.0)));
        assertFalse(playerService.creditWithTransactionId("user1", "1012994912041", BigDecimal.valueOf(100.0)));
        assertEquals(BigDecimal.valueOf(100.0), playerService.getBalance("user1"));
    }

    @Test
    public void testDebitWithTransactionId() {
        assertTrue(playerService.registerPlayer("user1", "password1"));
        assertTrue(playerService.creditWithoutTransactionId("user1", BigDecimal.valueOf(500.0)));
        assertTrue(playerService.debitWithTransactionId("user1", "102495841924", BigDecimal.valueOf(111.0)));
        assertFalse(playerService.debitWithTransactionId("user1", "2030521", BigDecimal.valueOf(1000.0)));
        assertFalse(playerService.debitWithTransactionId("user2", "2194018248151", BigDecimal.valueOf(50.0)));
        assertEquals(5, playerService.getAudits().size());
    }

}
