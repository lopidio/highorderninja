package br.com.guigasgame.round.hud;

import org.jsfml.system.Vector2i;

public class RoundHudPositionerFactory
{
	public RoundHudPositioner factory(Vector2i viewSize)
	{
		return new RoundHudDefaultPositioner(viewSize);
	}
}
