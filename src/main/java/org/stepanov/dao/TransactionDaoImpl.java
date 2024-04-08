package org.stepanov.dao;

import org.stepanov.entity.Transaction;
import org.stepanov.entity.types.TransactionType;
import org.stepanov.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDaoImpl implements Dao<Integer, Transaction> {
    private static final TransactionDaoImpl INSTANCE = new TransactionDaoImpl();

    public static TransactionDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Transaction> findById(Integer id) {
        String findByIdSql = """
                select * from wallet.transactions
                where transaction_id = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findByIdSql)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(buildTransaction(resultSet))
                    : Optional.empty();

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Transaction> findAll() {
        String findAllSql = """
                select * from wallet.transactions;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findAllSql)) {

            var resultSet = preparedStatement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();

            while (resultSet.next()) {
                transactions.add(buildTransaction(resultSet));
            }

            return transactions;

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Transaction> findByPlayerId(Integer playerId) {
        String findByIdSql = """
                select * from wallet.transactions 
                where player_id = ?;
                """;

        List<Transaction> transactions = new ArrayList<>();

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findByIdSql)) {
            preparedStatement.setObject(1, playerId);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var transaction = buildTransaction(resultSet);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public Transaction save(Transaction transaction) {
        String saveSql = """
                insert into wallet.transactions(transaction_id, type, amount, player_id)  
                values (?, ?, ?, ?);
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(saveSql)) {
            preparedStatement.setObject(1, transaction.getTransactionId());
            preparedStatement.setObject(2, transaction.getType(), Types.OTHER);
            preparedStatement.setObject(3, transaction.getAmount());
            preparedStatement.setObject(4, transaction.getPlayerId());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                transaction.setTransactionId(keys.getObject("transaction_id", Integer.class));
            }

            return transaction;

        } catch (SQLException e) {
            System.err.println("class: TransactionDaoImpl | method: save |  Ошибка при выполнении SQL-запроса: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Transaction transaction) {
        String updateSql = """
                 update wallet.transactions 
                 set type = ?, 
                     amount = ? 
                 where transaction_id = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setObject(1, transaction.getType(), Types.OTHER);
            preparedStatement.setObject(2, transaction.getAmount());
            preparedStatement.setObject(3, transaction.getTransactionId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }

    }

    @Override
    public boolean delete(Integer id) {
        String deleteSql = """
                delete from wallet.transactions  
                where id = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setObject(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        String deleteSql = """
                delete from wallet.transactions;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(deleteSql)) {

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            return false;
        }
    }

    private Transaction buildTransaction(ResultSet resultSet) throws SQLException {
        String transactionTypeString = resultSet.getString("type");
        TransactionType transactionType = TransactionType.valueOf(transactionTypeString);

        return Transaction.builder()
                .transactionId(resultSet.getInt("transaction_id"))
                .type(transactionType)
                .amount(resultSet.getBigDecimal("amount"))
                .playerId(resultSet.getInt("player_id"))
                .build();
    }
}
