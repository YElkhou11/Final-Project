package com.promineotech.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

	@Data
	@Entity
	public class Player {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long playerId;
	    private String name;
	    private String age;
	    private String position;
	    
	    @ToString.Exclude
	    @EqualsAndHashCode.Exclude 
	    @ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "team_id")
	    private Team team;
	    
	}