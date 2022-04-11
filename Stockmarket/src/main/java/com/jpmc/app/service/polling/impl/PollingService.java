package com.jpmc.app.service.polling.impl;


import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.repo.PollingRepo;
import com.jpmc.app.service.polling.IPollingService;

import java.util.List;


@Service
@Qualifier("dbPollingService")
public class PollingService implements IPollingService{
	
	@Autowired
	private PollingRepo pollingRepo;

	@Transactional
	public Polling save(Polling pollingData) {
		return pollingRepo.saveAndFlush(pollingData);
	}

	@Override
	public Polling fetch(String pollId) {
		return pollingRepo.getById(pollId);
	}

	@Override
	public List<Polling> findAll(){
		return pollingRepo.findAll();
	}

}
