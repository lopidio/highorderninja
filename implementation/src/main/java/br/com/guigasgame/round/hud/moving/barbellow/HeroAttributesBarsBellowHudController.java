package br.com.guigasgame.round.hud.moving.barbellow;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.controller.HeroAttributesMovingHudController;
import br.com.guigasgame.round.hud.moving.HeroAttributeMovingHud;

public class HeroAttributesBarsBellowHudController extends HeroAttributesMovingHudController
{
	private static final ColorBlender SHURIKEN_BAR_COLOR = ColorBlender.GRAY;
	private static final ColorBlender SMOKE_BOMB_BAR_COLOR = ColorBlender.BLUE;
	private static final ColorBlender LIFE_BAR_COLOR = ColorBlender.RED;
	private static final Vector2f SIZE = new Vector2f(30, 3);
	private static final int VERTICAL_OFFSET = 30;
	private static final int SEPARATOR = 4;

	public HeroAttributesBarsBellowHudController(PlayableGameHero gameHero)
	{
		super(gameHero);
	}

	@Override
	public void addAsHudController(RoundHeroAttributes roundHeroAttributes)
	{
		
		HeroAttributeMovingHud shuriken = new ShootingAttributeBarBellowHud(SHURIKEN_BAR_COLOR, new Vector2f(0, VERTICAL_OFFSET), SIZE);
		barsList.add(shuriken);
		HeroAttributeMovingHud smokeBomb = new ShootingAttributeBarBellowHud(SMOKE_BOMB_BAR_COLOR, new Vector2f(0, VERTICAL_OFFSET + SEPARATOR + SIZE.y), SIZE);
		barsList.add(smokeBomb);
		HeroAttributeMovingHud life = new LifeAttributeBarBellowHud(LIFE_BAR_COLOR, new Vector2f(0, VERTICAL_OFFSET + 2*(SEPARATOR + SIZE.y)), SIZE);
		barsList.add(life);

		roundHeroAttributes.getShurikens().addListener(shuriken);
		roundHeroAttributes.getSmokeBomb().addListener(smokeBomb);
		roundHeroAttributes.getLife().addListener(life);
	}

	@Override
	public void markToDestroy()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMarkedToDestroy()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
