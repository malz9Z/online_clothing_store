package com.thungashoe.entity;

import com.thungashoe.entity.composite_key.OrderTrackId;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.thungashoe.common.OrderAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(OrderTrackId.class)
@Table(name = "order_tracks")
public class OrderTrack {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Id
	@Enumerated(EnumType.STRING) 
	@JoinColumn(name = "action")
	private OrderAction action;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time")
	private LocalDateTime creationTime;
	
	@ManyToOne
	@JoinColumn(name = "creater_id")
	private User creater;
	
	private String description;
}
