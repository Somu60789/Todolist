package com.todolist.todolist.controller;

import com.todolist.todolist.exception.ResourceNotFoundException;
import com.todolist.todolist.repository.tasksRepository;
import com.todolist.todolist.model.tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@RestController
@RequestMapping("/todo")
public class tasksController {

    @Autowired
    private tasksRepository tasksRepository;
    //get the tasks
    @GetMapping("tasks")
    public List<tasks> getAllTasks(){
        return this.tasksRepository.findAll();
    }
    //get task by id
    @GetMapping("tasks/{id}")
    public ResponseEntity<tasks> getTaskById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        tasks tasks = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + id));
        return ResponseEntity.ok().body(tasks);
    }
    //save task
    @PostMapping("tasks")
    public tasks createTasks(@Validated @RequestBody tasks tasks){

        return  tasksRepository.save(tasks);
    }
    //update task
    @PutMapping("tasks/{id}")
    public ResponseEntity<tasks> updateTasks(@PathVariable(value = "id") Long id,
                                             @Validated @RequestBody tasks taskDetails) throws ResourceNotFoundException{
        tasks tasks = tasksRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("task not found for this id :: "+ id));
        tasks.setTitle(taskDetails.getTitle());
        tasks.setId(taskDetails.getId());
        tasks.setCompleted(taskDetails.getCompleted());

        return ResponseEntity.ok(this.tasksRepository.save(tasks));
    }
    //delete task
    @DeleteMapping("tasks/{id}")
    public Map<String, Boolean> deleteTasks(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
        tasks tasks = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + id));
        this.tasksRepository.delete(tasks);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
