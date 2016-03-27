package br.com.guigasgame.round.hud.dynamic.heroattributes;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.controller.HudObject;

public abstract class HeroAttributesMovingHudController extends HudObject
{
	protected PlayableGameHero gameHero;
	protected List<HeroAttributeMovingHud> barsList;
	
	public HeroAttributesMovingHudController(PlayableGameHero gameHero)
	{
		this.gameHero = gameHero;
		barsList = new ArrayList<>();
	}
	
	@Override
	public void update(float deltaTime)
	{
		final Vector2f position = gameHero.getMassCenter();
		if (gameHero.isMarkedToDestroy() || gameHero.isPlayerDead())
		{
			System.out.println("Destroying HUD");
			markToDestroy();
		}
		for( HeroAttributeMovingHud attributeBarBellowHud : barsList )
		{
			attributeBarBellowHud.updatePosition(position);
			attributeBarBellowHud.update(deltaTime);
		}
	}
	
	@Override
	public void destroy()
	{
		barsList.clear();
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( HeroAttributeMovingHud attributeBarBellowHud : barsList )
		{
			attributeBarBellowHud.draw(renderWindow);
		}
	}


}
