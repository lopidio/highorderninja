package br.com.guigasgame.shape.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SceneryFile 
{
	@XmlElement
	private BodyShape bodyShape;
	@XmlElement
	private List<Point> spawnPoint;
	@XmlElement
	private List<Point> itemSpots;
	
	public SceneryFile() {
		super();
	}

	public SceneryFile(BodyShape bodyShapes, List<Point> spawnPoint, List<Point> itemSpots) {
		super();
		this.bodyShape = bodyShapes;
		this.spawnPoint = spawnPoint;
		this.itemSpots = itemSpots;
	}




	public BodyShape getBodyShapes() {
		return bodyShape;
	}




	public void setBodyShapes(BodyShape bodyShape) {
		this.bodyShape = bodyShape;
	}




	public List<Point> getSpawnPoint() {
		return spawnPoint;
	}




	public void setSpawnPoint(List<Point> spawnPoint) {
		this.spawnPoint = spawnPoint;
	}




	public List<Point> getItemSpots() {
		return itemSpots;
	}




	public void setItemSpots(List<Point> itemSpots) {
		this.itemSpots = itemSpots;
	}




	public static void main(String[] args) throws JAXBException
	{

		BodyShape bodyShape = new BodyShape();
		bodyShape.addCircleShape(new CircleShape(new Point(98, 65), 2.f));
		bodyShape.addCircleShape(new CircleShape(new Point(982, 165), 22.f));
		
		RectangleShape rectangleShape = new RectangleShape(new Point(65, 53), new Point(56, 45));
		rectangleShape.setProperty("Deadly");
		bodyShape.addRectangleShape(rectangleShape);

		List<Point> spawnPoint = new ArrayList<Point>();
		spawnPoint.add(new Point(112, 483));
		spawnPoint.add(new Point(1321, 1543));
		spawnPoint.add(new Point(1524, 3643));
		
		
		List<Point> itemSpots = new ArrayList<Point>();
		itemSpots.add(new Point(12, 43));
		itemSpots.add(new Point(121, 143));
		itemSpots.add(new Point(124, 343));
		
		SceneryFile sceneryFile = new SceneryFile(bodyShape, spawnPoint, itemSpots);

		try
		{
			JAXBContext context = JAXBContext
					.newInstance(SceneryFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			m.marshal(sceneryFile, System.out);

			// Write to File
			// m.marshal(anim, new File("ninjaSmooth.xml"));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		// @SuppressWarnings("unchecked")
		// AnimationPropertiesFile<HeroAnimationsIndex> fromFile =
		// ((AnimationPropertiesFile<HeroAnimationsIndex>)
		// AnimationPropertiesFile
		// .loadFromFile("oi.txt"));
		// System.out.println(fromFile.textureFilename);

	}
	
	
}
