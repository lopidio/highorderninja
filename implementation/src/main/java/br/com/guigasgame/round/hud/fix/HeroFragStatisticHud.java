package br.com.guigasgame.round.hud.fix;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.frag.FragStatisticListener;
import br.com.guigasgame.resourcemanager.FontResourceManager;
import br.com.guigasgame.round.hud.controller.HudObject;

public class HeroFragStatisticHud extends HudObject implements FragStatisticListener
{
	private final Text text;
	private final Text title;
	private final RectangleShape rectangleShape;
	private int kills;
	private int deaths;
	private final Vector2f positionRatio;
	private String stringFormatter;
	
	public HeroFragStatisticHud(Vector2f positionRatio, String name, ColorBlender color)
	{
		this.positionRatio = positionRatio;
		
		rectangleShape = new RectangleShape();
		rectangleShape.setFillColor(color.makeTranslucid(1.5f).darken(2).getSfmlColor());
		rectangleShape.setOutlineColor(color.makeTranslucid(1.5f).darken(10).getSfmlColor());
		rectangleShape.setOutlineThickness(3);
		
		Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getFragStatistcsFontFilename());

		this.title = new Text();
		title.setFont(font);
		title.setColor(color.makeTranslucid(0.9f).darken(2).getSfmlColor());
		title.setFont(font);
		title.setStyle(Text.BOLD);
		title.setString(name);
		
		this.text = new Text();
		text.setFont(font);
		text.setColor(color.makeTranslucid(0.9f).getSfmlColor());
		text.setFont(font);
		text.setStyle(Text.BOLD);
		updateText();
	}
	
	@Override
	public void setViewSize(Vector2i size)
	{
		title.setPosition(size.x*positionRatio.x, size.y*positionRatio.y);
		title.setCharacterSize(size.x/100);
		text.setCharacterSize(size.x/50);
		text.setPosition(size.x*positionRatio.x, size.y*positionRatio.y);
		
		rectangleShape.setPosition(size.x*positionRatio.x, size.y*positionRatio.y);
		adjustShapes();
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(rectangleShape);
		renderWindow.draw(title);
		renderWindow.draw(text);
	}
 
	@Override
 	public void onChange(FragStatistic statistic)
 	{
		kills = statistic.getKills();
		deaths = statistic.getDeaths();
		updateText();
 	}	
	
	private void adjustShapes()
	{
		text.setString(stringFormatter);
		text.setOrigin(text.getLocalBounds().width/2, text.getLocalBounds().height/2);
		title.setOrigin(title.getLocalBounds().width/2, title.getLocalBounds().height/2);
		rectangleShape.setSize(new Vector2f(text.getLocalBounds().width*1.2f, text.getLocalBounds().height*1.2f));
		rectangleShape.setOrigin(rectangleShape.getLocalBounds().width/2, rectangleShape.getLocalBounds().height/2 - 8);
	}
	
	private void updateText()
	{
		stringFormatter = String.format("%02d|%02d", kills, deaths);
		adjustShapes();
	}
}
