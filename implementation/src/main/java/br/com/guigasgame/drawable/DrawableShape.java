package br.com.guigasgame.drawable;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shape;

public class DrawableShape implements Drawable
{

	private Shape drawableShape;
	
	public DrawableShape(Shape drawableShape)
	{
		this.drawableShape = drawableShape;
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(drawableShape);
	}

}
