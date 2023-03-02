package com.alejandro.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import com.alejandro.todolist.service.ToDoService;
import com.alejandro.todolist.model.ToDo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    // @Test
	// void testCreateToDo_3() {
	// 	// GIVEN
	// 	ToDo newToDo = new ToDo("", 3, null);

	// 	// WHEN 
	// 	ToDo savedToDo = toDoService.createToDo(newToDo);

	// 	// THEN
    //     assertNull(savedToDo);
	// }

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
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);

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
        List<String> ordering = new ArrayList<>();
        ordering.add("asc");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);

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
        List<String> ordering = new ArrayList<>();
        ordering.add("asc");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);

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
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);

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
        List<String> ordering = new ArrayList<>();
        ordering.add("asc");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, ordering, "undone", 0, 0);

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
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
		List<ToDo> toDos = toDoService.getAllToDos("go", sorting, ordering, "name", 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo1);
		assertEquals(toDos.get(1), savedToDo2);
		assertFalse(toDos.contains(savedToDo3));
	}

	@Test
	void testGetAllToDosSortedByDueDateDescAndPriorityDesc() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = toDoService.createToDo(newToDo1);
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = toDoService.createToDo(newToDo2);
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = toDoService.createToDo(newToDo3);
        ToDo newToDo4 = new ToDo("Walk my dog", 2, LocalDate.of(2023, 3, 2));
		ToDo savedToDo4 = toDoService.createToDo(newToDo4);
        ToDo newToDo5 = new ToDo("Buy groceries", 3, LocalDate.of(2023, 3, 3));
		ToDo savedToDo5 = toDoService.createToDo(newToDo5);

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
        sorting.add("priority");
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
        ordering.add("desc");
		List<ToDo> toDos = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);

		// THEN
		assertEquals(toDos.get(0), savedToDo3);
		assertEquals(toDos.get(1), savedToDo5);
		assertEquals(toDos.get(2), savedToDo1);
		assertEquals(toDos.get(3), savedToDo4);
		assertEquals(toDos.get(4), savedToDo2);
	}

    @Test   
    void testDeleteToDo_1() {
        // GIVEN 
        toDoService.clearDB();
        ToDo newToDo = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo = toDoService.createToDo(newToDo);

        // WHEN 
        toDoService.deleteToDo(savedToDo.getId());
        List<ToDo> toDos = toDoService.getAllToDos(null, null, null, null, 0, 0);

        // THEN
        assertEquals(toDos.size(), 0);
    }

    @Test   
    void testDeleteToDo_2() {
        // GIVEN 
        toDoService.clearDB();
        ToDo newToDo = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		toDoService.createToDo(newToDo);

        // WHEN 
        toDoService.deleteToDo(newToDo.getId()); // Wrong ID
        List<ToDo> toDos = toDoService.getAllToDos(null, null, null, null, 0, 0);

        // THEN
        assertEquals(toDos.size(), 1);
    }

    @Test   
    void testUpdateToDo_1() {
        // GIVEN 
        toDoService.clearDB();
        ToDo newToDo = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo = toDoService.createToDo(newToDo);

        // WHEN 
        newToDo = new ToDo("Play valorant", 3, LocalDate.of(2023, 3, 3));
        ToDo updatedToDo = toDoService.updateToDo(savedToDo.getId(), newToDo);
        List<ToDo> toDos = toDoService.getAllToDos(null, null, null, null, 0, 0);

        // THEN
        assertEquals(toDos.size(), 1);
        assertEquals(savedToDo.getId(), updatedToDo.getId());
        assertEquals(toDos.get(0).getId(), updatedToDo.getId());
        assertEquals(toDos.get(0).getCreatedAt(), updatedToDo.getCreatedAt());
        assertEquals(toDos.get(0).getText(), updatedToDo.getText());
		assertEquals(toDos.get(0).getDoneDate(), updatedToDo.getDoneDate());
		assertEquals(toDos.get(0).getDueDate(), updatedToDo.getDueDate());
		assertEquals(toDos.get(0).getIsDone(), updatedToDo.getIsDone());
		assertEquals(toDos.get(0).getPriority(), updatedToDo.getPriority());
    }

    @Test   
    void testUpdateToDo_2() {
        // GIVEN 
        toDoService.clearDB();
        ToDo newToDo = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo = toDoService.createToDo(newToDo);

        // WHEN 
        newToDo = new ToDo("Play valorant", 3, LocalDate.of(2023, 3, 3));
        ToDo updatedToDo = toDoService.updateToDo(newToDo.getId(), newToDo); // Wrong ID
        List<ToDo> toDos = toDoService.getAllToDos(null, null, null, null, 0, 0);

        // THEN
        assertEquals(toDos.size(), 1);
        assertNull(updatedToDo);
        assertEquals(toDos.get(0).getId(), savedToDo.getId());
        assertEquals(toDos.get(0).getCreatedAt(), savedToDo.getCreatedAt());
        assertEquals(toDos.get(0).getText(), savedToDo.getText());
		assertEquals(toDos.get(0).getDoneDate(), savedToDo.getDoneDate());
		assertEquals(toDos.get(0).getDueDate(), savedToDo.getDueDate());
		assertEquals(toDos.get(0).getIsDone(), savedToDo.getIsDone());
		assertEquals(toDos.get(0).getPriority(), savedToDo.getPriority());
    }

    @Test
	void testToDoDone() {
		// GIVEN
        toDoService.clearDB();
		ToDo newToDo = new ToDo("Go to the doctor", 3, LocalDate.now());
		ToDo savedToDo = toDoService.createToDo(newToDo);

		// WHEN 
        savedToDo = toDoService.setToDoAsDone(savedToDo.getId());

		// THEN
		assertTrue(savedToDo.getIsDone());
        assertNotNull(savedToDo.getDoneDate());
	}

    @Test
	void testToDoUndone() {
		// GIVEN
        toDoService.clearDB();
		ToDo newToDo = new ToDo("Go to the doctor", 3, LocalDate.now());
		ToDo savedToDo = toDoService.createToDo(newToDo);
        savedToDo = toDoService.setToDoAsDone(savedToDo.getId());

		// WHEN 
        savedToDo = toDoService.setToDoAsUndone(savedToDo.getId());

		// THEN
		assertFalse(savedToDo.getIsDone());
        assertNull(savedToDo.getDoneDate());
	}

    @Test
    void testToDoPagination() {
        // GIVEN
        toDoService.clearDB();
        ToDo newToDo1 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo1);
        ToDo newToDo2 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo2);
        ToDo newToDo3 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo3);
        ToDo newToDo4 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo4);
        ToDo newToDo5 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo5);
        ToDo newToDo6 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo6);
        ToDo newToDo7 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo7);
        ToDo newToDo8 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo8);
        ToDo newToDo9 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo9);
        ToDo newToDo10 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo10);
        ToDo newToDo11 = new ToDo("Go to the doctor", 3, LocalDate.now());
		toDoService.createToDo(newToDo11);

        // WHEN
        List<ToDo> toDos = toDoService.getAllToDos(null, null, null, null, 0, 0);

        // THEN
        assertEquals(toDos.size(), 10);
    }
}
