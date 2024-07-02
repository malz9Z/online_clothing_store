package com.thungashoe.domain.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "product_items")
public class ProductItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	private Color color;

	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	private Size size;

	private Long quantity;

	@Column(name = "price_retail")
	private Double priceRetail;

	@Column(name = "urls")
	private List<String> urlImgs;

	private Boolean isDeleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id")
	private Sale sale;

	@Builder.Default
	@ToString.Exclude
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderDetail> orderDetails = new HashSet<>();
	
	@Override
    public int hashCode() {
        return Objects.hash(
        		product != null ? product.getName() : null,
                color != null ? color.getName() : null,
                size != null ? size.getName() : null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductItem other = (ProductItem) obj;
        if (other.product == null || other.color == null ||other.size == null) 
        	return false;
        return Objects.equals(product.getName(), other.product.getName()) &&
               Objects.equals(color.getName(), other.color.getName()) &&
               Objects.equals(size.getName(), other.size.getName());
    }
}
