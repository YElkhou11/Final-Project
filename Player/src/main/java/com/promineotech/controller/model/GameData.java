package com.promineotech.controller.model;

import java.util.HashSet;
import java.util.Set;

import com.promineotech.entity.Game;
import com.promineotech.entity.Player;
import com.promineotech.entity.Team;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameData {
	private Long gameId;
    private String date;
    private String venue;
    private String result;
    private Set<String> teams = new HashSet<>();
    
    public GameData(Game game) {  
    	gameId = game.getGameId();
    	date = game.getDate();
    	venue = game.getVenue();
    	result = game.getResult();
    	
    	for(Team team : game.getTeams()) {
    		teams.add(team.getName());
    	}
    }
    
    @Data
    @NoArgsConstructor
    static class TeamResponse {
    	private Long teamId;
        private String name;
        private String city;
        private String coach;
        private Set<Player> players = new HashSet<>();
        
        TeamResponse(Team team) {
        	teamId = team.getTeamId();
        	name = team.getName();
        	city = team.getCity();
        	coach = team.getCoach();
        
        	for(Player player : team.getPlayers()) {
        		players.add(player);
        	}
        }
    }
}

        
  

