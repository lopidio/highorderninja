package br.com.guigasgame.camera;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;
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
import br.com.guigasgame.updatable.UpdatableFromTime;

public class CameraController implements UpdatableFromTime, Drawable
{
	private static final float INNER_FRAME_SCALE = 0.55f;
	private static final float OUTTER_FRAME_SCALE = 0.80f;
	private static final float ZOOM_OUT_FACTOR = 1.005f;
	private static final float ZOOM_IN_FACTOR = 0.985f;
	
	private final br.com.guigasgame.math.FloatRect sceneryBoundaries;
	private List<Body> bodiesToControl;
	private View view;
	private RenderWindow renderWindow;
	private Shape outterFrame;
	private Shape innerFrame;
	private CameraCenterFrame centerFrame;
	
	public CameraController(br.com.guigasgame.math.FloatRect sceneBoundaries)
	{
		sceneryBoundaries = sceneBoundaries;
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
		
		centerFrame.update(deltaTime);
		checkZoomOut();
		checkZoomIn();
		

		final Vector2f focusCenter = centerFrame.getCenter();
		adjustZoomFrameCenter(innerFrame, focusCenter);
		adjustZoomFrameCenter(outterFrame, focusCenter);
		
		final Vector2f newCameraCenter = adjustCenterToSceneBoundaries(focusCenter, view.getSize()); 
		interpolateToNewCenter(newCameraCenter);

		renderWindow.setView(view);
	}
	
	private void interpolateToNewCenter(Vector2f newCameraCenter)
	{
		final Vector2f interpolator = Vector2f.mul(Vector2f.sub(newCameraCenter, view.getCenter()), 0.2f); //a + (b - a)*factor
		final Vector2f ultimate = Vector2f.add(interpolator, view.getCenter());
		view.setCenter(ultimate);		
	}

	private void adjustZoomFrameCenter(Shape frame, Vector2f center)
	{
		FloatRect frameGlobalBound = frame.getGlobalBounds();
		frame.setPosition(adjustCenterToSceneBoundaries(center, 
				new Vector2f(frameGlobalBound.width,
							frameGlobalBound.height)));
	}

	private Vector2f adjustCenterToSceneBoundaries(Vector2f focusCenter, Vector2f viewSize)
	{
		float xCenter = focusCenter.x;
		float yCenter = focusCenter.y;
		if (focusCenter.y + viewSize.y/2 > sceneryBoundaries.top + sceneryBoundaries.height)
		{
			yCenter = sceneryBoundaries.top + sceneryBoundaries.height - viewSize.y/2;
		}
		else if (focusCenter.y - viewSize.y/2 < sceneryBoundaries.top)
		{
			yCenter = sceneryBoundaries.top + viewSize.y/2;
		}
		
		if (focusCenter.x + viewSize.x/2 > sceneryBoundaries.left + sceneryBoundaries.width)
		{
			xCenter = sceneryBoundaries.left + sceneryBoundaries.width - viewSize.x/2;
		}
		else if (focusCenter.x - viewSize.x/2 < sceneryBoundaries.left)
		{
			xCenter = sceneryBoundaries.left + viewSize.x/2;
		}
		
		return new Vector2f(xCenter, yCenter);
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
		for( Body body : bodiesToControl )
		{
			if (!innerFrame.getGlobalBounds().contains(WorldConstants.physicsToSfmlCoordinates(body.getWorldCenter())))
				return false;
		}
		return true;
	}

	private boolean isOnePlayerOutsideOutterFrame()
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
