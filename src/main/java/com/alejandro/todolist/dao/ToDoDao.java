package com.alejandro.todolist.dao;

import java.util.UUID;

import com.alejandro.todolist.model.ToDo;

public interface ToDoDao {
    ToDo insertToDo(UUID id, ToDo todo);

    default ToDo createToDo(ToDo toDo) {
        UUID id = UUID.randomUUID();
        return insertToDo(id, toDo);
    }
}
