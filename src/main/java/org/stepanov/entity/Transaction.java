package org.stepanov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stepanov.entity.types.TransactionType;

import java.math.BigDecimal;

/**
 * Класс Transaction, описывающий транзакцию
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Integer transactionId;
    private TransactionType type;
    private BigDecimal amount;
    private Integer playerId;

    public static Transaction createTransaction(Integer transactionId, TransactionType type, BigDecimal amount, Integer playerId) {
        return Transaction.builder()
                .transactionId(transactionId)
                .type(type)
                .amount(amount)
                .playerId(playerId)
                .build();
    }
}
