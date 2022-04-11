package com.jpmc.app.model;

import com.jpmc.app.dataobjects.TransactionType;
import lombok.*;

/**
 * DTO use to trade a stock
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StockTradeDTO {
	@NonNull
	private String stockCode;

	@NonNull
	private Integer totalUnit;

	@NonNull
	private TransactionType type;

	@NonNull
	private Double pricePerUnit;

}
