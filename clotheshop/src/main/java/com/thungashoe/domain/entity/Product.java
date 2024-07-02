package com.thungashoe.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
@NamedEntityGraph(name = "graph.Product",
	attributeNodes = {
		@NamedAttributeNode(value = "productItems", subgraph = "subgraph.productItems"),
		@NamedAttributeNode(value = "brand"),
		@NamedAttributeNode(value = "category") },
	subgraphs = {
		@NamedSubgraph(name = "subgraph.productItems",
			attributeNodes = { 
				@NamedAttributeNode(value = "color"),
				@NamedAttributeNode(value = "size"),
				@NamedAttributeNode(value = "sale"),
				@NamedAttributeNode(value = "orderDetails")
			}
		) 
		}
)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "publish_time")
	private LocalDateTime publishTime;

	private String description;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private LocalDateTime creationTime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creater_id")
	private User creater;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updater_id")
	private User updater;

	@Builder.Default
	@ToString.Exclude
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductItem> productItems = new HashSet<>();

	@ToString.Exclude
	@ManyToMany(mappedBy = "favoriteProducts")
	private Set<User> customers;

	public void addroductItem(ProductItem productItem) {
		productItem.setProduct(this);
		productItems.add(productItem);
	}

	public void removeroductItem(ProductItem productItem) {
		productItems.remove(productItem);
		productItem.setProduct(null);
	}
}
