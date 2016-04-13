package br.com.guigasgame.frag;

public class TeamFragEventParser extends FragEventParser
{

	private final int teamId;
	
	public TeamFragEventParser(int teamId) 
	{
		this.teamId = teamId;
	}

	@Override
	protected void handleEvent(FragEventWrapper eventWrapper) 
	{
		checkImOwner(eventWrapper);
		checkImOther(eventWrapper);
	}
	
	private void checkImOwner(FragEventWrapper eventWrapper)
	{
		if (eventWrapper.getMyTeamId() == teamId)
		{
			if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
				eventWrapper.adjustOwnerFragStatistic(frag);
		}
	}
	
	private void checkImOther(FragEventWrapper eventWrapper)
	{
		if (eventWrapper.getOtherTeamId() == teamId)
		{
			if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
				eventWrapper.adjustOtherFragStatistic(frag);
		}
	}	


}
