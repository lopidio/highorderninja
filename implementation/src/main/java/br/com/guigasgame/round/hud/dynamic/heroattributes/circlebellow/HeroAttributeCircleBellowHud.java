package br.com.guigasgame.round.hud.dynamic.heroattributes.circlebellow;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.color.ColorInterpolator;
import br.com.guigasgame.color.ColorLinearInterpolator;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributeMoveableHud;

public class HeroAttributeCircleBellowHud implements HeroAttributeMoveableHud
{
	
	protected float radius;
	protected ColorInterpolator innerColor;
	protected ColorInterpolator outterColor;
	private CircleShape innerShape;
	private CircleShape outterShape;
	private final Vector2f offset;
	
	public HeroAttributeCircleBellowHud(ColorBlender color, Vector2f offset, float radius)
	{
		this.offset = offset;
		this.radius = radius;
		
		innerColor = new ColorLinearInterpolator(color);
		outterColor = new ColorLinearInterpolator(color.darken(1.5f));
		
		innerShape = new CircleShape();
		innerShape.setRadius(radius);
		innerShape.setOrigin(innerShape.getRadius(), innerShape.getRadius());
		innerShape.setFillColor(innerColor.getCurrentColor().getSfmlColor());

		outterShape = new CircleShape();
		outterShape.setRadius(radius + 1);
		outterShape.setOrigin(outterShape.getRadius(), outterShape.getRadius());
		outterShape.setFillColor(outterColor.getCurrentColor().getSfmlColor());
		outterShape.setOutlineColor(outterColor.getCurrentColor().darken(3).getSfmlColor());
		outterShape.setOutlineThickness(1f);
	}

	public void updatePosition(Vector2f center)
	{
		final Vector2f offsetCenter = Vector2f.add(center, offset);
		outterShape.setPosition(offsetCenter);
		innerShape.setPosition(offsetCenter.x, offsetCenter.y);
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
		innerShape.setFillColor(innerColor.getCurrentColor().getSfmlColor());
		outterShape.setFillColor(outterColor.getCurrentColor().getSfmlColor());
	}
	
	public void adjustCircleLength(HeroAttribute heroAttribute)
	{
		final float ratio = heroAttribute.getCurrentValue()/heroAttribute.getMaxValue();
		innerShape.setRadius(ratio*radius);
		innerShape.setOrigin(innerShape.getRadius(), innerShape.getRadius());
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
