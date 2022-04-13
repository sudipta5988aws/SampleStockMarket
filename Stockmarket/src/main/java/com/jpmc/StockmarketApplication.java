package com.jpmc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.jpmc.app.util.TableImport;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.jpmc"})
public class StockmarketApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StockmarketApplication.class, args);
		log.info("loading initial data..");
		context.getBean(TableImport.class).fillTableData();
	}
}
