package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.resourcemanager.TextureResourceManager;

class NinjaRopePieceDrawable implements Drawable
{
	private RectangleShape shape;
	private float angle;
	private Vector2f position;
	private float size;
	private Texture texture;
	private ColorBlender color;
	
	public NinjaRopePieceDrawable(Vec2 heroPosition, Vec2 hookPoint, ColorBlender colorBlender)
	{
		this.color = colorBlender;
		shape = new RectangleShape(new Vector2f(3, -WorldConstants.toSfmlWorld(size)));
		shape.setOrigin(1.5f, 0);
		shape.setFillColor(Color.BLUE);
		texture = TextureResourceManager.getInstance().getResource(FilenameConstants.getRopePieceAnimationFilename());
		final float size = hookPoint.clone().sub(heroPosition).length();
		this.size = size;
		this.position = WorldConstants.physicsToSfmlCoordinates(hookPoint);
		texture.setRepeated(true);
		this.angle = WorldConstants.calculateAngleInRadians(heroPosition.sub(hookPoint).clone());
		shape.setRotation(angle);
	}
	
	public void setAngleInRadians(float radians)
	{
		this.angle = (float) WorldConstants.radiansToDegrees(radians);
	}
	
	public void setSize(float size)
	{
		this.size = size;
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(shape);
	}
	
	public void updateShape()
	{
		shape = new RectangleShape(new Vector2f(3, -WorldConstants.toSfmlWorld(size)));
		shape.setTextureRect(new IntRect(shape.getLocalBounds()));
		shape.setFillColor(color.getSfmlColor());
		shape.setTexture(texture);
		shape.setOrigin(1.5f, 0);
		shape.setPosition(position);
//		shape.setOrigin(shape.getSize());
		shape.setRotation(angle);
	}

}
