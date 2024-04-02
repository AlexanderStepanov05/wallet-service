package org.stepanov.service;

import org.stepanov.dao.TransactionDaoImpl;
import org.stepanov.entity.Player;
import org.stepanov.entity.Transaction;
import org.stepanov.entity.types.ActionType;
import org.stepanov.entity.types.AuditType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс WalletTransactionsService представляет собой сервис для управления транзакциями.
 * Он позволяет выполнять дебетовые и кредитные транзакции,  а также просматривать историю транзакций.
 */
public class WalletTransactionService implements Service<Integer, Transaction> {
    TransactionDaoImpl transactionDao = new TransactionDaoImpl();


    @Override
    public Optional<Transaction> findById(Integer id) {
        return transactionDao.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
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
