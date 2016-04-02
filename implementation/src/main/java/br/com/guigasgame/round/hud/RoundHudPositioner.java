package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;

public interface RoundHudPositioner
{
	Vector2f getFragCounterPosition(PlayableHeroDefinition gameHeroProperties);
	Vector2f getReverseTimeCounterPosition();
}
