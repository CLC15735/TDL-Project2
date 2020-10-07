package com.qa.ToDoList.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.ToDoList.dto.TDListsDTO;
import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.persistance.domain.TDLists;
import com.qa.ToDoList.persistance.repository.TDListsRepository;

@SpringBootTest
public class TDListsServiceIntegrationTest {
	
	@Autowired
	private TDListsService service;
	
	@Autowired
	private TDListsRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	private TDListsDTO mapToDTO (TDLists tdLists) {
		return this.mapper.map(tdLists, TDListsDTO.class);
	}
	
	private TDLists testLists;
	private TDLists testListsWithId;
	private TDListsDTO tdListsDTO;
	
	//Set values
	private Long id;
	private String listTitle = "Shopping list";
	private String listSubtitle = "Groceries";
	
	//Assign values to variables
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testLists = new TDLists(listTitle, listSubtitle);
		this.testListsWithId = this.repo.save(this.testLists);
		this.tdListsDTO = this.mapToDTO(testListsWithId);
		this.id = this.testListsWithId.getId();
	}
	
	@Test
	void testCreate() {
		assertThat(this.tdListsDTO).isEqualTo(this.service.create(testLists));
	}
	
	@Test
	void testReadAll() {
		//The problem is with this.service.read()
		assertThat(Stream.of(this.tdListsDTO).collect(Collectors.toList())).isEqualTo(this.service.read());
	}
	
	@Test
	void testReadOne() {
		assertThat(this.tdListsDTO).isEqualTo(this.service.read(this.id));
	}
	
	@Test
	void testUpdate() {
		final List<TasksDTO> TASKS = new ArrayList<>();
		TDListsDTO newList = new TDListsDTO(null, "Home stuff", "Things to buy for the house", TASKS);
		TDListsDTO updateList = new TDListsDTO(this.id, newList.getListTitle(), newList.getListSubtitle(), newList.getTasks());
	
		assertThat(updateList).isEqualTo(this.service.update(this.id, newList));
	}
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.id)).isTrue();
	}

}
