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

import java.time.temporal.ChronoUnit;

import com.alejandro.todolist.Factory.SortingFactory;

@Repository("dao")
public class ToDoDataAccessService implements ToDoDao{
    private static List<ToDo> DB = new ArrayList<>();

    @Autowired
    private SortingFactory sortingFactory;

    @Override
    public Map<String,Object> createToDo(ToDo toDo) {
        ToDo newToDo = new ToDo(toDo.getText(), toDo.getPriority(), toDo.getDueDate());
        if (newToDo.getText().equals("")) {
            String error = "No duplicate to dos are allowed";
                Map<String,Object> responseMap = new HashMap<String, Object>();
                responseMap.put("error", error);
                responseMap.put("todo", null);
                return responseMap;
        }

        Predicate<ToDo> byName = t -> StringUtils.containsIgnoreCase(t.getText(), newToDo.getText());
        List<ToDo> filteredList = filter(byName, DB);
        for(ToDo t : filteredList ) {
            if(t.getText().equals(newToDo.getText())) {
                // Duplicate found
                String error = "No duplicate to dos are allowed";
                Map<String,Object> responseMap = new HashMap<String, Object>();
                responseMap.put("error", error);
                responseMap.put("todo", null);
                return responseMap;
            }
        }
        DB.add(newToDo);
        Map<String,Object> responseMap = new HashMap<String, Object>();
        responseMap.put("todo", newToDo);
        return responseMap;
    }

    private List<ToDo> filter(Predicate<ToDo> predicate, List<ToDo> list) {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    } 

    private String buildNextOrPreviousUrl(String text, List<String> sort_by, List<String> order_by, List<String> filter_by, int priority, int page, boolean isNext) {
        String url = "/todos";

        if(text != null && !text.equals("")) {
            if(url.contains("?")) {
                url += "&text=";
            } else {
                url += "?text=";
            }
            url += text;
        }
        if (sort_by != null) {
            if(url.contains("?")) {
                url += "&sort_by=";
            } else {
                url += "?sort_by=";
            }
            url += sort_by.get(0);
            if (sort_by.size() == 2) {
                url += ",";
                url += sort_by.get(1);
            }
        }
        if (order_by != null) {
            if(url.contains("?")) {
                url += "&order_by=";
            } else {
                url += "?order_by=";
            }
            url += order_by.get(0);
            if (order_by.size() == 2) {
                url += ",";
                url += order_by.get(1);
            }
        }
        if (filter_by != null) {
            if(url.contains("?")) {
                url += "&filter_by=";
            } else {
                url += "?filter_by=";
            }
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
                url += "&priority=";
                url += Integer.toString(priority);
            }
        }

        if (isNext) {
            if(url.contains("?")) {
                url += "&page=";
            } else {
                url += "?page=";
            }
            url += Integer.toString(page + 1);
        } else {
            if(url.contains("?")) {
                url += "&page=";
            } else {
                url += "?page=";
            }
            url += Integer.toString(page - 1);
        }
        return url;
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
        if ((page * 10) + 9 < returnList.size()) {
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

    private String calculateDuration(List<ToDo> toDos) {
        long secondsAll = 0;
        for(ToDo toDo : toDos) {
            secondsAll += ChronoUnit.SECONDS.between(toDo.getCreatedAt(), toDo.getDoneDate());
        }
        secondsAll /= toDos.size();
        long minutesAll = secondsAll / 60;
        secondsAll %= 60;
        
        return String.format("%02d:%02d minutes", minutesAll, secondsAll);
    }
    
    @Override
    public Map<String,Object> calculateDurations() {
        Map<String,Object> responseMap = new HashMap<String, Object>();

        Predicate<ToDo> byDone = toDo -> toDo.getIsDone() == true;
        List<ToDo> filteredToDos = filter(byDone, DB);
        if (filteredToDos.isEmpty()) {
            responseMap.put("averageAll", null);
            responseMap.put("averageHigh", null);
            responseMap.put("averageMedium", null);
            responseMap.put("averageLow", null);
            return responseMap;
        }
        
        String resultAll = calculateDuration(filteredToDos);
        String resultHigh = null;
        String resultMedium = null;
        String resultLow = null;
        
        Predicate<ToDo> byPriorityHigh = toDo -> toDo.getPriority() == 3;
        List<ToDo> filteredByHighToDos = filter(byPriorityHigh, filteredToDos);
        if(!filteredByHighToDos.isEmpty()) {
            resultHigh = calculateDuration(filteredByHighToDos);
        } 

        Predicate<ToDo> byPriorityMedium = toDo -> toDo.getPriority() == 2;
        List<ToDo> filteredByMediumToDos = filter(byPriorityMedium, filteredToDos);
        if(!filteredByMediumToDos.isEmpty()) {
            resultMedium = calculateDuration(filteredByMediumToDos);
        } 

        Predicate<ToDo> byPriorityLow = toDo -> toDo.getPriority() == 1;
        List<ToDo> filteredByLowToDos = filter(byPriorityLow, filteredToDos);
        if(!filteredByLowToDos.isEmpty()) {
            resultLow = calculateDuration(filteredByLowToDos);
        } 
        
        responseMap.put("averageAll", resultAll);
        responseMap.put("averageHigh", resultHigh);
        responseMap.put("averageMedium", resultMedium);
        responseMap.put("averageLow", resultLow);
        return responseMap;
    }
}
