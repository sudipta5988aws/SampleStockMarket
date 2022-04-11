package com.jpmc.app.dataobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name="polling")
@Getter
@Setter
public class Polling extends PersistenceEntity {

	private static final long serialVersionUID = 76567239875479973L;
	
	@Column(name="status",nullable = false)
	@Enumerated(EnumType.STRING)
	private PollingStatus status;

	@Column(name="result",nullable = true)
	private String result;
	
	@Column(name="metadata",nullable=false)
	private String metadata;
	
	@Column(name="percentage",nullable = false)
	private Integer percentage;

}
