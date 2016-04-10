package br.com.guigasgame.gameobject.hero.playable;

import br.com.guigasgame.camera.Followable;

public interface FollowableListener
{
	void turnFollowingOff(Followable gameHero);
	void turnFollowingOn(Followable followable);
}
