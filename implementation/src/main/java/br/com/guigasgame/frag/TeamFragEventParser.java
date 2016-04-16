package br.com.guigasgame.frag;

import br.com.guigasgame.round.event.FragEventParser;
import br.com.guigasgame.round.event.HeroFragEventWrapper;

public class TeamFragEventParser extends FragEventParser
{
	private final int teamId;
	
	public TeamFragEventParser(int teamId) 
	{
		this.teamId = teamId;
	}

	@Override
	protected void handleEvent(HeroFragEventWrapper eventWrapper) 
	{
		if (eventWrapper.getMyTeamId() == teamId)
		{
			eventWrapper.adjustFragStatistic(frag);
		}
	}
}
