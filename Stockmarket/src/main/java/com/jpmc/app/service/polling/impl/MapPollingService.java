package com.jpmc.app.service.polling.impl;

import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.service.polling.IPollingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("mapBasedPoller")
public class MapPollingService implements IPollingService {

    private final Map<String,Polling> dataStore = new HashMap<>();


    @Override
    public Polling save(Polling pollData) {
        dataStore.put(pollData.getId(),pollData);
        return pollData;
    }

    @Override
    public Polling fetch(String id) {
        return dataStore.get(id);
    }

    @Override
    public List<Polling> findAll() {
        return null;
    }
}
