package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.frag.HeroEventWrapper;


public class SpawnEventWrapper implements HeroEventWrapper
{

	private PlayableGameHero playableGameHero;

	public SpawnEventWrapper(PlayableGameHero playableGameHero)
	{
		this.playableGameHero = playableGameHero;
	}
	
	public PlayableGameHero getPlayableGameHero()
	{
		return playableGameHero;
	}

}
