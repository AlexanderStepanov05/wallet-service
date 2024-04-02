package org.stepanov.service;

import lombok.Getter;
import org.stepanov.dao.PlayerDaoImpl;
import org.stepanov.entity.Player;
import org.stepanov.entity.Transaction;
import org.stepanov.entity.types.ActionType;
import org.stepanov.entity.types.AuditType;
import org.stepanov.entity.types.TransactionType;

import java.math.BigDecimal;
import java.util.*;

/**
 * Класс WalletPlayerService представляет собой сервис для управления взаимодействием с игроками.
 * Он предоставляет следующие функциональности:
 * - Регистрация новых игроков.
 * - Аутентификация игроков.
 * - Проверка балансов игроков.
 */
@Getter
public class WalletPlayerService implements Service<Integer, Player> {
    PlayerDaoImpl playerDao = new PlayerDaoImpl();

    @Override
    public Optional<Player> findById(Integer id) {
        return playerDao.findById(id);
    }

    @Override
    public List<Player> findAll() {
        return playerDao.findAll();
    }

    @Override
    public void save(Player player) {
        playerDao.save(player);
    }

    @Override
    public void update(Player player) {
        playerDao.update(player);
    }

    @Override
    public boolean delete(Integer id) {
        return playerDao.delete(id);
    }

    @Override
    public boolean deleteAll() {
        return playerDao.deleteAll();
    }
}
