package br.com.guigasgame.gameobject.item;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.playable.CollidableHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public abstract class GameItem extends GameObject
{

	protected final GameItemIndex index;
	protected final GameItemProperties properties;
	protected GameItemCollidable collidable;
	private float lifeTime;

	protected GameItem(GameItemIndex index, Vec2 position)
	{
		this.index = index;
		this.properties = GameItemPropertiesPool.getGameItemProperties(index);

		collidable = new GameItemCollidable(position, properties);
		collidable.addListener(this);
		collidableList.add(collidable);

		lifeTime = properties.lifeTime;

		initializeAnimation(position);

	}

	private void initializeAnimation(Vec2 position)
	{
		Animation animation = Animation.createAnimation(AnimationsCentralPool.getGameItemsAnimationRepository().getAnimationsProperties(index));
		animation.scale(new Vector2f(properties.size.getX()/animation.getWidth(), 
				properties.size.getY()/animation.getHeight()));
		animation.setPosition(WorldConstants.physicsToSfmlCoordinates(position));
		drawableList.add(animation);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( Drawable drawable : drawableList )
		{
			drawable.draw(renderWindow);
		}
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body otherBody = (Body) other;
		List<CollidableCategory> categoryList = CollidableCategory.fromMask(otherBody.getFixtureList().getFilterData().categoryBits);
		for( CollidableCategory category : categoryList )
		{
//			System.out.println("Item collided with: " + category.name());
			if (category == CollidableCategory.HEROES)
			{
				CollidableHero collidableHero = (CollidableHero) otherBody.getUserData();
				collidableHero.getPlayableHero().addItem(this);
			}
			else if (category == CollidableCategory.SHURIKEN)
			{
				markToDestroy();
			}
		}
	}

	@Override
	public void update(float deltaTime)
	{
		if (isAlive())
		{
			final float angleInDegrees = (float) WorldConstants.radiansToDegrees(collidable.getAngleRadians());

			lifeTime -= deltaTime;
			for( Drawable drawable : drawableList )
			{
				Animation animation = (Animation) drawable;
				animation.setPosition(WorldConstants.physicsToSfmlCoordinates(collidable.getPosition()));
				animation.update(deltaTime);
				animation.setRotation(angleInDegrees);
			}
			checkAlpha();
			if (lifeTime <= 0)
				markToDestroy();
		}
	}

	private void checkAlpha()
	{
		final float ratio = lifeTime / properties.lifeTime;
		if (ratio < .4) // Less than 40% of lifeTime left
		{
			for( Drawable drawable : drawableList )
			{
				Animation animation = (Animation) drawable;
				animation.setAlpha((int) (ratio*255.0));
			}
		}
	}

	public abstract void acts(PlayableGameHero playableGameHero);

}
