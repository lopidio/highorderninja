package br.com.guigasgame.gameobject.input.hero;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlEnum;

import br.com.guigasgame.gameobject.input.InputListener;
import br.com.guigasgame.gameobject.input.InputMapController;

public class GameHeroInputMap {
	@XmlEnum
	public enum HeroInputKey {
		LEFT, 
		RIGHT, 
		UP, 
		DOWN,
		
		JUMP, //X 
		ROPE, //L1
		SHOOT, //Quadrado
		ACTION, // (SLIDE) R1 
		STOP //R2
	}

	InputListener<HeroInputKey> inputHeroListener;

	private Map<HeroInputKey, InputMapController<HeroInputKey>> inputMap;

	public GameHeroInputMap(InputListener<HeroInputKey> listener, Map<HeroInputKey, InputMapController<HeroInputKey>> inputMap) {
		this.inputMap = inputMap;
		this.inputHeroListener = listener;

		for (Entry<GameHeroInputMap.HeroInputKey, InputMapController<HeroInputKey>> map : inputMap.entrySet()) {
			map.getValue().setInputListener(inputHeroListener);
		}
	}

	public void update() {
		for (Entry<GameHeroInputMap.HeroInputKey, InputMapController<HeroInputKey>> map : inputMap.entrySet()) {
			map.getValue().handleEvent();
		}
	}

	public void setInputListener(InputListener<HeroInputKey> listener) {
		this.inputHeroListener = listener;
	}

	public static GameHeroInputMap loadFromConfigFile(InputListener<HeroInputKey> listener, String pathToInputFile){
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(HeroInputConfigFile.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			HeroInputConfigFile heroInputConfigFile = (HeroInputConfigFile) jaxbUnmarshaller.unmarshal(new File(pathToInputFile));
			System.out.println(heroInputConfigFile.map.size());

			GameHeroInputMap gameHeroInput = new GameHeroInputMap(listener, heroInputConfigFile.map);
			return gameHeroInput;
		} catch (JAXBException e) {
			// CREATES INPUT FILE
			e.printStackTrace();
			
			/*
		PlayerInputConfigFile anim = new PlayerInputConfigFile();
		anim.map = new HashMap<HeroInputKey, InputController>();
		anim.playerID = 5;
		anim.map.put(HeroInputKey.LEFT, InputController.createKeyboardEvent(Key.SPACE, null, HeroInputKey.LEFT));
		anim.map.put(HeroInputKey.JUMP, InputController.createKeyboardEvent(Key.UP, null, HeroInputKey.JUMP));
		
        try {
            JAXBContext context = JAXBContext.newInstance(PlayerInputConfigFile.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            // Write to System.out for debugging
             m.marshal(anim, System.out);
 
            // Write to File
//            m.marshal(emp, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }		
 			 */
		}
		
		
//		InputController jump = InputController.createKeyboardEvent(Key.SPACE, this, HeroInputKey.JUMP);
//		InputController left = InputController.createKeyboardEvent(Key.LEFT, this, HeroInputKey.LEFT);
//		InputController right = InputController.createKeyboardEvent(Key.RIGHT, this, HeroInputKey.RIGHT);
//		InputController action = InputController.createMouseClickEvent(Button.LEFT, this, HeroInputKey.ACTION);
//		keyMap.put(jump.getHeroKey(), jump);
//		keyMap.put(right.getHeroKey(), right);
//		keyMap.put(left.getHeroKey(), left);
//		keyMap.put(action.getHeroKey(), action);

		
		return null;
	}
}
