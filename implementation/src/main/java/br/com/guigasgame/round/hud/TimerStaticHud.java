package br.com.guigasgame.round.hud;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.file.FilenameConstants;


public class TimerStaticHud extends HudObject
{

	private Text text;
	private float counterValue;
	boolean dead;

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
		counterValue += deltaTime;
		String newString = String.format("%02d:%02d", (int)counterValue/60, (int)counterValue%60);
		text.setString(newString);
	}

	@Override
	public void markToDestroy()
	{
		dead = true;
	}

	@Override
	public boolean isMarkedToDestroy()
	{
		return dead;
	}

}
