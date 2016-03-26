package br.com.guigasgame.round.hud.fix;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.round.hud.controller.HudObject;
import br.com.guigasgame.time.ReverseTimeCounter.ReverseTimeCounterListener;


public class TimerStaticHud extends HudObject implements ReverseTimeCounterListener
{

	private Text text;
	
	public TimerStaticHud(Vector2f position)
	{
		try
		{
			this.text = new Text();
			Font font = new Font();
			font.loadFromFile(Paths.get(FilenameConstants.getCounterFontFilename()));
			text.setFont(font);
			text.setCharacterSize(24);
			text.setPosition(position);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
		ReverseTimeCounterListener.super.onDecimalChange(currentValue);
	}

}
