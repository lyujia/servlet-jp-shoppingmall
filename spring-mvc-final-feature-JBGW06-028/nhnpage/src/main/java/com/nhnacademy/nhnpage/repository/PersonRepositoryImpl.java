package com.nhnacademy.nhnpage.repository;

import com.nhnacademy.nhnpage.domain.Admin;
import com.nhnacademy.nhnpage.domain.Person;
import com.nhnacademy.nhnpage.domain.User;
import com.nhnacademy.nhnpage.exception.IdPassNotCorrectException;
import com.nhnacademy.nhnpage.exception.NotPersonException;
import com.nhnacademy.nhnpage.exception.PersonAlreadyExistsException;
import com.nhnacademy.nhnpage.transaction.DbConnectionThreadLocal;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PersonRepositoryImpl implements PersonRepository{
    @Override
    public Person findById(String id) {
        String sql = "SELECT * FROM Persons WHERE id = ?";
        try (Connection conn = DbConnectionThreadLocal.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if(rs.getString("role").equals("ADMIN")) {
                        return new Admin(rs.getString("id"), rs.getString("name"),rs.getString("password"));
                    }else{
                        return new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
                    }
                } else {
                    throw new NotPersonException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred", e);
        }
    }

    @Override
    public Person matches(String id, String password) {
        String sql = "SELECT * FROM Person WHERE id = ? AND password = ?";
        try (Connection conn = DbConnectionThreadLocal.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if(rs.getString("role").equals("ADMIN")) {
                        return new Admin(rs.getString("id"), rs.getString("name"),rs.getString("password"));
                    }else{
                        return new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
                    }

                } else {
                    throw new IdPassNotCorrectException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred", e);
        }
    }

    @Override
    public Person register(Person person) {
        String sql = "INSERT INTO Person (id, password) VALUES (?, ?)";
        try (Connection conn = DbConnectionThreadLocal.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, person.getId());
            pstmt.setString(2, person.getPassword());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new PersonAlreadyExistsException();
            }
            return person;
        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred", e);
        }
    }

    @Override
    public Person update(Person person) {
        String sql = "UPDATE Person SET password = ? WHERE id = ?";
        try (Connection conn = DbConnectionThreadLocal.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, person.getPassword());
            pstmt.setString(2, person.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new NotPersonException();
            }
            return person;
        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred", e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Person WHERE id = ?";
        try (Connection conn = DbConnectionThreadLocal.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new NotPersonException();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred", e);
        }
    }



}
