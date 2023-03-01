package com.alejandro.todolist.Strategy;

import java.util.List;
import java.util.Comparator;

import com.alejandro.todolist.model.ToDo;

public class SortByDueDatePriority implements SortingInterface {
    // private Comparator<ToDo> priorityComparator = Comparator.comparing(ToDo::getPriority);
    // private Comparator<ToDo> comp = Comparator.nullsLast(Comparator.comparing(ToDo::getDueDate)).thenComparing(priorityComparator);
    private Comparator<ToDo> dueDateDescPriorityDesc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.reverseOrder()))
                                                        .thenComparingInt(ToDo::getPriority);
    private Comparator<ToDo> dueDateAscPriorityDesc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.naturalOrder()))
                                                        .thenComparingInt(ToDo::getPriority);
    private Comparator<ToDo> dueDateDescPriorityAsc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.reverseOrder()))
                                                        .thenComparingInt(ToDo::getPriority).reversed();
    private Comparator<ToDo> dueDateAscPriorityAsc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.naturalOrder()))
                                                        .thenComparingInt(ToDo::getPriority).reversed();

    @Override
    public List<ToDo> getSortedList(List<ToDo> toDos, List<String> order_by) {
        String dueDateOrdering = order_by.get(0);
        String priorityOrdering = order_by.get(1);
        
        if (dueDateOrdering.equals("desc") && priorityOrdering.equals("desc")) {
            return sortList(toDos, dueDateDescPriorityDesc);
            // return sortList(toDos, new ToDoComparator());
        } else if (dueDateOrdering.equals("asc") && priorityOrdering.equals("desc")) {
            return sortList(toDos, dueDateAscPriorityDesc);
        } else if (dueDateOrdering.equals("desc") && priorityOrdering.equals("asc")) {
            return sortList(toDos, dueDateDescPriorityAsc);
        } else {
            return sortList(toDos, dueDateAscPriorityAsc);
        }
    }
}