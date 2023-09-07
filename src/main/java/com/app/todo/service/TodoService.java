package com.app.todo.service;

import com.app.todo.dao.TodoDao;
import com.app.todo.model.SqlResponse;
import com.app.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoService {

    @Autowired
    TodoDao todoDao;
    public List<Todo> listAllTodos (Boolean isCompleted) {
        List<Todo> todos = todoDao.listAllTodo(isCompleted);
        if(todos != null && todos.size() > 0) {
            return todos;
        } else {
            return null;
        }
    }
    public Todo getTodo (int id) {
        Todo todo = todoDao.getTodoAsObject(id);
        if(todo != null) {
            return todo;
        } else {
            return null;
        }
    }

    public Todo updateTodo(int id, Todo todo) {
        return todoDao.updateTodo(id, todo);

    }
    public ArrayList<Todo> createTodo(ArrayList<Todo> todoList) {
        ArrayList<Todo> todoResList = new ArrayList<>();
        for(Todo todo : todoList) {
            todo = todoDao.insertTodo(todo);
            todoResList.add(todo);
        }
        return todoResList;
    }

    public int[] createTodoBatch(ArrayList<Todo> todoList) {
        return todoDao.insertTodoBatch(todoList);
    }

    public int deleteTodo(int id) {
        return todoDao.deleteTodo(id);
    }
}
