package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.projectile.aimer.ProjectileAimer;


public abstract class Projectile extends GameObject
{
	protected final ProjectileIndex index;
	protected final ProjectileProperties properties;
	
	protected CollidableFilter collidableFilter;
	protected Vec2 direction;
	protected IntegerMask targetMask;
	protected ProjectileCollidable collidable;

	protected Projectile(ProjectileIndex index, Vec2 direction, Vec2 position)
	{
		this.index = index;

		Animation animation = Animation.createAnimation(AnimationsCentralPool.getProjectileAnimationRepository().getAnimationsProperties(index));

		this.properties = ProjectilesPropertiesPool.getProjectileProperties(index);
		this.direction = direction.clone();

		collidable = new ProjectileCollidable(position);
		collidable.addListener(this);
		collidableList.add(collidable);
		collidableFilter = null;
		
		this.targetMask = new IntegerMask();

		drawableList.add(animation);
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		for (Drawable drawable : drawableList) 
		{
			drawable.draw(renderWindow);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		if (alive)
		{
			for( Drawable drawable : drawableList )
			{
				Animation animation = (Animation) drawable;
				animation.setPosition(WorldConstants.physicsToSfmlCoordinates(collidable.getPosition()));
				animation.update(deltaTime);
			}
		}
	}

	protected FixtureDef createFixtureDef()
	{
		CircleShape projectileShape = new CircleShape();
		projectileShape.setRadius(properties.radius);

		FixtureDef def = new FixtureDef();
		def.restitution = properties.restitution;
		def.shape = projectileShape;
		def.density = properties.mass;
		def.friction = properties.friction;
		def.filter = new CollidableFilterBox2dAdapter(collidableFilter).toBox2dFilter();

		return def;
	}

	protected void shoot()
	{
		Body body = collidable.getBody();
		FixtureDef def = createFixtureDef();
		body.createFixture(def);
		
		ProjectileAimer aimer = new ProjectileAimer(this, collidable.getBody());
		direction = aimer.getFinalDirection();

		direction.normalize();
		direction.mulLocal(properties.initialSpeed);
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

	public Vec2 getDirection() 
	{
		return direction;
	}
	
	public CollidableFilter getCollidableFilter() 
	{
		return collidableFilter;
	}
	
	public ProjectileProperties getProperties()
	{
		return properties;
	}

	public IntegerMask getTargetMask() 
	{
		return targetMask;
	}
	
}
