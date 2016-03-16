package br.com.guigasgame.scenery.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.animation.AnimationProperties;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.math.Rect;
import br.com.guigasgame.scenery.background.BackgroundFile;
import br.com.guigasgame.scenery.background.BackgroundItemProperties;
import br.com.guigasgame.scenery.creation.SceneryShapes;
import br.com.guigasgame.shape.CircleShape;
import br.com.guigasgame.shape.Point;
import br.com.guigasgame.shape.RectangleShape;
import br.com.guigasgame.shape.TriangleShape;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SceneryFile
{

	@XmlElement
	private SceneryShapes sceneryShapes;
	@XmlElement
	private SpawnPoints spawnPoints;
	@XmlElement
	private ItemSpots itemSpots;
	@XmlElement
	private BackgroundFile backgroundFile;

	public SceneryFile()
	{
		super();
	}
	
	public SceneryFile(SceneryShapes sceneryShapes, SpawnPoints spawnPoints, ItemSpots itemSpots, BackgroundFile backgroundFile) 
	{
		super();
		this.sceneryShapes = sceneryShapes;
		this.spawnPoints = spawnPoints;
		this.itemSpots = itemSpots;
		this.backgroundFile = backgroundFile;
	}

	public SpawnPoints getSpawnPoints() 
	{
		return spawnPoints;
	}

	public ItemSpots getItemSpots() 
	{
		return itemSpots;
	}

	public BackgroundFile getBackgroundFile() 
	{
		return backgroundFile;
	}

	public SceneryShapes getSceneryShapes()
	{
		return sceneryShapes;
	}

	
	public void setSceneryShapes(SceneryShapes sceneryShapes)
	{
		this.sceneryShapes = sceneryShapes;
	}
	
	public static SceneryFile loadFromFile(String filename) throws Exception
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(SceneryFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		SceneryFile sceneryFile = (SceneryFile) jaxbUnmarshaller.unmarshal(new File(filename));
		return sceneryFile;
	}

	public static void main(String[] args) throws JAXBException
	{

		SceneryShapes bodyShape = new SceneryShapes();

		bodyShape.addRectangleShape(new RectangleShape(new Point(300, 760), new Point(1040, 20), "darkStoneTexture.jpg")); //ground
		bodyShape.addRectangleShape(new RectangleShape(new Point(300, 0), new Point(1040, 10), "darkStoneTexture.jpg")); //ceil
		bodyShape.addRectangleShape(new RectangleShape(new Point(20, 300), new Point(20, 440), "darkStoneTexture.jpg")); //left wall
		bodyShape.addRectangleShape(new RectangleShape(new Point(1340, 300), new Point(20, 440), "darkStoneTexture.jpg")); //right wall

//		bodyShape.addRectangleShape(new RectangleShape(new Point(280, 480), new Point(10, 160))); // |
		bodyShape.addRectangleShape(new RectangleShape(new Point(160, 480), new Point(10, 170), "darkStoneTexture.jpg")); // |

		bodyShape.addRectangleShape(new RectangleShape(new Point(200, 160), new Point(60, 10), "darkStoneTexture.jpg"));
//		bodyShape.addCircleShape(new CircleShape(new Point(200, 200), 50));
//		bodyShape.addRectangleShape(new RectangleShape(new Point(500, 120), new Point(10, 10)));
		bodyShape.addTriangleShape(new TriangleShape(new Point(500,  120), new Point(660,  120), new Point(580,  150), "darkStoneTexture.jpg"));
		bodyShape.addTriangleShape(new TriangleShape(new Point(800,  120), new Point(960,  120), new Point(880,  150), "darkStoneTexture.jpg"));
//		bodyShape.addRectangleShape(new RectangleShape(new Point(800, 120), new Point(10, 10)));
//		bodyShape.addRectangleShape(new RectangleShape(new Point(1100, 160), new Point(60, 10)));
		bodyShape.addCircleShape(new CircleShape(new Point(1100, 200), 50, "darkStoneTexture.jpg"));
		
		bodyShape.addRectangleShape(new RectangleShape(new Point(800, 640), new Point(380, 10), "darkStoneTexture.jpg")); //middle floor
		bodyShape.addRectangleShape(new RectangleShape(new Point(950, 540), new Point(400, 10), "darkStoneTexture.jpg")); //middle floor
		bodyShape.addRectangleShape(new RectangleShape(new Point(660, 440), new Point(240, 10), "darkStoneTexture.jpg")); //middle floor
		bodyShape.addTriangleShape(new TriangleShape(new Point(419,  440), new Point(420,  370), new Point(600,  440), "darkStoneTexture.jpg"));
		bodyShape.addRectangleShape(new RectangleShape(new Point(430, 540), new Point(10, 100), "darkStoneTexture.jpg")); //|

		
		
		List<Point> spawnPoint = new ArrayList<Point>();
    	spawnPoint.add(new Point(200, 10));
		spawnPoint.add(new Point(800, 10));
		spawnPoint.add(new Point(1000, 360));
		SpawnPoints spawnPoints = new SpawnPoints(spawnPoint);

		List<Point> itemSpots = new ArrayList<Point>();
		itemSpots.add(new Point(12, 43));
		ItemSpots spots = new ItemSpots(itemSpots);
		
		
		List<BackgroundItemProperties> backgroundItems = new ArrayList<>();
		AnimationProperties back = new AnimationProperties((short) 1, (short) 0, (short) 12, new Rect(0, 0, 1500, 1000), true);
		AnimationProperties moonProperties = new AnimationProperties((short) 1, (short) 0, (short) 12, new Rect(0, 0, 160, 160), true);
		AnimationProperties cloud1Properties = new AnimationProperties((short) 1, (short) 0, (short) 12, new Rect(160, 0, 600, 200), true);
		AnimationProperties cloud2Properties = new AnimationProperties((short) 1, (short) 0, (short) 12, new Rect(0, 230, 400, 400), true);

		backgroundItems.add(new BackgroundItemProperties(back, new Point(0, 0), 1000, "backGroundTexture.png"));
		backgroundItems.add(new BackgroundItemProperties(moonProperties, new Point(10, 0), new Point(800, 100), 100, false, "backGroundItems.png"));
		backgroundItems.add(new BackgroundItemProperties(cloud1Properties, new Point(-150, 0), new Point(1200, 200), 90, true, "backGroundItems.png"));
		backgroundItems.add(new BackgroundItemProperties(cloud2Properties, new Point(200, 0), new Point(100, 150), 80, true, "backGroundItems.png"));
		
		BackgroundFile backgroundFile = new BackgroundFile(backgroundItems);
		

		SceneryFile sceneryFile = new SceneryFile(bodyShape, spawnPoints, spots, backgroundFile);

		try
		{
			JAXBContext context = JAXBContext.newInstance(SceneryFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			m.marshal(sceneryFile, System.out);

			// Write to File
			 m.marshal(sceneryFile, new File(FilenameConstants.getSceneryFilename()));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}

}
