package com.promineotech.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.controller.model.TeamData;
import com.promineotech.controller.model.TeamData.TeamGame;
import com.promineotech.controller.model.TeamData.TeamPlayer;
import com.promineotech.entity.Game;
import com.promineotech.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/teams")
@Slf4j
public class TeamController {
    @Autowired
    private TeamService teamService;
    
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TeamData insertTeam(@RequestBody TeamData teamData) { 
    	log.info("Creating team {}", teamData);
    			return teamService.saveTeam(teamData);
   }
    
    @PutMapping("/{teamId}")
    public TeamData updateTeam(@PathVariable Long teamId,
    		@RequestBody TeamData teamData) {
    	teamData.setTeamId(teamId);
    	log.info("Updating team {}", teamData);
    	return teamService.saveTeam(teamData);
    	
    }		

    @GetMapping 
	public List<TeamData> retrieveAllTeams() {
		log.info("Retrieving all teams");
		return teamService.retrieveAllTeams();
	}
	
	@GetMapping("/{teamId}")
	public TeamData retrieveTeamId(@PathVariable Long teamId) {
		log.info("Retrieving team with ID={}", teamId);
		return teamService.retrieveTeamById(teamId);
	}

	@DeleteMapping("/{teamId}")
	public Map<String, String> deleteTeamById(@PathVariable Long teamId) {
		log.info("Deleting team with ID={}", teamId);
	
	teamService.deleteTeamById(teamId);
	
	return Map.of("message", "Team with ID=" + teamId + " deleted. ");

	}
//    Player CRUD Operations ******************************
	
	@PostMapping("/{teamId}/player")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamPlayer addPlayerToTeam(@PathVariable Long teamId,
		@RequestBody TeamPlayer teamPlayer) {
	log.info("Adding player {} to team with ID={}", teamPlayer, 
			teamId);
	
	return teamService.savePlayer(teamId, teamPlayer);
	
	}

	 @GetMapping("/players")
	    public List<TeamPlayer> retrieveAllPlayers() {
	    	log.info("Retrieving all players");
	        return teamService.retrieveAllPlayers();
	    }

	    @GetMapping("/{playerId}/player")
	    public TeamPlayer retrievePlayerById(@PathVariable Long playerId) {
	    	log.info("Retrieving player with ID= {}", playerId);
	    	return teamService.retrievePlayerById(playerId);
	    }

	    @DeleteMapping("/{playerId}/player")
	    public Map<String, String> deletePlayerById(@PathVariable Long playerId) {
	        log.info("Deleting player with ID= {}", playerId);
	        
	        teamService.deletePlayerById(playerId);
	        
	        return Map.of("message", "Player with ID=" + playerId + "deleted.");
	    }
	    
// 		Game CRUD Operations ******************************
	
	@PostMapping("/{teamId}/game")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamGame addGameToTeam(@PathVariable Long teamId,
		@RequestBody TeamGame teamGame) {
	log.info("Adding game {} to team with ID= {}", teamGame,
			teamId);
	
	return teamService.saveGame(teamId, teamGame);
	
	}

	@PutMapping("/{teamid}/{gameid}")
    public ResponseEntity<TeamGame> updateGame(@PathVariable Long gameid, @RequestBody TeamGame game, @PathVariable Long teamid) {
        
    	game.setGameId(gameid);
    	
    	TeamGame updatedGame = teamService.saveGame(teamid, game);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }
	
	@GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = teamService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/{id}/game")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        Game game = teamService.getGameById(id);
                return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/game")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        teamService.deleteGameById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
