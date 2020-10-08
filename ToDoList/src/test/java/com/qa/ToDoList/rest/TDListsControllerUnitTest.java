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

import com.qa.ToDoList.dto.TDListsDTO;
import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.persistance.domain.TDLists;
import com.qa.ToDoList.service.TDListsService;

@SpringBootTest
public class TDListsControllerUnitTest {
	
	@Autowired
	private TDListsController controller;
	
	@MockBean
	private TDListsService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private TDListsDTO mapToDTO(TDLists tdLists) {
		return this.mapper.map(tdLists, TDListsDTO.class);
	}
	
	private List<TDLists> tdLists;
	private TDLists testLists;
	private TDLists testListsWithId;
	private TDListsDTO tdListsDTO;
	
	//Set values
	final Long id = 1L;
	final String listTitle = "Shopping list";
	final String listSubtitle = "Groceries";
	
	@BeforeEach
	void init() {
		this.tdLists = new ArrayList<>();
		this.testLists = new TDLists(listTitle, listSubtitle);
		this.testListsWithId = new TDLists(testLists.getListTitle(), testLists.getListSubtitle());
		this.testListsWithId.setId(this.id);
		this.tdLists.add(testLists);
		this.tdListsDTO = mapper.map(testListsWithId, TDListsDTO.class);
	}
	
	@Test
	void testCreate() {
		when(this.service.create(testLists)).thenReturn(this.tdListsDTO);
		TDListsDTO listCreated = this.tdListsDTO;
		
		assertThat(new ResponseEntity<TDListsDTO>(listCreated, HttpStatus.CREATED)).isEqualTo(this.controller.create(testLists));
		
		verify(this.service, times(1)).create(this.testLists);
	}
	
	@Test
	void testReadAll() {
		when(this.service.read()).thenReturn(this.tdLists.stream().map(this::mapToDTO).collect(Collectors.toList()));
	
		List<TDListsDTO> readList = this.controller.read().getBody();
		assertThat(readList.isEmpty()).isFalse();
		
		verify(this.service, times(1)).read();
	}
	
	@Test
	void testReadOne() {
		when(this.service.read(this.id)).thenReturn(this.mapToDTO(testListsWithId));
	
		assertThat(new ResponseEntity<TDListsDTO>(this.tdListsDTO, HttpStatus.OK)).isEqualTo(this.controller.read(this.id));
	
		verify(this.service, times(1)).read(this.id);
	}
	
	@Test
	void testUpdate() {
		final List<TasksDTO> TASKS = new ArrayList<>();
		TDListsDTO listUpdate = new TDListsDTO(null, "Home stuff", "Things to buy for the house", TASKS);
		TDListsDTO listUpdateWithId = new TDListsDTO(this.id, listUpdate.getListTitle(), listUpdate.getListSubtitle(), listUpdate.getTasks());
	
		when(this.service.update(this.id, listUpdate)).thenReturn(listUpdateWithId);
		
		assertThat(new ResponseEntity<TDListsDTO>(listUpdateWithId, HttpStatus.ACCEPTED)).isEqualTo(this.controller.update(this.id, listUpdate));
		
		verify(this.service, times(1)).update(this.id, listUpdate);
	}
	
	@Test
	void testDelete() {
		this.controller.delete(this.id);
		
		verify(this.service, times(1)).delete(this.id);
	}

}
