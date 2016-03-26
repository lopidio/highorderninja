package br.com.guigasgame.round.hud.dynamic.heroattributes.barbellow.copy;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.color.ColorInterpolator;
import br.com.guigasgame.color.ColorLinearInterpolator;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributeMovingHud;

abstract class HeroAttributeBarBellowHud extends HeroAttributeMovingHud
{
	protected Vector2f size;
	protected ColorInterpolator innerColor;
	protected ColorInterpolator outterColor;
	private RectangleShape innerShape;
	private RectangleShape outterShape;
	private final Vector2f offset;
	

	public HeroAttributeBarBellowHud(ColorBlender color, Vector2f offset, Vector2f size)
	{
		this.offset = offset;
		this.size = size;
		
		innerColor = new ColorLinearInterpolator(color);
		outterColor = new ColorLinearInterpolator(color.darken(2.5f));
		
		innerShape = new RectangleShape(size);
		innerShape.setOrigin(0, innerShape.getSize().y / 2);
		innerShape.setFillColor(innerColor.getCurrentColor().getSfmlColor());

		outterShape = new RectangleShape(new Vector2f(size.x + 2, size.y + 2));
		outterShape.setOrigin(Vector2f.mul(outterShape.getSize(), 0.5f));
		outterShape.setFillColor(outterColor.getCurrentColor().getSfmlColor());
		outterShape.setOutlineColor(outterColor.getCurrentColor().darken(3).getSfmlColor());
		outterShape.setOutlineThickness(1f);
	}
	
	public void updatePosition(Vector2f center)
	{
		final Vector2f offsetCenter = Vector2f.add(center, offset);
		outterShape.setPosition(offsetCenter);
		innerShape.setPosition(offsetCenter.x - size.x/2, offsetCenter.y);
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

	protected void adjustBarsLength(HeroAttribute heroAttribute)
	{
		final float ratio = heroAttribute.getCurrentValue()/heroAttribute.getMaxValue();
		final Vector2f newSize = new Vector2f(size.x*ratio, size.y);
		innerShape.setSize(newSize);
	}

}
