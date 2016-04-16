package br.com.guigasgame.frag;

import br.com.guigasgame.round.event.FragEventParser;
import br.com.guigasgame.round.event.HeroFragEventWrapper;

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
			eventWrapper.adjustFragStatistic(frag);
		}
	}
	
}
