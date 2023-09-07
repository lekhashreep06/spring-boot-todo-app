package com.app.todo.controller;

import com.app.todo.model.SqlResponse;
import com.app.todo.model.Todo;
import com.app.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/todo")
    public ResponseEntity<List> listAllTodos(@RequestParam(value = "isCompleted", required = false) Boolean isCompleted) {
        List<Todo> todos = todoService.listAllTodos(isCompleted);
        if(todos != null) {
            return ResponseEntity.status(HttpStatus.OK).body(todos);
        } else {
            List<SqlResponse> resList = new ArrayList<>();
            SqlResponse res = new SqlResponse();
            res.setStatus("Failure");
            resList.add(res);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resList);
        }
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Object> getTodo(@PathVariable int id) {
        Todo todo =  todoService.getTodo(id);
        if(todo != null) {
            return ResponseEntity.status(HttpStatus.OK).body(todo);
        } else {
            SqlResponse res = new SqlResponse();
            res.setStatus("Failure");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }

    @DeleteMapping("todo/{id}")
    public ResponseEntity deleteTodo(@PathVariable int id) {
        int isDeleted = todoService.deleteTodo(id);
        if(isDeleted > 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "todo/{id}", consumes = "application/json")
    public Todo updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }



    @PostMapping(value = "/todo", consumes = "application/json", produces = "application/json")
    public void createTodo(@RequestBody ArrayList<Todo> todoList) {
        todoService.createTodoBatch(todoList);
    }
}
