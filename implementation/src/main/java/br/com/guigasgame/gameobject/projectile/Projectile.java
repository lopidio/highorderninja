package br.com.guigasgame.gameobject.projectile;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.playable.CollidableHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.aimer.ProjectileAimer;


public abstract class Projectile extends GameObject
{

	protected final ProjectileIndex index;
	protected final ProjectileProperties properties;

	protected CollidableFilter collidableFilter;
	protected Vec2 direction;
	protected ProjectileCollidable collidable;
	protected PlayableGameHero owner;
	protected List<IntegerMask> targetPriorityQueue;
	protected List<Animation> animationList;

	protected Projectile(ProjectileIndex index, Vec2 direction, PlayableGameHero gameHero)
	{
		this(index, direction, gameHero, gameHero.getCollidableHero().getBody().getWorldCenter());
	}

	protected Projectile(ProjectileIndex index, Vec2 direction, PlayableGameHero gameHero, Vec2 position)
	{
		this.index = index;
		
		Animation animation = Animation.createAnimation(AnimationsCentralPool.getProjectileAnimationRepository().getAnimationsProperties(index));
		
		this.properties = ProjectilesPropertiesPool.getProjectileProperties(index);
		this.direction = direction.clone();
		
		this.owner = gameHero;
		collidable = new ProjectileCollidable(position);
		collidable.addListener(this);
		collidableList.add(collidable);
		collidableFilter = null;
		targetPriorityQueue = new ArrayList<>();
		
		animationList = new ArrayList<>();
		animation.setPosition(WorldConstants.physicsToSfmlCoordinates(position));
		animationList.add(animation);
		drawableList.addAll(animationList);
		setAnimationsColor(gameHero.getHeroProperties().getColor().darken(2));
	}

	@Override
	public void update(float deltaTime)
	{
		if (isAlive())
		{
			for( Animation animation : animationList )
			{
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
		setAngle(WorldConstants.calculateAngleInRadians(direction));
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

	protected void setAnimationsColor(ColorBlender color)
	{
		for( Animation animation : animationList )
		{
			animation.setColor(color);
		}
	}

	public void setAngle(float angle)
	{
		for( Animation animation : animationList )
		{
			animation.setRotation(WorldConstants.radiansToDegrees(angle));
		}
	}

	public Vec2 getPosition()
	{
		return collidable.getPosition();
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body otherBody = (Body) other;
		if (otherBody.getUserData() != null)
		{
			Fixture fixture = otherBody.getFixtureList();
			List<CollidableCategory> categoryList = CollidableCategory.fromMask(fixture.getFilterData().categoryBits);
			for( CollidableCategory category : categoryList )
			{
				if (category == CollidableCategory.HEROES)
				{
					CollidableHero collidableHero = (CollidableHero) otherBody.getUserData();
					hitHero(collidableHero.getPlayableHero());
				}
				else
					hitCollidable(category, otherBody);
			}
		}
	}
	
	protected void hitHero(PlayableGameHero hitGameHero)
	{
		//hook
	}

	protected void hitCollidable(CollidableCategory category, Body other)
	{
		//hook
	}

	public PlayableGameHero getOwner()
	{
		return owner;
	}

	public List<IntegerMask> getTargetPriorityQueue()
	{
		return targetPriorityQueue;
	}
}
