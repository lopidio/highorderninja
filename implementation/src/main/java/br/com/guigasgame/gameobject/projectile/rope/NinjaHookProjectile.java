package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.raycast.RayCastHitAnyThing;


public class NinjaHookProjectile extends Projectile
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
		if (isDead())
			return;

		if (hookIsAttached)
		{
			ninjaRope.update(deltaTime);
		}
		else if (markToAttachHook)
		{
			attachHook();
		}
		else
		{
			verifyAutoDestruction();
		}
		
	}
	
	@Override
	public void onDestroy()
	{
		if (null != ninjaRope && !hookIsAttached)
			ninjaRope.destroy();
	}

	private void verifyAutoDestruction() 
	{
		RayCastHitAnyThing anyThing = new RayCastHitAnyThing(world, 
				gameHero.getCollidable().getBody().getWorldCenter(),
				collidable.getPosition(), 
				CollidableConstants.sceneryCategory.getMask());
		
		anyThing.shoot();
		if (anyThing.hasHit())
		{
			System.out.println("Wall is on the way");
			markToDestroy();
		}
		if (ropeIsTooLong())
		{
			markToDestroy();
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
	protected CollidableFilter createCollidableFilter()
	{
		// rope doesn't collides with heros
		collidableFilter = CollidableConstants.getRopeBodyCollidableFilter();
		
		return collidableFilter;
	}
	
	@Override
	protected IntegerMask editTarget(IntegerMask target) 
	{
		target = CollidableConstants.sceneryCategory.getMask();
		return target;
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

	public boolean isHookAttached()
	{
		return hookIsAttached;
	}

}
