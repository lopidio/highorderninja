package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class CameraController implements UpdatableFromTime, Drawable
{
	private static final float INNER_FRAME_SCALE = 0.55f;
	private static final float OUTTER_FRAME_SCALE = 0.80f;
	
	private List<Body> bodiesToControl;
	private View view;
	private RenderWindow renderWindow;
	private Shape outterFrame;
	private Shape innerFrame;
	private CameraCenterFrame centerFrame;
	public CameraController()
	{
		bodiesToControl = new ArrayList<>();
		centerFrame = new CameraCenterFrame();
		
	}
	
	public void addBodyToControl(Body body)
	{
		centerFrame.addBody(body);
		bodiesToControl.add(body);
	}

	public void removeBodytoControl(Body body)
	{
		centerFrame.removeBody(body);
		bodiesToControl.remove(body);
	}

	@Override
	public void update(float deltaTime)
	{
		if (bodiesToControl.size() <= 0)
			return;
		final float zoomOutFactor = 1.005f;
		final float zoomInFactor = 0.985f;
		if (isOnePlayerOutsideOutterWindow())
		{
			view.zoom(zoomOutFactor);
			innerFrame.scale(zoomOutFactor, zoomOutFactor);
			outterFrame.scale(zoomOutFactor, zoomOutFactor);

		}
		if (isEveryPlayerInsideInnerWindow() && innerFrame.getScale().x > 0.9f)
		{
			view.zoom(zoomInFactor);
			innerFrame.scale(zoomInFactor, zoomInFactor);
			outterFrame.scale(zoomInFactor, zoomInFactor);
		}
		
		Vector2f center = centerFrame.getCenter();

		view.setCenter(center);
		innerFrame.setPosition(center);
		outterFrame.setPosition(center);
		renderWindow.setView(view);
	}
	
	private boolean isEveryPlayerInsideInnerWindow()
	{
		for( Body body : bodiesToControl )
		{
			if (!innerFrame.getGlobalBounds().contains(WorldConstants.physicsToSfmlCoordinates(body.getWorldCenter())))
				return false;
		}
		return true;
	}

	private boolean isOnePlayerOutsideOutterWindow()
	{
		for( Body body : bodiesToControl )
		{
			if (!outterFrame.getGlobalBounds().contains(WorldConstants.physicsToSfmlCoordinates(body.getWorldCenter())))
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
		
		
		Shape smallest = new RectangleShape(centerFrame.getSize());
		smallest.setFillColor(new Color(200, 200, 0, 100));
		smallest.setOutlineThickness(1.0f);
		smallest.setPosition(centerFrame.getOrigin());
		renderWindow.draw(smallest);

	}
}
