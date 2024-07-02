package com.thungashoe.repository.common.impl;

import jakarta.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.thungashoe.repository.common.NaturalIdRepository;

import java.io.Serializable;
import java.util.Optional;

public class NaturalIdRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements NaturalIdRepository<T, ID> {

    private final EntityManager entityManager;
	
	public NaturalIdRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
        this.entityManager = entityManager;
	}

	@Override
	public Optional<T> naturalId(ID naturalId) {
		return entityManager.unwrap(Session.class)
				  .bySimpleNaturalId(this.getDomainClass())
			          .loadOptional(naturalId);
	}

}
