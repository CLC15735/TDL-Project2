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
import com.qa.ToDoList.dto.TDListsDTO;
import com.qa.ToDoList.persistance.domain.TDLists;
import com.qa.ToDoList.persistance.repository.TDListsRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class TDListsControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private TDListsRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private TDLists testLists;
	private TDLists testListsWithId;
	
	//Set values
	private Long id;
	private String listTitle = "Shopping list";
	private String listSubtitle = "Groceries";
	
	private TDListsDTO mapToDTO (TDLists tdLists) {
		return this.mapper.map(tdLists, TDListsDTO.class);
	}
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testLists = new TDLists(listTitle, listSubtitle);
		this.testListsWithId = this.repo.save(this.testLists);
		this.id = this.testListsWithId.getId();
	}
	
	@Test
	void testCreate() throws Exception {
		this.mock
			.perform(request(HttpMethod.POST, "/tdLists/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(testLists))
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().json(this.objectMapper.writeValueAsString(testListsWithId)));	
	}
	
	@Test
	void testReadAll() throws Exception {
		List<TDLists> tdList = new ArrayList<>();
		tdList.add(this.testListsWithId);
		
		String actual = this.mock
				.perform(request(HttpMethod.GET, "/tdLists/read")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals(this.objectMapper.writeValueAsString(tdList), actual);
	}
	
	@Test
	void testReadOne() throws Exception {
		this.mock
		.perform(request(HttpMethod.GET, "/tdLists/read/" + this.id)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.testLists)));
	}
	
	@Test
	void testUpdate() throws Exception {
	
		TDLists listUpdate = new TDLists("Home stuff", "Things to buy for the house");
		TDLists listUpdateWithId = new TDLists(listUpdate.getListTitle(), listUpdate.getListSubtitle());
		listUpdateWithId.setId(this.id);
		
		String actual = this.mock
				.perform(request(HttpMethod.PUT, "/tdLists/update/" + this.id)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsString(listUpdate)))
				.andExpect(status().isAccepted())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(listUpdateWithId)), actual);
		
	}
	
	@Test
	void testDelete() throws Exception {
		this.mock
			.perform(request(HttpMethod.DELETE, "/tdLists/delete/" + this.id))
			.andExpect(status().isNoContent());			
		
	}

}
