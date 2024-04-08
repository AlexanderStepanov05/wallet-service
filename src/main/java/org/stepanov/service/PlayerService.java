package org.stepanov.service;

import lombok.Getter;
import org.stepanov.dao.PlayerDao;
import org.stepanov.entity.Player;

import java.util.*;

/**
 * Класс PlayerService представляет собой сервис для управления взаимодействием с игроками.
 * Он предоставляет следующие функциональности:
 * - Регистрация новых игроков.
 * - Аутентификация игроков.
 * - Проверка балансов игроков.
 */
@Getter
public class PlayerService implements Service<Integer, Player> {
    private static final PlayerService INSTANCE = new PlayerService();
    PlayerDao playerDao = new PlayerDao();

    public static PlayerService getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return playerDao.findById(id);
    }

    public Optional<Player> findByUsername(String username) {
        return playerDao.findByUsername(username);
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
