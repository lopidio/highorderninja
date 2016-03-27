package br.com.guigasgame.round.hud.fix;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.frag.HeroFragStatistic;
import br.com.guigasgame.frag.HeroFragStatistic.HeroFragStatisticListener;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.controller.HudObject;

public class HeroFragStatisticHud extends HudObject implements HeroFragStatisticListener
{
	
	private Text text;
	private final RectangleShape rectangleShape;
	private final HeroFragStatistic fragStatistic;
	private final Vector2f positionRatio;
	private String stringFormatter;
	
	public HeroFragStatisticHud(Vector2f positionRatio, PlayableGameHero gameHero)
	{
		this.positionRatio = positionRatio;
		this.fragStatistic = gameHero.getFragStatistic();
		
		rectangleShape = new RectangleShape();
		rectangleShape.setFillColor(gameHero.getHeroProperties().getColor().makeTranslucid(1.5f).darken(2).getSfmlColor());
		rectangleShape.setOutlineColor(gameHero.getHeroProperties().getColor().makeTranslucid(1.5f).darken(10).getSfmlColor());
		rectangleShape.setOutlineThickness(3);
		
		try
		{
			this.text = new Text();
			Font font = new Font();
			font.loadFromFile(Paths.get(FilenameConstants.getCounterFontFilename()));
			text.setColor(gameHero.getHeroProperties().getColor().makeTranslucid(0.9f).getSfmlColor());
			text.setFont(font);
//			text.setStyle(Text.BOLD);
			updateText();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void setViewSize(Vector2i size)
	{
		text.setCharacterSize(size.x/50);
		text.setPosition(size.x*positionRatio.x, size.y*positionRatio.y);
		
		rectangleShape.setPosition(size.x*positionRatio.x, size.y*positionRatio.y);
		adjustShapes();
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(rectangleShape);
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
	
	private void adjustShapes()
	{
		text.setString(stringFormatter);
		text.setOrigin(text.getLocalBounds().width/2, text.getLocalBounds().height/2);
		rectangleShape.setSize(new Vector2f(text.getLocalBounds().width*1.2f, text.getLocalBounds().height*1.2f));
		rectangleShape.setOrigin(rectangleShape.getLocalBounds().width/2, rectangleShape.getLocalBounds().height/2 - 8);
	}
	
	private void updateText()
	{
		stringFormatter = String.format("%02d|%02d", fragStatistic.getKills(), fragStatistic.getDeaths());
		adjustShapes();
	}
}
