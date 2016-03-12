package br.com.guigasgame.round.hud.barbellow;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.HeroAttributesHudController;

public class HeroAttributesBarsBellowHudController extends HeroAttributesHudController
{
	private static final ColorBlender SHURIKEN_BAR_COLOR = ColorBlender.GRAY;
	private static final ColorBlender SMOKE_BOMB_BAR_COLOR = ColorBlender.BLUE;
	private static final ColorBlender LIFE_BAR_COLOR = ColorBlender.RED;
	private int VERTICAL_OFFSET = 40;
	private int SEPARATOR = 1;
	private PlayableGameHero gameHero;
	private List<HeroAttributeBarBellowHud> barsList;

	public HeroAttributesBarsBellowHudController(PlayableGameHero gameHero)
	{
		this.gameHero = gameHero;
		barsList = new ArrayList<>();
	}

	@Override
	public void update(float deltaTime)
	{
		final Vector2f position = gameHero.getMassCenter();
		for( HeroAttributeBarBellowHud attributeBarBellowHud : barsList )
		{
			attributeBarBellowHud.setCenter(position);
			attributeBarBellowHud.update(deltaTime);
		}
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( HeroAttributeBarBellowHud attributeBarBellowHud : barsList )
		{
			attributeBarBellowHud.draw(renderWindow);
		}
	}

	@Override
	public void addAsHudController(RoundHeroAttributes roundHeroAttributes)
	{
		ShootingAttributeBarBellowHud shuriken = new ShootingAttributeBarBellowHud(SHURIKEN_BAR_COLOR, new Vector2f(0, VERTICAL_OFFSET));
		barsList.add(shuriken);
		ShootingAttributeBarBellowHud smokeBomb = new ShootingAttributeBarBellowHud(SMOKE_BOMB_BAR_COLOR, new Vector2f(0, VERTICAL_OFFSET + SEPARATOR + ShootingAttributeBarBellowHud.SIZE.y));
		barsList.add(smokeBomb);
		LifeAttributeBarBellowHud life = new LifeAttributeBarBellowHud(LIFE_BAR_COLOR, new Vector2f(0, VERTICAL_OFFSET + 2*(SEPARATOR + ShootingAttributeBarBellowHud.SIZE.y)));
		barsList.add(life);

		roundHeroAttributes.getShurikens().addListener(shuriken);
		roundHeroAttributes.getSmokeBomb().addListener(smokeBomb);
		roundHeroAttributes.getLife().addListener(life);
	}

}
