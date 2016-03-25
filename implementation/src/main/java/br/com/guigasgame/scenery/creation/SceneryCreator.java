package br.com.guigasgame.scenery.creation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.math.FloatRect;
import br.com.guigasgame.scenery.background.Background;
import br.com.guigasgame.scenery.background.BackgroundCreator;
import br.com.guigasgame.scenery.file.SceneryFile;
import br.com.guigasgame.shape.Point;

public class SceneryCreator
{
	private SceneShapeCreator shapeCreator;
	private Collection<Vector2f> itemSpots;
	private Collection<Vector2f> spawnPoints;
	private Background background;
	private FloatRect boundariesTollerance;
	private ColorBlender backGroundColor;
	
	public SceneryCreator(SceneryFile sceneryFile) throws Exception
	{
		shapeCreator = new SceneShapeCreator(sceneryFile.getSceneryShapes());
		background = new BackgroundCreator(sceneryFile.getBackgroundFile()).createBackground();
		backGroundColor = sceneryFile.getBackgroundColor();
		
		itemSpots = new ArrayList<>();
		itemSpots.addAll(addAsVector2fPoint(sceneryFile.getItemSpots().getSpot()));
		spawnPoints = new ArrayList<>();
		spawnPoints.addAll(addAsVector2fPoint(sceneryFile.getSpawnPoints().getPoint()));
		boundariesTollerance = sceneryFile.getBoundariesTollerance();
	}
		
	public FloatRect getBoundariesTollerance()
	{
		return boundariesTollerance;
	}

	private Collection<Vector2f> addAsVector2fPoint(List<Point> points)
	{
		List<Vector2f> retorno = new ArrayList<>();
		for (Point point : points) 
		{
			retorno.add(new Vector2f(point.getX(), point.getY()));
		}
		return retorno;
	}

	public Background getBackground() 
	{
		return background;
	}
	
	public Collection<? extends Vector2f> getItemsSpots()
	{
		return itemSpots;
	}

	public Collection<? extends Vector2f> getSpawnPoints() 
	{
		return spawnPoints;
	}

	public SceneShapeCreator getSceneShapeCreator() 
	{
		return shapeCreator;
	}

	public ColorBlender getBackgroundColor()
	{
		return backGroundColor;
	}

}
