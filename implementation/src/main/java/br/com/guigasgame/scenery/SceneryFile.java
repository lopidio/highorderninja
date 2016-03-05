package br.com.guigasgame.scenery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.file.FilenameConstants;
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
	private List<Point> spawnPoint;
	@XmlElement
	private List<Point> itemSpots;
	@XmlAttribute
	private String textureName;

	public SceneryFile()
	{
		super();
	}
	
	public SceneryFile(SceneryShapes sceneryShapes, List<Point> spawnPoint, List<Point> itemSpots, String textureName)
	{
		this.sceneryShapes = sceneryShapes;
		this.spawnPoint = spawnPoint;
		this.itemSpots = itemSpots;
		this.textureName = textureName;
	}

	public List<Point> getSpawnPoint()
	{
		return spawnPoint;
	}

	public void setSpawnPoint(List<Point> spawnPoint)
	{
		this.spawnPoint = spawnPoint;
	}

	public List<Point> getItemSpots()
	{
		return itemSpots;
	}

	public void setItemSpots(List<Point> itemSpots)
	{
		this.itemSpots = itemSpots;
	}
	
	
	public String getTextureName()
	{
		return textureName;
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

		bodyShape.addRectangleShape(new RectangleShape(new Point(300, 760), new Point(1040, 20))); //ground
		bodyShape.addRectangleShape(new RectangleShape(new Point(300, 0), new Point(1040, 10))); //ceil
		bodyShape.addRectangleShape(new RectangleShape(new Point(20, 300), new Point(20, 440))); //left wall
		bodyShape.addRectangleShape(new RectangleShape(new Point(1340, 300), new Point(20, 440))); //right wall

//		bodyShape.addRectangleShape(new RectangleShape(new Point(280, 480), new Point(10, 160))); // |
		bodyShape.addRectangleShape(new RectangleShape(new Point(160, 480), new Point(10, 170))); // |

		bodyShape.addRectangleShape(new RectangleShape(new Point(200, 160), new Point(60, 10)));
//		bodyShape.addCircleShape(new CircleShape(new Point(200, 200), 50));
//		bodyShape.addRectangleShape(new RectangleShape(new Point(500, 120), new Point(10, 10)));
		bodyShape.addTriangleShape(new TriangleShape(new Point(500,  120), new Point(660,  120), new Point(580,  150)));
		bodyShape.addTriangleShape(new TriangleShape(new Point(800,  120), new Point(960,  120), new Point(880,  150)));
//		bodyShape.addRectangleShape(new RectangleShape(new Point(800, 120), new Point(10, 10)));
//		bodyShape.addRectangleShape(new RectangleShape(new Point(1100, 160), new Point(60, 10)));
		bodyShape.addCircleShape(new CircleShape(new Point(1100, 200), 50));
		
		bodyShape.addRectangleShape(new RectangleShape(new Point(800, 640), new Point(380, 10))); //middle floor
		bodyShape.addRectangleShape(new RectangleShape(new Point(950, 540), new Point(400, 10))); //middle floor
		bodyShape.addRectangleShape(new RectangleShape(new Point(660, 440), new Point(240, 10))); //middle floor
		bodyShape.addTriangleShape(new TriangleShape(new Point(419,  440), new Point(420,  370), new Point(600,  440)));
		bodyShape.addRectangleShape(new RectangleShape(new Point(430, 540), new Point(10, 100))); //|

		
		
		List<Point> spawnPoint = new ArrayList<Point>();
    	spawnPoint.add(new Point(200, 10));
		spawnPoint.add(new Point(800, 10));
		spawnPoint.add(new Point(1000, 360));

		List<Point> itemSpots = new ArrayList<Point>();
		itemSpots.add(new Point(12, 43));

		SceneryFile sceneryFile = new SceneryFile(bodyShape, spawnPoint, itemSpots, "darkStoneTexture.jpg");

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
