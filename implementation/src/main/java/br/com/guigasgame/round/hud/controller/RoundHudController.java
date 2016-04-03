package br.com.guigasgame.round.hud.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class RoundHudController implements UpdatableFromTime
{
	private final List<HudObject> staticHudList;
	private final List<HudObject> dynamicHudList;
	private final View staticView;
	
	public RoundHudController(Vector2i size)
	{
		staticHudList = new ArrayList<>();
		dynamicHudList = new ArrayList<>();
		staticView = new View(new Vector2f(0, 0), new Vector2f(size));
		staticView.setCenter(Vector2f.mul(new Vector2f(size), 0.5f));
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

}
