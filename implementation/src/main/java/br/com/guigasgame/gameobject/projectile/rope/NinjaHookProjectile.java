package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.CollidableFilterManipulator;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileCollidableFilter;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;


public class NinjaHookProjectile extends Projectile implements RayCastCallback
{
	private final GameHero gameHero;
	private World world;

	private boolean markToAttachHook;
	private boolean hookIsAttached;
	private NinjaRope ninjaRope;

	public NinjaHookProjectile(Vec2 direction, GameHero gameHero)
	{
		super(ProjectileIndex.ROPE, direction, gameHero.getCollidable().getBody().getWorldCenter());
		System.out.println("Pointing: " + direction);
		this.gameHero = gameHero;
		world = null;
		hookIsAttached = false;
		markToAttachHook = false;
	}
	
	@Override
	public void onEnter()
	{
		shoot();
	}


	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);

		if (hookIsAttached)
		{
			if (null != ninjaRope)
				ninjaRope.update(deltaTime);
		}
		else if (markToAttachHook)
		{
			attachHook();
		}
		else
		{
			world.raycast(this, gameHero.getCollidable().getBody().getWorldCenter(), collidable.getPosition());
			if (ropeIsTooLong())
			{
				markToDestroy();
			}
		}
		
	}

	private void attachHook()
	{
		if (hookIsAttached)
			return;

		collidable.getBody().setType(BodyType.STATIC);
		ninjaRope = new NinjaRope(world, properties, collidable.getBody().getPosition(), gameHero.getCollidable().getBody());
		
		System.out.println("Hook attached");
		hookIsAttached = true;
	}
	
	private boolean ropeIsTooLong()
	{
		return gameHero.getCollidable().getPosition().sub(collidable.getPosition()).lengthSquared() >= properties.maxDistance*properties.maxDistance;
	}

	@Override
	protected ProjectileCollidableFilter createCollidableFilter()
	{
		// rope doesn't collides with heros
		projectileCollidableFilter = new ProjectileCollidableFilter(CollidableFilterManipulator.createFromCollidableFilter(CollidableConstants.getRopeBodyCollidableFilter()));
		projectileCollidableFilter.aimTo(CollidableConstants.sceneryCategory);
		
		return projectileCollidableFilter;
	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		this.world = world;
	}
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		markToAttachHook = true;
	}
	
	public NinjaRope getNinjaRope()
	{
		return ninjaRope;
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		CollidableFilter fixtureCollider = new CollidableFilterBox2dAdapter(fixture.getFilterData()).toCollidableFilter();
		if (projectileCollidableFilter.getCollidableFilter().matches(fixtureCollider.getCategory()))//if collides with something interesting
		{
			if (projectileCollidableFilter.getAimingMask().matches(fixtureCollider.getCategory().getValue())) //if collides with what I am aiming to
			{
				if (!hookIsAttached)
				{
					System.out.println("Wall on the way");
					markToDestroy();
					return 0;
				}
			}
		}
		return 1; //ignore
	}

	public boolean isHookAttached()
	{
		return hookIsAttached;
	}

}
