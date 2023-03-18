package com.alejandro.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.todolist.dao.ToDoDao;
import com.alejandro.todolist.model.ToDo;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@Service
public class ToDoService {

    @Autowired
    private ToDoDao toDoDao;

    public Map<String,Object> createToDo(ToDo toDo) {
        if(toDo.getText().length() > 120 || toDo.getText().equals("")) {
            Map<String,Object> responseMap = new HashMap<String, Object>();
                responseMap.put("error", "Please provide a valid to do name");
                responseMap.put("todo", null);
                return responseMap;
        }
        return toDoDao.createToDo(toDo);
    }

    public Map<String,Object> getAllToDos(String text, List<String> sort_by, List<String> order_by, List<String> filter_by, int priority, int page) {
        return toDoDao.getAllToDos(text, sort_by, order_by, filter_by, priority, page);
    }

    public Map<String,Object> deleteToDo(UUID id) {
        Optional<ToDo> toDo = toDoDao.getToDoById(id);

        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (toDo.isEmpty()) {
                responseMap.put("error", "To do was not found");
                responseMap.put("todo", null);
                return responseMap;
        } 
        toDoDao.deleteToDo(toDo.get());
        responseMap.put("todo", toDo);
        return responseMap;
    }

    public Map<String,Object> updateToDo(UUID id, ToDo toDo) {
        ToDo updatedToDo = toDoDao.updateToDoById(id, toDo);
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (updatedToDo == null) {
                responseMap.put("error", "To do could not be updated");
                responseMap.put("todo", null);
                return responseMap;
        }
        responseMap.put("todo", updatedToDo);
        return responseMap;
    } 

    public Map<String,Object> setToDoAsDone(UUID id) {
        ToDo updatedToDo = toDoDao.setToDoAsDone(id);
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (updatedToDo == null) {
                responseMap.put("error", "To do could not be set as done");
                responseMap.put("todo", null);
                return responseMap;
        }
        responseMap.put("todo", updatedToDo);
        return responseMap;
    }

    public Map<String,Object> setToDoAsUndone(UUID id) {
        ToDo updatedToDo = toDoDao.setToDoAsUndone(id);
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (updatedToDo == null) {
                responseMap.put("error", "To do could not be set as done");
                responseMap.put("todo", null);
                return responseMap;
        }
        responseMap.put("todo", updatedToDo);
        return responseMap;
    }

    public void clearDB() {
        toDoDao.clearDB();
    }

    public Map<String,Object> calculateDurations() {
        return toDoDao.calculateDurations();
    }
}
