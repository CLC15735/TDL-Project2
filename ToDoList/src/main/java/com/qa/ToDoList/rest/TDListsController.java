package com.qa.ToDoList.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.ToDoList.dto.TDListsDTO;
import com.qa.ToDoList.persistance.domain.TDLists;
import com.qa.ToDoList.service.TDListsService;

@RestController
@CrossOrigin
@RequestMapping("/tdLists")
public class TDListsController {
	
	private TDListsService service;

	@Autowired
	public TDListsController(TDListsService service) {
		super();
		this.service = service;
	}
	
	//Create
	@PostMapping("/create")
	public ResponseEntity<TDListsDTO> create(@RequestBody TDLists tdLists) {
		TDListsDTO newList = this.service.create(tdLists);
		
		return new ResponseEntity<>(newList, HttpStatus.CREATED);	
	}
	
	//Read all
	@GetMapping("/read")
	public ResponseEntity<List<TDListsDTO>> read() {
		
		return ResponseEntity.ok(this.service.read());
	}
	
	//Read one
	@GetMapping("/read/{id}")
	public ResponseEntity<TDListsDTO> read(@PathVariable Long id) {
		
		return ResponseEntity.ok(this.service.read(id));
	}
	
	//Update
		@PutMapping("/update/{id}")
		public ResponseEntity<TDListsDTO> update(@PathVariable Long id, @RequestBody TDListsDTO tdListsDTO) {
			
			return new ResponseEntity<>(this.service.update(id, tdListsDTO), HttpStatus.ACCEPTED);
		}
	
	//Delete
		@DeleteMapping("delete/{id}")
		public ResponseEntity<TDListsDTO> delete(@PathVariable Long id) {
			
			return this.service.delete(id)
					? new ResponseEntity<>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}


}
