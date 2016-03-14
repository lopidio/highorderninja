package br.com.guigasgame.round.hud.moving;

import org.jsfml.system.Vector2f;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributeListener;
import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class HeroAttributeMovingHud implements HeroAttributeListener, Drawable, UpdatableFromTime
{
	public abstract void updatePosition(Vector2f position);

}
