package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public abstract class HeroFragEventWrapper extends EventWrapper
{
	private final PlayableGameHero other;
	
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
	
	protected abstract void adjustFragStatistic(FragStatistic fragStatistic);

}
