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
	private Text outlineText;
	private final HeroFragCounter fragCounter;
	
	public HeroFragCounterHud(Vector2f position, HeroFragCounter counter, ColorBlender color)
	{
		this.fragCounter = counter;
		try
		{
			this.text = new Text();
			this.outlineText = new Text();
			Font font = new Font();
			font.loadFromFile(Paths.get(FilenameConstants.getCounterFontFilename()));
			text.setColor(color.makeTranslucid(0.2f).getSfmlColor());
			outlineText.setColor(color.makeTranslucid(0.8f).darken(3).getSfmlColor());
			text.setFont(font);
			outlineText.setFont(font);
			text.setCharacterSize(24);
			outlineText.setCharacterSize(25);
			outlineText.setStyle(Text.BOLD);
			updateText();
			text.setOrigin(text.getLocalBounds().width/2, text.getLocalBounds().height/2);
			outlineText.setOrigin(outlineText.getLocalBounds().width/2, outlineText.getLocalBounds().height/2);
			text.setPosition(position);
			outlineText.setPosition(position);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(outlineText);
		renderWindow.draw(text);		
	}
	
	@Override
	public void onKillIncrement(int kills)
	{
		updateText();
	}

	@Override
	public void onShootIncrement(int shoots)
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
		String newString = String.format("(%d)%02d/%02d", fragCounter.getShoots(), fragCounter.getKills(), fragCounter.getDeaths());
		text.setString(newString);
		outlineText.setString(newString);
	}
}
