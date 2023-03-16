package com.alejandro.todolist.Strategy;

import java.util.List;
import java.util.Comparator;

import com.alejandro.todolist.model.ToDo;

/**
 * The SortByDueDatePriority class implements the SortingInterface interface and provides a method
 * to sort a list of ToDo objects by due date and then priority
 */
public class SortByDueDatePriority implements SortingInterface {
    private Comparator<ToDo> priorityComparator = Comparator.comparing(ToDo::getPriority);
    // private Comparator<ToDo> comp = Comparator.nullsLast(Comparator.comparing(ToDo::getDueDate)).thenComparing(priorityComparator);
    private Comparator<ToDo> dueDateDescPriorityDesc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.reverseOrder()))
                                                        .thenComparing(priorityComparator.reversed());
    private Comparator<ToDo> dueDateAscPriorityDesc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.naturalOrder()))
                                                        .thenComparing(priorityComparator.reversed());
    private Comparator<ToDo> dueDateDescPriorityAsc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.reverseOrder()))
                                                        .thenComparing(priorityComparator);
    private Comparator<ToDo> dueDateAscPriorityAsc = Comparator.comparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.naturalOrder()))
                                                        .thenComparing(priorityComparator);

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