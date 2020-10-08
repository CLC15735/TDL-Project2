package com.qa.ToDoList.persistance.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TDListsJUnitTest {
	
	@Test
	public void getterAndSetterCorrectness() throws Exception {
	    new BeanTester().testBean(TDLists.class);
	}
	
	
	@Test
	public void testEquals() {
		TDLists newList = new TDLists("House stuff", "Weekly chores");
		TDLists actual = new TDLists (newList.getListTitle(), newList.getListSubtitle());
		assertEquals(newList, actual);
	}

}
