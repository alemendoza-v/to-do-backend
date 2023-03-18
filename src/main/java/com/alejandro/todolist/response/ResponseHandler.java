package com.alejandro.todolist.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * It generates a response object that can be returned by the controller
 */
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object pages, Object previous, Object next, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("pages", pages);
            map.put("prev", previous);
            map.put("next", next);
            map.put("status", status.value());
            map.put("data", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", status.value());
            map.put("data", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", status.value());

        return new ResponseEntity<Object>(status);
    }
}