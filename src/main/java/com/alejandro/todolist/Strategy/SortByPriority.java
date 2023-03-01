package com.alejandro.todolist.Strategy;

import java.util.List;
import java.util.Comparator;

import com.alejandro.todolist.model.ToDo;

public class SortByPriority implements SortingInterface {
    @Override
    public List<ToDo> getSortedList(List<ToDo> toDos, List<String> order_by) {
        Comparator<ToDo> ascComparator = Comparator.comparing(ToDo::getPriority);
        Comparator<ToDo> descComparator = Comparator.comparing(ToDo::getPriority).reversed();

        if (order_by.contains("asc")) {
            return sortList(toDos, ascComparator);
        } else {
            return sortList(toDos, descComparator);
        }
    }
}
