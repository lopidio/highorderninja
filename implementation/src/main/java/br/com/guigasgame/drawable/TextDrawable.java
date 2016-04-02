package br.com.guigasgame.drawable;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;

public class TextDrawable implements Drawable
{
	private Text text;

	public TextDrawable(Text text)
	{
		this.text = text;
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(text);
	}

}
