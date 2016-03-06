package br.com.guigasgame.gameobject.item;

import java.util.Map;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.file.FilenameConstants;

public class GameItemPropertiesPool
{
		private static GameItemPropertiesPool singleton;
		private Map<GameItemIndex, GameItemProperties> itemsPropertiesMap;
		
		static
		{
			singleton = new GameItemPropertiesPool();
		}
		
		private GameItemPropertiesPool()
		{
			loadItemsProperties();
		}
		
		public static GameItemProperties getGameItemProperties(GameItemIndex gameItemIndex)
		{
			return singleton.itemsPropertiesMap.get(gameItemIndex);
		}
		
		private void loadItemsProperties()
		{
			GameItemPropertiesFile gameItemPropertiesFile;
			try
			{
				gameItemPropertiesFile = GameItemPropertiesFile.loadFromFile(FilenameConstants.getItemsPropertiesFilename());
				itemsPropertiesMap = gameItemPropertiesFile.getItemMap();
			}
			catch (JAXBException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
}
