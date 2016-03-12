package br.com.guigasgame.round.hud;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.color.ColorInterpolator;
import br.com.guigasgame.color.ColorLinearInterpolator;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributeListener;
import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class HeroAttributeBarBellowHud implements HeroAttributeListener, Drawable, UpdatableFromTime
{
	public static final Vector2f SIZE = new Vector2f(40, 5);
	
	protected ColorInterpolator innerColor;
	protected ColorInterpolator outterColor;
	protected RectangleShape current;
	protected RectangleShape max;
	protected Vector2f center;
	protected final Vector2f offset;
	

	public HeroAttributeBarBellowHud(ColorBlender color, Vector2f offset)
	{
		this.offset = offset;
		this.center = new Vector2f(0, 0);
		
		innerColor = new ColorLinearInterpolator(color);
		outterColor = new ColorLinearInterpolator(color.darken(3));
		
		current = new RectangleShape(SIZE);
		current.setOrigin(0, SIZE.y/2);
		current.setFillColor(innerColor.getCurrentColor().getColor());
		current.setOutlineColor(innerColor.getCurrentColor().darken(2).getColor());
		current.setOutlineThickness(0.2f);

		max = new RectangleShape(new Vector2f(SIZE.x, SIZE.y));
		max.setOrigin(Vector2f.mul(SIZE, 0.5f));
		max.setFillColor(outterColor.getCurrentColor().getColor());
		max.setOutlineColor(Color.BLACK);
		max.setOutlineThickness(0.2f);
	}
	
	public void setCenter(Vector2f center)
	{
		this.center = center;
		updateCenters();
	}
	
	private void updateCenters()
	{
		final Vector2f offsetCenter = Vector2f.add(center, offset);
		max.setPosition(offsetCenter);
		current.setPosition(offsetCenter.x - SIZE.x/2, offsetCenter.y);
	}



	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(max);
		renderWindow.draw(current);
	}

	@Override
	public void update(float deltaTime)
	{
		innerColor.update(deltaTime);
		outterColor.update(deltaTime);
		current.setFillColor(innerColor.getCurrentColor().getColor());
		max.setFillColor(outterColor.getCurrentColor().getColor());
	}

}
