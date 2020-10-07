package com.qa.ToDoList.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.persistance.domain.Tasks;
import com.qa.ToDoList.persistance.repository.TasksRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TasksControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private TasksRepository repo;
	
	@Autowired
	ModelMapper modelMapper;
	
	private TasksDTO mapToDTO(Tasks tasks) {
		return this.modelMapper.map(tasks, TasksDTO.class);
	}

	@Autowired
    private ObjectMapper mapper;
	
	private Tasks testTasks;
	private Tasks testTasksWithId;
	private TasksDTO tasksDTO;
	
	//Set values
	private Long id;
	private String taskBody = "Pay phone bill";
	private String taskPriority = "high";
	
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testTasks = new Tasks(taskBody, taskPriority);
		this.testTasksWithId = this.repo.save(this.testTasks);
		this.id = this.testTasksWithId.getId();
		this.tasksDTO = this.mapToDTO(this.testTasksWithId);		
	}
	
	@Test
	void testCreate() throws Exception {
		this.mock
			.perform(request(HttpMethod.POST, "/tasks/create").contentType(MediaType.APPLICATION_JSON)
					.content(this.mapper.writeValueAsString(this.testTasks))
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().json(this.mapper.writeValueAsString(this.tasksDTO)));
		 
	}
	
	@Test
	void testReadAll() throws Exception {
		List<TasksDTO> tasksList = new ArrayList<>();
		tasksList.add(this.tasksDTO);
		String expected = this.mapper.writeValueAsString(tasksList);
		String actual = this.mock.perform(request(HttpMethod.GET, "/tasks/read").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals(expected, actual);
	}
	
	@Test
	void testReadOne() throws Exception {
		this.mock.perform(request(HttpMethod.GET, "/tasks/read/" + this.id).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json(this.mapper.writeValueAsString(this.tasksDTO)));
		
	}
	
	@Test 
	void testUpdate() throws Exception {
		TasksDTO taskToUpdate = new TasksDTO(null, "Buy groceries", "low");
		Tasks updatedTask = new Tasks(taskToUpdate.getTaskBody(), taskToUpdate.getTaskPriority());
		updatedTask.setId(this.id);
		TasksDTO updatedTaskDTO = this.mapToDTO(updatedTask);
		
		String expected = this.mapper.writeValueAsString(updatedTaskDTO);
		String actual = this.mock
				.perform(request(HttpMethod.PUT, "/tasks/update/" + this.id).contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(updatedTask))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())
				.andReturn().getResponse().getContentAsString();
	
		assertEquals(expected, actual);
	}
	
	@Test
	void testDelete() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/tasks/delete/" + this.id))
				.andExpect(status().isNoContent());
	}
}
