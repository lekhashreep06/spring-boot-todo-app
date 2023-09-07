package com.app.todo;

import com.app.todo.dao.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoApplication implements CommandLineRunner {

	@Autowired
	TodoDao todoDao;

	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		todoDao.createTable();
	}

}
