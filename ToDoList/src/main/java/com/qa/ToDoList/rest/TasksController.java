package com.qa.ToDoList.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.ToDoList.dto.TasksDTO;
import com.qa.ToDoList.persistance.domain.Tasks;
import com.qa.ToDoList.service.TasksService;

@RestController
@RequestMapping("/tasks")
public class TasksController {
	
	private TasksService service;
	
	@Autowired
	public TasksController(TasksService service) {
		super();
		this.service = service;
	}
	
	//Create method
	@PostMapping("/create")
	public ResponseEntity<TasksDTO> create(@RequestBody Tasks task) {
		
		TasksDTO newTask = this.service.create(task);
		
		return new ResponseEntity<>(newTask,HttpStatus.CREATED);
	}
	
	//Read all method
	@GetMapping("/read")
	public ResponseEntity<List<TasksDTO>> read(){
		
		return ResponseEntity.ok(this.service.read());
	}
	
	//Read one method
	@GetMapping("/read/{id}")
	public ResponseEntity<TasksDTO> read(@PathVariable Long id) {
		
		return ResponseEntity.ok(this.service.read(id));
	}
	
	//Update method
	@PutMapping("/update/{id}")
	public ResponseEntity<TasksDTO> update(@PathVariable Long id, @RequestBody TasksDTO tasksDTO) {
		TasksDTO updatedTask = this.service.update(tasksDTO, id);
		
		return new ResponseEntity<>(updatedTask, HttpStatus.ACCEPTED);
	}
	
	//Delete method
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TasksDTO> delete (@PathVariable Long id) {
		
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
