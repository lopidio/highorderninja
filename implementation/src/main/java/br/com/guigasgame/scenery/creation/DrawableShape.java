package br.com.guigasgame.scenery.creation;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shape;

import br.com.guigasgame.drawable.Drawable;

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
