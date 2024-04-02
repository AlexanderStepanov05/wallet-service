package org.stepanov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Player, описывающий пользователя
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private String username;
    private String password;
    private BigDecimal balance = BigDecimal.ZERO;
//    private List<Transaction> transactions;
}