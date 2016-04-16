package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.event.EventWrapper;


public class SpawnEventWrapper extends EventWrapper
{
	public SpawnEventWrapper(PlayableGameHero playableGameHero)
	{
		super(playableGameHero);
	}
}
