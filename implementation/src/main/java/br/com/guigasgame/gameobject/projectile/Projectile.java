package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.gameobject.GameObject;


public class Projectile extends GameObject
{
	protected final ProjectileIndex index;
	protected final ProjectileProperties properties;
	private final Animation animation;
	private Body body;
	private int collisionCounter;
	protected Vec2 direction;
	private final float sightDistance;
	private int playerID;

	public Projectile(ProjectileIndex index, Vec2 direction, Vec2 position, int playerID)
	{
		super(position.add(direction));

		this.playerID = playerID;
		this.sightDistance = 100;
		this.index = index;

		this.animation = Animation.createAnimation(AnimationsCentralPool.getProjectileAnimationRepository().getAnimationsProperties(index));
		animation.getSprite().setColor(Color.mul(Color.BLACK, Color.BLUE));

		this.properties = ProjectilesPropertiesPool.getProjectileProperties(index);
		this.direction = direction.clone();
		this.body = null;
		collisionCounter = 0;
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
	public void beginContact(Collidable collidable, Contact contact)
	{
		System.out.println(collidable);
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

	@Override
	public void onEnter()
	{
		FixtureDef def = createFixture();
		body.createFixture(def);

		ProjectileAimer aimer = new ProjectileAimer(sightDistance, direction, body, CollidersFilters.CATEGORY_PLAYER_MASK & ~playerID);
		direction = aimer.getFinalDirection();

		direction.normalize();
		direction.mulLocal(properties.initialSpeed);
		body.applyLinearImpulse(direction, body.getWorldCenter());
	}

	@Override
	protected void editBody(Body body)
	{
		this.body = body;
	}

}
