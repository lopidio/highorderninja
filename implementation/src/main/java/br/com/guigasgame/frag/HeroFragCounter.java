package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.List;

public class HeroFragCounter
{
	public interface HeroFragCounterListener
	{
		public default void onShootIncrement(int shoots)
		{
		}
		public default void onShootOnTargetIncrement(int shootsOnTarget)
		{
		}
		public default void onHitAsTargetIncrement(int hitAsTarget)
		{
		}
		public default void onDeathIncrement(int deaths)
		{
		}
		public default void onKillIncrement(int kills)
		{
		}
		public default void onSuicideIncrement(int suicides)
		{
		}
	}
	
	private int shoots;
	private int shootsOnTarget;
	private int hitAsTarget;
	private int deaths;
	private int kills;
	private int suicide;
	private final List<HeroFragCounterListener> fragListeners;
	
	public HeroFragCounter()
	{
		 shoots = 0;
		 shootsOnTarget = 0;
		 hitAsTarget = 0;
		 deaths = 0;
		 kills = 0;
		 suicide = 0;
		 fragListeners = new ArrayList<>();
	}
	
	public void addListener(HeroFragCounterListener listener)
	{
		fragListeners.add(listener);
	}
	
	public void incrementShoots()
	{
		++shoots;
		for( HeroFragCounterListener heroFragCounterListener : fragListeners )
		{
			heroFragCounterListener.onShootIncrement(shoots);
		}
	}

	public void incrementShootsOnTarget()
	{
		++shootsOnTarget;
		for( HeroFragCounterListener heroFragCounterListener : fragListeners )
		{
			heroFragCounterListener.onShootOnTargetIncrement(shootsOnTarget);
		}
	}
	
	public void incrementDeaths()
	{
		++deaths;
		for( HeroFragCounterListener heroFragCounterListener : fragListeners )
		{
			heroFragCounterListener.onDeathIncrement(deaths);
		}
	}
	
	public void incrementKills()
	{
		++kills;
		for( HeroFragCounterListener heroFragCounterListener : fragListeners )
		{
			heroFragCounterListener.onKillIncrement(kills);
		}
	}
	
	public void incrementSuicide()
	{
		incrementDeaths();
		++suicide;
		for( HeroFragCounterListener heroFragCounterListener : fragListeners )
		{
			heroFragCounterListener.onSuicideIncrement(suicide);
		}
	}

	public int getShoots()
	{
		return shoots;
	}
	
	public int getShootsOnTarget()
	{
		return shootsOnTarget;
	}
	
	public int getDeaths()
	{
		return deaths;
	}

	public int getKills()
	{
		return kills;
	}
	
	public int getSuicide()
	{
		return suicide;
	}
	
	public int getHitAsTarget()
	{
		return hitAsTarget;
	}

	public void incrementHitAsTarget()
	{
		++hitAsTarget;
		for( HeroFragCounterListener heroFragCounterListener : fragListeners )
		{
			heroFragCounterListener.onHitAsTargetIncrement(hitAsTarget);
		}
	}
	
	public HeroFragCounter incorporate(HeroFragCounter other)
	{
		 this.shoots += other.shoots;
		 this.shootsOnTarget += other.shootsOnTarget;
		 this.hitAsTarget += other.hitAsTarget;
		 this.deaths += other.deaths;
		 this.kills += other.kills;
		 this.suicide += other.suicide;
		 return this;
	}
	
}
