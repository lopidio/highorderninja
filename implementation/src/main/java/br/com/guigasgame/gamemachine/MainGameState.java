package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Joystick;
import org.jsfml.window.Joystick.Axis;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.CollisionManager;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputDevice;


public class MainGameState implements GameState
{

	World world;
	float timeFactor;

	List<GameObject> gameObjectsList;

	public MainGameState() throws JAXBException
	{
		gameObjectsList = new ArrayList<>();

		timeFactor = 1;

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		world.setContactListener(new CollisionManager());
		createGround(new Vec2(15, 38), new Vec2(52, 1)); //ground
		createGround(new Vec2(15, 0), new Vec2(52, 1)); //ceil
		createGround(new Vec2(1, 15), new Vec2(1, 22)); //left wall
		createGround(new Vec2(67, 15), new Vec2(1, 22)); //right wall

		createGround(new Vec2(14, 24), new Vec2(1, 8)); // |
		createGround(new Vec2(17, 24), new Vec2(12, 1)); // --

		createGround(new Vec2(50, 24), new Vec2(4, 1));
		createGround(new Vec2(50, 24), new Vec2(1, 4));


		createGround(new Vec2(10, 8), new Vec2(3, 1));
		createGround(new Vec2(25, 8), new Vec2(3, 1));
		createGround(new Vec2(40, 8), new Vec2(3, 1));
		createGround(new Vec2(55, 8), new Vec2(3, 1));

		initializeGameObject(Arrays.asList(
				new GameHero(1, new Vec2(10, 5), GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK)), 
				new GameHero(2, new Vec2(40, 5), GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK)),
				new GameHero(3, new Vec2(50, 10), GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD))));
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

		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableConstants.getSceneryCollidablefilter()).toBox2dFilter();

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
		for ( int i = 0; i < 4; ++i)
			System.out.println("Joystick ("+i+") is conected: " +Joystick.isConnected(i));
		
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(new OBBViewportTransform(), renderWindow);
		world.setDebugDraw(sfmlDebugDraw);
		// sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
		// sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
		// sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
		// sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
	}

	@Override
	public void handleEvent(Event event)
	{
		//Axis.Z R2/L2
		//Axis.U R3x
		//Axis.R R3y

		for ( int i = 0; i < 4; ++i)
		{
			if (Joystick.isConnected(i))
			{
				for( Axis axis : Axis.values() )
				{
					if (Joystick.getAxisPosition(i, axis) > 60 || Joystick.getAxisPosition(i, axis) < -60)
					{
						if (axis != Axis.V)
							System.out.println("Joys(" + i + "): " + axis.toString() + " -> " + (Joystick.getAxisPosition(i, axis) > 0 ? 1 : -1));
					}
					
				}
				
			}
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

		}
	}

	private void verifyNewObjectsToLists()
	{
		List<GameObject> objsToAdd = new ArrayList<>();
		objsToAdd.addAll(gameObjectsList);
		for( GameObject gameObject : objsToAdd )
		{
			if (gameObject.hasChildrenToAdd())
			{
				initializeGameObject(gameObject.getChildrenList());
			}
			gameObject.clearChildrenList();
		}
	}

	private void initializeGameObject(Collection<GameObject> childrenToAdd)
	{
		for( GameObject child : childrenToAdd )
		{
			Collidable collidable = child.getCollidable();
			if (collidable != null)
			{
				collidable.attachToWorld(world);
			}
			child.onEnter();
			gameObjectsList.add(child);
		}
	}

	private void clearDeadObjects()
	{
		Iterator<GameObject> iterator = gameObjectsList.iterator();
		while (iterator.hasNext())
		{
			GameObject toRemove = iterator.next(); // must be called before you can call iterator.remove()
			if (toRemove.isDead())
			{
				toRemove.onDestroy();
				Collidable collidable = toRemove.getCollidable();
				if (collidable != null)
				{
					world.destroyBody(collidable.getBody());
				}

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

		verifyNewObjectsToLists();
		clearDeadObjects();
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		world.drawDebugData();

		for( GameObject gameObject : gameObjectsList )
		{
			Drawable drawable = gameObject.getDrawable();
			if (drawable != null)
				drawable.draw(renderWindow);
		}
	}
}
