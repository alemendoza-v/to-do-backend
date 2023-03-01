package com.alejandro.todolist.Strategy;

import java.util.List;
import java.util.Comparator;

import com.alejandro.todolist.model.ToDo;

public class SortByPriorityDueDate implements SortingInterface {
    private Comparator<ToDo> priorityComparator = Comparator.comparing(ToDo::getPriority);

    private Comparator<ToDo> priorityDescDueDateDesc = priorityComparator.reversed().thenComparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.reverseOrder()));
    private Comparator<ToDo> priorityAscDueDateDesc = priorityComparator.thenComparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.reverseOrder()));
    private Comparator<ToDo> priorityDescDueDateAsc = priorityComparator.reversed().thenComparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.naturalOrder()));
    private Comparator<ToDo> priorityAscDueDateAsc = priorityComparator.thenComparing(
                                                        ToDo::getDueDate, 
                                                        Comparator.nullsLast(Comparator.naturalOrder()));

    @Override
    public List<ToDo> getSortedList(List<ToDo> toDos, List<String> order_by) {
        String dueDateOrdering = order_by.get(0);
        String priorityOrdering = order_by.get(1);
        
        if (dueDateOrdering.equals("desc") && priorityOrdering.equals("desc")) {
            return sortList(toDos, priorityDescDueDateDesc);
        } else if (dueDateOrdering.equals("asc") && priorityOrdering.equals("desc")) {
            return sortList(toDos, priorityAscDueDateDesc);
        } else if (dueDateOrdering.equals("desc") && priorityOrdering.equals("asc")) {
            return sortList(toDos, priorityDescDueDateAsc);
        } else {
            return sortList(toDos, priorityAscDueDateAsc);
        }
    }
}
