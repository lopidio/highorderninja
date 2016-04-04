package br.com.guigasgame.round.type;

import br.com.guigasgame.team.HeroTeam;

public interface RoundType
{
	boolean isRoundOver();
	void addTeam(HeroTeam team);
}
