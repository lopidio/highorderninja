package br.com.guigasgame.gameobject.input.hero;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard.Key;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.input.InputController;
import br.com.guigasgame.input.JoystickAxisInput;
import br.com.guigasgame.input.JoystickButtonInput;
import br.com.guigasgame.input.KeyboardInput;
import br.com.guigasgame.input.MouseInput;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class HeroInputConfigFile
{

	@XmlAttribute
	public int playerID;

	@XmlElement
	public Map<HeroInputKey, InputController<HeroInputKey>> map;

	public HeroInputConfigFile()
	{
		map = new HashMap<>();
	}

	static GameHeroInputMap parseFile(int playerID)
	{
		JAXBContext jaxbContext;
		try
		{
			jaxbContext = JAXBContext.newInstance(HeroInputConfigFile.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			HeroInputConfigFile heroInputConfigFile = (HeroInputConfigFile) jaxbUnmarshaller
					.unmarshal(new File(FilenameConstants
							.getInputPlayerConfigFilename(playerID)));

			// Insere as chaves no controlador de input
			for( Entry<HeroInputKey, InputController<HeroInputKey>> inputConfig : heroInputConfigFile.map
					.entrySet() )
			{
				inputConfig.getValue().setKey(inputConfig.getKey());
			}

			GameHeroInputMap gameHeroInput = new GameHeroInputMap(
					heroInputConfigFile.map);
			return gameHeroInput;
		}
		catch (JAXBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static void createDefaultFileFromPlayerID(int playerID)
	{

		HeroInputConfigFile heroInputConfigFile = new HeroInputConfigFile();

		heroInputConfigFile.map = new HashMap<HeroInputKey, InputController<HeroInputKey>>();
		heroInputConfigFile.playerID = playerID;

		heroInputConfigFile.map.put(HeroInputKey.LEFT, new InputController<HeroInputKey>().addInputHandler(new KeyboardInput(Key.LEFT))
																							.addInputHandler(new KeyboardInput(Key.A))
																							.addJoystickHandler(new JoystickAxisInput(Joystick.Axis.POV_Y, false))
																						);
		heroInputConfigFile.map.put(HeroInputKey.RIGHT, new InputController<HeroInputKey>().addInputHandler(new KeyboardInput(Key.RIGHT)).addJoystickHandler(new JoystickAxisInput(Joystick.Axis.POV_Y, true)));
		
//		
//		 heroInputConfigFile.map.put(HeroInputKey.ROPE, InputController
//				.createJoystickAxisEvent(Arrays.asList(new InputController.JoystickAxisStruct(Joystick.Axis.POV_Y, true))));
//		heroInputConfigFile.map.put(HeroInputKey.RIGHT, InputController
//				.createKeyboardEvent(Arrays.asList(Key.RIGHT, Key.D)));
//		heroInputConfigFile.map.put(HeroInputKey.UP, InputController
//				.createKeyboardEvent(Arrays.asList(Key.UP, Key.W)));
//		heroInputConfigFile.map.put(HeroInputKey.JUMP, InputController
//				.createKeyboardEvent(Arrays.asList(Key.SPACE)));
//		heroInputConfigFile.map.put(HeroInputKey.ACTION, InputController
//				.createJoystickButtonEvent(Arrays.asList(0)));
//
		try
		{
			JAXBContext context = JAXBContext
					.newInstance(HeroInputConfigFile.class, JoystickAxisInput.class, JoystickButtonInput.class, MouseInput.class, KeyboardInput.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			m.marshal(heroInputConfigFile, System.out);

			// Write to File
			m.marshal(
					heroInputConfigFile,
					new File("newInputHandlers.xml"));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		createDefaultFileFromPlayerID(0);

	}

}
