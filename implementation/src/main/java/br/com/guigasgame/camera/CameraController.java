package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class CameraController implements UpdatableFromTime
{
	private List<PlayableGameHero> heros;
	private View view;
	private RenderWindow renderWindow;
	private Vector2i regularSize;
	public CameraController()
	{
		heros = new ArrayList<>();
	}
	
	public void addHero(PlayableGameHero gameHero)
	{
		heros.add(gameHero);
	}

	public void removeHero(PlayableGameHero gameHero)
	{
		heros.remove(gameHero);
	}

	@Override
	public void update(float deltaTime)
	{
		//TODO currentZoom gets into math!!
		Vector2f longestDistance = getLongestDistanceBetweenHeros();
		System.out.println(longestDistance);
		if (longestDistance.x > regularSize.x*0.8)
			view.zoom(1.001f);
//		if (longestDistance.y > regularSize.y*0.8)
//			view.zoom(1.001f);
//		if (longestDistance.x < regularSize.x*0.5)
//			view.zoom(0.009f);
		view.setCenter(getCenterOfMass());
		renderWindow.setView(view);
	}

	public void createView(RenderWindow renderWindow)
	{
		this.renderWindow = renderWindow;
		regularSize = renderWindow.getSize();
		view = new View(new FloatRect(0, 0, regularSize.x, regularSize.y));
	}
	
	private Vector2f getLongestDistanceBetweenHeros()
	{
		Vec2 longest = new Vec2();
		for( int i = 0; i < heros.size() - 1; ++i)
		{
			for( int j = i + 1; j < heros.size(); ++j)
			{
				Vec2 distance = heros.get(i).getCollidableHero().getBodyPosition().sub(heros.get(j).getCollidableHero().getBodyPosition());
				if (distance.x > longest.x)
					longest.x = distance.x;
				if (distance.y > longest.y)
					longest.y = distance.y;
			}
			
		}
		return WorldConstants.physicsToSfmlCoordinates(longest);
	}

	private Vector2f getCenterOfMass()
	{
		Vec2 center = new Vec2();
		for( PlayableGameHero playableGameHero : heros )
		{
			center.addLocal(playableGameHero.getCollidableHero().getBodyPosition());
		}
		center.x /= heros.size();
		center.y /= heros.size();
		return WorldConstants.physicsToSfmlCoordinates(center);
	}
}
