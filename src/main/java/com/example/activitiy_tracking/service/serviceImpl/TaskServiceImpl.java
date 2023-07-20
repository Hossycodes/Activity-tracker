package com.example.activitiy_tracking.service.serviceImpl;

import com.example.activitiy_tracking.Dto.taskDto.CreateTaskDto;
import com.example.activitiy_tracking.enums.Status;
import com.example.activitiy_tracking.exception.NotFoundException;
import com.example.activitiy_tracking.exception.NotNullException;
import com.example.activitiy_tracking.model.ApiResponse;
import com.example.activitiy_tracking.model.Task;
import com.example.activitiy_tracking.model.User;
import com.example.activitiy_tracking.repository.TaskRepository;
import com.example.activitiy_tracking.repository.UserRepository;
import com.example.activitiy_tracking.service.TaskService;
import com.example.activitiy_tracking.util.ResponseManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final ResponseManager responseManager;
    private ApiResponse apiResponse;

    @Override
    public User findLoggedInUser() {
        Long userId = (Long) httpSession.getAttribute("userId");
        User user = userRepository.findById(userId).get();
        return user;
    }

    @Override
    public Task getTaskById(Long taskId){
        return taskRepository.findById(taskId).get();
    }

    @Override
    public ApiResponse<Task> createTask(CreateTaskDto createTaskDto) throws NotNullException {
        if(createTaskDto.getTitle().equals("")){
            throw new NotNullException("Task must have a title");
        } else if (createTaskDto.getDescription().equals("")) {
            throw new NotNullException("Task must have a description");
        } else {
            Task task = new Task();
            task.setTitle(createTaskDto.getTitle());
            task.setDescription(createTaskDto.getDescription());
            task.setStatus(Status.PENDING);
            task.setCreatedAt(LocalDateTime.now());
            task.setUser(findLoggedInUser());
            taskRepository.save(task);
            apiResponse = responseManager.success(task);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse<List<Task>> getAllUserTasks() {
        List<Task> userTasks = taskRepository.findTasksByUser(findLoggedInUser());
        return responseManager.success(userTasks);
    }

    @Override
    public ApiResponse<Task> viewTaskById(Long taskId) throws NotFoundException {
        Task task = taskRepository.findTaskByUserAndId(findLoggedInUser(), taskId);
        if(task != null){
            return  responseManager.success(task);
        }
        throw new NotFoundException("Task not available");
    }

    @Override
    public ApiResponse<List<Task>> getTaskByStatus(Status status) throws NotFoundException {
        List<Task> tasksByStatus = taskRepository.findTasksByUserAndStatus(findLoggedInUser(),status);
        if(tasksByStatus == null || tasksByStatus.size() < 1){
            throw new NotFoundException("No " + status +" Tasks");
        }
        return responseManager.success(tasksByStatus);
    }

    @Override
    public ApiResponse<Task> updateTaskStatus(Long taskId, Task newTask){
        Task editTask = getTaskById(taskId);
        editTask.setStatus(newTask.getStatus());
        editTask.setUpdatedAt(LocalDateTime.now());

        if(newTask.getStatus() == Status.DONE){
            editTask.setCompletedAt(LocalDateTime.now());
        }
        taskRepository.save(editTask);
        return responseManager.success(editTask);
    }

    @Override
    public ApiResponse<Task> updateTaskTitleOrDescription(Long taskId, Task newTask) throws NotFoundException {
        Task editTask = getTaskById(taskId);
        if(editTask != null || !editTask.equals("")){
            editTask.setTitle(newTask.getTitle());
            editTask.setDescription(newTask.getDescription());
            editTask.setUpdatedAt(LocalDateTime.now());
            Task newUpdatedTask = taskRepository.save(editTask);

            return responseManager.success(newUpdatedTask);
        } else {
            throw new NotFoundException("No such task");
        }
    }

    @Override
    public void deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
    }


}