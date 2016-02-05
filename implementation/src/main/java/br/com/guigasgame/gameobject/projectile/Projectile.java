package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;


public class Projectile extends GameObject
{
	protected final ProjectileIndex index;
	protected final ProjectileProperties properties;
	private final Animation animation;
	protected Vec2 direction;

	protected Projectile(ProjectileIndex index, Vec2 direction, Vec2 position)
	{
		this.index = index;

		this.animation = Animation.createAnimation(AnimationsCentralPool.getProjectileAnimationRepository().getAnimationsProperties(index));
		animation.getSprite().setColor(Color.mul(Color.BLACK, Color.BLUE));

		this.properties = ProjectilesPropertiesPool.getProjectileProperties(index);
		this.direction = direction.clone();

		drawable = animation;
	}

	@Override
	public void update(float deltaTime)
	{
		animation.getSprite().setPosition(WorldConstants.physicsToSfmlCoordinates(collidable.getPosition()));
		animation.update(deltaTime);
	}


}
