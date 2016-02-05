package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidersFilters;

public class Shuriken extends Projectile
{
	
	private final float sightDistance;
	private int collisionCounter;
	private int playerID;

	public Shuriken(Vec2 direction, Vec2 position, int playerID)
	{
		super(ProjectileIndex.SHURIKEN, direction, position);
		this.playerID = playerID;
		this.sightDistance = 100;
		collisionCounter = 0;

		collidable = new ShurikenCollidable(position);
		collidable.addListener(this);
	}

	@Override
	public void beginContact(Collidable collidable, Contact contact)
	{
		if (collidable != null)
		{
			System.out.println("Hit player!");
		}
		++collisionCounter;
		if (collisionCounter >= properties.numBounces)
		{
			markToDestroy();
		}
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
		def.filter.maskBits &= ~playerID; //Disable hero owner collision

		return def;
	}

	@Override
	public void onEnter()
	{
		Body body = collidable.getBody();
		FixtureDef def = createFixture();
		body.createFixture(def);

		ProjectileAimer aimer = new ProjectileAimer(sightDistance, direction, body, CollidersFilters.CATEGORY_PLAYER_MASK & ~playerID);
		direction = aimer.getFinalDirection();

		direction.normalize();
		direction.mulLocal(properties.initialSpeed);
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

	
}
