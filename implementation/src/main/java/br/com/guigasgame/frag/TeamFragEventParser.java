package br.com.guigasgame.frag;

public class TeamFragEventParser extends FragEventParser
{

	private final int teamId;
	
	public TeamFragEventParser(int teamId) 
	{
		this.teamId = teamId;
	}

	@Override
	public boolean acceptFragEvent(FragEventWrapper eventWrapper) 
	{
		return (eventWrapper.getMyTeamId() == teamId) || (eventWrapper.getOtherTeamId() == teamId);
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
			switch (eventWrapper.getFragEventIndex())
			{
				case KILL:
					if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
						frag.incrementKills();
					break;
				case SHOOT:
						frag.incrementShoots();
					break;
				case SHOOT_ON_TARGET:
					if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
						frag.incrementShootsOnTarget();
					break;
				case SUICIDE:
					frag.incrementDeaths();
					frag.incrementSuicides();
					break;
				default:
					break;
			}
		}
	}
	
	private void checkImOther(FragEventWrapper eventWrapper)
	{
		if (eventWrapper.getOtherTeamId() == teamId)
		{
			switch (eventWrapper.getFragEventIndex())
			{
				case KILL:
					frag.incrementDeaths();
					break;
				case SHOOT_ON_TARGET:
					if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
						frag.incrementHitAsTarget();
					break;
				default:
					break;
			}
			
		}
	}	


}
