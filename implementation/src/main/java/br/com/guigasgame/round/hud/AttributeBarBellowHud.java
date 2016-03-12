package br.com.guigasgame.round.hud;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributeListener;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class AttributeBarBellowHud implements HeroAttributeListener, Drawable, UpdatableFromTime
{
	public static final Vector2f SIZE = new Vector2f(40, 5);
	
//	private Color fillColor;
//	private Color outlineColor;
	private RectangleShape current;
	private RectangleShape max;
	private Vector2f center;
	private final Vector2f offset;
	

	public AttributeBarBellowHud(Color fillColor, Vector2f offset)
	{
		this.offset = offset;
		this.center = new Vector2f(0, 0);
//		this.fillColor = fillColor;
//		this.outlineColor = Color.add(fillColor, Color.BLACK);
		current = new RectangleShape(SIZE);
		current.setOrigin(0, SIZE.y/2);
		current.setFillColor(fillColor);

		max = new RectangleShape(SIZE);
		max.setOrigin(Vector2f.mul(SIZE, 0.5f));
		max.setFillColor(Color.BLACK);
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
	public void isFull(HeroAttribute heroAttribute)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotEmpty(HeroAttribute heroAttribute)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changed(HeroAttribute heroAttribute, float value)
	{
		float ratio = heroAttribute.getCurrentValue()/heroAttribute.getMaxValue();
		final Vector2f newSize = new Vector2f(SIZE.x*ratio, SIZE.y);
		
		current.setSize(newSize);
//		current.setOrigin(Vector2f.mul(newSize, 0.5f));
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
		// TODO Auto-generated method stub
		
	}

}
