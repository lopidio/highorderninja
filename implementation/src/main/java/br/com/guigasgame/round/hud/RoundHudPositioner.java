package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.team.HeroTeam;

public interface RoundHudPositioner
{
	Vector2f getReverseTimeCounterPosition();
	Vector2f getTeamFragCounterPositioner(HeroTeam heroTeam);
	Vector2f getHeroFragCounterPosition(PlayableHeroDefinition gameHeroProperties);
}
