package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsfml.graphics.CircleShape;
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
	private static final float INNER_FRAME_SCALE = 0.55f;
	private static final float OUTTER_FRAME_SCALE = 0.75f;
	private static final float ZOOM_OUT_FACTOR = 1.005f;
	private static final float ZOOM_IN_FACTOR = 0.985f;
	
//	private final FloatRect sceneryBoundaries;
	private List<PlayableGameHero> playersToControl;
	private View view;
	private RenderWindow renderWindow;
	private Shape outterFrame;
	private Shape innerFrame;
	private CameraCenterFrame centerFrame;
	
	public CameraController()
	{
//		sceneryBoundaries = floatRect;
		playersToControl = new ArrayList<>();
		centerFrame = new CameraCenterFrame();
		
	}
	
	public void addPlayerToControl(PlayableGameHero player)
	{
		centerFrame.addBody(player.getCollidableHero().getBody());
		playersToControl.add(player);
	}

	public void removePlayertoControl(PlayableGameHero player)
	{
		centerFrame.removeBody(player.getCollidableHero().getBody());
		playersToControl.remove(player);
	}

	@Override
	public void update(float deltaTime)
	{
		if (playersToControl.size() <= 0)
			return;
		
		checkDeadPlayers();
		
		centerFrame.update(deltaTime);
		checkZoomOut();
		checkZoomIn();
		

		final Vector2f focusCenter = centerFrame.getCenter();
		innerFrame.setPosition(focusCenter);
		outterFrame.setPosition(focusCenter);
		interpolateToNewCenter(focusCenter);

		renderWindow.setView(view);
	}
	
	private void checkDeadPlayers()
	{
		Iterator<PlayableGameHero> iterator = playersToControl.iterator();
		while (iterator.hasNext())
		{
			PlayableGameHero toRemove = iterator.next(); // must be called before you can call iterator.remove()
			if (toRemove.isPlayerDead() || toRemove.isMarkedToDestroy())
			{
				centerFrame.removeBody(toRemove.getCollidableHero().getBody());
				iterator.remove();
			}
		}
	}

	private void interpolateToNewCenter(Vector2f newCameraCenter)
	{
		final Vector2f interpolator = Vector2f.mul(Vector2f.sub(newCameraCenter, view.getCenter()), 0.2f); //a + (b - a)*factor
		final Vector2f ultimate = Vector2f.add(interpolator, view.getCenter());
		view.setCenter(ultimate);		
	}

	private void checkZoomIn()
	{
		if (isEveryPlayerInsideInnerFrame() && innerFrame.getScale().x > 0.9f)
		{
			view.zoom(ZOOM_IN_FACTOR);
			innerFrame.scale(ZOOM_IN_FACTOR, ZOOM_IN_FACTOR);
			outterFrame.scale(ZOOM_IN_FACTOR, ZOOM_IN_FACTOR);
		}
	}

	private void checkZoomOut()
	{
		if (isOnePlayerOutsideOutterFrame())
		{
			view.zoom(ZOOM_OUT_FACTOR);
			innerFrame.scale(ZOOM_OUT_FACTOR, ZOOM_OUT_FACTOR);
			outterFrame.scale(ZOOM_OUT_FACTOR, ZOOM_OUT_FACTOR);

		}
	}
	
	private boolean isEveryPlayerInsideInnerFrame()
	{
		for( PlayableGameHero player : playersToControl )
		{
			if (!innerFrame.getGlobalBounds().contains(WorldConstants.physicsToSfmlCoordinates(player.getCollidableHero().getBody().getWorldCenter())))
				return false;
		}
		return true;
	}

	private boolean isOnePlayerOutsideOutterFrame()
	{
		for( PlayableGameHero player : playersToControl )
		{
			if (!outterFrame.getGlobalBounds().contains(WorldConstants.physicsToSfmlCoordinates(player.getCollidableHero().getBody().getWorldCenter())))
				return true;
		}
		return false;
	}

	public void createView(RenderWindow renderWindow)
	{
		this.renderWindow = renderWindow;
		
		Vector2f size = new Vector2f(renderWindow.getSize());
		Vector2f innerSize = Vector2f.mul(size, INNER_FRAME_SCALE);
		Vector2f outterSize = Vector2f.mul(size, OUTTER_FRAME_SCALE);
		
		innerFrame = createSfmlRectangle(innerSize, renderWindow.getView().getCenter());
		innerFrame.setOutlineColor(new Color(200, 0, 0, 100));
		innerFrame.setFillColor(new Color(200, 200, 0, 100));
		innerFrame.setOutlineThickness(1.0f);
		
		outterFrame = createSfmlRectangle(outterSize, renderWindow.getView().getCenter());
		outterFrame.setOutlineColor(new Color(0, 0, 200, 100));
		outterFrame.setFillColor(new Color(0, 200, 200, 100));
		outterFrame.setOutlineThickness(1.0f);
		
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
		renderWindow.draw(innerFrame);
		renderWindow.draw(outterFrame);
		
		Shape circle = new CircleShape(3);
		circle.setPosition(centerFrame.getCenter());
		renderWindow.draw(circle);
		
		Shape smallest = new RectangleShape(centerFrame.getSize());
		smallest.setFillColor(new Color(200, 200, 0, 100));
		smallest.setOutlineThickness(1.0f);
		smallest.setPosition(centerFrame.getOrigin());
		renderWindow.draw(smallest);

	}
}
