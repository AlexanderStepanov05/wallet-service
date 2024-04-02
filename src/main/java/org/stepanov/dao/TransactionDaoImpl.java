package org.stepanov.dao;

import org.stepanov.entity.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionDaoImpl implements Dao<Integer, Transaction> {
    @Override
    public Optional<Transaction> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> findAll() {
        return null;
    }

    @Override
    public void save(Transaction transaction) {

    }

    @Override
    public void update(Transaction transaction) {

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
