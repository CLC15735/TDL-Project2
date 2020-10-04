package com.qa.ToDoList.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.ToDoList.persistance.domain.TDLists;

@Repository
public interface TDListsRepository extends JpaRepository<TDLists, Long> {

}