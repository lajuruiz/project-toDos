package com.example.todos;


import com.example.todos.controllers.TodosController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TodosApplicationTests {
	@Autowired
	private TodosController todoController;

	@Test
	void contextLoads() {
		Assertions.assertThat(todoController).isNotNull();
	}

}
