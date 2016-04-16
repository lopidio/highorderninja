package br.com.guigasgame.round.event;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.frag.FragStatisticListener;

public abstract class FragEventParser
{
	private final List<FragStatisticListener> fragListeners;
	protected final FragStatistic frag;
	
	public FragEventParser() 
	{
		this.fragListeners = new ArrayList<>();
		this.frag = new FragStatistic();
		EventCentralMessenger.getInstance().subscribe(this);
	}
	
	public void addListener(FragStatisticListener listener)
	{
		fragListeners.add(listener);
	}
	
	protected void notifyListeners()
	{
		for( FragStatisticListener fragStatisticListener : fragListeners )
		{
			fragStatisticListener.onFragChange(frag);
		}
	}	
	
	public FragStatistic getFrag()
	{
		return frag;
	}

	@Subscribe public void onHeroFragEvent(HeroFragEventWrapper eventWrapper) 
	{
		handleEvent(eventWrapper);
		notifyListeners();
	}

	protected abstract void handleEvent(HeroFragEventWrapper eventWrapper);

}
