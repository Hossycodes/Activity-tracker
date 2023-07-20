package com.example.activitiy_tracking.service;

import com.example.activitiy_tracking.Dto.taskDto.CreateTaskDto;
import com.example.activitiy_tracking.enums.Status;
import com.example.activitiy_tracking.exception.NotFoundException;
import com.example.activitiy_tracking.exception.NotNullException;
import com.example.activitiy_tracking.model.ApiResponse;
import com.example.activitiy_tracking.model.Task;
import com.example.activitiy_tracking.model.User;

import java.util.List;

public interface TaskService {
    User findLoggedInUser();

    Task getTaskById(Long taskId);

    ApiResponse<Task> createTask(CreateTaskDto createTaskDto) throws NotNullException;
    ApiResponse<List<Task>> getAllUserTasks();
    ApiResponse<Task> viewTaskById(Long taskId) throws NotFoundException;
    ApiResponse<List<Task>> getTaskByStatus(Status status) throws NotFoundException;

    ApiResponse<Task> updateTaskStatus(Long taskId, Task newTask);

    ApiResponse<Task> updateTaskTitleOrDescription(Long taskId, Task newTask) throws NotFoundException;
    void deleteTask(Long taskId);
}