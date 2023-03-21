package com.alejandro.todolist.Factory;

import org.springframework.stereotype.Component;

import com.alejandro.todolist.Strategy.*;

import java.util.List;

/**
 * The SortingFactory class is a factory class that returns a SortingInterface object based on the
 * sorting criteria
 */
@Component
public class SortingFactory {

    public SortingInterface getStrategy(List<String> sorted_by) {
        if (sorted_by.size() == 1 && sorted_by.contains("dueDate")) {
            return new SortByDueDate();
        } else if (sorted_by.size() == 1 && sorted_by.contains("priority")) {
            return new SortByPriority();
        } else if (sorted_by.size() == 2 && sorted_by.get(0).equals("dueDate")) {
            return new SortByDueDatePriority();
        } else {
            return new SortByPriorityDueDate();
        }
    }
}
