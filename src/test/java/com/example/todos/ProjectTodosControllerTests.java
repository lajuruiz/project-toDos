package com.example.todos;

import com.example.todos.persistence.entities.Todos;
import com.example.todos.domain.repositories.TodosRepository;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectTodosControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TodosRepository testsRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void getShouldReturnNoContentEmptyTodos() throws Exception {
		List<Todos> tests = new ArrayList<>();
		Mockito.when(testsRepository.findAll()).thenReturn(tests);

		this.mockMvc
			.perform(get("/api/todos"))
			.andDo(print())
			.andExpect(status().isNoContent())
			.andExpect(
				content().string(containsString(""))
			);
	}

	@Test
	public void getShouldReturnOKTodos() throws Exception {
		List<Todos> tests = List.of(
				new Todos(1, "todo 1", "description 1", new Date())
		);
		String json = objectMapper.writeValueAsString(tests);

		Mockito.when(testsRepository.findAll()).thenReturn(tests);

		this.mockMvc
				.perform(get("/api/todos"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnOKTodo() throws Exception {
		Todos todo = new Todos(1, "todo 1", "description 1", new Date());
		String json = objectMapper.writeValueAsString(todo);

		Mockito.when(testsRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

		this.mockMvc
				.perform(get("/api/todos/" + todo.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void getByIdShouldReturnNotFoundTodo() throws Exception {
		Mockito.when(testsRepository.findById(1)).thenReturn(Optional.empty());

		this.mockMvc
				.perform(get("/api/todos/1"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(
					content().string("")
				);
	}

	@Test
	public void postShouldReturnCreatedTodo() throws Exception {
		Todos todo = new Todos(1, "todo 1", "description 1", new Date());
		String json = objectMapper.writeValueAsString(todo);

		Mockito.when(testsRepository.save(Mockito.any(Todos.class))).thenReturn(todo);

		this.mockMvc
				.perform(
					post("/api/todos").contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void putShouldReturnCreatedTodo() throws Exception {
		Todos todo = new Todos(1, "todo 1", "description 1", new Date());
		String json = objectMapper.writeValueAsString(todo);

		Mockito.when(testsRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
		Mockito.when(testsRepository.save(Mockito.any(Todos.class))).thenReturn(todo);

		this.mockMvc
				.perform(
						put("/api/todos/" + todo.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(
					content().json(json)
				);
	}

	@Test
	public void putShouldReturnNotFoundTodo() throws Exception {
		Todos todo = new Todos(1, "todo updated name", "todo updated name", new Date());
		String json = objectMapper.writeValueAsString(todo);

		Mockito.when(testsRepository.findById(todo.getId())).thenReturn(Optional.empty());
		Mockito.when(testsRepository.save(Mockito.any(Todos.class))).thenReturn(todo);

		this.mockMvc
				.perform(
					put("/api/todos/" + todo.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void putShouldReturnBadRequestTodo() throws Exception {
		Todos todo = new Todos(null, "", "", new Date());
		String json = objectMapper.writeValueAsString(todo);

		Mockito.when(testsRepository.save(Mockito.any(Todos.class))).thenReturn(todo);

		this.mockMvc
				.perform(
					put("/api/todos/" + todo.getId()).contentType(MediaType.APPLICATION_JSON).content(json)
				)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteShouldReturnOkSuccessRemoveTodo() throws Exception {
		Integer testId = 1;
		Mockito.when(testsRepository.existsById(testId)).thenReturn(true);

		this.mockMvc
				.perform(
					delete("/api/todos/" + testId)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
					content().string("")
				);

		Mockito.verify(testsRepository).deleteById(testId); // check that the method was called
	}

	@Test
	public void deleteShouldReturnNoContentTodo() throws Exception {
		Integer testId = 2;
		Mockito.when(testsRepository.existsById(2)).thenReturn(false);

		this.mockMvc
				.perform(
					delete("/api/todos/" + testId)
				)
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(
					content().string("")
				);

		Mockito.verify(testsRepository, Mockito.never()).deleteById(testId); // check that the method was called
	}
}
