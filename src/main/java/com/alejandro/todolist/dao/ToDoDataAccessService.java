package com.alejandro.todolist.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.alejandro.todolist.model.ToDo;

@Repository("dao")
public class ToDoDataAccessService implements ToDoDao{
    private static List<ToDo> DB = new ArrayList<>();

    @Override
    public ToDo createToDo(ToDo toDo) {
        ToDo newToDo = new ToDo(toDo.getText(), toDo.getPriority(), toDo.getDueDate());
        DB.add(newToDo);
        return newToDo;
    }

    @Override
    public List<ToDo> getAllToDos() {
        return DB;
    }

    @Override  
    public List<ToDo> getAllToDos(String text, List<String> sort_by, String order_by, int page) {
        List<ToDo> sortedDB = DB;
        if(sort_by != null) {
            if (sort_by.contains("dueDate") && sort_by.contains("priority")) {
                sortedDB = DB.stream()
                            .sorted(
                                Comparator.comparing(ToDo::getDueDate,
                                Comparator.nullsLast(Comparator.reverseOrder()))
                                .thenComparing(ToDo::getPriority).reversed())
                            .collect(Collectors.toList());
            } else if (sort_by.contains("dueDate")) {
                sortedDB = DB.stream()
                            .sorted(
                                Comparator.comparing(ToDo::getDueDate,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                            .collect(Collectors.toList());
            } else if (sort_by.contains("priority")) {
                sortedDB = DB.stream()
                            .sorted((o1,o2)->{return o2.getPriority() - o1.getPriority();})
                            .collect(Collectors.toList());
            }
        }
        return sortedDB;
    }

    @Override
    public Optional<ToDo> getToDoById(UUID id) {
        return DB.stream()
                .filter(toDo -> toDo.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteToDoById(UUID id) {
        Optional<ToDo> toDo = getToDoById(id);

        if (toDo.isEmpty()) {
            return;
        } 
        DB.remove(toDo.get());
        return;
    }

    @Override
    public ToDo updateToDoById(UUID id, ToDo newToDo) {
        return getToDoById(id)
                .map(toDo -> {
                    int indexOfToDo = DB.indexOf(toDo);
                    if (indexOfToDo >= 0) {
                        DB.set(indexOfToDo, newToDo);
                        return newToDo;
                    }
                    return null;
                })
                .orElse(null);
    }

    @Override 
    public ToDo setToDoAsDone(UUID id) {
        return getToDoById(id)
            .map(toDo -> {
                toDo.setDone(true);
                toDo.setDoneDate(LocalDateTime.now());
                return toDo;
            })
            .orElse(null);
    }

    @Override
    public ToDo setToDoAsUndone(UUID id) {
        return getToDoById(id)
            .map(toDo -> {
                toDo.setDone(false);
                toDo.setDoneDate(null);
                return toDo;
            })
            .orElse(null);
    }
}
