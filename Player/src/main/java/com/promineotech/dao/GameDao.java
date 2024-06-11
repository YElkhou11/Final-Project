package com.promineotech.dao;

	
	import org.springframework.data.jpa.repository.JpaRepository;

import com.promineotech.entity.Game;

	public interface GameDao extends JpaRepository<Game, Long> {
	
	}

