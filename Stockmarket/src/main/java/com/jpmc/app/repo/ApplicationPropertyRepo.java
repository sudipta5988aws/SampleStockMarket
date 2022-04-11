package com.jpmc.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jpmc.app.dataobjects.ApplicationProperty;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationPropertyRepo extends JpaRepository<ApplicationProperty, String>{

}
