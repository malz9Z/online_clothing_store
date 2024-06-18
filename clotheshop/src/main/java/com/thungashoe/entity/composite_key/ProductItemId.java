package com.thungashoe.entity.composite_key;

import java.io.Serializable;

import org.hibernate.annotations.NaturalId;

import com.thungashoe.entity.Color;
import com.thungashoe.entity.Product;
import com.thungashoe.entity.Size;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
@AttributeOverrides({
	  @AttributeOverride( name = "product", column = @Column(name = "product_id", nullable = false)),
	  @AttributeOverride( name = "size", column = @Column(name = "size_id", nullable = false)),
	  @AttributeOverride( name = "color", column = @Column(name = "color_id", nullable = false))
})
@SuppressWarnings("serial")
public class ProductItemId implements Serializable{

	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	private Color color;
	
	@NaturalId
	@ManyToOne(fetch = FetchType.LAZY)
	private Size size;
}
