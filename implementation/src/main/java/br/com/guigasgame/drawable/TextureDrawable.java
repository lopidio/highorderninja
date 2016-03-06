package br.com.guigasgame.drawable;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class TextureDrawable implements Drawable
{

	private Sprite sprite;

	public TextureDrawable(Texture texture)
	{
		sprite = new Sprite(texture);
//		sprite.setOrigin(frameRect.width / 2, frameRect.height / 2);
//		
//		secondsSinceLastUpdate = 0;
//		currentFrameNumber = 0;
//		origin = new Vector2f(0, 0);
//		updateFrameRect();
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(sprite);
	}

}
