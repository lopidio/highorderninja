package br.com.guigasgame.round.hud.dynamic.heroattributes;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.gameobject.hero.playable.HeroDeathsListener;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.controller.HudObject;

public abstract class HeroMovingHudController extends HudObject implements HeroDeathsListener
{
	protected PlayableGameHero gameHero;
	protected List<HeroAttributeMovingHud> barsList;
	private boolean enabled;
	
	public HeroMovingHudController(PlayableGameHero gameHero)
	{
		this.gameHero = gameHero;
		barsList = new ArrayList<>();
		enabled = true;
	}
	
	@Override
	public void playerHasDied(PlayableGameHero gameHero)
	{
		enabled = false;
	}
	
	@Override
	public void playerHasRespawn(PlayableGameHero gameHero)
	{
		enabled = true;		
	}
	
	@Override
	public void update(float deltaTime)
	{
		if (enabled)
		{
			final Vector2f position = gameHero.getMassCenter();
			for( HeroAttributeMovingHud attributeBarBellowHud : barsList )
			{
				attributeBarBellowHud.updatePosition(position);
				attributeBarBellowHud.update(deltaTime);
			}
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
		if (enabled)
		{
			for( HeroAttributeMovingHud attributeBarBellowHud : barsList )
			{
				attributeBarBellowHud.draw(renderWindow);
			}
		}
	}


}
