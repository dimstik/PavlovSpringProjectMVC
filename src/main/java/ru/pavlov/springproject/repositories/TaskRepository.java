package ru.pavlov.springproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavlov.springproject.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
