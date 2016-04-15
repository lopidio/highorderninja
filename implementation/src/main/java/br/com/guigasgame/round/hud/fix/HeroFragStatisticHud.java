package br.com.guigasgame.round.hud.fix;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.frag.FragStatisticListener;
import br.com.guigasgame.resourcemanager.FontResourceManager;
import br.com.guigasgame.round.hud.controller.HudObject;

public class HeroFragStatisticHud extends HudObject implements FragStatisticListener
{
	private final Text text;
	private int kills;
	private int deaths;
	private String stringFormatter;
	
	public HeroFragStatisticHud(ColorBlender color)
	{
		Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getFragStatistcsFontFilename());
		
		this.text = new Text();
		text.setFont(font);
		text.setCharacterSize(15);
		text.setColor(color.makeTranslucid(0.9f).lighten(2).getSfmlColor());
		text.setFont(font);
		updateText();
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(text);
	}
	
	public void setPosition(Vector2f position)
	{
		text.setPosition(position);
		text.setOrigin(text.getLocalBounds().width/2, text.getLocalBounds().height/2);
	}
 
	@Override
 	public void onFragChange(FragStatistic statistic)
 	{
		kills = statistic.getKills();
		deaths = statistic.getDeaths();
		updateText();
 	}	
	
	private void updateText()
	{
		stringFormatter = String.format("%02d|%02d", kills, deaths);
		text.setString(stringFormatter);
	}
}
