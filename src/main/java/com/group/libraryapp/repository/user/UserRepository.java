package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //생성
    public void saveUser(String name, long age){
        String sql = "INSERT INTO user (name, age) VALUES (?,?)";
        jdbcTemplate.update(sql, name, age);
    }

    //조회
    public List<UserResponse> getUsers(){
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

    //수정
    public boolean isUserNotExist(Long id){
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(sql,(rs, rpwNum) -> 0, id).isEmpty();
    }
    public void updateUser(String name, Long id){
        String sql = "UPDATE user SET name=? WHERE id= ?";
        jdbcTemplate.update(sql,name,id);
    }

    //삭제
    public boolean isUserNotExist(String name){
        String sql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.query(sql,(rs, rpwNum) -> 0, name).isEmpty();
    }
    public void deleteUser(String name){
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql,name);
    }

}
