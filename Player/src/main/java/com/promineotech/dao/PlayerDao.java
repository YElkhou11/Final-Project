package com.promineotech.dao;

	
	import org.springframework.data.jpa.repository.JpaRepository;

import com.promineotech.entity.Player;

	public interface PlayerDao extends JpaRepository<Player, Long> {
	}
