package br.com.guigasgame.round.type;

import br.com.guigasgame.frag.FragStatisticListener;
import br.com.guigasgame.time.TimerEventsController;

public interface RoundType extends FragStatisticListener
{
	void setupTimeEvents(TimerEventsController timerEventsController);
}
