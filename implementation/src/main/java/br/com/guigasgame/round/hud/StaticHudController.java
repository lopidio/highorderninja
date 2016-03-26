package br.com.guigasgame.round.hud;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class StaticHudController implements UpdatableFromTime, Drawable
{
	private List<HudObject> staticHudList;
	private View view;
	
	public StaticHudController()
	{
		staticHudList = new ArrayList<>();
	}

	public void createView(RenderWindow renderWindow)
	{
		view = new View(new FloatRect(0, 0, renderWindow.getSize().x, renderWindow.getSize().y));
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( HudObject hudObject : staticHudList )
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
	}

	public View getView()
	{
		return view;
	}

	public void addHud(HudObject hudObject)
	{
		staticHudList.add(hudObject);
	}
	
}
