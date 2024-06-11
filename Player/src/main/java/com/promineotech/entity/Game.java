package com.promineotech.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


	@Data
	@Entity
	public class Game {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long gameId;
	    
	    private String date;
	    private String venue;
	    private String result;
	   
	    @EqualsAndHashCode.Exclude 
	    @ToString.Exclude
	    @ManyToMany(mappedBy = "games", cascade = CascadeType.PERSIST)
	    private Set<Team> teams = new HashSet<>();

}

