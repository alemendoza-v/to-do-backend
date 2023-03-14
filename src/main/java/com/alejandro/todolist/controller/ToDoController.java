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

    @PostMapping
    public ToDo addToDo(@Valid @RequestBody ToDo toDo) {
        return toDoService.createToDo(toDo);
    }

    @GetMapping
    public ResponseEntity<Object> getAllToDos(@RequestParam(defaultValue = "") String text,
                                  @RequestParam(required = false) List<String> sort_by,
                                  @RequestParam(required = false) List<String> order_by,
                                  @RequestParam(required = false) List<String> filter_by,
                                  @RequestParam(defaultValue = "0") int priority,
                                  @RequestParam(defaultValue = "0") int page) {
        Map<String,Object> result = toDoService.getAllToDos(text, sort_by, order_by, filter_by, priority, page);
        return ResponseHandler.generateResponse(result.get("prev"), result.get("next"), HttpStatus.OK, result.get("todos"));
    }

    @DeleteMapping(path = "{id}")
    public void deleteToDoById(@PathVariable("id") UUID id) {
        toDoService.deleteToDo(id);
    }

    @PutMapping(path = "{id}")
    public ToDo updateToDoById(@PathVariable("id") UUID id, @RequestBody @Valid ToDo toDo) {
        return toDoService.updateToDo(id, toDo);
    }

    @PostMapping(path = "{id}/done")
    public ToDo setToDoAsDone(@PathVariable("id") UUID id) {
        return toDoService.setToDoAsDone(id);
    }

    @PostMapping(path = "{id}/undone") 
    public ToDo setToDoAsUndone(@PathVariable("id") UUID id) {
        return toDoService.setToDoAsUndone(id);
    }
}
