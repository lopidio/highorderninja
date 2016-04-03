package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.frag.FragEventMessenger.FragEventListener;

public class FragStatistic implements FragEventListener
{
	private int shoots;
	private int shootsOnTarget;
	private int hitAsTarget;
	private int deaths;
	private int kills;
	private int suicide;
	private final int id;
	private final List<FragStatisticListener> fragListeners;
	
	public FragStatistic(int id)
	{
		this.id = id;
		 shoots = 0;
		 shootsOnTarget = 0;
		 hitAsTarget = 0;
		 deaths = 0;
		 kills = 0;
		 suicide = 0;
		 fragListeners = new ArrayList<>();
	}
	
	public void addListener(FragStatisticListener listener)
	{
		fragListeners.add(listener);
	}
	
	private void notifyListeners()
	{
		for( FragStatisticListener fragStatisticListener : fragListeners )
		{
			fragStatisticListener.onChange(this);
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

	public FragStatistic incorporate(FragStatistic other)
	{
		 this.shoots += other.shoots;
		 this.shootsOnTarget += other.shootsOnTarget;
		 this.hitAsTarget += other.hitAsTarget;
		 this.deaths += other.deaths;
		 this.kills += other.kills;
		 this.suicide += other.suicide;
		 return this;
	}

	@Override
	public void receiveEvent(FragEventWrapper eventWrapper)
	{
		System.out.println(eventWrapper.getFragEventIndex());
		checkImOwner(eventWrapper);
		checkImOther(eventWrapper);
		notifyListeners();
	}

	private void checkImOwner(FragEventWrapper eventWrapper)
	{
		final int eventOwnerId = eventWrapper.getMyId();
		if (eventOwnerId == id)
		{
			switch (eventWrapper.getFragEventIndex())
			{
				case KILL:
					++kills;
					break;
				case SHOOT:
					++shoots;
					break;
				case SHOOT_ON_TARGET:
					++shootsOnTarget;
					break;
				case SUICIDE:
					++deaths;
					++suicide;
					break;
				default:
					break;
			}
		}
	}
	
	private void checkImOther(FragEventWrapper eventWrapper)
	{
		final int eventOtherId = eventWrapper.getOtherId();
		if (eventOtherId == id)
		{
			switch (eventWrapper.getFragEventIndex())
			{
				case KILL:
					++deaths;
					break;
				case SHOOT_ON_TARGET:
					++hitAsTarget;
					break;
				default:
					break;
			}
			
		}
	}
	
}
