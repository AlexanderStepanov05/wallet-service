package org.stepanov.service;

import org.stepanov.dao.TransactionDao;
import org.stepanov.entity.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * Класс WalletTransactionsService представляет собой сервис для управления транзакциями.
 * Он позволяет выполнять дебетовые и кредитные транзакции,  а также просматривать историю транзакций.
 */
public class TransactionService implements Service<Integer, Transaction> {
    private static final TransactionService INSTANCE = new TransactionService();
    TransactionDao transactionDao = new TransactionDao();

    public static TransactionService getInstance() {
        return INSTANCE;
    }


    @Override
    public Optional<Transaction> findById(Integer id) {
        return transactionDao.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    public List<Transaction> findByPlayerId(Integer id) {
        return transactionDao.findByPlayerId(id);
    }

    @Override
    public void save(Transaction transaction) {
        transactionDao.save(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        transactionDao.update(transaction);
    }

    @Override
    public boolean delete(Integer id) {
        return transactionDao.delete(id);
    }

    @Override
    public boolean deleteAll() {
        return transactionDao.deleteAll();
    }
}
