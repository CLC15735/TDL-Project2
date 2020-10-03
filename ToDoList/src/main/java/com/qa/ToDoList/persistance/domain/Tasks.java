package com.qa.ToDoList.persistance.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tasks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String taskBody;
	
	@NotNull
	private String taskPriority;
	
	//Haven't created yet
//	@ManyToOne
//	private ToDoList toDoList;

	public Tasks(Long id, String taskTitle, String taskSubtitle) {
		super();
		this.id = id;
		this.taskBody = taskTitle;
		this.taskPriority = taskSubtitle;
	}
	

}
