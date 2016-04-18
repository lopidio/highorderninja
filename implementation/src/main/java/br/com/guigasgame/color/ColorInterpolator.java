package br.com.guigasgame.color;

import br.com.guigasgame.updatable.UpdatableFromTime;

public interface ColorInterpolator extends UpdatableFromTime
{
	ColorBlender getCurrentColor();
	void interpolateToColor(ColorBlender color, float duration);
	void interpolateFromColor(ColorBlender color, float duration);
	boolean hasFinished();
}
