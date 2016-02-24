package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.projectile.aimer.ProjectileAimer;


public abstract class Projectile extends GameObject
{
	protected final ProjectileIndex index;
	protected final ProjectileProperties properties;
	
	private final Animation animation;
	protected CollidableFilter collidableFilter;
	protected Vec2 direction;
	protected IntegerMask targetMask;

	protected Projectile(ProjectileIndex index, Vec2 direction, Vec2 position)
	{
		this.index = index;

		this.animation = Animation.createAnimation(AnimationsCentralPool.getProjectileAnimationRepository().getAnimationsProperties(index));
		animation.setColor(Color.mul(Color.BLACK, Color.BLUE));

		this.properties = ProjectilesPropertiesPool.getProjectileProperties(index);
		this.direction = direction.clone();

		collidable = new ProjectileCollidable(position);
		collidable.addListener(this);
		collidableFilter = null;
		this.targetMask = editTarget(new IntegerMask());
		
		drawable = animation;
	}

	protected IntegerMask editTarget(IntegerMask target)
	{
		//hook method
		return target;
	}
	
	protected abstract CollidableFilter createCollidableFilter();
//	{
//		projectileCollidableFilter = new ProjectileCollidableFilter(CollidableConstants.getShurikenCollidableFilter());
//		return projectileCollidableFilter;
//	}

	@Override
	public void update(float deltaTime)
	{
		animation.setPosition(WorldConstants.physicsToSfmlCoordinates(collidable.getPosition()));
		animation.update(deltaTime);
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
		collidableFilter = createCollidableFilter();
		def.filter = new CollidableFilterBox2dAdapter(collidableFilter).toBox2dFilter();
		return def;
	}

	protected void shoot()
	{
		Body body = collidable.getBody();
		FixtureDef def = createFixtureDef();
		body.createFixture(def);
		
		ProjectileAimer aimer = new ProjectileAimer(this);
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
