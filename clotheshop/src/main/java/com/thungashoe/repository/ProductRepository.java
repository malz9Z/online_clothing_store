package com.thungashoe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thungashoe.domain.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product>{

    @EntityGraph(value = "graph.Product", type = EntityGraph.EntityGraphType.LOAD)
	List<Product> findAll();
    
    @EntityGraph(value = "graph.Product", type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findAll(Pageable pageable);
    
    @EntityGraph(value = "graph.Product", type = EntityGraph.EntityGraphType.LOAD)
	Page<Product> findAll(Specification<Product> spec, Pageable pageable);
	
    @EntityGraph(value = "graph.Product", type = EntityGraph.EntityGraphType.LOAD)
	Optional<Product> findById(String id);
	
//	@Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productItems")
//    List<Product> findAllWithItems();
//
//    @Query(value = "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productItems",
//           countQuery = "SELECT COUNT(p) FROM Product p")
//    Page<Product> findAllWithItems(Pageable pageable);
//	
//	@Query("SELECT p FROM Product p LEFT JOIN FETCH p.productItems WHERE p.id = :id")
//	Optional<Product> findProductWithItems(@Param("id") Long id);
	
	Page<Product> findByNameContainingOrBrandContainingOrDescriptionContaining(
            String name, String brand, String description, Pageable pageable);
	
	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
	
	@Query("SELECT DISTINCT s.name FROM Size s")
	List<String> findAllSizes();
	
	@Query("SELECT DISTINCT s.name FROM Color s")
	List<String> findAllColors();
	
	@Query("SELECT DISTINCT s.name FROM Brand s")
	List<String> findAllBrands();
    
    @Query("SELECT MAX(pi.priceRetail) FROM ProductItem pi")
    Optional<Double> findMaxPriceRetail();
    
    @Query("SELECT MIN(pi.priceRetail) FROM ProductItem pi")
    Optional<Double> findMinPriceRetail();

}
