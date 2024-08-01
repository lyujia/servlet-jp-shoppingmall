package com.nhnacademy.nhnpage.repository;

import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.domain.Post.Category;
import com.nhnacademy.nhnpage.transaction.DbConnectionThreadLocal;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements PostRepository {


    @Override
    public Post registerPost(Post post) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO Post (title, content, authorId, isExistscomment,category,date) VALUES ( ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setString(3, post.getAuthorId());
            pstmt.setBoolean(4,false);
            pstmt.setString(5, post.getCategory().toString());
            pstmt.setTimestamp(6, Timestamp.valueOf(post.getDate().atStartOfDay()));


            pstmt.executeUpdate();
            return post;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register post", e);
        }
    }//등록한다.//postid는 넣을때 스테이틱 변수로 넣는다.

    @Override
    public List<Post> getPostsByAuthor(String authorId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Post WHERE authorId = ?";
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, authorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(rs.getString("title"), rs.getString("content"), rs.getString("authorId"), rs.getBoolean("isExistscomment"),Category.valueOf(rs.getString("category")),rs.getTimestamp("date").toLocalDateTime().toLocalDate()));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve posts by author", e);
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByAuthorAndCategory(String authorId, Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Post WHERE authorId = ? AND category = ?";
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, authorId);
            pstmt.setString(2, category.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("authorId"),
                            rs.getBoolean("isExistscomment"),
                            Category.valueOf(rs.getString("category")),
                            rs.getTimestamp("date").toLocalDateTime().toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve posts by author and category", e);
        }
        return posts;
    }


    @Override
    public List<Post> getPostsByCategory(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Post WHERE category = ?";
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, category.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(rs.getString("title"), rs.getString("content"), rs.getString("authorId"), rs.getBoolean("isExistscomment"),Category.valueOf(rs.getString("category")),rs.getTimestamp("date").toLocalDateTime().toLocalDate()));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve posts by category", e);
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByCommentExists() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Post WHERE isExistscomment = 0";
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(

                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("authorId"),
                            rs.getBoolean("isExistscomment"),
                            Category.valueOf(rs.getString("category")),
                            rs.getTimestamp("date").toLocalDateTime().toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve posts with comments", e);
        }
        return posts;
    }
}
