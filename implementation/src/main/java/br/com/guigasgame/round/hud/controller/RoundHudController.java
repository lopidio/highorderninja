package br.com.guigasgame.round.hud.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.frag.HeroFragStatistic;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.RoundHudPositioner;
import br.com.guigasgame.round.hud.dynamic.heroattributes.barbellow.HeroAttributesCircleAndBarsBellowHudController;
import br.com.guigasgame.round.hud.fix.HeroFragStatisticHud;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class RoundHudController implements UpdatableFromTime
{
	private List<HudObject> staticHudList;
	private List<HudObject> dynamicHudList;
	private View staticView;
	private final RoundHudPositioner roundHudPositioner;
	
	public RoundHudController(RoundHudPositioner roundHudPositioner)
	{
		staticHudList = new ArrayList<>();
		dynamicHudList = new ArrayList<>();
		this.roundHudPositioner = roundHudPositioner;
	}

	public void setViewSize(Vector2i size)
	{
		staticView = new View(new FloatRect(0, 0, size.x, size.y));
		for( HudObject hudObject : dynamicHudList )
		{
			hudObject.setViewSize(size);
		}
		for( HudObject hudObject : staticHudList )
		{
			hudObject.setViewSize(size);
		}
	}

	public void drawStaticHud(RenderWindow renderWindow)
	{
		for( HudObject hudObject : staticHudList )
		{
			hudObject.draw(renderWindow);
		}
	}

	public void drawDynamicHud(RenderWindow renderWindow)
	{
		for( HudObject hudObject : dynamicHudList )
		{
			hudObject.draw(renderWindow);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		for( HudObject hudObject : staticHudList )
		{
			hudObject.update(deltaTime);
		}
		for( HudObject hudObject : dynamicHudList )
		{
			hudObject.update(deltaTime);
		}
		Destroyable.clearDestroyable(staticHudList);
		Destroyable.clearDestroyable(dynamicHudList);
	}

	public View getStaticView()
	{
		return staticView;
	}

	public void addStaticHud(HudObject hudObject)
	{
		staticHudList.add(hudObject);
	}

	public void addDynamicHud(HudObject hudObject)
	{
		dynamicHudList.add(hudObject);
	}

	public void addHeroHud(PlayableGameHero gameHero)
	{
		addHeroMovingHud(gameHero);
		addHeroStaticHud(gameHero);
	}

	private void addHeroStaticHud(PlayableGameHero gameHero)
	{
		//TODO THERE HAS TO BE A FACTORY AS AN INSTANCE ATTRIBUTE
		HudObject hud = new HeroAttributesCircleAndBarsBellowHudController(gameHero); 
		addDynamicHud(hud);
	}

	private void addHeroMovingHud(PlayableGameHero gameHero)
	{
		Vector2f position = roundHudPositioner.getTeamFragCounterPositioner(gameHero.getHeroProperties().getHeroTeam());
		HeroFragStatistic fragCounter = gameHero.getFragStatistic();			
//		HeroFragStatisticHud fragCounterHud = new HeroFragStatisticHud(position, gameHero);
		HeroFragStatisticHud fragCounterHud = new HeroFragStatisticHud(position, gameHero);
		fragCounter.addListener(fragCounterHud);
		addStaticHud(fragCounterHud);
	}

}
