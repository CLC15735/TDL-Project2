package com.qa.ToDoList.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.persistance.domain.Tasks;
import com.qa.ToDoList.persistance.repository.TasksRepository;

@SpringBootTest
public class TasksServiceIntegrationTest {
	
	@Autowired
	private TasksService service;
	
	@Autowired
	private TasksRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	private TasksDTO mapToDTO (Tasks tasks) {
		return this.mapper.map(tasks, TasksDTO.class);
	}
	
	private Tasks testTasks;
	private Tasks testTasksWithId;
	private TasksDTO testTasksDTO;
	
	//Set values
	private Long id;
	private String taskBody = "Pay phone bill";
	private String taskPriority = "high";
	
	
	//Assign the values to the variables 
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testTasks = new Tasks(taskBody, taskPriority);
		this.testTasksWithId = this.repo.save(this.testTasks);
		this.testTasksDTO = this.mapToDTO(this.testTasksWithId);
		this.id = this.testTasksWithId.getId();
	}
	
	//Test that what you are passing to the service (task) gets created and it is equal to the DTO set (JSON format)
	@Test
	void testCreate() {
		assertThat(this.testTasksDTO).isEqualTo(this.service.create(testTasks));
	}
	
	@Test
	void testReadAll() {
		assertThat(Stream.of(this.testTasksDTO).collect(Collectors.toList())).isEqualTo(this.service.read());
	}
	
	@Test 
	void testReadOne() {
		assertThat(this.testTasksDTO).isEqualTo(this.service.read(this.id));
	}
	
	@Test
	void testUpdate() {
		TasksDTO newTask = new TasksDTO(null, "Buy groceries", "low");
		TasksDTO updateTask = new TasksDTO(this.id, newTask.getTaskBody(), newTask.getTaskPriority());
		
		assertThat(updateTask).isEqualTo(this.service.update(newTask, this.id));
	}
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.id)).isTrue();
	}

}
