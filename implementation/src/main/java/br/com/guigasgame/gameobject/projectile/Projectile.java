package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;


public class Projectile extends GameObject
{
	protected final ProjectileIndex index;
	protected final ProjectileProperties properties;
	private final Animation animation;
	protected Body body;
	protected Vec2 direction;

	protected Projectile(ProjectileIndex index, Vec2 direction, Vec2 position)
	{
		super(position.add(direction));

		this.index = index;

		this.animation = Animation.createAnimation(AnimationsCentralPool.getProjectileAnimationRepository().getAnimationsProperties(index));
		animation.getSprite().setColor(Color.mul(Color.BLACK, Color.BLUE));

		this.properties = ProjectilesPropertiesPool.getProjectileProperties(index);
		this.direction = direction.clone();
		this.body = null;
	}

	@Override
	public void update(float deltaTime)
	{
		animation.getSprite().setPosition(WorldConstants.physicsToSfmlCoordinates(body.getPosition()));
		animation.update(deltaTime);
	}

	@Override
	public Sprite getSprite()
	{
		return animation.getSprite();
	}

	@Override
	protected void editBodyDef(BodyDef bodyDef)
	{
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.bullet = true;
	}

	@Override
	protected void editBody(Body body)
	{
		this.body = body;
	}

}
