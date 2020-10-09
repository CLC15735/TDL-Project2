package com.qa.ToDoList.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TDListsDTO {
	
	private Long id;
	private String listTitle;
	private String listSubtitle;
	private List<TasksDTO> tasks = new ArrayList<>();

}
