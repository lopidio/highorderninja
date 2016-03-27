package br.com.guigasgame.round.hud.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.round.hud.RoundHudPositioner;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class HudController implements UpdatableFromTime
{
	private List<HudObject> staticHudList;
	private List<HudObject> dynamicHudList;
	private View staticView;
	private final RoundHudPositioner roundHudPositioner;
	
	public HudController(RoundHudPositioner roundHudPositioner)
	{
		staticHudList = new ArrayList<>();
		dynamicHudList = new ArrayList<>();
		this.roundHudPositioner = roundHudPositioner;
	}

	public void setViewSize(Vector2i size)
	{
		staticView = new View(new FloatRect(0, 0, size.x, size.y));
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

	public View getView()
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

	public RoundHudPositioner getRoundHudPositioner()
	{
		return roundHudPositioner;
	}
	
}
