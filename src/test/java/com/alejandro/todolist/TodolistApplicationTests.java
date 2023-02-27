package com.alejandro.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import com.alejandro.todolist.service.ToDoService;
import com.alejandro.todolist.model.ToDo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TodolistApplicationTests {

	@Autowired
	private ToDoService toDoService;

	@Test
	void testCreateToDo_1() {
		// GIVEN
		ToDo newToDo = new ToDo("Go to the doctor", 3, LocalDate.now());

		// WHEN 
		ToDo savedToDo = toDoService.createToDo(newToDo);

		// THEN
		assertEquals(newToDo.getText(), savedToDo.getText());
		assertEquals(newToDo.getDoneDate(), savedToDo.getDoneDate());
		assertEquals(newToDo.getDueDate(), savedToDo.getDueDate());
		assertEquals(newToDo.getIsDone(), savedToDo.getIsDone());
		assertEquals(newToDo.getPriority(), savedToDo.getPriority());
	}

	@Test
	void testCreateTodo_2() {
		// GIVEN
		ToDo newToDo = new ToDo("Go to the doctor", 3, null);

		// WHEN 
		ToDo savedToDo = toDoService.createToDo(newToDo);

		// THEN
		assertEquals(newToDo.getText(), savedToDo.getText());
		assertEquals(newToDo.getDoneDate(), savedToDo.getDoneDate());
		assertEquals(newToDo.getDueDate(), savedToDo.getDueDate());
		assertEquals(newToDo.getIsDone(), savedToDo.getIsDone());
		assertEquals(newToDo.getPriority(), savedToDo.getPriority());
	}
}
