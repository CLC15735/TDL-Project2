package com.qa.ToDoList.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.ToDoList.dto.TDListsDTO;
import com.qa.ToDoList.exception.ListNotFoundException;
import com.qa.ToDoList.persistance.domain.TDLists;
import com.qa.ToDoList.persistance.repository.TDListsRepository;
import com.qa.ToDoList.utils.ToDoListBeanUtils;

@Service
public class TDListsService {
	
	private TDListsRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public TDListsService(TDListsRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TDListsDTO mapToDTO (TDLists tdLists) {
		return this.mapper.map(tdLists, TDListsDTO.class);
	}
	
	//Create a new list
	public TDListsDTO create(TDLists tdLists) {
		TDLists newTDList = this.repo.save(tdLists);
		TDListsDTO mappedTDList = this.mapToDTO(newTDList);
		
		return mappedTDList;
	}
	
	//Read all lists 
	public List<TDListsDTO> read() {
		List<TDLists> allListsFromRepo = this.repo.findAll();
		List<TDListsDTO> streamedLists = allListsFromRepo.stream().map(this::mapToDTO).collect(Collectors.toList());
	
		return streamedLists;
	}
	
	//Read one list using the id
	public TDListsDTO read(Long id) {
		TDLists listById = this.repo.findById(id).orElseThrow(ListNotFoundException::new);
		TDListsDTO mappedList = this.mapToDTO(listById);
		
		return mappedList;
	}
	
	//Update one list using its id
	public TDListsDTO update(Long id, TDListsDTO tdListsDTO) {
		TDLists listToUpdate = this.repo.findById(id).orElseThrow(ListNotFoundException::new);
		ToDoListBeanUtils.mergeNotNull(tdListsDTO, listToUpdate);
		TDLists savedUpdatedList = this.repo.save(listToUpdate);
		
		return this.mapToDTO(savedUpdatedList);
	}
	
	//Delete one list using its id
	public boolean delete (Long id) {
		if (!this.repo.existsById(id)) {
			throw new ListNotFoundException();
		}
		
		this.repo.deleteById(id);
		
		return !this.repo.existsById(id);
	}
	

	
	
}
