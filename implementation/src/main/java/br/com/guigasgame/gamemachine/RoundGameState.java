package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.camera.CameraController;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollisionManager;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.gameobject.item.GameItemCreationController;
import br.com.guigasgame.round.hud.StaticHudController;
import br.com.guigasgame.round.hud.TimerStaticHud;
import br.com.guigasgame.round.hud.controller.HeroAttributesHudController;
import br.com.guigasgame.round.hud.controller.HeroAttributesMovingHudController;
import br.com.guigasgame.round.hud.moving.barbellow.HeroAttributesCircleAndBarsBellowHudController;
import br.com.guigasgame.scenery.SceneController;
import br.com.guigasgame.scenery.creation.SceneryCreator;
import br.com.guigasgame.team.HeroTeam;


public class RoundGameState implements GameState
{

	private World world;
	private float timeFactor;

	private List<GameObject> gameObjectsList;
	private GameItemCreationController gameItemController;
	private SceneController scenery;
	private CameraController cameraController;
	private List<HeroAttributesHudController> movingHudList;
	private ColorBlender backgroundColor;
	private StaticHudController staticHudController;

	public RoundGameState(List<HeroTeam> teams, SceneryCreator sceneryCreator, RoundHeroAttributes roundHeroAttributes) throws JAXBException
	{
		CollidableCategory.display();
		gameObjectsList = new ArrayList<>();
		this.scenery = new SceneController(sceneryCreator);
		timeFactor = 1;

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		world.setContactListener(new CollisionManager());
		gameItemController = new GameItemCreationController(scenery);
		this.backgroundColor = scenery.getBackgroundColor();
		
		scenery.attachToWorld(world);
		scenery.onEnter();
		cameraController = new CameraController();
		movingHudList = new ArrayList<>();
		staticHudController = new StaticHudController();
		
		initializeHeros(teams, scenery, roundHeroAttributes);
	}

	private void initializeHeros(List<HeroTeam> teams, SceneController scenery, RoundHeroAttributes roundHeroAttributes)
	{
		for( HeroTeam team : teams )
		{
			team.setFriendlyFire(true);
			List<PlayableHeroDefinition> heros = team.getHerosList();
			for (PlayableHeroDefinition gameHeroProperties : heros) 
			{
				gameHeroProperties.setSpawnPosition(WorldConstants.sfmlToPhysicsCoordinates(scenery.popRandomSpawnPoint()));
				gameHeroProperties.setHeroAttributes(roundHeroAttributes.clone());
				PlayableGameHero gameHero = new PlayableGameHero(gameHeroProperties);
				HeroAttributesMovingHudController hud = new HeroAttributesCircleAndBarsBellowHudController(gameHero);
				hud.addAsHudController(gameHeroProperties.getRoundHeroAttributes());
				movingHudList.add(hud);
				initializeGameObject(Arrays.asList(gameHero));
				cameraController.addPlayerToControl(gameHero);
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
		cameraController.createView(renderWindow);
		staticHudController.createView(renderWindow);
		staticHudController.addHud(new TimerStaticHud(new Vector2f(renderWindow.getView().getCenter().x, 10)));
	}

	@Override
	public void handleEvent(Event event, RenderWindow renderWindow)
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
		
		// catch the resize events
	    if (event.type == Type.RESIZED)
	    {
	        // update the view to the new size of the window
	        FloatRect visibleArea = new FloatRect(0, 0, event.asSizeEvent().size.x, event.asSizeEvent().size.y);
	        renderWindow.setView(new View(visibleArea));
	        cameraController.createView(renderWindow);
	        staticHudController.createView(renderWindow);
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

	@Override
	public void update(float deltaTime)
	{
		float updateTime = deltaTime * timeFactor;
		// float deltaTime = timeMaster.getElapsedTime().asSeconds();
		world.step(updateTime, 8, 3);
		world.clearForces();
		
		scenery.update(deltaTime);
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.update(updateTime);
		}
		for( HeroAttributesHudController hud : movingHudList )
		{
			hud.update(deltaTime);
		}
		gameItemController.update(deltaTime);

		verifyNewObjectsToLists();
		checkGameOjbectsAgainsSceneryBoundaries();
		Destroyable.clearDestroyable(movingHudList);
		Destroyable.clearDestroyable(gameObjectsList);
		
		cameraController.update(deltaTime);
		staticHudController.update(deltaTime);
	}

	private void checkGameOjbectsAgainsSceneryBoundaries()
	{
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.checkAgainstSceneryBoundaries(scenery.getBoundaries());
		}
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.setView(cameraController.getCameraView());

		scenery.drawBackgroundItems(renderWindow);
		world.drawDebugData();
		
//		cameraController.draw(renderWindow);

		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.draw(renderWindow);
		}
		
		scenery.draw(renderWindow);
		scenery.drawForegroundItems(renderWindow);
		
		for( HeroAttributesHudController hud : movingHudList )
		{
			hud.draw(renderWindow);
		}
		renderWindow.setView(staticHudController.getView());
		staticHudController.draw(renderWindow);
	}

	@Override
	public ColorBlender getBackgroundColor()
	{
		return backgroundColor;
	}
	
	
}
