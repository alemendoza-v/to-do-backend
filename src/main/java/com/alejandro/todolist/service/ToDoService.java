package com.alejandro.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alejandro.todolist.dao.ToDoDao;
import com.alejandro.todolist.model.ToDo;

import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {

    private final ToDoDao toDoDao;

    @Autowired
    public ToDoService(@Qualifier("dao") ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    public ToDo createToDo(ToDo toDo) {
        return toDoDao.createToDo(toDo);
    }

    public List<ToDo> getAllToDos() {
        return toDoDao.getAllToDos();
    }

    public void deleteToDo(UUID id) {
        toDoDao.deleteToDoById(id);
    }

    public ToDo updateToDo(UUID id, ToDo toDo) {
        return toDoDao.updateToDoById(id, toDo);
    } 

    public ToDo setToDoAsDone(UUID id) {
        return toDoDao.setToDoAsDone(id);
    }

    public ToDo setToDoAsUndone(UUID id) {
        return toDoDao.setToDoAsUndone(id);
    }
}
