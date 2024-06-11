package com.promineotech.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Team {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String name;
    private String city;
    private String coach;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude 
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL,
    	orphanRemoval = true)
    private Set<Player> players = new HashSet<>();
    
    @EqualsAndHashCode.Exclude 
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "team_game",
    		joinColumns = @JoinColumn(name = "team_id"),
    		inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games = new HashSet<>();
}
