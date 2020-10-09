package com.qa.ToDoList.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.persistance.domain.Tasks;
import com.qa.ToDoList.service.TasksService;

@SpringBootTest
public class TasksControllerUnitTest {
	
	@Autowired
	private TasksController controller;
	
	@MockBean
	private TasksService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private TasksDTO mapToDTO(Tasks tasks) {
		return this.mapper.map(tasks, TasksDTO.class);
	}
	
	private List<Tasks> tasksList;
	private Tasks testTasks;
	private Tasks testTasksWithId;
	private TasksDTO tasksDTO;
	
	//Set up the values
	private final Long id = 1L;
	private final String taskBody = "Pay phone bill";
	private final String taskPriority = "high";
	
	@BeforeEach
	void init() {
		this.tasksList = new ArrayList<>();
		this.testTasks = new Tasks(taskBody, taskPriority);
		this.testTasksWithId = new Tasks(testTasks.getTaskBody(), testTasks.getTaskPriority());
		this.testTasksWithId.setId(this.id);
		this.tasksList.add(testTasksWithId);
		this.tasksDTO = this.mapToDTO(testTasksWithId);
	}
	
	@Test
	void testCreate() {
		//When the create method is called in the service, return a JSON string
		when(this.service.create(testTasks)).thenReturn(this.tasksDTO);
		TasksDTO taskCreated = this.tasksDTO;
		
		//Make sure that what it comes back from calling the controller is the same as the expected outcome 
		assertThat(new ResponseEntity<TasksDTO>(taskCreated, HttpStatus.CREATED)).isEqualTo(this.controller.create(testTasks));
		
		//Make sure the mocked service is called and works
		verify(this.service, times(1)).create(this.testTasks);
	}
	
	@Test
	void testReadAll() {
		//When you call the read method in the service return the tasksList in a stream and in a JSON format
		when(this.service.read()).thenReturn(this.tasksList.stream().map(this::mapToDTO).collect(Collectors.toList()));
		
		//Because read() returns a responseEntity, the .getBody() is used to recover the list itself
		List<TasksDTO> readList = this.controller.read().getBody();
		assertThat(readList.isEmpty()).isFalse();
		
		verify(this.service, times(1)).read();		
	}
	
	@Test
	void testReadOne() {
		when(this.service.read(this.id)).thenReturn(this.mapToDTO(testTasksWithId));
	
		assertThat(new ResponseEntity<TasksDTO>(tasksDTO, HttpStatus.OK)).isEqualTo(this.controller.read(this.id));
		
		verify(this.service, times(1)).read(this.id);
	}
	
	@Test
	void testUpdate() {
		//For calling the service, you need an updated task
		TasksDTO taskUpdate = new TasksDTO(null, "Buy groceries", "low");
		TasksDTO taskUpdateWithId = new TasksDTO(this.id, taskUpdate.getTaskBody(), taskUpdate.getTaskPriority());
		
		//When you call the service, the outcome will be the same info you put in your update but with the id of the existing task
		when(this.service.update(taskUpdate, this.id)).thenReturn(taskUpdateWithId);
		
		assertThat(new ResponseEntity<TasksDTO>(taskUpdateWithId, HttpStatus.ACCEPTED)).isEqualTo(this.controller.update(this.id, taskUpdate));
		
		verify(this.service, times(1)).update(taskUpdate, this.id);
	}
	
	@Test
	void testDelete() {
		this.controller.delete(this.id);
		
		verify(this.service, times(1)).delete(this.id);
	}
}
