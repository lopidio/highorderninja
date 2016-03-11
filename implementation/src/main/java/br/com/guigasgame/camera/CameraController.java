package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class CameraController implements UpdatableFromTime, Drawable
{
	private List<PlayableGameHero> heros;
	private View view;
	private RenderWindow renderWindow;
//	private FloatRect innerWindow;
//	private FloatRect outterWindow;
	private Shape outterShape;
	private Shape innerShape;
	private CameraCenterSmallestRect smallestRect;
	public CameraController()
	{
		heros = new ArrayList<>();
		smallestRect = new CameraCenterSmallestRect();
		
	}
	
	public void addHero(PlayableGameHero gameHero)
	{
		smallestRect.addBody(gameHero.getCollidableHero().getBody());
		heros.add(gameHero);
	}

	public void removeHero(PlayableGameHero gameHero)
	{
		smallestRect.removeBody(gameHero.getCollidableHero().getBody());
		heros.remove(gameHero);
	}

	@Override
	public void update(float deltaTime)
	{
		if (heros.size() <= 0)
			return;
		final float zoomOutFactor = 1.005f;
		final float zoomInFactor = 0.985f;
		if (isOnePlayerOutsideOutterWindow())
		{
			view.zoom(zoomOutFactor);
			innerShape.scale(zoomOutFactor, zoomOutFactor);
			outterShape.scale(zoomOutFactor, zoomOutFactor);

		}
		if (isEveryPlayerInsideInnerWindow() && innerShape.getScale().x > 0.9f)
		{
			view.zoom(zoomInFactor);
			innerShape.scale(zoomInFactor, zoomInFactor);
			outterShape.scale(zoomInFactor, zoomInFactor);
		}
		
		Vector2f center = smallestRect.getCenter();

		view.setCenter(center);
		innerShape.setPosition(center);
		outterShape.setPosition(center);
		renderWindow.setView(view);
	}
	
	private boolean isEveryPlayerInsideInnerWindow()
	{
		for( PlayableGameHero playableGameHero : heros )
		{
			if (!innerShape.getGlobalBounds().contains(WorldConstants.physicsToSfmlCoordinates(playableGameHero.getCollidableHero().getBodyPosition())))
				return false;
		}
		return true;
	}

	private boolean isOnePlayerOutsideOutterWindow()
	{
		for( PlayableGameHero playableGameHero : heros )
		{
			if (!outterShape.getGlobalBounds().contains(WorldConstants.physicsToSfmlCoordinates(playableGameHero.getCollidableHero().getBodyPosition())))
				return true;
		}
		return false;
	}

	public void createView(RenderWindow renderWindow)
	{
		this.renderWindow = renderWindow;
		
		Vector2f size = new Vector2f(renderWindow.getSize());
		Vector2f innerSize = Vector2f.mul(size, 0.55f);
		Vector2f outterSize = Vector2f.mul(size, 0.80f);
//		innerWindow = new FloatRect(renderWindow.getView().getCenter(), innerSize);
//		outterWindow = new FloatRect(renderWindow.getView().getCenter(), outterSize);
		
		innerShape = createSfmlRectangle(innerSize, renderWindow.getView().getCenter());
		innerShape.setOutlineColor(new Color(200, 0, 0, 100));
		innerShape.setFillColor(new Color(200, 200, 0, 100));
		innerShape.setOutlineThickness(1.0f);
		
		outterShape = createSfmlRectangle(outterSize, renderWindow.getView().getCenter());
		outterShape.setOutlineColor(new Color(0, 0, 200, 100));
		outterShape.setFillColor(new Color(0, 200, 200, 100));
		outterShape.setOutlineThickness(1.0f);
		
		view = new View(new FloatRect(0, 0, renderWindow.getSize().x, renderWindow.getSize().y));
	}
	
	private Shape createSfmlRectangle(Vector2f dimension, Vector2f position)
	{
		Shape shape = new RectangleShape(dimension);
		shape.setPosition(position);
		shape.setOrigin(dimension.x/2, dimension.y/2);
		
		return shape;
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(innerShape);
		renderWindow.draw(outterShape);
		
		
		Shape smallest = new RectangleShape(smallestRect.getSize());
		smallest.setFillColor(new Color(200, 200, 0, 100));
		smallest.setOutlineThickness(1.0f);
		smallest.setPosition(smallestRect.getOrigin());
		System.out.println(smallestRect.getSize());
		renderWindow.draw(smallest);

	}
}
