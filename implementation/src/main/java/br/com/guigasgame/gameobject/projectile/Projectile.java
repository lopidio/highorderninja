package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.gameobject.GameObject;


public class Projectile extends GameObject
{

	protected final ProjectileIndex index;
	protected final ProjectileDirection direction;
	protected final ProjectileProperties properties;
	private final Animation animation;
	private Body body;
	private int collisionCounter;

	public Projectile(ProjectileIndex index, ProjectileDirection direction, Vec2 position)
	{
		super(position);

		this.index = index;
		this.direction = direction;
		this.properties = ProjectilesPropertiesRepository.getProjectileProperties(index);
		this.animation = Animation.createAnimation(AnimationsRepositoryCentral.getProjectileAnimationRepository().getAnimationsProperties(index));
		animation.getSprite().setColor(Color.mul(Color.BLACK, Color.BLUE));
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
		return def;
	}

	@Override
	public void beginContact(Collidable collidable)
	{
		++collisionCounter;
		if (collisionCounter >= properties.numBounces)
		{
			markToDestroy();
		}
	}

	@Override
	public void onEnter()
	{
		body.createFixture(createFixture());

		Vec2 directionVec = new Vec2(direction.getX(), direction.getY());
		directionVec.normalize();
		directionVec.mul(properties.initialSpeed);
		body.applyLinearImpulse(directionVec, body.getWorldCenter());
	}

	@Override
	protected void editBody(Body body)
	{
		this.body = body;
	}
}
