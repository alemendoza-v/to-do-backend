package com.alejandro.todolist.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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

    private List<ToDo> filter(Predicate<ToDo> predicate, List<ToDo> list) {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    } 

    @Override  
    public List<ToDo> getAllToDos(String text, List<String> sort_by, String order_by, String filter_by, int priority, int page) {
        List<ToDo> returnList = DB;

        if (sort_by != null) {
            if (sort_by.contains("dueDate") && sort_by.contains("priority")) {
                returnList = DB.stream()
                                .sorted(
                                    Comparator.comparing(ToDo::getDueDate,
                                    Comparator.nullsLast(Comparator.reverseOrder()))
                                    .thenComparing(ToDo::getPriority).reversed())
                                .collect(Collectors.toList());
            } else if (sort_by.contains("dueDate")) {
                if (order_by.contains("asc")) {
                    returnList = DB.stream()
                                .sorted(
                                    Comparator.comparing(ToDo::getDueDate,
                                    Comparator.nullsLast(Comparator.naturalOrder())))
                                .collect(Collectors.toList());
                } else {
                    returnList = DB.stream()
                                .sorted(
                                    Comparator.comparing(ToDo::getDueDate,
                                    Comparator.nullsLast(Comparator.reverseOrder())))
                                .collect(Collectors.toList());
                }
                
            } else if (sort_by.contains("priority")) {
                if (order_by.contains("desc")) {
                    returnList = DB.stream()
                                .sorted((o1,o2)->{return o2.getPriority() - o1.getPriority();})
                                .collect(Collectors.toList());
                } else {
                    returnList = DB.stream()
                                .sorted((o1,o2)->{return o1.getPriority() - o2.getPriority();})
                                .collect(Collectors.toList());
                }
            }
        }
        if (filter_by != null) {
            if (filter_by.equals("done")) {
                Predicate<ToDo> byDone = toDo -> toDo.getIsDone() == true;
                returnList = filter(byDone, returnList);

            } else if (filter_by.equals("undone")) {
                Predicate<ToDo> byUnDone = toDo -> toDo.getIsDone() == false;
                returnList = filter(byUnDone, returnList);

            } else if (filter_by.equals("name")) {
                Predicate<ToDo> byName = toDo -> StringUtils.containsIgnoreCase(toDo.getText(), text);
                returnList = filter(byName, returnList);

            } else if (filter_by.equals("priority")) {
                Predicate<ToDo> byPriority = toDo -> toDo.getPriority() == priority;
                returnList = filter(byPriority, returnList);
            }
        }
        return returnList;
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
