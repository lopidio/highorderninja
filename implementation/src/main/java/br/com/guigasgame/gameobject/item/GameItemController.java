package br.com.guigasgame.gameobject.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.item.life.LifeItem;
import br.com.guigasgame.gameobject.item.shurikenpack.ShurikenPackItem;
import br.com.guigasgame.scenery.Scenery;
import br.com.guigasgame.updatable.UpdatableFromTime;


public class GameItemController implements UpdatableFromTime
{

	private Collection<GameObject> itemsToAdd;
	private Map<GameItemIndex, GameItemCreator> itemsMap;
	private Scenery scenery;

	public GameItemController(Scenery scenery)
	{
		this.scenery = scenery;
		itemsToAdd = new ArrayList<>();

		itemsMap = new HashMap<>();
		for( GameItemIndex items : GameItemIndex.values() )
		{
			itemsMap.put(items, new GameItemCreator(GameItemPropertiesPool.getGameItemProperties(items).period));
		}

	}

	@Override
	public void update(float deltaTime)
	{
		for( Entry<GameItemIndex, GameItemCreator> entry : itemsMap.entrySet() )
		{
			entry.getValue().update(deltaTime);
			if (entry.getValue().isTimeToCreate())
			{
				entry.getValue().reset();
				GameObject itemToAdd = createItem(entry.getKey());
				if (itemToAdd != null)
					itemsToAdd.add(itemToAdd);
			}
		}

	}

	private GameObject createItem(GameItemIndex key)
	{
		GameObject retorno = null;
		if (key == GameItemIndex.SHURIKEN_PACK)
		{
			retorno = new ShurikenPackItem(WorldConstants.sfmlToPhysicsCoordinates(scenery.getRandomItemSpot()));
		}
		else if (key == GameItemIndex.LIFE)
		{
			retorno = new LifeItem(WorldConstants.sfmlToPhysicsCoordinates(scenery.getRandomItemSpot()));
		}
		return retorno;
	}

	public boolean hasItemToAdd()
	{
		return itemsToAdd.size() > 0;
	}

	public Collection<GameObject> getChildrenList()
	{
		return itemsToAdd;
	}

	public void clearItemToAddList()
	{
		itemsToAdd.clear();
	}

}
