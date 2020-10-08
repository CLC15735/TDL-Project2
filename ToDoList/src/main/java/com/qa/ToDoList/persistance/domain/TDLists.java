package com.qa.ToDoList.persistance.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class TDLists {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String listTitle;
	
	private String listSubtitle;
	
	//, fetch = FetchType.EAGER
	@OneToMany(mappedBy = "tdLists", cascade = CascadeType.ALL)
	private List<Tasks> tasks = new ArrayList<>();

	public TDLists(String listTitle, String listSubtitle) {
		this.listTitle = listTitle;
		this.listSubtitle = listSubtitle;
	}

//	public TDLists(String listTitle, String listSubtitle, List<Tasks> tasks) {
//		super();
//		this.listTitle = listTitle;
//		this.listSubtitle = listSubtitle;
//		this.tasks = tasks;
//	}
	
	
	

}
