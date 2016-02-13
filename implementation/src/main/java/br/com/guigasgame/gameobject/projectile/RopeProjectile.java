package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.gameobject.hero.GameHero;


public class RopeProjectile extends Projectile
{

	private final GameHero gameHero;

	public RopeProjectile(Vec2 direction, Vec2 position, GameHero gameHero)
	{
		super(ProjectileIndex.SHURIKEN, direction, position);
		this.gameHero = gameHero;
	}

	@Override
	public void beginContact(Collidable other, Contact contact)
	{
		addChild(new Rope(collidable.getPosition(), gameHero));
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
		def.filter.categoryBits = CollidersFilters.CATEGORY_BULLET;

		def.filter.maskBits = CollidersFilters.MASK_ROPE;

		return def;
	}

	@Override
	public void onEnter()
	{
		Body body = collidable.getBody();
		FixtureDef def = createFixture();
		body.createFixture(def);

		direction.normalize();
		direction.mulLocal(properties.initialSpeed);
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

}
