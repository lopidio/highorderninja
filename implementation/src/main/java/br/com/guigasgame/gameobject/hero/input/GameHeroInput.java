package br.com.guigasgame.gameobject.hero.input;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlEnum;

public class GameHeroInput implements InputHeroListener {
	@XmlEnum
	public enum HeroInputKey {
		LEFT, RIGHT, UP, DOWN, JUMP, ROPE, SHOOT, ACTION,
	}

	InputHeroListener inputHeroListener;

	private Map<HeroInputKey, InputMapController> inputMap;

	public GameHeroInput(InputHeroListener listener, Map<HeroInputKey, InputMapController> inputMap) {
		this.inputMap = inputMap;
		this.inputHeroListener = listener;

		for (Entry<GameHeroInput.HeroInputKey, InputMapController> map : inputMap.entrySet()) {
			map.getValue().setInputListener(inputHeroListener);
		}
//		this.inputMap = inputMap;
	}

	public void update() {
		for (Entry<GameHeroInput.HeroInputKey, InputMapController> map : inputMap.entrySet()) {
			map.getValue().handleEvent();
		}
	}

	public void setInputListener(InputHeroListener listener) {
		this.inputHeroListener = listener;
	}

	@Override
	public void isPressed(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.isPressed(key);
	}
	
	@Override
	public void isReleased(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.isReleased(key);
	}
	
	@Override
	public void inputReleased(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.inputReleased(key);
	}

	@Override
	public void inputPressed(HeroInputKey key) {
		if (null != inputHeroListener)
			inputHeroListener.inputPressed(key);
	}

	public static GameHeroInput loadFromConfigFile(InputHeroListener listener, String pathToInputFile){
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(HeroInputConfigFile.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			HeroInputConfigFile playerInputConfigFile = (HeroInputConfigFile) jaxbUnmarshaller.unmarshal(new File(pathToInputFile));
			System.out.println(playerInputConfigFile.map.size());

			GameHeroInput gameHeroInput = new GameHeroInput(listener, playerInputConfigFile.map);
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
