package com.alejandro.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.todolist.dao.ToDoDao;
import com.alejandro.todolist.model.ToDo;

import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {

    @Autowired
    private ToDoDao toDoDao;

    public ToDo createToDo(ToDo toDo) {
        return toDoDao.createToDo(toDo);
    }

    public List<ToDo> getAllToDos(String text, List<String> sort_by, List<String> order_by, String filter_by, int priority, int page) {
        return toDoDao.getAllToDos(text, sort_by, order_by, filter_by, priority, page);
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

    public void clearDB() {
        toDoDao.clearDB();
    }
}
