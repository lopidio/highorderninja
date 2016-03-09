package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
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
//	private Vector2i regularSize;
//	private FloatRect innerWindow;
//	private FloatRect outterWindow;
	private Shape outterShape;
	private Shape innerShape;
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
		
		FloatRect rect = centerSmallestRect();
		Vector2f center = new Vector2f(rect.left + rect.width/2, rect.top + rect.height/2);

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

//	public static float getArea(Vector2f vec)
//	{
//		return vec.x * vec.y;
//	}
//
//	public float getRegularArea()
//	{
//		return regularSize.x * regularSize.y;
//	}

	public void createView(RenderWindow renderWindow)
	{
		this.renderWindow = renderWindow;
//		regularSize = renderWindow.getSize();
		
		Vector2f size = new Vector2f(renderWindow.getSize());
		Vector2f innerSize = Vector2f.mul(size, 0.6f);
		Vector2f outterSize = Vector2f.mul(size, 0.9f);
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

	private FloatRect centerSmallestRect()
	{
		
		Vector2f first = WorldConstants.physicsToSfmlCoordinates(heros.get(0).getCollidableHero().getBody().getWorldCenter());
		float smallestX = first.x;
		float smallestY = first.y;

		float biggestX = smallestX;
		float biggestY = smallestY;

		for( int i = 1; i < heros.size(); ++i)
		{
			Vector2f point = WorldConstants.physicsToSfmlCoordinates(heros.get(i).getCollidableHero().getBody().getWorldCenter());

			if (point.x > biggestX)
				biggestX = point.x;
			else if (point.x < smallestX)
				smallestX = point.x;

			if (point.y > biggestY)
				biggestY = point.y;
			else if (point.y < smallestY)
				smallestY = point.y;
		}
		return new FloatRect(smallestX, smallestY, biggestX - smallestX, biggestY - smallestY);
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(innerShape);
		renderWindow.draw(outterShape);
		
		
		FloatRect smallestRect = centerSmallestRect();
		System.out.println(smallestRect);
		Vector2f size = new Vector2f(smallestRect.width, smallestRect.height);
		Shape smallest = new RectangleShape(size);
		smallest.setFillColor(new Color(200, 200, 0, 100));
		smallest.setOutlineThickness(1.0f);
//		smallest.setOrigin(Vector2f.mul(size, 0.5f));
		smallest.setPosition(smallestRect.left, smallestRect.top);
		renderWindow.draw(smallest);

	}
}
