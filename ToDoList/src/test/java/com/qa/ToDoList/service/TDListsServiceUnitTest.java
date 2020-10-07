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

import com.qa.ToDoList.dto.TDListsDTO;
import com.qa.ToDoList.persistance.domain.TDLists;
import com.qa.ToDoList.persistance.repository.TDListsRepository;

@SpringBootTest
public class TDListsServiceUnitTest {
	
	@Autowired
	private TDListsService service;
	
	@MockBean
	private TDListsRepository repo;
	
	@MockBean
	private ModelMapper mapper;
	
	private List<TDLists> tdLists;
	private TDLists testLists;
	private TDLists testListsWithId;
	private TDListsDTO tdListsDTO;
	
	//Set values
	final Long id = 1L;
	final String listTitle = "Shopping list";
	final String listSubtitle = "Groceries";
	
	//Assign values to variables
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
		//Repo
		when(this.repo.save(this.testLists)).thenReturn(this.testListsWithId);
		
		//Mocked mapper
		when(this.mapper.map(testListsWithId, TDListsDTO.class)).thenReturn(this.tdListsDTO);
	
		assertThat(this.tdListsDTO).isEqualTo(this.service.create(this.testLists));
		
		verify(this.repo, times(1)).save(this.testLists);
	}
	
	@Test
	void testReadAll() {
		//Repo
		when(this.repo.findAll()).thenReturn(this.tdLists);
		
		//Mocked mapper
		when(this.mapper.map(testListsWithId, TDListsDTO.class)).thenReturn(this.tdListsDTO);
		
		assertThat(this.service.read().isEmpty()).isFalse();
		
		verify(this.repo, times(1)).findAll();
	}
	
	@Test
	void testReadOne() {
		//Repo
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testListsWithId));
				
		//Mocked mapper
		when(this.mapper.map(testListsWithId, TDListsDTO.class)).thenReturn(this.tdListsDTO);
		
		assertThat(this.tdListsDTO).isEqualTo(this.service.read(this.id));
	}
	
	@Test
	void testUpdate() {
		TDLists tdList = new TDLists("Home stuff", "Things to buy for the house");
		tdList.setId(this.id);
		
		TDListsDTO tdListsDTO = new TDListsDTO(null, "Home stuff", "Things to buy for the house", null);
		
		TDLists updateList = new TDLists(tdListsDTO.getListTitle(), tdListsDTO.getListSubtitle());
		updateList.setId(this.id);
		
		TDListsDTO updateListDTO = new TDListsDTO(this.id, updateList.getListTitle(), updateList.getListSubtitle(), null);
		
		//Repo
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testListsWithId));
		when(this.repo.save(tdList)).thenReturn(updateList);
		
		//Mocked mapper
		when(this.mapper.map(updateList, TDListsDTO.class)).thenReturn(updateListDTO);
		
		assertThat(updateListDTO).isEqualTo(this.service.update(this.id, tdListsDTO));
	
		verify(this.repo, times(1)).findById(this.id);
		verify(this.repo, times(1)).save(updateList);
	}
	
	@Test
	void TestDelete() {
		when(this.repo.existsById(this.id)).thenReturn(true, false);
		
		assertThat(this.service.delete(this.id)).isTrue();
		
		verify(this.repo, times(1)).deleteById(this.id);
		verify(this.repo, times(2)).existsById(this.id);
	}

}
