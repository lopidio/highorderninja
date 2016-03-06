package br.com.guigasgame.gameobject.item;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.shape.Point;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class GameItemPropertiesFile
{
	@XmlElement
	private Map<GameItemIndex, GameItemProperties> itemMap;

	public GameItemPropertiesFile()
	{
		this.itemMap = new HashMap<>();
	}

	public GameItemPropertiesFile(Map<GameItemIndex, GameItemProperties> itemMap)
	{
		this.itemMap = itemMap;
	}

	public Map<GameItemIndex, GameItemProperties> getItemMap()
	{
		return itemMap;
	}

	public static GameItemPropertiesFile loadFromFile(String filename) throws JAXBException 
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(GameItemPropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		GameItemPropertiesFile itemPropertiesFile = ((GameItemPropertiesFile) jaxbUnmarshaller
				.unmarshal(new File(filename)));

		return itemPropertiesFile;
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			Map<GameItemIndex, GameItemProperties> maps = new HashMap<>();
			maps.put(GameItemIndex.LIFE, new GameItemProperties(0.5f, 0.2f, 1, new Point(10, 10), 30, 15));
			maps.put(GameItemIndex.SHURIKEN_PACK, new GameItemProperties(0.2f, 1f, 3, new Point(10, 10), 20, 5));
			GameItemPropertiesFile itemPropertiesFile = new GameItemPropertiesFile();
			itemPropertiesFile.itemMap = maps;
			
			JAXBContext context = JAXBContext.newInstance(GameItemPropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging 
			m.marshal(itemPropertiesFile, System.out);

//			 Write to File 
			 m.marshal(itemPropertiesFile, new File(FilenameConstants.getItemsPropertiesFilename()));
		}
		catch
		(JAXBException e)
		{
			e.printStackTrace();
		}

	}	

	
}
