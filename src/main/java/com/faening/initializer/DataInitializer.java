package com.faening.initializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitializer {
    public static void initialize(Connection connection) {
        try {
            // Ler o arquivo data.sql
            InputStream inputStream = DataInitializer.class.getResourceAsStream("/data.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder sqlBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }

            // Executar os comandos SQL
            Statement statement = connection.createStatement();
            statement.execute(sqlBuilder.toString());
            statement.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
