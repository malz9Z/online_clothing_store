package com.thungashoe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thungashoe.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    @EntityGraph(value = "graph.Category", type = EntityGraph.EntityGraphType.LOAD)
	List<Category> findByParentIsNull();
}
