package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.state.NinjaRopeSwingingState;


public class NinjaRopeProjectile extends Projectile
{

	private final float maxDistance = 30.f;

	private final GameHero gameHero;

	public NinjaRopeProjectile(Vec2 direction, GameHero gameHero)
	{
		super(ProjectileIndex.ROPE, adjustInitialDirection(direction), gameHero.getCollidable().getBody().getWorldCenter());
		this.gameHero = gameHero;
	}

	@Override
	public void beginContact(Collidable other, Contact contact)
	{
		NinjaRope ninjaRope = new NinjaRope(collidable.getPosition(), gameHero);
		gameHero.addAction(new HeroStateSetterAction(new NinjaRopeSwingingState(gameHero, ninjaRope)));

		addChild(ninjaRope);

		markToDestroy();
	}

	private FixtureDef createFixture()
	{
		CircleShape projectileShape = new CircleShape();
		projectileShape.setRadius(properties.radius);

		FixtureDef def = new FixtureDef();
		def.restitution = properties.restitution;
		def.shape = projectileShape;
		def.density = properties.mass;
		def.filter.categoryBits = CollidersFilters.projectileCategory.value();
		def.filter.maskBits = CollidersFilters.projectilesCollideWith.except(CollidersFilters.getPlayerCategory(gameHero.getPlayerID())).value(); //Disable hero owner collision
		
		return def;
	}

	@Override
	public void onEnter()
	{
		Body body = collidable.getBody();
		FixtureDef def = createFixture();
		body.createFixture(def);
		
		ProjectileAimer aimer = new ProjectileAimer(body, direction, 
					CollidersFilters.projectilesCollideWith, CollidersFilters.projectilesCollideWith);
		
		aimer.setSightDistance(maxDistance);

		direction = aimer.getFinalDirection();
		direction.normalize();
		direction.mulLocal(properties.initialSpeed);
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

	private static Vec2 adjustInitialDirection(Vec2 direction)
	{
		if (direction.y == 0)
		{
			direction.addLocal(0, -1);
			direction.normalize();
		}
		else
		{
			direction.y = -Math.abs(direction.y);
		}
		return direction;
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (gameHero.getCollidable().getPosition().sub(collidable.getPosition()).length() >= maxDistance)
		{
			System.out.println("Rope is too far");
			// Rope is too far
			markToDestroy();
		}
	}

}
