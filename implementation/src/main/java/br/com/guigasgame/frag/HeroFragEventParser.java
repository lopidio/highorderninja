package br.com.guigasgame.frag;

public class HeroFragEventParser extends FragEventParser
{

	private final int heroId;
	
	public HeroFragEventParser(int heroId) 
	{
		this.heroId = heroId;
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
			if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
				eventWrapper.adjustOwnerFragStatistic(frag);
		}
	}
	
	private void checkImOther(FragEventWrapper eventWrapper)
	{
		if (eventWrapper.getOtherId() == heroId)
		{
			if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
				eventWrapper.adjustOtherFragStatistic(frag);
		}
	}

}
