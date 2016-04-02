package br.com.guigasgame.drawable;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class TextureDrawable implements Drawable
{
	private Sprite sprite;

	public TextureDrawable(Texture texture, Vector2f position)
	{
		sprite = new Sprite(texture);
		sprite.setPosition(position);
	}
	
	public TextureDrawable(Texture backgroundTexture)
	{
		this(backgroundTexture, new Vector2f(0, 0));
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(sprite);
	}

}
