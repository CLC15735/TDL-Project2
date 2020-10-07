package com.qa.ToDoList.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.persistance.domain.Tasks;
import com.qa.ToDoList.persistance.repository.TasksRepository;

@SpringBootTest
public class TasksServiceUnitTest {
	
	@Autowired
	private TasksService service;
	
	@MockBean
	private TasksRepository repo;
	
	@MockBean
	private ModelMapper mapper;
	
	private List<Tasks> tasksLists;
	private Tasks testTasks;
	private Tasks testTasksWithId;
	private TasksDTO tasksDTO;
	
	//Set the expected values
	final Long id = 1L;
	final String taskBody = "Pay phone bill";
	final String taskPriority = "high";
	
	@BeforeEach
	void init() {
		this.tasksLists = new ArrayList<>();
		this.testTasks = new Tasks(taskBody, taskPriority);
		this.tasksLists.add(testTasks);
		this.testTasksWithId = new Tasks(testTasks.getTaskBody(), testTasks.getTaskPriority());
		this.testTasksWithId.setId(id);
		this.tasksDTO = mapper.map(testTasksWithId, TasksDTO.class);
	}
	
	@Test
	void testCreate() {	
		//Set up the repository(mocked)
		when(this.repo.save(this.testTasks))
			.thenReturn(this.testTasksWithId);
		
		//Set up the mocked model mapper
		when(this.mapper.map(testTasksWithId, TasksDTO.class)).thenReturn(this.tasksDTO);
		
		//Make sure that expected and actual match
		assertThat(this.tasksDTO).isEqualTo(this.service.create(this.testTasks));
	
		verify(this.repo, times(1)).save(this.testTasks);
	}
	
	@Test
	void testReadAll() {
		//Set up the mocked model mapper
		when(this.mapper.map(testTasksWithId, TasksDTO.class)).thenReturn(this.tasksDTO);
	
		//Sets up the repo, returning a list
		when(this.repo.findAll()).thenReturn(this.tasksLists);
		
		//Reading all tasks --> is Empty returns a boolean --> make sure it is false
		assertThat(this.service.read().isEmpty()).isFalse();
		
		verify(this.repo, times(1)).findAll();
	
	}
	
	@Test
	void testReadOne() {
		//Set up the mocked model mapper
		when(this.mapper.map(testTasksWithId, TasksDTO.class)).thenReturn(this.tasksDTO);
		
		//Set up the repo, looking fot a single task using the id
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testTasksWithId));
		
		//Comparing the expected value to the one retrieved from the repo
		assertThat(this.tasksDTO).isEqualTo(this.service.read(this.id));
	
		verify(this.repo, times(1)).findById(this.id);
		
	}
	
	@Test
	void testUpdate() {
		Tasks task = new Tasks( "Buy groceries", "low");
		task.setId(this.id);
		
		TasksDTO tasksDTO = new TasksDTO(null, "Buy groceries", "low");
		
		Tasks updateTask = new Tasks(tasksDTO.getTaskBody(), tasksDTO.getTaskPriority());
		updateTask.setId(this.id);
		
		TasksDTO updateTaskDTO = new TasksDTO(this.id, updateTask.getTaskBody(), updateTask.getTaskPriority());
	
		//Set up the repo, looking fot a single task using the id
		when(this.repo.findById(this.id)).thenReturn(Optional.of(task));
		
		//Feed the updated task to the repo
		when(this.repo.save(task)).thenReturn(updateTask);
		
		//Feed the updated task to the mapper, which will convert it into a JSON string
		when(this.mapper.map(updateTask, TasksDTO.class)).thenReturn(updateTaskDTO);
	
		//Make sure expected and actual match
		assertThat(updateTaskDTO).isEqualTo(this.service.update(tasksDTO, this.id));
	
		verify(this.repo, times(1)).findById(this.id);
		verify(this.repo, times(1)).save(updateTask);
	}
	
	@Test
	void testDelete() {
		when(this.repo.existsById(this.id)).thenReturn(true, false);
		
		assertThat(this.service.delete(this.id)).isTrue();
		
		verify(this.repo, times(1)).deleteById(this.id);
		verify(this.repo, times(2)).existsById(this.id);
	}
 
}
