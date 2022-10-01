package com.todolist.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.todolist.todolist.model.tasks;

@Repository
public interface tasksRepository extends JpaRepository<tasks, Long>{
}
