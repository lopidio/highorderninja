package br.com.guigasgame.round.hud.dynamic.heroattributes.barbellow;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributeMoveableHud;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroMovingHudController;
import br.com.guigasgame.round.hud.dynamic.heroattributes.circlebellow.HeroAttributesArcBellowHud;

public class HeroAttributesDefaultHudController extends HeroMovingHudController
{
	private static final ColorBlender SHURIKEN_BAR_COLOR = ColorBlender.GRAY.makeTranslucid(1.8f);
	private static final ColorBlender SMOKE_BOMB_BAR_COLOR = ColorBlender.GREEN.makeTranslucid(1.8f);
	private static final ColorBlender LIFE_BAR_COLOR = ColorBlender.RED.makeTranslucid(1.8f);
	private static final Vector2f SIZE = new Vector2f(30, 10);
	private static final int VERTICAL_OFFSET = 40;
	private static final int SEPARATOR = 4;
	private boolean markedToDestroy;

	public HeroAttributesDefaultHudController(PlayableGameHero gameHero)
	{
		super(gameHero);
		
		final RoundHeroAttributes roundHeroAttributes = gameHero.getHeroProperties().getRoundHeroAttributes();

		HeroAttributeMoveableHud smokeBomb = new ShootingAttributeBarBellowHud(SMOKE_BOMB_BAR_COLOR.add(gameHero.getHeroProperties().getColor()), new Vector2f(-SIZE.x/14, VERTICAL_OFFSET + SEPARATOR + SIZE.y/2 + 2), Vector2f.mul(SIZE, 0.7f));
		heroAttributesList.add(smokeBomb);
		HeroAttributeMoveableHud shuriken = new ShootingAttributeBarBellowHud(SHURIKEN_BAR_COLOR.add(gameHero.getHeroProperties().getColor()), new Vector2f(0, VERTICAL_OFFSET), SIZE);
		heroAttributesList.add(shuriken);
		final float radius = 15;
		HeroAttributeMoveableHud life = new HeroAttributesArcBellowHud(LIFE_BAR_COLOR.add(gameHero.getHeroProperties().getColor()), new Vector2f( -13 -radius, VERTICAL_OFFSET + radius/2), radius);
		heroAttributesList.add(life);
		
		roundHeroAttributes.getShurikens().addListener(shuriken);
		roundHeroAttributes.getSmokeBomb().addListener(smokeBomb);
		roundHeroAttributes.getLife().addListener(life);
	}

	@Override
	public void markToDestroy()
	{
		markedToDestroy = true;
	}

	@Override
	public boolean isMarkedToDestroy()
	{
		return markedToDestroy;
	}
	
	@Override
	public void destroy()
	{
		heroAttributesList.clear();
	}

}
