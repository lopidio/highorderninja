package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.raycast.RayCastHitAnyThing;


public class NinjaHookProjectile extends Projectile
{
	private final PlayableGameHero gameHero;
	private World world;

	private boolean markToAttachHook;
	private boolean hookIsAttached;
	private NinjaRope ninjaRope;
	private Vec2 attachPoint;

	public NinjaHookProjectile(Vec2 direction, PlayableGameHero gameHero)
	{
		super(ProjectileIndex.ROPE, direction, gameHero.getCollidableHero().getBody().getWorldCenter());
		this.gameHero = gameHero;
		world = null;
		hookIsAttached = false;
		markToAttachHook = false;

		targetMask = CollidableCategory.SCENERY.getCategoryMask();
		collidableFilter = CollidableCategory.ROPE_NODE.getFilter();
		setAnimationsColor(gameHero.getHeroProperties().getColor());
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
				gameHero.getCollidableHero().getBody().getWorldCenter(),
				collidable.getPosition(), 
				CollidableCategory.SCENERY.getCategoryMask());
		
		anyThing.shoot();
		if (anyThing.hasHit())
		{
			System.out.println("Wall is on the way");
//			markToDestroy();
			this.attachPoint = anyThing.getCallBackWrapper().point;
			markToAttachHook = true;

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
		ninjaRope = new NinjaRope(world, properties, attachPoint, gameHero.getCollidableHero().getBody(), gameHero.getHeroProperties().getColor());
		
		System.out.println("Hook attached");
		hookIsAttached = true;
	}
	
	private boolean ropeIsTooLong()
	{
		return gameHero.getCollidableHero().getPosition().sub(collidable.getPosition()).lengthSquared() >= properties.maxDistance*properties.maxDistance;
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
		WorldManifold manifold = new WorldManifold();
		contact.getWorldManifold(manifold);
		this.attachPoint = manifold.points[0];
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
