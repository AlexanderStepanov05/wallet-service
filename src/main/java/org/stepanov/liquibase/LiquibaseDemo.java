package org.stepanov.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.stepanov.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseDemo {
    private static final LiquibaseDemo INSTANCE = new LiquibaseDemo();

    public void runMigrations() {
        try {
            var connection = ConnectionManager.getConnection();
            var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            var liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграции успешно выполнены");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static LiquibaseDemo getInstance() {
        return INSTANCE;
    }
}
