package org.stepanov.dao;

import lombok.NoArgsConstructor;
import org.stepanov.entity.Player;
import org.stepanov.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class PlayerDaoImpl implements Dao<Integer, Player> {
    private static final PlayerDaoImpl INSTANCE = new PlayerDaoImpl();

    public static PlayerDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Player> findById(Integer id) {
        String findByIdSql = """
                select * from wallet.players
                where id = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(findByIdSql)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(buildUser(resultSet))
                    : Optional.empty();

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Player> findAll() {
        String findAllSql = """
                select * from wallet.players;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findAllSql)) {

            var resultSet = preparedStatement.executeQuery();
            List<Player> players = new ArrayList<>();

            while (resultSet.next()) {
                players.add(buildUser(resultSet));
            }

            return players;

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Optional<Player> findByUsername(String username) {
        String findByUsernameSql = """
                select * from wallet.players 
                where username = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findByUsernameSql)) {
            preparedStatement.setObject(1, username);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public Player save(Player player) {
        String saveSql = """
                insert into wallet.players(username, password, balance) 
                values (?, ?, ?);
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(saveSql)) {
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.setBigDecimal(3, player.getBalance());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                player.setId(generatedKeys.getObject("id", Integer.class));
            }

            return player;

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Player player) {
        String updateSql = """
                update wallet.players 
                set username = ?, 
                    password = ?, 
                    balance = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.setBigDecimal(3, player.getBalance());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }

    }

    @Override
    public boolean delete(Integer id) {
        String deleteSql = """
                delete from wallet.players 
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
                delete from wallet.players;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(deleteSql)) {

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            return false;
        }
    }

    private Player buildUser(ResultSet resultSet) throws SQLException {
        return Player.builder()
                .id(resultSet.getObject("id", Integer.class))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .balance(resultSet.getBigDecimal("balance"))
                .build();
    }
}
