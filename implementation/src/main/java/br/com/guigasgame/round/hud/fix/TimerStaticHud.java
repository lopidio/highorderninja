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
import br.com.guigasgame.time.ReverseTimeCounter.ReverseTimeCounterListener;


public class TimerStaticHud extends HudObject implements ReverseTimeCounterListener
{

	private final Text text;
	private final Vector2f positionRatio;
	
	public TimerStaticHud(Vector2f positionRatio)
	{
		this.positionRatio = positionRatio;
		this.text = new Text();
		Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getTimerCounterFontFilename());
		text.setFont(font);
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
	
	@Override
	public void halfTime(float currentValue)
	{
		text.setColor(Color.RED);
	}
	
	@Override
	public void timeOut()
	{
		text.setColor(Color.BLACK);
	}
	
	@Override
	public void onDecimalChange(int currentValue)
	{
		String newString = String.format("%02d:%02d", currentValue/60, currentValue%60);
		if (currentValue <= 0)
		{
			currentValue = Math.abs(currentValue);
			newString = String.format("-%02d:%02d", currentValue/60, currentValue%60);
		}
		text.setString(newString);
	}

}
