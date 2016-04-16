package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
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
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.gameobject.item.GameItemCreationController;
import br.com.guigasgame.round.RoundProperties;
import br.com.guigasgame.round.event.EventCentralMessenger;
import br.com.guigasgame.round.hud.RoundHudSkin;
import br.com.guigasgame.round.hud.controller.RoundHudController;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroMovingHudController;
import br.com.guigasgame.round.hud.fix.HeroFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TeamFragStatisticHud;
import br.com.guigasgame.round.hud.fix.TimerStaticHud;
import br.com.guigasgame.round.type.GameHeroSpawner;
import br.com.guigasgame.round.type.RoundMode;
import br.com.guigasgame.scenery.SceneController;
import br.com.guigasgame.team.HeroTeam;


public class RoundGameState implements GameState
{
	private float timeFactor;
	private final World world;
	private final List<GameObject> gameObjectsList;
	private final GameItemCreationController gameItemCreator;
	private final SceneController scenery;
	private final CameraController cameraController;
	private final RoundHudController hudController;
	private RoundMode roundType;
	private GameHeroSpawner gameHeroSpawner;
	private RoundProperties roundAttributes;
	
	public RoundGameState(RoundProperties roundAttributes)
	{
		CollidableCategory.display();
		this.roundAttributes = roundAttributes;
		gameObjectsList = new ArrayList<>();
		gameHeroSpawner = new GameHeroSpawner(this);
		roundType = roundAttributes.getRoundType();
		roundType.setRoundState(this);
		EventCentralMessenger.getInstance().subscribe(roundType);
		EventCentralMessenger.getInstance().subscribe(this);

		this.scenery = new SceneController(roundAttributes.getSceneryInitializer());
		timeFactor = 1;

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		world.setContactListener(new CollisionManager());
		gameItemCreator = new GameItemCreationController(scenery);
		
		scenery.attachToWorld(world);
		scenery.onEnter();
		
		
		RoundHudSkin hudSkin = roundAttributes.getHudSkin();
		hudController = new RoundHudController(hudSkin.getIdealView());
		TimerStaticHud timerStaticHud = hudSkin.createTimerStaticHud(roundAttributes.getTotalTime());
		hudController.addStaticHud(timerStaticHud);

		cameraController = new CameraController(scenery.getCenter());
		initializeHeros();
	}
	
	private void initializeHeros()
	{
		final RoundHudSkin hudSkin = roundAttributes.getHudSkin();
		for( HeroTeam team : roundAttributes.getTeams().getTeamList() )
		{
			team.setFriendlyFire(true);
			final List<PlayableHeroDefinition> heros = team.getHerosList();
			TeamFragStatisticHud teamFragStatisticHud = null;
//			if (heros.size() > 0)
			{
//				team.getFragCounter().addListener();

				teamFragStatisticHud = hudSkin.createTeamFragHud(team);
				hudController.addStaticHud(teamFragStatisticHud);
			}
			for (PlayableHeroDefinition gameHeroProperties : heros) 
			{
				gameHeroProperties.setHeroAttributes(roundAttributes.getHeroAttributes().clone());
				final PlayableGameHero gameHero = new PlayableGameHero(gameHeroProperties);
				final HeroFragStatisticHud heroFragStatisticHud = hudSkin.createHeroFragHud(gameHero);

				HeroMovingHudController attributesMovingHudController = hudSkin.createHeroAttributesHud(gameHero);
				
				hudController.addDynamicHud(attributesMovingHudController);
				spawnHero(gameHero);
				teamFragStatisticHud.addHeroFragHud(heroFragStatisticHud);
				initializeGameObject(Arrays.asList(gameHero));
			}
		}
	}
	
	public void addHeroToSpawn(PlayableGameHero hero)
	{
		gameHeroSpawner.addHeroToSpawn(hero, roundAttributes.getSpawnTime());
	}
	
	public void spawnHero(final PlayableGameHero gameHero)
	{
		gameHero.spawn(WorldConstants.sfmlToPhysicsCoordinates(scenery.popRandomSpawnPoint()));
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
//		sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
        cameraController.setViewSize(renderWindow.getSize());
//        hudController.setViewSize(renderWindow.getSize());
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
			if (event.asKeyEvent().key == Key.U) timeFactor = 0.01f;
			if (event.asKeyEvent().key == Key.I) timeFactor = 0.3f;
			if (event.asKeyEvent().key == Key.O) timeFactor = 1f;
			if (event.asKeyEvent().key == Key.P) timeFactor = 3;
		}
		
		// catch the resize events
	    if (event.type == Type.RESIZED)
	    {
	        // update the view to the new size of the window
	        FloatRect visibleArea = new FloatRect(0, 0, event.asSizeEvent().size.x, event.asSizeEvent().size.y);
	        renderWindow.setView(new View(visibleArea));
	        cameraController.setViewSize(renderWindow.getSize());
	    }
	}

	private void verifyNewObjectsToLists()
	{
		initializeGameObject(gameItemCreator.checkReproduction());
		List<GameObject> objsToAdd = new ArrayList<>();
		objsToAdd.addAll(gameObjectsList);
		for( GameObject gameObject : objsToAdd )
		{
			initializeGameObject(gameObject.reproduce());
		}
	}

	private void initializeGameObject(Collection<? extends GameObject> collection)
	{
		for( GameObject child : collection )
		{
			child.attachToWorld(world);
			child.onEnter();
			gameObjectsList.add(0, child);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		float updateTime = deltaTime * timeFactor;
		updateObjects(updateTime);
		EventCentralMessenger.getInstance().update();
	}

	private void updateObjects(float updateTime)
	{
		world.step(updateTime, 8, 3);
		world.clearForces();
		
		scenery.update(updateTime);
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.update(updateTime);
		}
		gameHeroSpawner.update(updateTime);
		gameItemCreator.update(updateTime);
		cameraController.update(updateTime);
		hudController.update(updateTime);
		verifyNewObjectsToLists();
		checkGameOjbectsAgainsSceneryBoundaries();
		Destroyable.clearDestroyable(gameObjectsList);
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

//		world.drawDebugData();
//		cameraController.draw(renderWindow);

		scenery.draw(renderWindow);
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.draw(renderWindow);
		}
		
		scenery.drawForegroundItems(renderWindow);
		
		hudController.drawDynamicHud(renderWindow);
		renderWindow.setView(hudController.getStaticView());
		hudController.drawStaticHud(renderWindow);
	}

	@Override
	public ColorBlender getBackgroundColor()
	{
		return scenery.getBackgroundColor();
	}

}
