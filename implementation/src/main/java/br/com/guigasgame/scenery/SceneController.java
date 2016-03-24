package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.math.FloatRect;
import br.com.guigasgame.math.Randomizer;
import br.com.guigasgame.scenery.background.Background;
import br.com.guigasgame.scenery.creation.SceneryCreator;
import br.com.guigasgame.scenery.creation.SceneryShapeCollidable;

public class SceneController extends GameObject
{
	private static float SIZE_EXCEDENT = 20;
	private List<br.com.guigasgame.shape.Shape> shapes;
	private final List<Vector2f> itemSpots;
	private final List<Vector2f> spawnPoints;
	private List<Vector2f> remainingSpawnPoints;
	private Background background;	
	private FloatRect boundaries;
	

	public SceneController (SceneryCreator sceneryCreator) 
	{
		background = sceneryCreator.getBackground();
		shapes = sceneryCreator.getSceneShapeCreator().getBox2dShapes();
		itemSpots = new ArrayList<>(sceneryCreator.getItemsSpots());
		spawnPoints = new ArrayList<>(sceneryCreator.getSpawnPoints());
		remainingSpawnPoints = new ArrayList<>();
		fillRemaingSpawnPoints();
		
		drawableList.addAll(sceneryCreator.getSceneShapeCreator().getDrawableList());
	}

	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		for( br.com.guigasgame.shape.Shape shape : shapes )
		{
			SceneryShapeCollidable shapeCollidable = new SceneryShapeCollidable(new Vec2(), shape);
			shapeCollidable.attachToWorld(world);
			shapeCollidable.addFixture(shape.createAsBox2dShape());
			collidableList.add(shapeCollidable);	
		}
		shapes.clear();
		boundaries = calculateBoundaries();
	}
	
	@Override
	public void update(float deltaTime)
	{
		background.update(deltaTime);
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

	private FloatRect calculateBoundaries()
	{
		AABB aabb = new AABB();
		
		for( Collidable collidable : collidableList )
		{
			for (Fixture fixtureIterator = collidable.getBody().getFixtureList(); fixtureIterator != null; fixtureIterator = fixtureIterator.getNext()) 
			{
				aabb.combine(aabb, fixtureIterator.getAABB(0));
			}
		}
		
		Vec2[] vertices = {new Vec2(), new Vec2(), new Vec2(), new Vec2()};
		aabb.getVertices(vertices);

		final Vector2f lower = WorldConstants.physicsToSfmlCoordinates(vertices[0]);
		final Vector2f upper = WorldConstants.physicsToSfmlCoordinates(vertices[2]);
		FloatRect retorno = new FloatRect(	lower.x - SIZE_EXCEDENT,
											lower.y - SIZE_EXCEDENT, 
											upper.x - lower.x + SIZE_EXCEDENT, 
											upper.y - lower.y + SIZE_EXCEDENT);
		return retorno;
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
	
}
