package br.com.guigasgame.round.hud.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.moving.HeroAttributeMovingHud;

public abstract class HeroAttributesMovingHudController extends HeroAttributesHudController
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
		for( HeroAttributeMovingHud attributeBarBellowHud : barsList )
		{
			attributeBarBellowHud.updatePosition(position);
			attributeBarBellowHud.update(deltaTime);
		}
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
