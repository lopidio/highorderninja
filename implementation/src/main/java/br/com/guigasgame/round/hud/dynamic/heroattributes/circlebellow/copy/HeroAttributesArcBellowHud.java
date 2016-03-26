package br.com.guigasgame.round.hud.dynamic.heroattributes.circlebellow.copy;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.color.ColorInterpolator;
import br.com.guigasgame.color.ColorLinearInterpolator;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributeMovingHud;

public class HeroAttributesArcBellowHud extends HeroAttributeMovingHud
{

	protected float radius;
	protected ColorInterpolator innerColor;
	protected ColorInterpolator outterColor;
	private VertexArray innerShape;
	private CircleShape outterShape;
	private final Vector2f offset;
	private float ratio;
	
	public HeroAttributesArcBellowHud(ColorBlender color, Vector2f offset, float radius)
	{
		this.offset = offset;
		this.radius = radius;
		this.ratio = 1;
		
		
		innerColor = new ColorLinearInterpolator(color);
		outterColor = new ColorLinearInterpolator(color.darken(2f));
		
		innerShape = new VertexArray(PrimitiveType.TRIANGLE_FAN);

		outterShape = new CircleShape();
		outterShape.setRadius(radius + 1);
		outterShape.setOrigin(outterShape.getRadius(), outterShape.getRadius());
		outterShape.setFillColor(outterColor.getCurrentColor().getSfmlColor());
		outterShape.setOutlineColor(outterColor.getCurrentColor().darken(3).getSfmlColor());
		outterShape.setOutlineThickness(1f);
	}

	private void updateVertices(Vector2f center, float ratio)
	{
		final float totalPoints = 100;
		final double angle = 2*Math.PI/totalPoints;
		innerShape.clear();
		
		innerShape.add(new Vertex(center, innerColor.getCurrentColor().getSfmlColor()));
		for (int i = 0; i <= totalPoints*ratio; ++i)
		{
			final Vector2f circlePerimeter = new Vector2f((float)(radius*Math.cos(i*angle) + center.x), (float)(radius*Math.sin(i*angle) + center.y));
			innerShape.add(new Vertex(circlePerimeter, innerColor.getCurrentColor().getSfmlColor()));
		}
	}

	public void updatePosition(Vector2f center)
	{
		final Vector2f offsetCenter = Vector2f.add(center, offset);
		outterShape.setPosition(offsetCenter);
		updateVertices(offsetCenter, ratio);
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(outterShape);
		renderWindow.draw(innerShape);
	}

	@Override
	public void update(float deltaTime)
	{
		innerColor.update(deltaTime);
		outterColor.update(deltaTime);
//		innerShape.setFillColor(innerColor.getCurrentColor().getSfmlColor());
		outterShape.setFillColor(outterColor.getCurrentColor().getSfmlColor());
	}
	
	public void adjustCircleLength(HeroAttribute heroAttribute)
	{
		if (innerShape.size() > 0)
		{
			ratio = heroAttribute.getCurrentValue()/heroAttribute.getMaxValue();
			updateVertices(innerShape.get(0).position, ratio);
	//		innerShape.setRadius(ratio*radius);
	//		innerShape.setOrigin(innerShape.getRadius(), innerShape.getRadius());
		}
	}
	
	@Override
	public void gotFull(HeroAttribute heroAttribute)
	{
		innerColor.interpolateFromColor(ColorBlender.YELLOW, 0.5f);
	}

	@Override
	public void changed(HeroAttribute heroAttribute, float value)
	{
		adjustCircleLength(heroAttribute);
		if (value < 0)
		{
			innerColor.interpolateFromColor(innerColor.getCurrentColor().darken(2f), 0.5f);
		}
	}
}
	