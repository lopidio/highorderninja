package br.com.guigasgame.round.type;

import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.time.TimerEventsController;


public class DeathMatchRoundType implements RoundType
{

	private final int deaths;
	private TimerEventsController timerEventsController;

	public DeathMatchRoundType(int deaths)
	{
		this.deaths = deaths;
	}

	@Override
	public void setupTimeEvents(TimerEventsController timerEventsController)
	{
		this.timerEventsController = timerEventsController;
	}

	@Override
	public void onChange(FragStatistic statistic)
	{
		if (statistic.getKills() >= deaths)
		{
			System.out.println("Arrá!!! CHUPA!");
		}
	}
}
