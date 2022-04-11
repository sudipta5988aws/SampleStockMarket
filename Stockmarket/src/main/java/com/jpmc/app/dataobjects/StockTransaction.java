package com.jpmc.app.dataobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.jpmc.app.model.StockTradeDTO;

import lombok.Getter;
import lombok.ToString;

@Entity
@DynamicUpdate
@Table(name="stock_transaction")
@Getter
@ToString(callSuper = true)
public class StockTransaction extends PersistenceEntity {
	
	private static final long serialVersionUID = 6317415056698583449L;

	@Column(name="stock_info_id")
	private String stock_info_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="stock_info_id",referencedColumnName = "id",insertable = false,updatable = false)
	private  StockInfo stockCore;
	
	
	@Column(name="transaction_type",nullable=false)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	
	@Column(name="unit_count",nullable=false)
	private Integer totalStocks;
	
	
	@Column(name="price_per_unit",nullable=false)
	private Double pricePerUnit;
	
	
	private StockTransaction(String stock_info_id,TransactionType transactionType,Integer totalStocks,Double pricePerUnit) {
		this.stock_info_id=stock_info_id;
		this.pricePerUnit=pricePerUnit;
		this.totalStocks=totalStocks;
		this.transactionType=transactionType;
	}
	
	public static class StockTransactionBuilder {
		
		private String stock_info_id;
		private TransactionType transactionType;
		private Double pricePerUnit;
		private Integer totalStocks;

		public StockTransactionBuilder() {
			
		}
		
		public StockTransactionBuilder withStockInfo(StockInfo stockInfo) {
			this.stock_info_id = stockInfo.getId();
			return this;
		}
		
		public StockTransactionBuilder withTradeInfo(StockTradeDTO tradeInfo) {
			this.transactionType=tradeInfo.getType();
			this.pricePerUnit=tradeInfo.getPricePerUnit();
			this.totalStocks=tradeInfo.getTotalUnit();
			return this;
		}
		
		public StockTransaction build() {
			return new StockTransaction(stock_info_id,transactionType,totalStocks,pricePerUnit);
		}
	
	}
	

}
