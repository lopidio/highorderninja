package br.com.guigasgame.round.hud.controller;

import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class HeroAttributesHudController implements Drawable, UpdatableFromTime, Destroyable
{
	public abstract void addAsHudController(RoundHeroAttributes roundHeroAttributes);
}
