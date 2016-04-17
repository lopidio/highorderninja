package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.math.Randomizer;
import br.com.guigasgame.scenery.background.Background;
import br.com.guigasgame.scenery.creation.SceneryCollidable;
import br.com.guigasgame.scenery.creation.SceneryInitialize;


public class SceneController extends GameObject
{

	private List<br.com.guigasgame.shape.Shape> shapes;
	private final List<Vector2f> itemSpots;
	private final List<Vector2f> spawnPoints;
	private List<Vector2f> remainingSpawnPoints;
	private Background background;
	private FloatRect boundaries;
	private List<SceneryCollidable> sceneryCollidables;
	private final br.com.guigasgame.math.FloatRect boundariesTollerance;
	private ColorBlender backgroundColor;

	public SceneController(SceneryInitialize sceneryCreator)
	{
		background = sceneryCreator.getBackground();
		shapes = sceneryCreator.getSceneShapeCreator().getBox2dShapes();
		itemSpots = new ArrayList<>(sceneryCreator.getItemsSpots());
		spawnPoints = new ArrayList<>(sceneryCreator.getSpawnPoints());
		remainingSpawnPoints = new ArrayList<>();
		this.backgroundColor = sceneryCreator.getBackgroundColor();
		fillRemaingSpawnPoints();

		drawableList.addAll(sceneryCreator.getSceneShapeCreator().getDrawableList());
		sceneryCollidables = new ArrayList<>();
		this.boundariesTollerance = sceneryCreator.getBoundariesTollerance();
	}

	@Override
	public void attachToWorld(World world)
	{
		//shape reuse
		java.util.Map<Float, SceneryCollidable> map = new HashMap<Float, SceneryCollidable>();
		super.attachToWorld(world);
		for( br.com.guigasgame.shape.Shape shape : shapes ) 
		{
			if (map.get(shape.getDamagePerSecond()) == null)
			{
				SceneryCollidable shapeCollidable = new SceneryCollidable(shape.getDamagePerSecond());
				shapeCollidable.attachToWorld(world);
				shapeCollidable.addFixture(shape.createBox2dShape());
				sceneryCollidables.add(shapeCollidable);

				map.put(shape.getDamagePerSecond(), shapeCollidable);
			}
			else
			{
				map.get(shape.getDamagePerSecond()).addFixture(shape.createBox2dShape());
			}
		}
		collidableList.addAll(sceneryCollidables);
		shapes.clear();
		calculateBoundary();
	}

	@Override
	public void update(float deltaTime)
	{
		background.update(deltaTime);
		for( SceneryCollidable sceneryCollidable : sceneryCollidables )
		{
			sceneryCollidable.update(deltaTime);
		}
	}

	public void drawBackgroundItems(RenderWindow renderWindow)
	{
		background.drawBackgroundItems(renderWindow);
	}

	public void drawForegroundItems(RenderWindow renderWindow)
	{
		background.drawForegroundItems(renderWindow);
	}

	private void fillRemaingSpawnPoints()
	{
		remainingSpawnPoints.addAll(spawnPoints);
	}

	public Vector2f popRandomSpawnPoint()
	{
		if (remainingSpawnPoints.size() == 0)
			fillRemaingSpawnPoints();
		int randIndex = Randomizer.getRandomIntInInterval(0, remainingSpawnPoints.size() - 1);
		Vector2f retorno = remainingSpawnPoints.get(randIndex);
		remainingSpawnPoints.remove(randIndex);
		return retorno;
	}

	private void calculateBoundary()
	{
		AABB aabb = new AABB();

		for( Collidable collidable : collidableList )
		{
			for( Fixture fixtureIterator = collidable.getBody().getFixtureList(); fixtureIterator != null; fixtureIterator = fixtureIterator.getNext() )
			{
				aabb.combine(aabb, fixtureIterator.getAABB(0));
			}
		}

		Vec2[] vertices = { new Vec2(), new Vec2(), new Vec2(), new Vec2() };
		aabb.getVertices(vertices);

		final Vector2f smallest = WorldConstants.physicsToSfmlCoordinates(vertices[0]);
		final Vector2f biggest = WorldConstants.physicsToSfmlCoordinates(vertices[2]);
		boundaries = new org.jsfml.graphics.FloatRect(	smallest.x 	- boundariesTollerance.left,
																smallest.y	- boundariesTollerance.top,
																biggest.x 	- smallest.x + boundariesTollerance.width + boundariesTollerance.width,
																biggest.y	- smallest.y + boundariesTollerance.height + boundariesTollerance.top);
	}

	public FloatRect getBoundaries()
	{
		return boundaries;
	}

	public void setBackground(Background background)
	{
		this.background = background;
	}

	public List<Vector2f> getItemSpots()
	{
		return itemSpots;
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		System.out.println(me);
	}

	public ColorBlender getBackgroundColor()
	{
		return backgroundColor;
	}

	public Vector2f getCenter()
	{
		return new Vector2f(boundaries.width/2 + boundaries.left,
							boundaries.height/2 + boundaries.top);
	}

}
