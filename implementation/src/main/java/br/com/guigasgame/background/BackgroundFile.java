package br.com.guigasgame.background;

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
import br.com.guigasgame.shape.Point;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class BackgroundFile
{
	@XmlElement
	List<BackgroundFileItem> backgroundItems;
	
	@XmlElement
	private String textureFilename;
	
	@XmlElement
	private String backgroundFilename;

	public BackgroundFile(List<BackgroundFileItem> backgroundItems, String textureFilename, String backgroundFilename)
	{
		this.backgroundItems = backgroundItems;
		this.textureFilename = textureFilename;
		this.backgroundFilename = backgroundFilename;
	}

	public BackgroundFile()
	{
		backgroundItems = new ArrayList<>();
		textureFilename = new String();
	}
	
	public void addBackgrounItem(BackgroundFileItem backgroundItem)
	{
		backgroundItems.add(backgroundItem);
	}

	public List<BackgroundFileItem> getBackgroundItems()
	{
		return backgroundItems;
	}

	public String getItemsTextureFilename()
	{
		return textureFilename;
	}
	
	
	public String getBackgroundTextureFilename()
	{
		return backgroundFilename;
	}

	public static BackgroundFile loadFromFile(String filename) throws Exception
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(BackgroundFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		BackgroundFile backgroundFile = (BackgroundFile) jaxbUnmarshaller.unmarshal(new File(filename));
		return backgroundFile;
	}

	public static void main(String[] args) throws JAXBException
	{

		List<BackgroundFileItem> backgroundItems = new ArrayList<>();
		AnimationProperties moonProperties = new AnimationProperties((short) 1, (short) 0, (short) 12, new Rect(0, 0, 160, 160), true);
		AnimationProperties cloud1Properties = new AnimationProperties((short) 1, (short) 0, (short) 12, new Rect(160, 0, 600, 200), true);
		AnimationProperties cloud2Properties = new AnimationProperties((short) 1, (short) 0, (short) 12, new Rect(0, 230, 400, 400), true);

		backgroundItems.add(new BackgroundFileItem(moonProperties, new Point(10, 0), new Point(800, 100), 100));
		backgroundItems.add(new BackgroundFileItem(cloud1Properties, new Point(-150, 0), new Point(1200, 200), 90));
		backgroundItems.add(new BackgroundFileItem(cloud2Properties, new Point(200, 0), new Point(100, 150), 80));
		
		
		BackgroundFile backgroundFile = new BackgroundFile(backgroundItems, "backGroundItems.png", "backGroundTexture.png");

		try
		{
			JAXBContext context = JAXBContext.newInstance(BackgroundFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			m.marshal(backgroundFile, System.out);

			// Write to File
			 m.marshal(backgroundFile, new File(FilenameConstants.getBackgroundFilename()));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}

}
