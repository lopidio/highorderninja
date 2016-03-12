package br.com.guigasgame.round.hud;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class HeroAttributesHudController implements Drawable, UpdatableFromTime
{
	public abstract void addAsHudController(RoundHeroAttributes roundHeroAttributes);
}
