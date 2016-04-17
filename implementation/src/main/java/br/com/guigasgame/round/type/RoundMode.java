package br.com.guigasgame.round.type;

import br.com.guigasgame.frag.FragStatisticListener;
import br.com.guigasgame.gamemachine.RoundGameState;

public interface RoundMode extends FragStatisticListener
{
	void setRoundState(RoundGameState roundGameState);
}
