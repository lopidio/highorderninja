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
	protected final ProjectileProperties properties;
	private final Animation animation;
	private Body body;
	private int collisionCounter;
	protected Vec2 direction;
	private final float sightDistance;
	private boolean activate;

	public Projectile(ProjectileIndex index, Vec2 direction, Vec2 position)
	{

		super(position.add(direction));

		this.sightDistance = 200;
		this.index = index;

		this.animation = Animation.createAnimation(AnimationsRepositoryCentral.getProjectileAnimationRepository().getAnimationsProperties(index));
		animation.getSprite().setColor(Color.mul(Color.BLACK, Color.BLUE));

		this.properties = ProjectilesPropertiesRepository.getProjectileProperties(index);
		this.direction = direction.clone();
		this.body = null;
		collisionCounter = 0;
		activate = false;
		
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
	public void endContact(Collidable collidable)
	{
//		if (!activate)
		{

			
		}
	}

	@Override
	public void beginContact(Collidable collidable)
	{
		// Works when owner hero is far enough
		if (activate)
		{
			++collisionCounter;
			if (collisionCounter >= properties.numBounces)
			{
				markToDestroy();
			}
		}
	}

	@Override
	public void onEnter()
	{
		body.createFixture(createFixture());

		activate = true;
		ProjectileAimer pa = new ProjectileAimer(sightDistance, 12, direction, (float) (Math.PI/8), body);
		direction = pa.getFinalDirection();

		direction.normalize();
		direction.mulLocal(properties.initialSpeed*3);
		body.applyLinearImpulse(direction, body.getWorldCenter());		
	}

	@Override
	protected void editBody(Body body)
	{
		this.body = body;
	}

}
