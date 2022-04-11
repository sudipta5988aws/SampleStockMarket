package com.jpmc.app.util;

import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.ApplicationProperty;
import com.jpmc.app.repo.ApplicationPropertyRepo;

@Slf4j
@Component
public class PropertyLoader {
	
	@Autowired
	private ApplicationPropertyRepo applicationPropertyRepo;
	
	private Map<String,String> properties;
	
	@PostConstruct
	public void loadProperties() {
		log.info("Loading application properties..");
		applicationPropertyRepo.findAll().forEach((Consumer<? super ApplicationProperty>) property ->properties.put(property.getKey(), property.getValue()));
	}

	public String getPropertyValue(String key) {
		return properties.get(key);
	}
	

}
