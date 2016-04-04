package br.com.guigasgame.round.type;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.team.HeroTeam;


public class DeathMatchRoundType implements RoundType
{

	private List<HeroTeam> teams;
	private int deaths;

	public DeathMatchRoundType(int deaths)
	{
		this.deaths = deaths;
		teams = new ArrayList<>();
	}

	@Override
	public boolean isRoundOver()
	{
		for( HeroTeam heroTeam : teams )
		{
			if (heroTeam.getFragCounter().getKills() >= deaths)
				return true;
		}
		return false;
	}

	@Override
	public void addTeam(HeroTeam heroTeam)
	{
		teams.add(heroTeam);
	}

}
