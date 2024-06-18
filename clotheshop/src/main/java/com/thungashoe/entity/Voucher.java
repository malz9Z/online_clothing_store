package com.thungashoe.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.thungashoe.common.TypeValue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "vouchers")
public class Voucher {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String name;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;

	@Column(name = "finish_time")
	private LocalDateTime finishTime;
	
	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "type")
	private TypeValue type;
	
	private Double value;
	
	@Column(name = "value_apply")
	private Double valueApply;
	
	@ManyToOne
	@JoinColumn(name = "creater_id")
	private User creater;

	@Builder.Default
	@ToString.Exclude
	@OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Order> orders = new HashSet<>();
	
	@ToString.Exclude
	@ManyToMany(mappedBy = "vouchers",fetch = FetchType.LAZY)
	private Set<User> customers;
}
