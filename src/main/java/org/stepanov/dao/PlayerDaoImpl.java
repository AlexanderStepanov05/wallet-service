package org.stepanov.dao;

import org.stepanov.entity.Player;

import java.util.List;
import java.util.Optional;

public class PlayerDaoImpl implements Dao<Integer, Player> {
    @Override
    public Optional<Player> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Player> findAll() {
        return null;
    }

    @Override
    public void save(Player player) {

    }

    @Override
    public void update(Player player) {

    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
