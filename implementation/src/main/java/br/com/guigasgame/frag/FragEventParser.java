package br.com.guigasgame.frag;

import java.util.ArrayList;
import java.util.List;

import br.com.guigasgame.frag.HeroEventCentralMessenger.HeroEventListener;

public abstract class FragEventParser implements HeroEventListener
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
	public final void receiveFragEvent(HeroEventWrapper eventWrapper)
	{
		System.out.println(eventWrapper.getClass().getSimpleName());
		handleEvent((FragEventWrapper) eventWrapper);
		notifyListeners();
	}

	protected abstract void handleEvent(FragEventWrapper eventWrapper);

}
