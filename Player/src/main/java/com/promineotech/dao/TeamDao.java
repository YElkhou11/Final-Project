package com.promineotech.dao;

	
	import org.springframework.data.jpa.repository.JpaRepository;

	import com.promineotech.entity.Team;
	
	public interface TeamDao extends JpaRepository<Team, Long> {
	
	}
