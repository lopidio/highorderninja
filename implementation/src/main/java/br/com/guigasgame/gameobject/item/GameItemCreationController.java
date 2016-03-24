package br.com.guigasgame.gameobject.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jbox2d.common.Vec2;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.item.life.LifeItem;
import br.com.guigasgame.gameobject.item.shurikenpack.ShurikenPackItem;
import br.com.guigasgame.math.Randomizer;
import br.com.guigasgame.scenery.SceneController;
import br.com.guigasgame.updatable.UpdatableFromTime;


public class GameItemCreationController implements UpdatableFromTime
{
	private List<Vector2f> itemsSpots;
	private Collection<GameObject> itemsToAdd;
	private Map<GameItemIndex, GameItemCreatorTimeCounter> itemsMap;

	public GameItemCreationController(SceneController scenery)
	{
		itemsSpots = new ArrayList<>();
		itemsSpots.addAll(scenery.getItemSpots());
		itemsToAdd = new ArrayList<>();

		itemsMap = new HashMap<>();
		for( GameItemIndex items : GameItemIndex.values() )
		{
			itemsMap.put(items, new GameItemCreatorTimeCounter(GameItemPropertiesPool.getGameItemProperties(items).period));
		}

	}

	@Override
	public void update(float deltaTime)
	{
		for( Entry<GameItemIndex, GameItemCreatorTimeCounter> entry : itemsMap.entrySet() )
		{
			entry.getValue().update(deltaTime);
			if (entry.getValue().isTimeToCreate())
			{
				entry.getValue().resetCounter();
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
			retorno = new ShurikenPackItem(getRandomItemSpot());
		}
		else if (key == GameItemIndex.LIFE)
		{
			retorno = new LifeItem(getRandomItemSpot());
		}
		return retorno;
	}
	
	private Vec2 getRandomItemSpot()
	{
		int randIndex = Randomizer.getRandomIntInInterval(0, itemsSpots.size() - 1);
		System.out.println("Item random spot: " + randIndex);
		return WorldConstants.sfmlToPhysicsCoordinates(new Vector2f(itemsSpots.get(randIndex).x , itemsSpots.get(randIndex).y));
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
