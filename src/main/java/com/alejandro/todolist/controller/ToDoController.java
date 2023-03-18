package com.alejandro.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.todolist.model.ToDo;
import com.alejandro.todolist.response.ResponseHandler;
import com.alejandro.todolist.service.ToDoService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RequestMapping(path="api/v1/todos")
@RestController
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    /**
     * It takes a ToDo object as a request body, validates it, and then calls the createToDo function in
     * the ToDoService class
     * 
     * @param toDo The ToDo object that we want to add to the database.
     * @return A ResponseEntity object is being returned.
     */
    @PostMapping
    public ResponseEntity<Object> addToDo(@Valid @RequestBody ToDo toDo) {
        Map<String,Object> result = toDoService.createToDo(toDo);
        if(result.get("todo") == null) {
            return ResponseHandler.generateResponse(result.get("error"), HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse(result.get("todo"), HttpStatus.CREATED);
    }

    /**
     * It takes in a bunch of parameters, calls the toDoService to get the data, and then returns a
     * response
     * 
     * @param text This is the text that you want to search for in the todo.
     * @param sort_by The field to sort the results by.
     * @param order_by This is a list of fields by which you want to sort the results.
     * @param filter_by This is a list of fields that you want to filter by.
     * @param priority 0 for all, 1 for high, 2 for medium, 3 for low
     * @param page The page number of the results to be returned.
     * @return ResponseEntity<Object>
     */
    @GetMapping
    public ResponseEntity<Object> getAllToDos(@RequestParam(required = false, defaultValue = "") String text,
                                  @RequestParam(required = false) List<String> sort_by,
                                  @RequestParam(required = false) List<String> order_by,
                                  @RequestParam(required = false) List<String> filter_by,
                                  @RequestParam(required = false, defaultValue = "0") int priority,
                                  @RequestParam(required = false, defaultValue = "0") int page) {
        Map<String,Object> result = toDoService.getAllToDos(text, sort_by, order_by, filter_by, priority, page);
        return ResponseHandler.generateResponse(result.get("pages"), result.get("prev"), result.get("next"), HttpStatus.OK, result.get("todos"));
    }

    /**
     * It deletes a todo by id.
     * 
     * @param id The id of the todo item to delete
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteToDoById(@PathVariable("id") UUID id) {
        Map<String,Object> result = toDoService.deleteToDo(id);
        if (result.get("todo") == null) {
            return ResponseHandler.generateResponse(result.get("error"), HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT);
    }

    /**
     * It updates the todo with the given id.
     * 
     * @param id The id of the ToDo we want to update.
     * @param toDo The ToDo object that we want to update.
     * @return A ToDo object
     */
    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateToDoById(@PathVariable("id") UUID id, @RequestBody @Valid ToDo toDo) {
        Map<String,Object> result = toDoService.updateToDo(id, toDo);
        if (result.get("todo") == null) {
            return ResponseHandler.generateResponse(result.get("error"), HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse(result.get("todo"), HttpStatus.OK);
    }

    /**
     * Set the todo with the given id as done.
     * 
     * @param id The id of the todo item
     * @return ToDo
     */
    @PostMapping(path = "{id}/done")
    public ResponseEntity<Object> setToDoAsDone(@PathVariable("id") UUID id) {
        Map<String,Object> result = toDoService.setToDoAsDone(id);
        if (result.get("todo") == null) {
            return ResponseHandler.generateResponse(result.get("error"), HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse(result.get("todo"), HttpStatus.OK);
    }

    /**
     * It sets the todo with the given id as undone
     * 
     * @param id The id of the todo item
     * @return A ToDo object
     */
    @PostMapping(path = "{id}/undone") 
    public ResponseEntity<Object> setToDoAsUndone(@PathVariable("id") UUID id) {
        Map<String,Object> result = toDoService.setToDoAsUndone(id);
        if (result.get("todo") == null) {
            return ResponseHandler.generateResponse(result.get("error"), HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse(result.get("todo"), HttpStatus.OK);
    }

    /**
     * It calculates the average, minimum and maximum duration of all the todos in the database
     * 
     * @return A map of the average duration of all todos and the average duration of todos that are
     * completed.
     */
    @GetMapping(path = "metrics")
    public ResponseEntity<Object> calculateDurations() {
        Map<String,Object> result = toDoService.calculateDurations();
        return ResponseHandler.generateResponse(result, HttpStatus.OK);
    }
}
