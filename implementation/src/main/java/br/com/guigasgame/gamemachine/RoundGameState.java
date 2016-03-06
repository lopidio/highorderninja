package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import br.com.guigasgame.background.Background;
import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollisionManager;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.playable.GameHeroProperties;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.item.GameItemController;
import br.com.guigasgame.scenery.Scenery;
import br.com.guigasgame.team.HeroTeam;


public class RoundGameState implements GameState
{

	private World world;
	private float timeFactor;

	private List<GameObject> gameObjectsList;
	private Background background;
	private GameItemController gameItemController;

	public RoundGameState(List<HeroTeam> teams, Scenery scenery, Background background) throws JAXBException
	{
		CollidableCategory.display();
		gameObjectsList = new ArrayList<>();

		timeFactor = 1;

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		world.setContactListener(new CollisionManager());
		this.background = background;
		gameItemController = new GameItemController(scenery);
		
		initializeGameObject(Arrays.asList(scenery));
		
		for( HeroTeam team : teams )
		{
			List<GameHeroProperties> heros = team.getHerosList();
			for (GameHeroProperties gameHeroProperties : heros) 
			{
				gameHeroProperties.setSpawnPosition(WorldConstants.sfmlToPhysicsCoordinates(scenery.popRandomSpawnPoint()));
				initializeGameObject(Arrays.asList(new PlayableGameHero(gameHeroProperties)));
			}
		}
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
//		 sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
	}

	@Override
	public void handleEvent(Event event)
	{
		//Axis.Z R2/L2
		//Axis.U R3x
		//Axis.R R3y

//		for ( int i = 0; i < 4; ++i)
//		{
//			if (Joystick.isConnected(i))
//			{
//				for( Axis axis : Axis.values() )
//				{
//					if (Joystick.getAxisPosition(i, axis) > 60 || Joystick.getAxisPosition(i, axis) < -60)
//					{
//						if (axis != Axis.V)
//							System.out.println("Joys(" + i + "): " + axis.toString() + " -> " + (Joystick.getAxisPosition(i, axis) > 0 ? 1 : -1));
//					}
//					
//				}
//				
//			}
//		}

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
		if (gameItemController.hasItemToAdd())
		{
			initializeGameObject(gameItemController.getChildrenList());
			gameItemController.clearItemToAddList();
		}
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
			child.attachToWorld(world);
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
				List<Collidable> collidableList = toRemove.getCollidable();
				for (Collidable collidable : collidableList) 
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
		float updateTime = deltaTime * timeFactor;
		// float deltaTime = timeMaster.getElapsedTime().asSeconds();
		world.step(updateTime, 8, 3);
		world.clearForces();
		
		background.update(updateTime);
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.update(updateTime);
		}
		gameItemController.update(deltaTime);

		verifyNewObjectsToLists();
		clearDeadObjects();
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		background.draw(renderWindow);
		world.drawDebugData();

		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.draw(renderWindow);
		}
	}
}
