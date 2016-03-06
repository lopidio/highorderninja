package br.com.guigasgame.gameobject.item;

import br.com.guigasgame.updatable.UpdatableFromTime;


class GameItemCreator implements UpdatableFromTime
{

	private final float period;
	private float remainingSeconds;
	private boolean createTime;

	public GameItemCreator(float period)
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

	public void reset()
	{
		remainingSeconds = period;
		createTime = false;
	}

}
