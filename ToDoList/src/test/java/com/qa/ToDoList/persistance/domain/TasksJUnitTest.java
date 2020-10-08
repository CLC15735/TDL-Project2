package com.qa.ToDoList.persistance.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.meanbean.test.BeanTester;

@SpringBootTest
public class TasksJUnitTest {
	
	@Test
	public void getterAndSetterCorrectness() throws Exception {
	    new BeanTester().testBean(Tasks.class);
	}
	
	
	@Test
	public void testEquals() {
		Tasks newTask = new Tasks("Homework", "high");
		Tasks actual = new Tasks (newTask.getTaskBody(), newTask.getTaskPriority());
		assertEquals(newTask, actual);
	}

}
