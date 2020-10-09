package com.qa.ToDoList.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TDListsDTOUnitTest {
	@Test
	public void getterAndSetterCorrectness() throws Exception {
	    new BeanTester().testBean(TDListsDTO.class);
	}
	
	
	@Test
	public void testEquals() {
		List<TasksDTO> tasks = new ArrayList<>();
		TDListsDTO newList = new TDListsDTO(1L,"Home stuff", "Weekly chores", tasks);
		TDListsDTO actual = new TDListsDTO (newList.getId(), newList.getListTitle(), newList.getListSubtitle(), newList.getTasks());
		assertEquals(newList, actual);
	}

}
