package br.com.guigasgame.round.hud.fix;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.resourcemanager.FontResourceManager;
import br.com.guigasgame.round.hud.controller.HudObject;
import br.com.guigasgame.time.TimerEventsController.TimeListener;


public class TimerStaticHud extends HudObject implements TimeListener
{
	public enum TimerEvents
	{
		HALF_TIME,
		FULL_TIME,
		DECIMAL_CHANGE
	}

	private final Text text;
	private final Vector2f positionRatio;
	private int currentValue;
	
	public TimerStaticHud(Vector2f positionRatio, int currentValue)
	{
		this.positionRatio = positionRatio;
		this.text = new Text();
		Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getTimerCounterFontFilename());
		text.setFont(font);
		this.currentValue = currentValue;
	}
	
	
	@Override
	public void setViewSize(Vector2i size)
	{
		text.setCharacterSize(size.x/50);
		text.setPosition(size.x*positionRatio.x,
						size.y*positionRatio.y);
		text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height/ 2);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(text);
	}

	@Override
	public void update(float deltaTime)
	{
		//do nothing
	}
	
	
	private void halfTime()
	{
		text.setColor(Color.RED);
	}
	
	private void timeOut()
	{
		text.setColor(Color.BLACK);
	}
	
	private void onDecimalChange()
	{
		--currentValue;
		String newString = String.format("%02d:%02d", Math.abs(currentValue)/60, Math.abs(currentValue)%60);
		if (currentValue <= 0)
		{
			newString = "-" + newString;
		}
		text.setString(newString);
	}


	@Override
	public void receiveTimeEvent(Object value)
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
				onDecimalChange();
				break;
			default:
				break;
		}
	}

}
