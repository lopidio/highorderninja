package br.com.guigasgame.gameobject.item.life;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.action.RegeneratesLifeAction;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.item.GameItem;
import br.com.guigasgame.gameobject.item.GameItemIndex;

public class LifeItem extends GameItem
{

	public LifeItem(Vec2 position)
	{
		super(GameItemIndex.LIFE, position);
	}
	
	@Override
	public void acts(PlayableGameHero playableGameHero)
	{
		playableGameHero.addAction(new RegeneratesLifeAction(100));
		markToDestroy();
	}

}
