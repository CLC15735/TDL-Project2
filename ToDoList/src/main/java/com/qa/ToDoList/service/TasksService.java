package com.qa.ToDoList.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.exception.TaskNotFoundException;
import com.qa.ToDoList.persistance.domain.Tasks;
import com.qa.ToDoList.persistance.repository.TasksRepository;
import com.qa.ToDoList.utils.ToDoListBeanUtils;

@Service
public class TasksService {
	private TasksRepository repo;
	private ModelMapper mapper;
	
	@Autowired
	public TasksService (TasksRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper =mapper;
	}
	
	private TasksDTO mapToDTO(Tasks tasks) {
		return this.mapper.map(tasks, TasksDTO.class);
	}
	
	//Add one task to the list
	public TasksDTO create(Tasks tasks) {
		Tasks newTask = this.repo.save(tasks);
		TasksDTO newTaskMapped = this.mapToDTO(newTask);
		return newTaskMapped;
	}
	
	//Read all the tasks on a list
	public List<TasksDTO> read() {
		List<Tasks> allTasksInRepo = this.repo.findAll();
		List<TasksDTO> streamedTasks = allTasksInRepo.stream()
				.map(this::mapToDTO).collect(Collectors.toList());
	
		return streamedTasks;
	}
	
	//Read just one of the tasks on a list
	public TasksDTO read(Long id) {
		Tasks taskById = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
		TasksDTO mappedTask = this.mapToDTO(taskById);
		return mappedTask;
	}
	
	//Update a task using its id
	public TasksDTO update(TasksDTO tasksDTO, Long id) {
		Tasks taskToUpdate = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
		ToDoListBeanUtils.mergeNotNull(tasksDTO, taskToUpdate);
		TasksDTO savedUpdate = this.mapToDTO(this.repo.save(taskToUpdate));
		
		return savedUpdate;
	}
	
	//Delete a task by its id
	public boolean delete(Long id) {
		if (!this.repo.existsById(id)) {
			throw new TaskNotFoundException();
		}
		
		this.repo.deleteById(id);
		//The return will be true when the task is deleted
		return !this.repo.existsById(id);
	}
	

}
