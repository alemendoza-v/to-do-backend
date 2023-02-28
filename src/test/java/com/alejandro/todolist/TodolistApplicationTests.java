package com.alejandro.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.OS;

import com.alejandro.todolist.service.ToDoService;
import com.alejandro.todolist.model.ToDo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
	void testCreateToDo_2() {
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

	@Test
	void testGetAllToDos() {
		// GIVEN 
		toDoService.clearDB();
		ToDo newToDo = new ToDo("Go to the doctor", 3, null);
		ToDo savedToDo = toDoService.createToDo(newToDo);

		// WHEN 
		List<ToDo> toDos = toDoService.getAllToDos(null, null, null, null, 0, 0);

		// THEN
		assertTrue(toDos.contains(savedToDo));
	}

	@Test
	void testGetAllToDosSortedByPriorityDesc() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, null);
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, null);
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, null);
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("priority");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, "desc", null, 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo3);
		assertEquals(toDos.get(1), savedToDo1);
		assertEquals(toDos.get(2), savedToDo2);
	}

	@Test
	void testGetAllToDosSortedByPriorityAsc() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, null);
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, null);
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, null);
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("priority");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, "asc", null, 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo2);
		assertEquals(toDos.get(1), savedToDo1);
		assertEquals(toDos.get(2), savedToDo3);
	}

	@Test
	void testGetAllToDosSortedByDueDateAsc() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, "asc", null, 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo2);
		assertEquals(toDos.get(1), savedToDo1);
		assertEquals(toDos.get(2), savedToDo3);
	}

	@Test
	void testGetAllToDosSortedByDueDateDesc() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, "desc", null, 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo3);
		assertEquals(toDos.get(1), savedToDo1);
		assertEquals(toDos.get(2), savedToDo2);
	}

	@Test
	void testGetAllToDosFilteredByName() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<ToDo> toDos = toDoService.getAllToDos("go", null, null, "name", 0, 0);

		// THEN
		assertTrue(toDos.contains(savedToDo1));
		assertTrue(toDos.contains(savedToDo2));
		assertFalse(toDos.contains(savedToDo3));
	}

	@Test
	void testGetAllToDosFilteredByPriority() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 3, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<ToDo> toDos = toDoService.getAllToDos(null, null, null, "priority", 3, 0);

		// THEN
		assertTrue(toDos.contains(savedToDo1));
		assertFalse(toDos.contains(savedToDo2));
		assertTrue(toDos.contains(savedToDo3));
	}

	@Test
	void testGetAllToDosFilteredByDone() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 3, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		toDoService.setToDoAsDone(savedToDo2.getId());

		// WHEN
		List<ToDo> toDos = toDoService.getAllToDos(null, null, null, "done", 0, 0);

		// THEN
		assertFalse(toDos.contains(savedToDo1));
		assertTrue(toDos.contains(savedToDo2));
		assertFalse(toDos.contains(savedToDo3));
	}

	@Test
	void testGetAllToDosFilteredByUnDone() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 3, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		toDoService.setToDoAsDone(savedToDo2.getId());

		// WHEN
		List<ToDo> toDos = toDoService.getAllToDos(null, null, null, "undone", 0, 0);

		// THEN
		assertTrue(toDos.contains(savedToDo1));
		assertFalse(toDos.contains(savedToDo2));
		assertTrue(toDos.contains(savedToDo3));
	}

	@Test
	void testGetAllToDosSortedByPriorityAscFilteredByUnDone() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		toDoService.setToDoAsDone(savedToDo3.getId());

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("priority");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, "asc", "undone", 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo2);
		assertEquals(toDos.get(1), savedToDo1);
		assertFalse(toDos.contains(savedToDo3));
	}

	@Test
	void testGetAllToDosSortedByDueDateDescFilteredByName() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
		List<ToDo> toDos = toDoService.getAllToDos("go", sorting, "desc", "name", 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo1);
		assertEquals(toDos.get(1), savedToDo2);
		assertFalse(toDos.contains(savedToDo3));
	}

	@Test
	void testGetAllToDosSortedByDueDateAndPriority() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate, priority");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, "desc", null, 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo3);
		assertEquals(toDos.get(1), savedToDo1);
		assertEquals(toDos.get(2), savedToDo2);
	}
}
