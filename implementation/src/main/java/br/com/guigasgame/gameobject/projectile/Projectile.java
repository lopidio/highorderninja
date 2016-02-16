package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.gameobject.GameObject;


public abstract class Projectile extends GameObject
{
	protected final ProjectileIndex index;
	protected final ProjectileProperties properties;
	private final Animation animation;
	private ProjectileCollidableFilter projectileCollidableFilter;
	protected Vec2 direction;

	protected Projectile(ProjectileIndex index, Vec2 direction, Vec2 position)
	{
		this.index = index;

		this.animation = Animation.createAnimation(AnimationsCentralPool.getProjectileAnimationRepository().getAnimationsProperties(index));
		animation.setColor(Color.mul(Color.BLACK, Color.BLUE));

		this.properties = ProjectilesPropertiesPool.getProjectileProperties(index);
		this.direction = direction.clone();

		collidable = new ProjectileCollidable(position);
		collidable.addListener(this);
		projectileCollidableFilter = null;
		
		drawable = animation;
	}

	protected abstract ProjectileCollidableFilter createCollidableFilter(ProjectileCollidableFilter collisionProperty);	

	@Override
	public void update(float deltaTime)
	{
		animation.setPosition(WorldConstants.physicsToSfmlCoordinates(collidable.getPosition()));
		animation.update(deltaTime);
	}

	protected void adjustInitialDirection()
	{
		//hook method
	}
	
	private FixtureDef createFixture()
	{
		CircleShape projectileShape = new CircleShape();
		projectileShape.setRadius(properties.radius);

		FixtureDef def = new FixtureDef();
		def.restitution = properties.restitution;
		def.shape = projectileShape;
		def.density = properties.mass;
		def.filter = CollidableConstants.projectileCollidableFilter.toFilter();
		return def;
	}

	public void shoot()
	{
		Body body = collidable.getBody();
		FixtureDef def = createFixture();
		body.createFixture(def);
		
		ProjectileAimer aimer = new ProjectileAimer(this, properties.range);
		
		direction = aimer.getFinalDirection();

		direction.normalize();
		direction.mulLocal(properties.initialSpeed);
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

	public Vec2 getDirection() 
	{
		return direction;
	}

	public ProjectileCollidableFilter getProjectileCollidableFilter() 
	{
		return projectileCollidableFilter;
	}
	
	
}
