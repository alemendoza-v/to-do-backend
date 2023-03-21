package com.alejandro.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.todolist.model.ToDo;
import com.alejandro.todolist.repository.ToDoRepository;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepo;

    /**
     * If the todo text is longer than 120 characters or is empty, return an error message, otherwise
     * create the todo
     * 
     * @param toDo This is the to do object that we are creating.
     * @return A map with an error message and null to-do, if there is an error and a map with a to-do if it was successful
     */
    public Map<String,Object> createToDo(ToDo toDo) {
        if(toDo.getText().length() > 120 || toDo.getText().equals("")) {
            Map<String,Object> responseMap = new HashMap<String, Object>();
                responseMap.put("error", "Please provide a valid to do name");
                responseMap.put("todo", null);
                return responseMap;
        }
        return toDoRepo.createToDo(toDo);
    }

    /**
     * It returns a map of all todos, sorted by the sort_by list, ordered by the order_by list,
     * filtered by the filter_by list, with a priority of priority, and a page number of page
     * 
     * @param text This is the text that you want to search for in the todo list.
     * @param sort_by A list of fields to sort by.
     * @param order_by This is a list of fields to order by.
     * @param filter_by This is a list of strings that can be used to filter the results. The strings
     * are the names of the fields in the ToDo class.
     * @param priority 1 for high, 2 for medium, 3 for low
     * @param page The page number of the results to be returned.
     * @return A map of the todos.
     */
    public Map<String,Object> getAllToDos(String text, List<String> sort_by, List<String> order_by, List<String> filter_by, int priority, int page) {
        return toDoRepo.getAllToDos(text, sort_by, order_by, filter_by, priority, page);
    }

    /**
     * If the to do is found, delete it and return the deleted to do. If the to do is not found, return
     * an error message
     * 
     * @param id The id of the todo you want to delete
     * @return A map with a key of "todo" and a value of the deleted todo.
     */
    public Map<String,Object> deleteToDo(UUID id) {
        Optional<ToDo> toDo = toDoRepo.getToDoById(id);

        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (toDo.isEmpty()) {
                responseMap.put("error", "To do was not found");
                responseMap.put("todo", null);
                return responseMap;
        } 
        toDoRepo.deleteToDo(toDo.get());
        responseMap.put("todo", toDo);
        return responseMap;
    }

    /**
     * > Update a to do by id
     * 
     * @param id The id of the todo you want to update
     * @param toDo The to do object that you want to update.
     * @return A map with a key of "todo" and a value of the updated todo object.
     */
    public Map<String,Object> updateToDo(UUID id, ToDo toDo) {
        if(toDo.getText().length() > 120 || toDo.getText().equals("")) {
            Map<String,Object> responseMap = new HashMap<String, Object>();
                responseMap.put("error", "Please provide a valid to do name");
                responseMap.put("todo", null);
                return responseMap;
        }
        Map<String,Object> updatedToDo = toDoRepo.updateToDoById(id, toDo);
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (updatedToDo.get("todo") == null) {
                responseMap.put("error", updatedToDo.get("error"));
                responseMap.put("todo", null);
                return responseMap;
        }
        responseMap.put("todo", updatedToDo);
        return responseMap;
    } 

    /**
     * > This function sets the todo with the given id as done
     * 
     * @param id The id of the to do item to be set as done.
     * @return A map with a key of "todo" and a value of the updated todo object.
     */
    public Map<String,Object> setToDoAsDone(UUID id) {
        ToDo updatedToDo = toDoRepo.setToDoAsDone(id);
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (updatedToDo == null) {
                responseMap.put("error", "To do could not be set as done");
                responseMap.put("todo", null);
                return responseMap;
        }
        responseMap.put("todo", updatedToDo);
        return responseMap;
    }

    /**
     * It takes a todo id, finds the todo in the database, sets the todo as undone, and returns the
     * updated todo
     * 
     * @param id The id of the to do item to be set as done.
     * @return A map with a key of "todo" and a value of the updated todo object.
     */
    public Map<String,Object> setToDoAsUndone(UUID id) {
        ToDo updatedToDo = toDoRepo.setToDoAsUndone(id);
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if (updatedToDo == null) {
                responseMap.put("error", "To do could not be set as un done");
                responseMap.put("todo", null);
                return responseMap;
        }
        responseMap.put("todo", updatedToDo);
        return responseMap;
    }

    /**
     * Clear the database.
     */
    public void clearDB() {
        toDoRepo.clearDB();
    }

    /**
     * "Calculate the average durations of all done to-do items."
     * 
     * The function is defined in the ToDoRepository class. It returns a Map of String to Object. The
     * String is the name of the statistic, and the Object is the value of the statistic
     * 
     * @return A map of the average duration of all todos.
     */
    public Map<String,Object> calculateDurations() {
        return toDoRepo.calculateDurations();
    }
}
