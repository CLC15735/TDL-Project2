package com.qa.ToDoList.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TasksDTOUnitTest {
	@Test
	public void getterAndSetterCorrectness() throws Exception {
	    new BeanTester().testBean(TasksDTO.class);
	}
	
	
	@Test
	public void testEquals() {
		TasksDTO newTask = new TasksDTO(1L,"Homework", "high");
		TasksDTO actual = new TasksDTO (newTask.getId(), newTask.getTaskBody(), newTask.getTaskPriority());
		assertEquals(newTask, actual);
	}

}
