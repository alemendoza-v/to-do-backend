package com.alejandro.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import com.alejandro.todolist.service.ToDoService;
import com.alejandro.todolist.model.ToDo;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("unchecked")
@SpringBootTest
class ToDoServiceTests {

	@Autowired
	private ToDoService toDoService;

	@Test
	void testCreateToDo_1() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo = new ToDo("Go to the doctor", 3, LocalDate.now());

		// WHEN 
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

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
		toDoService.clearDB();
		ToDo newToDo = new ToDo("Go to the doctor", 3, null);

		// WHEN 
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

		// THEN
		assertEquals(newToDo.getText(), savedToDo.getText());
		assertEquals(newToDo.getDoneDate(), savedToDo.getDoneDate());
		assertEquals(newToDo.getDueDate(), savedToDo.getDueDate());
		assertEquals(newToDo.getIsDone(), savedToDo.getIsDone());
		assertEquals(newToDo.getPriority(), savedToDo.getPriority());
	}

    @Test
	void testCreateToDo_3() {
		// GIVEN
		ToDo newToDo = new ToDo("", 3, null);

		// WHEN 
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

		// THEN
        assertNull(savedToDo);
	}

	@Test
	void testGetAllToDos() {
		// GIVEN 
		toDoService.clearDB();
		ToDo newToDo = new ToDo("Go to the doctor", 3, null);
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

		// WHEN 
		Map<String, Object> response = toDoService.getAllToDos(null, null, null, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

		// THEN
		assertTrue(toDos.contains(savedToDo));
	}

	@Test
	void testGetAllToDosSortedByPriorityDesc() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, null);
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, null);
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, null);
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("priority");
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
        Map<String, Object> response = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, null);
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, null);
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("priority");
        List<String> ordering = new ArrayList<>();
        ordering.add("asc");
        Map<String, Object> response = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
        List<String> ordering = new ArrayList<>();
        ordering.add("asc");
        Map<String, Object> response = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
        Map<String, Object> response = toDoService.getAllToDos(null, sorting, ordering, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		// WHEN
        List<String> filtering = new ArrayList<>();
		filtering.add("text");
        Map<String, Object> response = toDoService.getAllToDos("go", null, null, filtering, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		// WHEN
        List<String> filtering = new ArrayList<>();
		filtering.add("priority");
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, filtering, 3, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		toDoService.setToDoAsDone(savedToDo2.getId());

		// WHEN
		List<String> filtering = new ArrayList<>();
		filtering.add("done");
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, filtering, 3, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		toDoService.setToDoAsDone(savedToDo2.getId());

		// WHEN
		List<String> filtering = new ArrayList<>();
		filtering.add("undone");
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, filtering, 3, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

		// THEN
		assertTrue(toDos.contains(savedToDo1));
		assertFalse(toDos.contains(savedToDo2));
		assertTrue(toDos.contains(savedToDo3));
	}

    @Test
	void testGetAllToDosFilteredByUnDoneAndPriority() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 3, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		toDoService.setToDoAsDone(savedToDo1.getId());

		// WHEN
		List<String> filtering = new ArrayList<>();
		filtering.add("undone");
        filtering.add("priority");
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, filtering, 3, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

		// THEN
		assertFalse(toDos.contains(savedToDo1));
		assertFalse(toDos.contains(savedToDo2));
		assertTrue(toDos.contains(savedToDo3));
	}

    @Test
	void testGetAllToDosFilteredByDoneAndPriorityAndName() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 3, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 3, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 1, LocalDate.of(2023, 3, 3));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");
        ToDo newToDo4 = new ToDo("Write the technical log", 2, LocalDate.of(2023, 3, 4));
		ToDo savedToDo4 = (ToDo) toDoService.createToDo(newToDo4).get("todo");
        ToDo newToDo5 = new ToDo("Read The Phoenix Project", 1, LocalDate.of(2023, 3, 8));
		ToDo savedToDo5 = (ToDo) toDoService.createToDo(newToDo5).get("todo");

		toDoService.setToDoAsDone(savedToDo1.getId());
		toDoService.setToDoAsDone(savedToDo2.getId());
        
		// WHEN
		List<String> filtering = new ArrayList<>();
		filtering.add("done");
        filtering.add("priority");
        filtering.add("name");
        Map<String, Object> response = toDoService.getAllToDos("go", null, null, filtering, 3, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

		// THEN
		assertTrue(toDos.contains(savedToDo1));
		assertTrue(toDos.contains(savedToDo2));
        assertFalse(toDos.contains(savedToDo3));
        assertFalse(toDos.contains(savedToDo4));
        assertFalse(toDos.contains(savedToDo5));
	}
    
	@Test
	void testGetAllToDosSortedByPriorityAscFilteredByUnDone() {
		// GIVEN
		toDoService.clearDB();
		ToDo newToDo1 = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		toDoService.setToDoAsDone(savedToDo3.getId());

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("priority");
        List<String> ordering = new ArrayList<>();
        ordering.add("asc");
        List<String> filtering = new ArrayList<>();
		filtering.add("undone");
        Map<String, Object> response = toDoService.getAllToDos(null, sorting, ordering, filtering, 3, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
        List<String> filtering = new ArrayList<>();
		filtering.add("text");
        Map<String, Object> response = toDoService.getAllToDos("go", sorting, ordering, filtering, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo1 = (ToDo) toDoService.createToDo(newToDo1).get("todo");
		ToDo newToDo2 = new ToDo("Go to the office", 1, LocalDate.of(2023, 3, 2));
		ToDo savedToDo2 = (ToDo) toDoService.createToDo(newToDo2).get("todo");
		ToDo newToDo3 = new ToDo("Write the weekly essay", 3, LocalDate.of(2023, 3, 6));
		ToDo savedToDo3 = (ToDo) toDoService.createToDo(newToDo3).get("todo");
        ToDo newToDo4 = new ToDo("Walk my dog", 2, LocalDate.of(2023, 3, 2));
		ToDo savedToDo4 = (ToDo) toDoService.createToDo(newToDo4).get("todo");
        ToDo newToDo5 = new ToDo("Buy groceries", 3, LocalDate.of(2023, 3, 3));
		ToDo savedToDo5 = (ToDo) toDoService.createToDo(newToDo5).get("todo");

		// WHEN
		List<String> sorting = new ArrayList<>();
		sorting.add("dueDate");
        sorting.add("priority");
        List<String> ordering = new ArrayList<>();
        ordering.add("desc");
        ordering.add("desc");
		Map<String, Object> response = toDoService.getAllToDos("go", sorting, ordering, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

        // WHEN 
        toDoService.deleteToDo(savedToDo.getId());
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

        // THEN
        assertEquals(toDos.size(), 0);
    }

    @Test   
    void testDeleteToDo_2() {
        // GIVEN 
        toDoService.clearDB();
        ToDo newToDo = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		toDoService.createToDo(newToDo).get("data");

        // WHEN 
        toDoService.deleteToDo(new UUID(0, 0)); // Wrong ID
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

        // THEN
        assertEquals(1, toDos.size());
    }

    @Test   
    void testUpdateToDo_1() {
        // GIVEN 
        toDoService.clearDB();
        ToDo newToDo = new ToDo("Go to the doctor", 2, LocalDate.of(2023, 3, 3));
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

        // WHEN 
        newToDo = new ToDo("Play valorant", 3, LocalDate.of(2023, 3, 3));
        ToDo updatedToDo = (ToDo) toDoService.updateToDo(savedToDo.getId(), newToDo).get("todo");
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

        // WHEN 
        newToDo = new ToDo("Play valorant", 3, LocalDate.of(2023, 3, 3));
        ToDo updatedToDo = (ToDo) toDoService.updateToDo(newToDo.getId(), newToDo).get("todo"); // Wrong ID
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

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
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");

		// WHEN 
        savedToDo = (ToDo) toDoService.setToDoAsDone(savedToDo.getId()).get("todo");

		// THEN
		assertTrue(savedToDo.getIsDone());
        assertNotNull(savedToDo.getDoneDate());
	}

    @Test
	void testToDoUndone() {
		// GIVEN
        toDoService.clearDB();
		ToDo newToDo = new ToDo("Go to the doctor", 3, LocalDate.now());
		ToDo savedToDo = (ToDo) toDoService.createToDo(newToDo).get("todo");
        savedToDo = (ToDo) toDoService.setToDoAsDone(savedToDo.getId()).get("todo");

		// WHEN 
        savedToDo = (ToDo) toDoService.setToDoAsUndone(savedToDo.getId()).get("todo");

		// THEN
		assertFalse(savedToDo.getIsDone());
        assertNull(savedToDo.getDoneDate());
	}

    @Test
    void testToDoPagination_01() {
        // GIVEN
        toDoService.clearDB();
        ToDo newToDo1 = new ToDo("Go to the doctor1", 3, LocalDate.now());
		toDoService.createToDo(newToDo1);
        ToDo newToDo2 = new ToDo("Go to the doctor2", 3, LocalDate.now());
		toDoService.createToDo(newToDo2);
        ToDo newToDo3 = new ToDo("Go to the doctor3", 3, LocalDate.now());
		toDoService.createToDo(newToDo3);
        ToDo newToDo4 = new ToDo("Go to the doctor4", 3, LocalDate.now());
		toDoService.createToDo(newToDo4);
        ToDo newToDo5 = new ToDo("Go to the doctor5", 3, LocalDate.now());
		toDoService.createToDo(newToDo5);
        ToDo newToDo6 = new ToDo("Go to the doctor6", 3, LocalDate.now());
		toDoService.createToDo(newToDo6);
        ToDo newToDo7 = new ToDo("Go to the doctor7", 3, LocalDate.now());
		toDoService.createToDo(newToDo7);
        ToDo newToDo8 = new ToDo("Go to the doctor8", 3, LocalDate.now());
		toDoService.createToDo(newToDo8);
        ToDo newToDo9 = new ToDo("Go to the doctor9", 3, LocalDate.now());
		toDoService.createToDo(newToDo9);
        ToDo newToDo10 = new ToDo("Go to the doctor10", 3, LocalDate.now());
		toDoService.createToDo(newToDo10);
        ToDo newToDo11 = new ToDo("Go to the doctor11", 3, LocalDate.now());
		toDoService.createToDo(newToDo11);

        // WHEN
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

        // THEN
        assertEquals(toDos.size(), 10);
    }

    @Test
    void testToDoPagination_02() {
        // GIVEN
        toDoService.clearDB();
        ToDo newToDo1 = new ToDo("Go to the doctor1", 3, LocalDate.now());
		toDoService.createToDo(newToDo1);
        ToDo newToDo2 = new ToDo("Go to the doctor2", 3, LocalDate.now());
		toDoService.createToDo(newToDo2);
        ToDo newToDo3 = new ToDo("Go to the doctor3", 3, LocalDate.now());
		toDoService.createToDo(newToDo3);
        ToDo newToDo4 = new ToDo("Go to the doctor4", 3, LocalDate.now());
		toDoService.createToDo(newToDo4);
        ToDo newToDo5 = new ToDo("Go to the doctor5", 3, LocalDate.now());
		toDoService.createToDo(newToDo5);
        ToDo newToDo6 = new ToDo("Go to the doctor6", 3, LocalDate.now());
		toDoService.createToDo(newToDo6);
        ToDo newToDo7 = new ToDo("Go to the doctor7", 3, LocalDate.now());
		toDoService.createToDo(newToDo7);
        ToDo newToDo8 = new ToDo("Go to the doctor8", 3, LocalDate.now());
		toDoService.createToDo(newToDo8);
        ToDo newToDo9 = new ToDo("Go to the doctor9", 3, LocalDate.now());
		toDoService.createToDo(newToDo9);

        // WHEN
        Map<String, Object> response = toDoService.getAllToDos(null, null, null, null, 0, 0);
        List<ToDo> toDos = (List<ToDo>) response.get("todos");

        // THEN
        assertEquals(9, toDos.size());
    }
}
