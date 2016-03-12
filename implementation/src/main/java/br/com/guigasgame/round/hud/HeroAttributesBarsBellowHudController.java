package br.com.guigasgame.round.hud;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public class HeroAttributesBarsBellowHudController extends HeroAttributesHud
{
	private int VERTICAL_OFFSET = 40;
	private int SEPARATOR = 1;
	private PlayableGameHero gameHero;
	private List<AttributeBarBellowHud> barsList;

	public HeroAttributesBarsBellowHudController(PlayableGameHero gameHero)
	{
		this.gameHero = gameHero;
		barsList = new ArrayList<>();
	}

	@Override
	public void update(float deltaTime)
	{
		final Vector2f position = gameHero.getMassCenter();
		for( AttributeBarBellowHud attributeBarBellowHud : barsList )
		{
			attributeBarBellowHud.setCenter(position);
			attributeBarBellowHud.update(deltaTime);
		}
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( AttributeBarBellowHud attributeBarBellowHud : barsList )
		{
			attributeBarBellowHud.draw(renderWindow);
		}
	}

	@Override
	public void addAsListener(RoundHeroAttributes roundHeroAttributes)
	{
		AttributeBarBellowHud shuriken = new AttributeBarBellowHud(new Color(0, 70, 0), new Vector2f(0, VERTICAL_OFFSET));
		barsList.add(shuriken);
		AttributeBarBellowHud smokeBomb = new AttributeBarBellowHud(new Color(150, 150, 150), new Vector2f(0, VERTICAL_OFFSET + SEPARATOR + AttributeBarBellowHud.SIZE.y));
		barsList.add(smokeBomb);
		AttributeBarBellowHud life = new AttributeBarBellowHud(new Color(150, 0, 0), new Vector2f(0, VERTICAL_OFFSET + 2*(SEPARATOR + AttributeBarBellowHud.SIZE.y)));
		barsList.add(life);

		roundHeroAttributes.getShurikens().addListener(shuriken);
		roundHeroAttributes.getSmokeBomb().addListener(smokeBomb);
		roundHeroAttributes.getLife().addListener(life);
	}

}
