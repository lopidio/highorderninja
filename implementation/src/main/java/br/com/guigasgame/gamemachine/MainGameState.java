package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
import org.jsfml.window.Joystick;
import org.jsfml.window.Joystick.Axis;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.collision.CollidersFilters;
import br.com.guigasgame.collision.CollisionManager;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.GameHero;


public class MainGameState implements GameState
{

	World world;
	float timeFactor;
	GameHero gameHero;

	Body singleBlockBody;
	DistanceJoint joint;
	List<GameObject> gameObjectsList;

	public MainGameState() throws JAXBException
	{
		gameObjectsList = new ArrayList<>();
		timeFactor = 1;

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		world.setContactListener(new CollisionManager());
		createGround(new Vec2(15, 38), new Vec2(52, 1), false);
		createGround(new Vec2(15, 0), new Vec2(52, 1), false);
		createGround(new Vec2(1, 15), new Vec2(1, 22), false);
		createGround(new Vec2(9, 22), new Vec2(1, 10), false);
		createGround(new Vec2(67, 15), new Vec2(1, 22), false);
		createGround(new Vec2(50, 8), new Vec2(1, 1), true);
		singleBlockBody = createGround(new Vec2(25, 5), new Vec2(1, 1), true);

		gameHero = new GameHero(1, new Vec2(10, 5));
		initializeGameObject(Arrays.asList(gameHero, new GameHero(2, new Vec2(40, 5))));
	}

	private Body createGround(Vec2 position, Vec2 size, boolean mask)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(size.x, size.y);
		FixtureDef fixtureDef = new FixtureDef();

		if (!mask)
		{
			fixtureDef.filter.categoryBits = CollidersFilters.CATEGORY_SCENERY;
		}
		else
		{
			fixtureDef.filter.categoryBits = CollidersFilters.CATEGORY_SINGLE_BLOCK;
		}
		fixtureDef.filter.maskBits = CollidersFilters.MASK_SCENERY;

		fixtureDef.density = 0;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
		return body;

	}

	@Override
	public void load()
	{
	}

	@Override
	public void enterState(RenderWindow renderWindow)
	{
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(new OBBViewportTransform(), renderWindow);
		world.setDebugDraw(sfmlDebugDraw);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
	}

	@Override
	public void handleEvent(Event event)
	{
		int z;
		int w;
		int t;
		z = w = t = 0;

		if (Joystick.getAxisPosition(0, Axis.Z) > 60 //R2/L2
				|| Joystick.getAxisPosition(0, Axis.Z) < -60)
		{
			z = Joystick.getAxisPosition(0, Axis.Z) > 0 ? 1 : -1;
		}

		if (Joystick.getAxisPosition(0, Axis.U) > 60 //R3x
				|| Joystick.getAxisPosition(0, Axis.U) < -60)
		{
			w = Joystick.getAxisPosition(0, Axis.U) > 0 ? 1 : -1;
		}
		
		if (Joystick.getAxisPosition(0, Axis.R) > 60 //R3y
				|| Joystick.getAxisPosition(0, Axis.R) < -60)
		{
			t = Joystick.getAxisPosition(0, Axis.R) > 0 ? 1 : -1;
		}
		
		if (z != 0 || w != 0 || t != 0) 
			System.out.println(z + ", " + w + ", " + t);
		
		if (event.type == Type.JOYSTICK_BUTTON_PRESSED)
		{
			if (event.asJoystickButtonEvent().button == 4)
			{
				createJoint();
			}
			System.out.println(event.asJoystickButtonEvent().button);
		}
		if (event.type == Type.JOYSTICK_BUTTON_RELEASED)
		{
			if (event.asJoystickButtonEvent().button == 4)
			{
				removeJoint();
			}
			System.out.println(event.asJoystickButtonEvent().button);
		}

		if (event.type == Type.KEY_PRESSED)
		{
			if (event.asKeyEvent().key == Key.P)
			{
				timeFactor = 0.3f;
			}
			if (event.asKeyEvent().key == Key.O)
			{
				timeFactor = 1;
			}
			if (event.asKeyEvent().key == Key.LSHIFT)
			{
				if (joint == null)
				{
					createJoint();
				}
			}
		}
		if (event.type == Type.KEY_RELEASED)
			if (event.asKeyEvent().key == Key.LSHIFT)
			{
				removeJoint();
			}
	}
	
	private void removeJoint()
	{
		System.out.println("Remove");
		if (joint != null)
			world.destroyJoint(joint);
		joint = null;
	}

	private void createJoint()
	{
		DistanceJointDef distDef = new DistanceJointDef();

		distDef.bodyA = gameHero.getBody();
		distDef.bodyB = singleBlockBody;
		distDef.collideConnected = false;
		distDef.length = singleBlockBody.getPosition().sub(gameHero.getBody().getPosition()).length();

		System.out.println("Creates");
		joint = (DistanceJoint) world.createJoint(distDef);
	}

	private void addNewGameObjectsToList()
	{
		List<GameObject> objsToAdd = new ArrayList<>();
		objsToAdd.addAll(gameObjectsList);
		for( GameObject gameObject : objsToAdd )
		{
			if (gameObject.hasChildrenToAdd())
			{
				initializeGameObject(gameObject.getChildrenToAdd());
			}
			gameObject.clearChildrenToAdd();
		}
	}

	private void initializeGameObject(List<GameObject> childrenToAdd)
	{
		for( GameObject child : childrenToAdd )
		{
			child.load();
			child.attachBody(world);
			child.onEnter();
			gameObjectsList.add(child);
		}
	}

	private void clearDeadObject()
	{
		Iterator<GameObject> iterator = gameObjectsList.iterator();
		while (iterator.hasNext())
		{
			GameObject toRemove = iterator.next(); // must be called before you
													// can call
													// iterator.remove()
			if (toRemove.isDead())
			{
				toRemove.unload();
				toRemove.onDestoy();
				world.destroyBody(toRemove.getBody());
				iterator.remove();
			}
		}

	}

	@Override
	public void update(float deltaTime)
	{
		// float deltaTime = timeMaster.getElapsedTime().asSeconds();
		world.step(deltaTime * timeFactor, 8, 3);
		world.clearForces();

		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.update(deltaTime * timeFactor);
		}

		if (joint != null)
		{
			joint.setLength(joint.getLength() * 0.995f);
		}

		addNewGameObjectsToList();
		clearDeadObject();
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		world.drawDebugData();

		for( GameObject go : gameObjectsList )
		{
			renderWindow.draw(go.getSprite());
		}
	}

}
