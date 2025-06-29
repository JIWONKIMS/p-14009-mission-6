package com.back.simpleDb;

import com.back.domain.WiseSaying;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sql {
    Connection connection;

    public Sql(Connection connection) {
        this.connection = connection;
    }

    public boolean insert(long id, WiseSaying wiseSaying) {
        String sql = "INSERT INTO wise_sayings (id, author, content) VALUES (?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, wiseSaying.getAuthor());
            preparedStatement.setString(3, wiseSaying.getContent());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Insert error: " + e.getMessage());
            return false;
        }
    }

    public boolean update(long id, WiseSaying wiseSaying) {
        String sql = "UPDATE wise_sayings SET author = ?, content = ? WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, wiseSaying.getAuthor());
            preparedStatement.setString(2, wiseSaying.getContent());
            preparedStatement.setLong(3, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Update error: " + e.getMessage());
            return false;
        }
    }

    public  boolean delete(long id) {
        String sql = "DELETE FROM wise_sayings WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    public WiseSaying getWiseSaying(long id) {
        String sql = "SELECT * FROM wise_sayings WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new WiseSaying(resultSet.getLong("id"), resultSet.getString("author"), resultSet.getString("content"));
            }
        } catch (Exception e) {
            System.err.println("Get error: " + e.getMessage());
        }
        return null; // 없으면 null 반환
    }

    public Set<Map.Entry<Long, WiseSaying>> getWiseSayings() {
        String sql = "SELECT * FROM wise_sayings";
        Set<Map.Entry<Long, WiseSaying>> wiseSayingsSet = new java.util.HashSet<>();
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String author = resultSet.getString("author");
                String content = resultSet.getString("content");
                WiseSaying wiseSaying = new WiseSaying(id, author, content);
                wiseSayingsSet.add(Map.entry(id, wiseSaying));
            }
        } catch (Exception e) {
            System.err.println("Get all error: " + e.getMessage());
        }
        return wiseSayingsSet; // 빈 Set 반환 가능
    }

    public long getMapSizeServ() {
        String sql = "SELECT COUNT(*) FROM wise_sayings";
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (Exception e) {
            System.err.println("Get size error: " + e.getMessage());
        }
        return 0; // 오류 발생 시 0 반환
    }
}
