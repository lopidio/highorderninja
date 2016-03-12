package br.com.guigasgame.gameobject.item;

import br.com.guigasgame.updatable.UpdatableFromTime;


class GameItemCreatorTimeCounter implements UpdatableFromTime
{

	private final float period;
	private float remainingSeconds;
	private boolean createTime;

	public GameItemCreatorTimeCounter(float period)
	{
		this.period = period;
		remainingSeconds = period;
		createTime = false;
	}

	@Override
	public void update(float deltaTime)
	{
		remainingSeconds -= deltaTime;
		if (remainingSeconds <= 0)
			createTime = true;
	}

	public boolean isTimeToCreate()
	{
		return createTime;
	}

	public void resetCounter()
	{
		remainingSeconds = period;
		createTime = false;
	}

}
