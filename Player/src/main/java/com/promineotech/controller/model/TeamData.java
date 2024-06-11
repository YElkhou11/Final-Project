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
public class TeamData {
    	private Long teamId;
        private String name;
        private String city;
        private String coach;
        private Set<TeamGame> games = new HashSet<>();
        private Set<TeamPlayer> players = new HashSet<>();
        
        public TeamData(Team team) {
        	teamId = team.getTeamId();
        	name = team.getName();
        	city = team.getCity();
        	coach = team.getCoach();
        
        	for(Game game : team.getGames()) {
        		games.add(new TeamGame(game));
        	
        	for(Player player : team.getPlayers()) {
        		players.add(new TeamPlayer(player));
        	}
        	}
        }

 @Data
 @NoArgsConstructor
 public static class TeamGame {
	 private Long gameId;
	 private String date;
	 private String venue;
	 private String result;
	 
	 public TeamGame(Game game) {
		 gameId = game.getGameId();
		 date = game.getDate();
		 venue = game.getVenue();
		 result = game.getResult();
		 
	 }
 }
 
@Data
@NoArgsConstructor
public static class TeamPlayer {
	private Long playerId;
	private String name;
    private String age;
    private String position;
	
	 public TeamPlayer(Player player) {
		 playerId = player.getPlayerId();
		 name = player.getName();
		 age = player.getAge();
		 position = player.getPosition();
	 }
 	}
}

 
