package com.alejandro.todolist.dao;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import com.alejandro.todolist.model.ToDo;

public interface ToDoDao {
    ToDo createToDo(ToDo todo);

    List<ToDo> getAllToDos();

    Optional<ToDo> getToDoById(UUID id); 

    void deleteToDoById(UUID id);

    ToDo updateToDoById(UUID id, ToDo newToDo);

    ToDo setToDoAsDone(UUID id);

    ToDo setToDoAsUndone(UUID id);
}
