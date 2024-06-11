package com.promineotech.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promineotech.controller.model.TeamData;
import com.promineotech.controller.model.TeamData.TeamGame;
import com.promineotech.controller.model.TeamData.TeamPlayer;
import com.promineotech.dao.GameDao;
import com.promineotech.dao.PlayerDao;
import com.promineotech.dao.TeamDao;
import com.promineotech.entity.Game;
import com.promineotech.entity.Player;
import com.promineotech.entity.Team;

@Service

public class TeamService {
	
@Autowired
PlayerDao playerDao;
@Autowired
TeamDao teamDao;
@Autowired
GameDao gameDao;
private void copyPlayerFields(Player player,
		TeamPlayer teamPlayer) {
	player.setPlayerId(teamPlayer.getPlayerId());
	player.setName(teamPlayer.getName());
	player.setAge(teamPlayer.getAge());
	player.setPosition(teamPlayer.getPosition()); 
	
	}

	private void copyGameFields(Game game,
			TeamGame teamGame) {
		game.setGameId(teamGame.getGameId());
		game.setDate(teamGame.getDate());
		game.setVenue(teamGame.getVenue());
		game.setResult(teamGame.getResult());
	}
		
	private Player findOrCreatePlayer(Long teamId, Long playerId) {
		if(Objects.isNull(playerId)) {
			return new Player();
		}
		
		return findPlayerById(teamId, playerId);
	}
	
	private Game findOrCreateGame(Long teamId, Long gameId) {
		if(Objects.isNull(gameId)) {
			return new Game();
		}
		
		return findGameById(teamId, gameId);
		
	}
	
	private Player findPlayerById(Long teamId, Long playerId) {
		Player player = playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Player with ID=" + playerId + " was not found."));

	if(player.getTeam().getTeamId() != teamId) {
		throw new IllegalArgumentException("The player with ID=" + playerId
				+ " is not employeed by the team with ID=" + teamId + ".");
	}
	
	return player;
	
	}
	
	private Game findGameById(Long teamId, Long gameId) {
		Game game = gameDao.findById(gameId)
				.orElseThrow(() -> new NoSuchElementException(
						"Game with ID=" + gameId + " was not found."));
		
	boolean found = false;
	
	for(Team team : game.getTeams()) {
		if(team.getTeamId() == teamId) {
			found = true;
			break;
		}
	}
	
	if(!found) {
		throw new IllegalArgumentException("The game with ID=" + gameId
				+ " is not a member of the team with ID=" + teamId);
	}
	
	return game;
	
	}
	
	@Transactional(readOnly = false)
    public TeamPlayer savePlayer(Long teamId, 
    		TeamPlayer teamPlayer) {
    	Team team = findTeamById(teamId);
    Long playerId = teamPlayer.getPlayerId();
    Player player = findOrCreatePlayer(teamId, playerId);
    
    copyPlayerFields(player, teamPlayer);
    
    player.setTeam(team);
    team.getPlayers().add(player);
    
    Player dbPlayer = playerDao.save(player);
    
    return new TeamPlayer(dbPlayer); 
}
 
	private Team findTeamById(Long teamId) {
		return teamDao.findById(teamId)
				.orElseThrow(() -> new NoSuchElementException(
						"Team with ID=" + teamId + " was not found."));
	}

	@Transactional
    public TeamGame saveGame(Long teamId,
    		TeamGame teamGame) {
    
    	Team team = findTeamById(teamId);
    	
    Long gameId = teamGame.getGameId();
    Game game = findOrCreateGame(teamId, gameId);
    
    copyGameFields(game, teamGame);
    
    game.getTeams().add(team);
    team.getGames().add(game);
    
    Game dbGame = gameDao.save(game);
    
    return new TeamGame(dbGame); 
 }

	@Transactional(readOnly = true)
	public List<TeamData> retrieveAllTeams() {
		List<Team> teams = teamDao.findAll();
		
		List<TeamData> result = new LinkedList<>();
		
		for(Team team : teams) {
			TeamData psd = new TeamData(team);
			
			psd.getGames().clear();
			psd.getPlayers().clear();
			
			result.add(psd);
		}
		 return result;
	}
	
	@Transactional(readOnly = true)
	public TeamData retrieveTeamById(Long teamId) {
		return new TeamData(findTeamById(teamId));
	}
	
	@Transactional(readOnly = false)
	public void deleteTeamById(Long teamId) {
		Team team = findTeamById(teamId);
		teamDao.delete(team);
	}

	public List<TeamPlayer> retrieveAllPlayers() {
		List<Player> players = playerDao.findAll();
		List<TeamPlayer> result = new LinkedList<>();
		for (Player player : players) {
			result.add(new TeamPlayer(player));
		}
		return result;
}
		
	public TeamPlayer retrievePlayerById(Long playerId) {
		return new TeamPlayer(playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Player with ID=" + playerId + " was not found.")));
	}

	public void deletePlayerById(Long playerId) {
		Player player = playerDao.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException(
                		"Player with ID=" + playerId + " was not found."));
        playerDao.delete(player);
		
	}

	public List<Game> getAllGames() {
		return gameDao.findAll();
	}

	public Game getGameById(Long id) {
		return gameDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Game with ID=" + id + " was not found."));
	
	}

	public void deleteGameById(Long gameId) {
		Game game = gameDao.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException(
                		"Game with ID=" + gameId + " was not found."));
        gameDao.delete(game);
		
	}

	public Game createGame(Game game) {
		return gameDao.save(game);

	}

	public Game updateGame(Long id, Game updatedGame) {
		Game existingGame = gameDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Game with ID=" + id + " was not found."));
        copyGameFields(existingGame, new TeamGame(updatedGame));
        return gameDao.save(existingGame);
	}

	public TeamData saveTeam(TeamData teamData) {
		Long teamId = teamData.getTeamId();
		Team team = findOrCreateTeam(teamId);
		
		copyTeamFields(team, teamData);
		return new TeamData(teamDao.save(team));
	}

	private void copyTeamFields(Team team, 
			TeamData teamData) {
		team.setName(teamData.getName());
		team.setCity(teamData.getCity());
		team.setCoach(teamData.getCoach());
	}

	private Team findOrCreateTeam(Long teamId) {
		if(Objects.isNull(teamId)) {
			return new Team();
		}
		else {
			return findTeamById(teamId);
		}
	}
}
