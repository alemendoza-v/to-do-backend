package com.alejandro.todolist.Strategy;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.alejandro.todolist.model.ToDo;

// A strategy interface used for sorting.
public interface SortingInterface {
    public List<ToDo> getSortedList(List<ToDo> toDos, List<String> order_by);

    default List<ToDo> sortList(List<ToDo> toDos, Comparator<ToDo> comparator) {
        return toDos.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
    }
}
