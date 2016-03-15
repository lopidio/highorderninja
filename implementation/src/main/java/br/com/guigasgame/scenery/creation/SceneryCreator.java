package br.com.guigasgame.scenery.creation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.background.Background;
import br.com.guigasgame.scenery.file.SceneryFile;
import br.com.guigasgame.shape.Point;

public class SceneryCreator
{
	private SceneShapeCreator shapeCreator;
	private Collection<Vector2f> itemSpots;
	private Collection<Vector2f> spawnPoints;
	private Background background;
	
	public SceneryCreator(SceneryFile sceneryFile) throws Exception
	{
		shapeCreator = new SceneShapeCreator(sceneryFile.getSceneryShapes());
		background = new Background(sceneryFile.getBackgroundFile());
		
		itemSpots = new ArrayList<>();
		itemSpots.addAll(addAsVector2fPoint(sceneryFile.getItemSpots().getSpot()));
		spawnPoints = new ArrayList<>();
		spawnPoints.addAll(addAsVector2fPoint(sceneryFile.getSpawnPoints().getPoint()));
		itemSpots.addAll(addAsVector2fPoint(sceneryFile.getSpawnPoints().getPoint()));
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

}
