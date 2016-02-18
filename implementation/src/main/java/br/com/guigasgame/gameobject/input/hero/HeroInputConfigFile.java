package br.com.guigasgame.gameobject.input.hero;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.jsfml.window.Mouse.Button;

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
		try
		{
			JAXBContext context = JAXBContext.newInstance(HeroInputConfigFile.class, JoystickAxisInput.class, JoystickButtonInput.class, MouseInput.class, KeyboardInput.class);
			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			HeroInputConfigFile heroInputConfigFile = (HeroInputConfigFile) jaxbUnmarshaller.unmarshal(new File(FilenameConstants.getInputPlayerConfigFilename(playerID)));

			// Insere as chaves no controlador de input
			for( Entry<HeroInputKey, InputController<HeroInputKey>> inputConfig : heroInputConfigFile.map.entrySet() )
			{
				inputConfig.getValue().setDeviceId(playerID-1);
				inputConfig.getValue().setUserData(inputConfig.getKey());
			}

			GameHeroInputMap gameHeroInput = new GameHeroInputMap(heroInputConfigFile.map);
			return gameHeroInput;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	static void createDefaultFileFromPlayerID(int playerID)
	{

		HeroInputConfigFile heroInputConfigFile = new HeroInputConfigFile();

		//LinkedHashMap keeps the keys in the order they were inserted. Easy XML readibility
		heroInputConfigFile.map = new LinkedHashMap<HeroInputKey, InputController<HeroInputKey>>();
		heroInputConfigFile.playerID = playerID;

		heroInputConfigFile.map.put(HeroInputKey.LEFT, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.LEFT)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.X, JoystickAxisInput.AxisSignal.NEGATIVE)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.POV_Y, JoystickAxisInput.AxisSignal.NEGATIVE)));
				
		heroInputConfigFile.map.put(HeroInputKey.RIGHT, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.RIGHT)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.X, JoystickAxisInput.AxisSignal.POSITIVE)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.POV_Y, JoystickAxisInput.AxisSignal.POSITIVE)));


		heroInputConfigFile.map.put(HeroInputKey.UP, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.UP)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.Y, JoystickAxisInput.AxisSignal.NEGATIVE)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.POV_X, JoystickAxisInput.AxisSignal.POSITIVE)));

				
		heroInputConfigFile.map.put(HeroInputKey.DOWN, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.DOWN)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.Y, JoystickAxisInput.AxisSignal.POSITIVE)).
				addInputHandler(new JoystickAxisInput(Joystick.Axis.POV_X, JoystickAxisInput.AxisSignal.NEGATIVE)));

		heroInputConfigFile.map.put(HeroInputKey.JUMP, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.X)).
				addInputHandler(new JoystickButtonInput(0)));

		heroInputConfigFile.map.put(HeroInputKey.SHOOT, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.Z)).
				addInputHandler(new MouseInput(Button.LEFT)).
				addInputHandler(new JoystickButtonInput(5)));

		heroInputConfigFile.map.put(HeroInputKey.SLIDE, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.C)).
				addInputHandler(new JoystickButtonInput(1)));

		heroInputConfigFile.map.put(HeroInputKey.ACTION, new InputController<HeroInputKey>().
				addInputHandler(new KeyboardInput(Key.V)).
				addInputHandler(new JoystickButtonInput(3)));

		try
		{
			JAXBContext context = JAXBContext
					.newInstance(HeroInputConfigFile.class, JoystickAxisInput.class, JoystickButtonInput.class, MouseInput.class, KeyboardInput.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema-instance");
			

			// Write to System.out for debugging
			m.marshal(heroInputConfigFile, System.out);

			// Write to File
			m.marshal( heroInputConfigFile, new File("newInputHandlers.xml"));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
//		parseFile(0);
		createDefaultFileFromPlayerID(0);
	}

}
