package com.jpmc.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jpmc.app.dataobjects.Polling;

@Repository
public interface PollingRepo extends JpaRepository<Polling, String>{

}
