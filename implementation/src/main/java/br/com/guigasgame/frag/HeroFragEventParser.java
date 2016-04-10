package br.com.guigasgame.frag;

public class HeroFragEventParser extends FragEventParser
{

	private final int heroId;
	
	public HeroFragEventParser(int heroId) 
	{
		this.heroId = heroId;
	}

	@Override
	public boolean acceptFragEvent(FragEventWrapper eventWrapper) 
	{
		return (eventWrapper.getMyId() == heroId) || (eventWrapper.getOtherId() == heroId);
	}

	@Override
	protected void handleEvent(FragEventWrapper eventWrapper) 
	{
		checkImOwner(eventWrapper);
		checkImOther(eventWrapper);
	}
	
	private void checkImOwner(FragEventWrapper eventWrapper)
	{
		if (eventWrapper.getMyId() == heroId)
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
		if (eventWrapper.getOtherId() == heroId)
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
