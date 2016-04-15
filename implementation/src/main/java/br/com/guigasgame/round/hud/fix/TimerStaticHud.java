package br.com.guigasgame.round.hud.fix;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.round.hud.controller.HudObject;


public abstract class TimerStaticHud extends HudObject
{
	private final int totalTime;
	private float currentTime;
	private List<Drawable> drawablesList;
	
	public TimerStaticHud(int totalTime)
	{
		this.totalTime = totalTime;
		this.currentTime = totalTime;
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
		if (checkDecimalChange(deltaTime))
		{
			final int intCurrentTime = (int) currentTime;
			onDecimalChange(intCurrentTime);
			if ((float)intCurrentTime/totalTime == 0.5f)
				halfTime();
			else if (intCurrentTime == 0)
				timeOut();
		}
		currentTime -= deltaTime;
	}
	
	private boolean checkDecimalChange(float deltaTime)
	{
		final int decimal = ((int) Math.floor(currentTime))%10;
		final int newDecimal = ((int) Math.floor((currentTime - deltaTime)))%10;
		return decimal != newDecimal;
	}
	
	protected abstract void halfTime();
	protected abstract void timeOut();
	protected abstract void onDecimalChange(int currentValue);

}
