package br.com.guigasgame.round.hud.barbellow;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.color.ColorInterpolator;
import br.com.guigasgame.color.ColorLinearInterpolator;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributeListener;
import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class HeroAttributeBarBellowHud implements HeroAttributeListener, Drawable, UpdatableFromTime
{
	public static final Vector2f SIZE = new Vector2f(40, 5);
	
	protected ColorInterpolator innerColor;
	protected ColorInterpolator outterColor;
	private RectangleShape innerShape;
	private RectangleShape outterShape;
	private final Vector2f offset;
	

	public HeroAttributeBarBellowHud(ColorBlender color, Vector2f offset)
	{
		this.offset = offset;
		
		innerColor = new ColorLinearInterpolator(color);
		outterColor = new ColorLinearInterpolator(color.darken(1.5f));
		
		innerShape = new RectangleShape(SIZE);
		innerShape.setOrigin(0, innerShape.getSize().y / 2);
		innerShape.setFillColor(innerColor.getCurrentColor().getSfmlColor());

		outterShape = new RectangleShape(new Vector2f(SIZE.x + 2, SIZE.y + 2));
		outterShape.setOrigin(Vector2f.mul(outterShape.getSize(), 0.5f));
		outterShape.setFillColor(outterColor.getCurrentColor().getSfmlColor());
		outterShape.setOutlineColor(outterColor.getCurrentColor().darken(2).getSfmlColor());
		outterShape.setOutlineThickness(1f);
	}
	
	public void setCenter(Vector2f center)
	{
		final Vector2f offsetCenter = Vector2f.add(center, offset);
		outterShape.setPosition(offsetCenter);
		innerShape.setPosition(offsetCenter.x - SIZE.x/2, offsetCenter.y);
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
		final Vector2f newSize = new Vector2f(SIZE.x*ratio, SIZE.y);
		innerShape.setSize(newSize);
	}

}
