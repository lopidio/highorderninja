package br.com.guigasgame.scenery;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shape;

import br.com.guigasgame.drawable.Drawable;

public class DrawableTile implements Drawable
{

	private Shape drawableShape;
	
	public DrawableTile(Shape drawableShape)
	{
		this.drawableShape = drawableShape;
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(drawableShape);
	}

}
