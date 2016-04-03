package br.com.guigasgame.round.hud.fix;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.frag.FragStatistic;
import br.com.guigasgame.frag.FragStatisticListener;
import br.com.guigasgame.resourcemanager.FontResourceManager;
import br.com.guigasgame.round.hud.controller.HudObject;

public class TeamFragStatisticHud extends HudObject implements FragStatisticListener
{
	private final Text title;
	private final Text score;
	private final RectangleShape rectangleShape;
	private int kills;
	private int deaths;
	private String stringFormatter;
	private final List<HeroFragStatisticHud> herosStatisticHudList;
	
	public TeamFragStatisticHud(Vector2f position, String name, ColorBlender color)
	{
		this.herosStatisticHudList = new ArrayList<>();
		this.rectangleShape = initializeRectangle(position, color); 
		this.title = initializeTitle(color, name, position);
		this.score = initializeScore(position, color);
		adjustTitleSize();
		
		updateText();
		title.setOrigin(title.getLocalBounds().width/2, title.getLocalBounds().height/2);
		score.setOrigin(score.getLocalBounds().width/2, score.getLocalBounds().height/2);

	}

	private static Text initializeScore(Vector2f position, ColorBlender color)
	{
		final Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getFragStatistcsFontFilename());
		final Text score = new Text();
		score.setColor(color.makeTranslucid(0.9f).getSfmlColor());
		score.setFont(font);
		score.setStyle(Text.BOLD);
		score.setCharacterSize(20);
		score.setPosition(Vector2f.add(position, new Vector2f(0, 20)));
		return score;
	}

	private static Text initializeTitle(ColorBlender color, String name, Vector2f position)
	{
		final Font font = FontResourceManager.getInstance().getResource(FilenameConstants.getFragStatistcsFontFilename());
		final Text title = new Text();
//		title.setStyle(Text.BOLD);
//		title.setStyle(Text.UNDERLINED);
		title.setColor(color.makeTranslucid(0.9f).lighten(2).getSfmlColor());
		title.setCharacterSize(30);
		title.setFont(font);
		title.setString(name);
		title.setPosition(position);
		title.setOrigin(title.getLocalBounds().width/2, title.getLocalBounds().height/2);

		return title;
	}

	private void adjustTitleSize()
	{
		//http://en.sfml-dev.org/forums/index.php?topic=8413.0
		/*
		- declare your sf::Text with its string
		- compute its total size (getLocalBounds)
		- compute the ratio desired_size / actual_size
		- multiply the character size by this ratio
		- since the character size is an integer, if you want an exact match you'll need to adjust the scale factor too:
		-- compute the new size of the text (getLocalBounds)
		-- compute the ratio desired_size / actual_size
		-- set it as the scale factor of the text (setScale)
		 */
		
		// Get the size of the text
		final FloatRect bounds = title.getLocalBounds();
		final float ratio = rectangleShape.getSize().x/bounds.width;
		title.setCharacterSize((int) (title.getCharacterSize()*ratio));
	}

	private static RectangleShape initializeRectangle(Vector2f position, ColorBlender color)
	{
		final RectangleShape rectangle = new RectangleShape();
		rectangle.setPosition(position);
		rectangle.setFillColor(color.makeTranslucid(2.5f).darken(2).getSfmlColor());
		rectangle.setOutlineColor(color.makeTranslucid(1.5f).darken(10).getSfmlColor());
		rectangle.setOutlineThickness(3);
		rectangle.setSize(new Vector2f(150, 50));
		rectangle.setOrigin(rectangle.getLocalBounds().width/2, +10);
		return rectangle;
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(rectangleShape);
		renderWindow.draw(title);
		renderWindow.draw(score);
		for( HudObject hudObject : herosStatisticHudList )
		{
			hudObject.draw(renderWindow);
		}
	}
 
	@Override
 	public void onChange(FragStatistic statistic)
 	{
		kills = statistic.getKills();
		deaths = statistic.getDeaths();
		updateText();
 	}	
	
	private void updateText()
	{
		stringFormatter = String.format("%02d|%02d", kills, deaths);
		score.setString(stringFormatter);
	}
	
	public void addHeroFragHud(HeroFragStatisticHud fragCounterHud)
	{
		herosStatisticHudList.add(fragCounterHud);
		adjustHerosFragPosition();
	}

	private void adjustHerosFragPosition()
	{
		for( int i = 0; i < herosStatisticHudList.size(); ++i )
		{
			final Vector2f heroFragHudPosition = calculateHeroFragHudPosition(i);
			herosStatisticHudList.get(i).setPosition(heroFragHudPosition);
		}
		adjustRectangleShape();
	}

	private void adjustRectangleShape()
	{
		float verticalAdjust = ((herosStatisticHudList.size() - 1)/2)*20 + 70;
		rectangleShape.setSize(new Vector2f(rectangleShape.getSize().x, 
											verticalAdjust));
	}

	private Vector2f calculateHeroFragHudPosition(int i)
	{
		final int line = (i/2)*20 + 25;
		final int col = 35 -(i%2)*75;
		return Vector2f.add(score.getPosition(), new Vector2f(col, line));
	}
}
