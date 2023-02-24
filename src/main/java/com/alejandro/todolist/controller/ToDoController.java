package com.alejandro.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.alejandro.todolist.service.ToDoService;

import java.util.List;
import java.util.UUID;

@RequestMapping(path="api/v1/todos")
@RestController
public class ToDoController {
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping
    public ToDo addToDo(@RequestBody ToDo toDo) {
        return toDoService.createToDo(toDo);
    }

    @GetMapping
    public List<ToDo> getAllToDos(@RequestParam(defaultValue = "") String text,
                                  @RequestParam(required = false) List<String> sort_by,
                                  @RequestParam(defaultValue = "desc") String order_by,
                                  @RequestParam(required = false) String filter_by,
                                  @RequestParam(defaultValue = "0") int priority,
                                  @RequestParam(defaultValue = "0") int page) {
        return toDoService.getAllToDos(text, sort_by, order_by, filter_by, priority, page);
    }

    @DeleteMapping(path = "{id}")
    public void deleteToDoById(@PathVariable("id") UUID id) {
        toDoService.deleteToDo(id);
    }

    @PutMapping(path = "{id}")
    public ToDo updateToDoById(@PathVariable("id") UUID id, @RequestBody ToDo toDo) {
        return toDoService.updateToDo(id, toDo);
    }

    @PostMapping(path = "{id}/done")
    public ToDo setToDoAsDone(@PathVariable("id") UUID id) {
        return toDoService.setToDoAsDone(id);
    }

    @PutMapping(path = "{id}/undone") 
    public ToDo setToDoAsUndone(@PathVariable("id") UUID id) {
        return toDoService.setToDoAsUndone(id);
    }
}
