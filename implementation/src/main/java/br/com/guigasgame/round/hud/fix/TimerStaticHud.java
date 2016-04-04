package br.com.guigasgame.round.hud.fix;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.round.RoundProperties;
import br.com.guigasgame.round.hud.controller.HudObject;
import br.com.guigasgame.time.TimerEventsController;
import br.com.guigasgame.time.TimerEventsController.TimeListener;


public abstract class TimerStaticHud extends HudObject implements TimeListener
{
	public enum TimerEvents
	{
		HALF_TIME,
		FULL_TIME,
		DECIMAL_CHANGE
	}

	private int totalTime;
	private List<Drawable> drawablesList;
	
	public TimerStaticHud(int totalTime)
	{
		this.totalTime = totalTime;
		drawablesList = new ArrayList<>();
	}
	
	protected void addToDrawableList(Drawable drawable)
	{
		drawablesList.add(drawable);
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( Drawable drawable : drawablesList )
		{
			drawable.draw(renderWindow);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		//do nothing
	}
	
	@Override
	public final void receiveTimeEvent(Object value)
	{
		TimerEvents event = (TimerEvents) value;
		switch (event)
		{
			case HALF_TIME:
				halfTime();
				break;
			case FULL_TIME:
				timeOut();
				break;
			case DECIMAL_CHANGE:
				onDecimalChange(--totalTime);
				break;
			default:
				break;
		}
	}
	
	protected abstract void halfTime();
	protected abstract void timeOut();
	protected abstract void onDecimalChange(int currentValue);

	public void setupTimerEvent(TimerEventsController timerEventsController, RoundProperties roundAttributes)
	{
		timerEventsController.addEventListener(this, roundAttributes.getTotalTime()/2, TimerEvents.HALF_TIME);
		timerEventsController.addEventListener(this, roundAttributes.getTotalTime(), TimerEvents.FULL_TIME);
		timerEventsController.addPeriodicEventListener(this, 1, TimerEvents.DECIMAL_CHANGE);
	}

}
