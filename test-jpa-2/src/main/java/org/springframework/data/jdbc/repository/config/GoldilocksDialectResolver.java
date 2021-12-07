package org.springframework.data.jdbc.repository.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.data.jdbc.repository.config.DialectResolver.JdbcDialectProvider;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.GoldilocksDialect;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;

public class GoldilocksDialectResolver implements JdbcDialectProvider {
    @Override
    public Optional<Dialect> getDialect(JdbcOperations operations) {
            return Optional.ofNullable(
            operations.execute((ConnectionCallback<Dialect>) GoldilocksDialectResolver::getDialect));
    }
    
    private static Dialect getDialect(Connection connection) throws SQLException  {
    DatabaseMetaData metaData = connection.getMetaData();
    String name = metaData.getDatabaseProductName().toLowerCase(Locale.ROOT);
    if (name.contains("goldilocks")) {
        return GoldilocksDialect.INSTANCE;
    }
    return null;
}
}
