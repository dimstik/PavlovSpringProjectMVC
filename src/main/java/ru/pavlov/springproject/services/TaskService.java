package ru.pavlov.springproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavlov.springproject.entity.Status;
import ru.pavlov.springproject.entity.Task;
import ru.pavlov.springproject.repositories.TaskRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> findAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task findById(Integer id) {
        Optional<Task> taskById = taskRepository.findById(id);
        return taskById.orElse(null);
    }

    @Transactional
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Transactional
    public void updateTask(Integer id, Task updateTask) {
        updateTask.setId(id);
        taskRepository.save(updateTask);
    }

    @Transactional
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    public List<String> getAllStatus() {
        return Arrays.stream(Status.values())
                .map(Status::name)
                .collect(Collectors.toList());
    }
}
