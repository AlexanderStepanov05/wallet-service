package org.stepanov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.stepanov.types.TransactionType;

import java.math.BigDecimal;

/**
 * Класс Transaction, описывающий транзакцию
 */
@Data
@AllArgsConstructor
public class Transaction {
    private String id;
    private BigDecimal amount;
    private TransactionType type;
}
