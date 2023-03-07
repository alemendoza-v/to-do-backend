package com.alejandro.todolist.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alejandro.todolist.model.ToDo;
import com.alejandro.todolist.Factory.SortingFactory;

@Repository("dao")
public class ToDoDataAccessService implements ToDoDao{
    private static List<ToDo> DB = new ArrayList<>();

    @Autowired
    private SortingFactory sortingFactory;

    @Override
    public ToDo createToDo(ToDo toDo) {
        ToDo newToDo = new ToDo(toDo.getText(), toDo.getPriority(), toDo.getDueDate());
        DB.add(newToDo);
        return newToDo;
    }

    private List<ToDo> filter(Predicate<ToDo> predicate, List<ToDo> list) {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    } 

    private String buildNextOrPreviousUrl(String text, List<String> sort_by, List<String> order_by, List<String> filter_by, int priority, int page, boolean isNext) {
        String url = "http://localhost:9090/ap1/v1/todos";

        if(text != null && !text.equals("")) {
            url += "?text=";
            url += text;
        }
        if (sort_by != null) {
            url += "?sort_by=";
            url += sort_by.get(0);
            if (sort_by.size() == 2) {
                url += ",";
                url += sort_by.get(1);
            }
        }
        if (order_by != null) {
            url += "?order_by=";
            url += order_by.get(0);
            if (order_by.size() == 2) {
                url += ",";
                url += order_by.get(1);
            }
        }
        if (filter_by != null) {
            url += "?filter_by=";
            url += filter_by.get(0);
            if (filter_by.size() >= 2) {
                url += ",";
                url += filter_by.get(1);
            }
            if (filter_by.size() == 3) {
                url += ",";
                url += filter_by.get(2);
            }
            if(filter_by.contains("priority")) {
                url += "?priority=";
                url += Integer.toString(priority);
            }
        }

        if (isNext) {
            url += "?page=";
            url += Integer.toString(page + 1);
        } else {
            url += "?page=";
            url += Integer.toString(page - 1);
        }
        return url;
    }

    @Override
    public Map<String,Object> getAllToDos() {
        List<ToDo> returnList = DB.subList(0, 9);
        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap.put("prev", null);
        responseMap.put("next","http://localhost:9090/api/v1/todos?page=1");
        responseMap.put("todos", returnList);
        return responseMap;
    }

    @Override  
    public Map<String,Object> getAllToDos(String text, List<String> sort_by, List<String> order_by, List<String> filter_by, int priority, int page) {
        List<ToDo> returnList = DB;

        if (sort_by != null) {
            returnList = sortingFactory.getStrategy(sort_by).getSortedList(returnList, order_by);
        }

        if (filter_by != null) {
            if (filter_by.contains("done")) {
                Predicate<ToDo> byDone = toDo -> toDo.getIsDone() == true;
                returnList = filter(byDone, returnList);

            } else if (filter_by.contains("undone")) {
                Predicate<ToDo> byUnDone = toDo -> toDo.getIsDone() == false;
                returnList = filter(byUnDone, returnList);

            }
            if (filter_by.contains("text")) {
                Predicate<ToDo> byName = toDo -> StringUtils.containsIgnoreCase(toDo.getText(), text);
                returnList = filter(byName, returnList);

            } 
            if (filter_by.contains("priority")) {
                if (priority != 0) {
                    Predicate<ToDo> byPriority = toDo -> toDo.getPriority() == priority;
                    returnList = filter(byPriority, returnList);
                }
            }
        }
       
        int startIndex = page * 10;
        int endIndex = 0;
        if ((page * 10) + 9 <= returnList.size()) {
            endIndex = (page * 10) + 10;
        } else {
            endIndex = returnList.size();
        }

        String next = null;
        String prev = null;

        if (returnList.size() > (page * 10) + 10)  {
            next = buildNextOrPreviousUrl(text, sort_by, order_by, filter_by, priority, page, true);
        }

        if (page > 0) {
            prev = buildNextOrPreviousUrl(text, sort_by, order_by, filter_by, priority, page, false);
        }

        Map<String,Object> responseMap = new HashMap<String, Object>();
        responseMap.put("prev", prev);
        responseMap.put("next", next);
        responseMap.put("todos", returnList.subList(startIndex, endIndex));
        return responseMap;
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
                        toDo.setText(newToDo.getText());
                        toDo.setPriority(newToDo.getPriority());
                        toDo.setDueDate(newToDo.getDueDate());
                        return toDo;
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

    @Override
    public void clearDB() {
        DB.clear();
    }
}
