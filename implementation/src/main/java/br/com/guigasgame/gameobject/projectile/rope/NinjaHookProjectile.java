package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.raycast.RayCastHitAnyThing;


public class NinjaHookProjectile extends Projectile
{

	private final PlayableGameHero gameHero;
	private final NinjaRopePieceDrawable pieceDrawable;
	private World world;

	private boolean markToAttachHook;
	private boolean hookIsAttached;
	private NinjaRope ninjaRope;
	private Vec2 attachPoint;

	public NinjaHookProjectile(Vec2 direction, PlayableGameHero gameHero)
	{
		super(ProjectileIndex.ROPE_HOOK_PROJECTILE, direction, gameHero);
		this.gameHero = gameHero;
		world = null;
		hookIsAttached = false;
		markToAttachHook = false;

		targetPriorityQueue.add(CollidableCategory.SCENERY.getCategoryMask());
		collidableFilter = CollidableCategory.ROPE_NODE.getFilter();
		pieceDrawable = new NinjaRopePieceDrawable(gameHero.getPosition(), gameHero.getPosition(), gameHero.getHeroProperties().getColor());
		drawableList.add(pieceDrawable);
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
		if (isMarkedToDestroy())
			return;

		updateDrawableShape();

		if (markToAttachHook)
		{
			attachHook();
		}
		else
		{
			verifyObstacle();
			verifyRopeMaxSize();
		}

	}

	private void updateDrawableShape()
	{
		pieceDrawable.setAngleInRadians(WorldConstants.calculateAngleInRadians(gameHero.getPosition().sub(collidable.getPosition()).clone()));
		pieceDrawable.setSize(gameHero.getPosition().sub(collidable.getPosition()).length());
		pieceDrawable.setPosition(collidable.getPosition());
		pieceDrawable.updateShape();
	}

	@Override
	public void onDestroy()
	{
		if (null != ninjaRope && !hookIsAttached)
			ninjaRope.markToDestroy();
	}

	private void verifyObstacle()
	{
		RayCastHitAnyThing anyThing = new RayCastHitAnyThing(world, gameHero.getCollidableHero().getBody().getWorldCenter(), 
				collidable.getPosition(), CollidableCategory.SCENERY.getCategoryMask());

		anyThing.shoot();
		if (anyThing.hasHit())
		{
			System.out.println("Wall is on the way");
			this.attachPoint = anyThing.getCallBackWrapper().point;
			markToAttachHook = true;

		}
	}

	private void verifyRopeMaxSize()
	{
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
		collidable.getBody().setTransform(attachPoint, 0);
		ninjaRope = new NinjaRope(this, gameHero, properties.maxDistance);

		System.out.println("Hook attached");
		hookIsAttached = true;
	}

	private boolean ropeIsTooLong()
	{
		return gameHero.getCollidableHero().getPosition().sub(collidable.getPosition()).lengthSquared() >= properties.maxDistance
				* properties.maxDistance;
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

	public void removeDrawableBody()
	{
		drawableList.remove(pieceDrawable);
	}

}
