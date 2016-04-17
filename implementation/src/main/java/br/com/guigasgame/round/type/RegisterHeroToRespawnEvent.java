package br.com.guigasgame.round.type;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.event.EventWrapper;


public class RegisterHeroToRespawnEvent extends EventWrapper
{

	private final PlayableGameHero heroToRespawn;
	private final float timeToRespawn;

	public RegisterHeroToRespawnEvent(Object sender, PlayableGameHero heroToRespawn, float timeToRespawn)
	{
		super(sender);
		this.heroToRespawn = heroToRespawn;
		this.timeToRespawn = timeToRespawn;
	}

	public PlayableGameHero getHeroToRespawn()
	{
		return heroToRespawn;
	}

	public float getTimeToRespawn()
	{
		return timeToRespawn;
	}
	
}
