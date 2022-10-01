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
import java.util.List;

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
    @GetMapping("/tasks/{id}")
    public ResponseEntity<tasks> getTaskById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        tasks tasks = tasksRepository.findById(Long  id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + id));
        return ResponseEntity.ok().body(tasks);
    }
    //save task
    @PostMapping("/tasks")
    public tasks createTasks(@Validated @RequestBody tasks tasks){
        return  tasksRepository.save(tasks);
    }
    //update task
    @PutMapping("/tasks/{id}")
    public ResponseEntity<tasks> updateTasks(@PathVariable(value = "id") Long id,
                                             @Validated @RequestBody tasks title) throws ResourceNotFoundException{
        tasks tasks = tasksRepository.findAllById(id)
                .orElseThrow(()-> new ResourceNotFoundException("task not found for this id :: "+ id));
        tasks.setTitle(tasks);
    }
    //delete task
}
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }