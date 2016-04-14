package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.frag.EventCentralMessenger.EventListener;

public abstract class FragEventParser implements EventListener
{
	private final List<FragStatisticListener> fragListeners;
	protected final FragStatistic frag;
	
	public FragEventParser() 
	{
		this.fragListeners = new ArrayList<>();
		this.frag = new FragStatistic();
	}
	
	public void addListener(FragStatisticListener listener)
	{
		fragListeners.add(listener);
	}
	
	protected void notifyListeners()
	{
		for( FragStatisticListener fragStatisticListener : fragListeners )
		{
			fragStatisticListener.onChange(frag);
		}
	}	
	
	@Override
	public final void receiveFragEvent(EventWrapper eventWrapper)
	{
		handleEvent((HeroFragEventWrapper) eventWrapper);
		notifyListeners();
	}

	protected abstract void handleEvent(HeroFragEventWrapper eventWrapper);

}
