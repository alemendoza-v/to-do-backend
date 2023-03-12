package com.alejandro.todolist.dao;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import com.alejandro.todolist.model.ToDo;

public interface ToDoDao {
    ToDo createToDo(ToDo todo);

    Map<String,Object> getAllToDos(String text, List<String> sort_by, List<String> order_by, List<String> filter_by, int priority, int page);

    Optional<ToDo> getToDoById(UUID id); 

    void deleteToDoById(UUID id);

    ToDo updateToDoById(UUID id, ToDo newToDo);

    ToDo setToDoAsDone(UUID id);

    ToDo setToDoAsUndone(UUID id);

    void clearDB();
}
