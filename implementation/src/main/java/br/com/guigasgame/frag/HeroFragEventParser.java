package br.com.guigasgame.frag;

public class HeroFragEventParser extends FragEventParser
{

	private final int heroId;
	
	public HeroFragEventParser(int heroId) 
	{
		this.heroId = heroId;
	}

	@Override
	protected void handleEvent(HeroFragEventWrapper eventWrapper) 
	{
		if (eventWrapper.getMyId() == heroId)
		{
			if (eventWrapper.getMyTeamId() != eventWrapper.getOtherTeamId())
				eventWrapper.adjustFragStatistic(frag);
		}
	}
	
}
