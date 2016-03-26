package br.com.guigasgame.round.hud;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.frag.HeroFragCounter;
import br.com.guigasgame.frag.HeroFragCounter.HeroFragCounterListener;

public class HeroFragCounterHud extends HudObject implements HeroFragCounterListener
{
	
	private Text text;
	private final HeroFragCounter fragCounter;
	
	public HeroFragCounterHud(Vector2f position, HeroFragCounter counter, ColorBlender color)
	{
		this.fragCounter = counter;
		try
		{
			this.text = new Text();
			Font font = new Font();
			font.loadFromFile(Paths.get(FilenameConstants.getCounterFontFilename()));
			text.setColor(color.makeTranslucid(0.2f).getSfmlColor());
			text.setFont(font);
			text.setCharacterSize(24);
			text.setPosition(position);
			updateText();
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
	public void onKillIncrement(int kills)
	{
		updateText();
	}


	@Override
	public void onDeathIncrement(int deaths)
	{
		updateText();
	}
	
	private void updateText()
	{
		String newString = String.format("%02d/%02d", fragCounter.getKills(), fragCounter.getDeaths());
		text.setString(newString);
	}
}
