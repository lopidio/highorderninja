package br.com.guigasgame.team;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;

public class TeamsController
{
//	HeroTeam teamAlpha = new HeroTeam(TeamIndex.ALPHA);
//	HeroTeam teamBravo = new HeroTeam(TeamIndex.BRAVO);
//	HeroTeam teamCharlie = new HeroTeam(TeamIndex.CHARLIE);
//	HeroTeam teamDelta = new HeroTeam(TeamIndex.DELTA);
//	HeroTeam teamEcho = new HeroTeam(TeamIndex.ECHO);
//	HeroTeam teamFoxtrot = new HeroTeam(TeamIndex.FOXTROT);
//	HeroTeam teamGolf = new HeroTeam(TeamIndex.GOLF);
//	HeroTeam teamHotel = new HeroTeam(TeamIndex.HOTEL);
	
	Map<TeamIndex, HeroTeam> teams;
	
	public TeamsController()
	{
		teams = new HashMap<>();
		for( TeamIndex index : TeamIndex.values() )
		{
			try
			{
				teams.put(index, new HeroTeam(index));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void addHeroDefinition(PlayableHeroDefinition heroDefinition, TeamIndex index)
	{
		teams.get(index).addGameHero(heroDefinition);
	}

	public void setTeamsUp()
	{
		for( HeroTeam heroTeam : teams.values() )
		{
			heroTeam.setHeroesUp();
		}
	}

	public Collection<HeroTeam> getTeamList()
	{
		return teams.values();
	}
	
}
