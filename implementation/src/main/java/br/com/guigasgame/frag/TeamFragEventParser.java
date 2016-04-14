package br.com.guigasgame.frag;

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
			if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
				eventWrapper.adjustFragStatistic(frag);
		}
	}
}
