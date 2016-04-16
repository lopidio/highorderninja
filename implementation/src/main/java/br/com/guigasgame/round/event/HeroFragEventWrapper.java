package br.com.guigasgame.round.event;

import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public abstract class HeroFragEventWrapper extends EventWrapper
{
	protected final PlayableGameHero other;
	
	public HeroFragEventWrapper(PlayableGameHero me, PlayableGameHero other)
	{
		super(me);
		this.other = other;
	}

	public HeroFragEventWrapper(PlayableGameHero me)
	{
		this( me, null);
	}

	public int getMyId()
	{
		return ((PlayableGameHero) sender).getHeroProperties().getPlayerId();
	}

	public int getMyTeamId()
	{
		return ((PlayableGameHero) sender).getHeroProperties().getHeroTeam().getTeamId();
	}

	public int getOtherId()
	{
		if (other == null)
			return -1;
		return other.getHeroProperties().getPlayerId();
	}
	
	public int getOtherTeamId()
	{
		if (other == null)
			return -1;
		return other.getHeroProperties().getHeroTeam().getTeamId();
	}
	
	public abstract void adjustFragStatistic(FragStatistic fragStatistic);

}
