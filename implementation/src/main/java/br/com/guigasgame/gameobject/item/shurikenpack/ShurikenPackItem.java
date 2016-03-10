package br.com.guigasgame.gameobject.item.shurikenpack;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.action.AddShurikenPackAction;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.item.GameItem;
import br.com.guigasgame.gameobject.item.GameItemIndex;

public class ShurikenPackItem extends GameItem
{

	public ShurikenPackItem( Vec2 position)
	{
		super(GameItemIndex.SHURIKEN_PACK, position);
	}
	
	@Override
	public void acts(PlayableGameHero playableGameHero)
	{
		playableGameHero.addAction(new AddShurikenPackAction());
		markToDestroy();
	}


}
