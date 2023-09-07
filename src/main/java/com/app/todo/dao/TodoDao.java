package com.app.todo.dao;

import com.app.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public class TodoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public TodoDao() {
    }

    public void createTable() {
        String sql =  "CREATE TABLE IF NOT EXISTS todo(id int primary key NOT NULL AUTO_INCREMENT, task varchar(100) NOT NULL, isCompleted BOOLEAN)";
        this.jdbcTemplate.update(sql);
    }

    public Todo insertTodo(Todo todo) {
        try {
            String sql = "insert into todo(task, isCompleted) values(?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, todo.getTask());
                pst.setBoolean(2,false);
                return pst;
            }, keyHolder);
            todo.setId(keyHolder.getKey().intValue());
            return todo;
        } catch(DataAccessException e) {
            return null;
        }
    }

    public List<Todo> listAllTodo(Boolean isCompleted) {
        try {
            String sql = "";
            RowMapper<Todo> todoRowMapper = new RowMapper<Todo>() {
                @Override
                public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Todo todo = new Todo();
                    todo.setId(rs.getInt("id"));
                    todo.setTask(rs.getString("task"));
                    todo.setIsCompleted(rs.getBoolean("isCompleted"));
                    return todo;
                }
            };
            if(isCompleted != null) {
                sql = "select * from todo where isCompleted = ?";
                return this.jdbcTemplate.query(sql, todoRowMapper, isCompleted);
            } else {
                sql = "select * from todo";
                return this.jdbcTemplate.query(sql, todoRowMapper);
            }

        } catch(DataAccessException e) {
            return null;
        }
    }

    /*
    public List<Todo> getTodo(int id) {
        try {
            String sql = "select * from todo where id=?";
            return this.jdbcTemplate.query(sql, new RowMapper<Todo>() {
                @Override
                public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Todo todo = new Todo();
                    todo.setId(rs.getInt("id"));
                    todo.setTasks(rs.getString("task"));
                    todo.setCompleted(rs.getBoolean("isCompleted"));
                    System.out.println(todo.isCompleted());
                    return todo;
                }
            }, id);
        } catch(DataAccessException e) {
            return null;
        }
    }
    */

    public Todo getTodoAsObject(int id) {
        try {
            String sql = "select * from todo where id=?";
            return this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Todo>(Todo.class), id);
        } catch(DataAccessException e) {
            return null;
        }
    }

    public Todo updateTodo(int id, Todo todo) {
        try {
            System.out.println(todo.getTask()+ todo.getIsCompleted());
            if(todo.getTask() != null) {
                String sql = "update todo set task=? where id=?";
                jdbcTemplate.update(sql, todo.getTask(), id);
            }
            if(todo.getIsCompleted() != null) {
                String sql = "update todo set isCompleted=? where id = ?";
                jdbcTemplate.update(sql, todo.getIsCompleted(), id);
            }
            todo = getTodoAsObject(id);
            System.out.println(todo.getTask()+ todo.getIsCompleted());
            return todo;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public int deleteTodo(int id) {
        try {
            String sql = "delete from todo where id=?";
            return jdbcTemplate.update(sql, id);
        } catch(DataAccessException e) {
            return -1;
        }
    }

    public int[] insertTodoBatch(ArrayList<Todo> todoResList) {
        try {
            String sql = "insert into todo(task, isCompleted) values(?, ?)";
            int[] sqlRes = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Todo todo = todoResList.get(i);
                    ps.setString(1, todo.getTask());
                    ps.setBoolean(2, todo.getIsCompleted());
                }

                @Override
                public int getBatchSize() {
                    return todoResList.size();
                }
            });
            return sqlRes;
        } catch(DataAccessException e) {
            return new int[-1];
        }
    }
}
