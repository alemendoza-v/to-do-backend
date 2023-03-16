package com.alejandro.todolist.Strategy;

import java.util.List;
import java.util.Comparator;

import com.alejandro.todolist.model.ToDo;

/**
 * SortByDueDate implements the SortingInterface and sorts the list of ToDos by due date.
 */
public class SortByDueDate implements SortingInterface {
    Comparator<ToDo> ascComparator = Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
    Comparator<ToDo> descComparator = Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()));

    @Override
    public List<ToDo> getSortedList(List<ToDo> toDos, List<String> order_by) {
        if (order_by.contains("asc")) {
            return sortList(toDos, ascComparator);
        } else {
            return sortList(toDos, descComparator);
        }
    }
}
