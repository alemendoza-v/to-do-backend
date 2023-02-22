package com.alejandro.todolist.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alejandro.todolist.model.ToDo;

public class ToDoDataAccessService implements ToDoDao{
    private static List<ToDo> DB = new ArrayList<>();

    @Override
    public ToDo insertToDo(UUID id, ToDo toDo) {
        ToDo newToDo = new ToDo(id, toDo.getText(), toDo.getPriority());
        DB.add(newToDo);
        return newToDo;
    }
}
