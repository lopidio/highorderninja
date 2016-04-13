package br.com.guigasgame.frag;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public abstract class FragEventWrapper implements HeroEventWrapper
{
	private final int myId;
	private final int myTeamId;
	private int otherId;
	private int otherTeamId;

	
	public FragEventWrapper(PlayableGameHero me, PlayableGameHero other)
	{
		this( me.getHeroProperties().getPlayerId(), me.getHeroProperties().getHeroTeam().getTeamId(),
				other.getHeroProperties().getPlayerId(), other.getHeroProperties().getHeroTeam().getTeamId());
	}

	public FragEventWrapper(PlayableGameHero me)
	{
		this( me.getHeroProperties().getPlayerId(), me.getHeroProperties().getHeroTeam().getTeamId(), -1, -1);
	}


	public FragEventWrapper(int myId, int myTeamId, int otherId, int otherTeamId)
	{
		this.myId = myId;
		this.myTeamId = myTeamId;
		this.otherId = otherId;
		this.otherTeamId = otherTeamId;
	}
	
	public int getMyId()
	{
		return myId;
	}

	public int getMyTeamId()
	{
		return myTeamId;
	}

	public int getOtherId()
	{
		return otherId;
	}
	
	public int getOtherTeamId()
	{
		return otherTeamId;
	}
	
	
	protected abstract void adjustOwnerFragStatistic(FragStatistic fragStatistic);
	protected void adjustOtherFragStatistic(FragStatistic fragStatistic)
	{
		//hook method
	}

}
