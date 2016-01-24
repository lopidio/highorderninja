package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.state.HeroStatesPropertiesRepository;


public class Projectile extends GameObject
{

	private final ProjectileIndex index;
	private ProjectileDirection direction;
	private final ProjectileProperties properties;
	private Animation animation;
	private Body body;

	public Projectile(ProjectileIndex index, ProjectileDirection direction)
	{
		this.index = index;
		this.direction = direction;
		this.properties = ProjectilesPropertiesRepository.getProjectileProperties(index);
		animation = null;
		body = null;
	}

	@Override
	public void update(float deltaTime)
	{
		animation.update(deltaTime);
	}

	@Override
	public Sprite getSprite()
	{
		return animation.getSprite();
	}

	@Override
	protected BodyDef getBodyDef(Vec2 position)
	{
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		bodyDef.position = position;
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.bullet = true;

		return bodyDef;
	}

	private FixtureDef createFixture()
	{
		CircleShape projectileShape = new CircleShape();
		projectileShape.setRadius(properties.radius);

		FixtureDef def = new FixtureDef();
		def.restitution = properties.restitution;
		def.shape = projectileShape;
		def.density = properties.mass;
		def.filter.categoryBits = CollidersFilters.CATEGORY_BULLET;
		def.filter.maskBits = CollidersFilters.MASK_BULLET;
		return def;
	}

	@Override
	public void beginContact(Collidable collidable)
	{
		System.out.println("Colidiu");
	}

	@Override
	public void attachBody(World world, Vec2 bodyPosition)
	{
		body = world.createBody(getBodyDef(bodyPosition));
		body.createFixture(createFixture());

		Vec2 directionVec = new Vec2(direction.getX(), direction.getY());
		directionVec.normalize();
		directionVec.mul(properties.initialSpeed);
		body.applyLinearImpulse(directionVec, body.getWorldCenter());
	}
	
	

}
