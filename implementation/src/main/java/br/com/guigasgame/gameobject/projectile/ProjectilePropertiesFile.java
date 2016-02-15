package br.com.guigasgame.gameobject.projectile;

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

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectilePropertiesFile
{
	@XmlElement
	private Map<ProjectileIndex, ProjectileProperties> projectilesPropertiesMap;

	public ProjectilePropertiesFile()
	{
		projectilesPropertiesMap = new HashMap<>();
	}
	
	public static ProjectilePropertiesFile loadFromFile(String filename) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ProjectilePropertiesFile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ProjectilePropertiesFile projectilePropertiesFile = ((ProjectilePropertiesFile) jaxbUnmarshaller
				.unmarshal(new File(filename)));
		return projectilePropertiesFile;
	}

	
	public Map<ProjectileIndex, ProjectileProperties> getProjectileMap()
	{
		return projectilesPropertiesMap;
	}

	public static void main(String[] args) throws JAXBException {
		
		try
		{
			ProjectilePropertiesFile states = new ProjectilePropertiesFile();
			ProjectileProperties properties = new ProjectileProperties((short)0, 1.f, 1f, 0.1f, 1.f, 12.f, 30.f);

			states.projectilesPropertiesMap.put(ProjectileIndex.SHURIKEN, properties);

			JAXBContext context = JAXBContext
					.newInstance(ProjectilePropertiesFile.class);
			Marshaller m = context.createMarshaller(); // for pretty-print XML
														// in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging 
			m.marshal(states, System.out);

//			 Write to File 
			 m.marshal(states, new File("projectiles.xml"));
		}
		catch
		(JAXBException e)
		{
			e.printStackTrace();
		}

//		StatePropertiesFile fromFile = ((StatePropertiesFile) StatePropertiesFile.loadFromFile("oi.txt"));

	}	
	
}
