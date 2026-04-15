package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repositories.TodoRepoistiory;
import com.example.demo.schema.Todo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoService {

    private TodoRepoistiory todoRepoistiory;
    
    public List<Todo> getAllTodos(){
        // some algo to be exex
        return todoRepoistiory.findAll();
    }
}
