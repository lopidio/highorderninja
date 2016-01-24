package br.com.guigasgame.gamemachine;

import javax.xml.bind.JAXBException;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.collision.CollisionManager;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileDirection;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.time.TimeMaster;


public class MainGameState implements GameState
{

	World world;
	TimeMaster timeMaster;
	GameHero gameHero;

	Body singleBlockBody;
	DistanceJoint joint;

	public MainGameState() throws JAXBException
	{
		timeMaster = new TimeMaster();

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		world.setContactListener(new CollisionManager());
		createGround(new Vec2(15, 38), new Vec2(52, 1));
		createGround(new Vec2(1, 15), new Vec2(1, 22));
		createGround(new Vec2(67, 15), new Vec2(1, 22));
		singleBlockBody = createGround(new Vec2(25, 5), new Vec2(1, 1));

		gameHero = new GameHero(1);
	}

	private Body createGround(Vec2 position, Vec2 size)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(size.x, size.y);
		FixtureDef fixtureDef = new FixtureDef();

		fixtureDef.filter.categoryBits = CollidersFilters.CATEGORY_SCENERY;
		fixtureDef.filter.maskBits = CollidersFilters.MASK_SCENERY;

		fixtureDef.density = 0;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
		return body;

	}

	@Override
	public void load()
	{
		gameHero.load();
		gameHero.attachBody(world, new Vec2(10, 5));
	}

	@Override
	public void enterState(RenderWindow renderWindow)
	{
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(
				new OBBViewportTransform(), renderWindow);
		world.setDebugDraw(sfmlDebugDraw);
		sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
	}

	
	
	@Override
	public void handleEvent(Event event)
	{
//		Vector2i speed = new Vector2i((int)Joystick.getAxisPosition(0, Axis.X)/100, (int)Joystick.getAxisPosition(0, Axis.Y)/100);
//		if (timeMaster != null) timeMaster.handleEvent(event);
//		System.out.println(Joystick.isButtonPressed(0, 6));
//		if (event.type == Type.JOYSTICK_MOVED)
//		{
//			System.out.println(speed);
//		}
		if (event.type == Type.KEY_PRESSED)
		{
			if (event.asKeyEvent().key == Key.LSHIFT)
			{
				if (joint == null)
				{
					
					DistanceJointDef distDef = new DistanceJointDef();
					
					distDef.bodyA = gameHero.getBody();
					distDef.bodyB = singleBlockBody;
					distDef.collideConnected = false;
					distDef.length = singleBlockBody.getPosition().sub(gameHero.getBody().getPosition()).length();
					
					System.out.println("Creates");
					joint = (DistanceJoint) world.createJoint(distDef);
				}
			}
			if (event.asKeyEvent().key == Key.Q)
			{
				Projectile projectile = new Projectile(ProjectileIndex.SHURIKEN, ProjectileDirection.DOWN_LEFT);
				projectile.attachBody(world, gameHero.getBody().getPosition().add(new Vec2(1, 0)));
			}
		}
		if (event.type == Type.KEY_RELEASED)
			if (event.asKeyEvent().key == Key.LSHIFT)
			{
				System.out.println("Remove");
				world.destroyJoint(joint);
				joint = null;
			}
	}

	@Override
	public void update()
	{
		float deltaTime = timeMaster.getElapsedTime().asSeconds();
		timeMaster.restart();
		world.step(1 / 60.f, 8, 3);
		world.clearForces();

		if (joint != null)
		{
			joint.setLength(joint.getLength()*0.995f);
		}
		
		gameHero.update(deltaTime);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		world.drawDebugData();
		renderWindow.draw(gameHero.getSprite());
	}

}
