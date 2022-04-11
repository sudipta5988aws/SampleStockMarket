package com.jpmc.app.dataobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DynamicUpdate
@Table(name="stock_info")
@Getter
@Setter
@ToString(callSuper = true)
public class StockInfo extends PersistenceEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="code", nullable=false,unique = true)
	private String code;
	
	@Column(name="stock_type",nullable = false)
	@Enumerated(EnumType.STRING)
	private StockType stockType;
	
	@Column(name = "last_dividend", nullable=false)
	private Double lastDividend = 0.00;
	
	@Column(name="fixed_dividend")
	private Integer fixedDividend;
	
	
	@Column(name="per_value",nullable=false)
	private Integer per_value;
	
	@Column(name="curr_price",nullable = false)
	private Double currentPrice;
	
}
