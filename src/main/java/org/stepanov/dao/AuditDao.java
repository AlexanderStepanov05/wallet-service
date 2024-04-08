package org.stepanov.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.stepanov.entity.Audit;
import org.stepanov.entity.types.ActionType;
import org.stepanov.entity.types.AuditType;
import org.stepanov.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditDao implements Dao<Integer, Audit> {
    private static final AuditDao INSTANCE = new AuditDao();

    public static AuditDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Audit> findById(Integer id) {
        String findByIdSql = """
                select * from wallet.audits
                where id = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findByIdSql)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(buildAudit(resultSet))
                    : Optional.empty();

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Audit> findAll() {
        String findAllSql = """
                select * from wallet.audits;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findAllSql)) {

            var resultSet = preparedStatement.executeQuery();
            List<Audit> audits = new ArrayList<>();

            while (resultSet.next()) {
                audits.add(buildAudit(resultSet));
            }

            return audits;

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Optional<Audit> findByUsername(String username) {
        String findByUsernameSql = """
                select * from wallet.audits 
                where player_username = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(findByUsernameSql)) {
            preparedStatement.setObject(1, username);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(buildAudit(resultSet));
            } else {
                return Optional.empty();
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public Audit save(Audit audit) {
        String saveSql = """
                insert into wallet.audits(audit_type, action_type, player_username) 
                values (?, ?, ?);
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, audit.getPlayerFullName());
            preparedStatement.setObject(2, audit.getAuditType(), Types.OTHER);
            preparedStatement.setObject(3, audit.getActionType(), Types.OTHER);

            var affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        audit.setId(keys.getObject(1, Integer.class));
                    }
                } catch (SQLException e) {
                    System.err.println("Ошибка при получении сгенерированного ключа: " + e.getMessage());
                }
            } else {
                System.err.println("Ошибка при выполнении SQL-запроса. Нет добавленных записей.");
            }

            return audit;

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Audit audit) {
        String updateSql = """
                update wallet.audits 
                set audit_type = ?, 
                    action_type = ?, 
                where id = ?;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setObject(1, audit.getAuditType());
            preparedStatement.setObject(2, audit.getActionType());
            preparedStatement.setObject(3, audit.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }

    }

    @Override
    public boolean delete(Integer id) {
        String deleteSql = """
                delete from wallet.audits 
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
                delete from wallet.audits;
                """;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(deleteSql)) {

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            return false;
        }
    }

    private Audit buildAudit(ResultSet resultSet) throws SQLException {
        String auditTypeString = resultSet.getString("audit_type");
        AuditType auditType = AuditType.valueOf(auditTypeString);

        String actionTypeString = resultSet.getString("action_type");
        ActionType actionType = ActionType.valueOf(actionTypeString);

        return Audit.builder()
                .id(resultSet.getInt("id"))
                .auditType(auditType)
                .actionType(actionType)
                .playerFullName(resultSet.getString("player_username"))
                .build();
    }
}
