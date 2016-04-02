package br.com.guigasgame.gameobject.projectile.rope;


class NinjaRopeScaler
{
	public static final float MIN_LINK_SIZE = 0.01f;
	private static final float SIZE_CHANGE_PER_SECOND = 30f;
	private final float maxSize;
	private float currentSize;
	private boolean markToShorten;
	private boolean markToIncrease;

	public NinjaRopeScaler(float currentSize, float maxSize)
	{
		this.maxSize = maxSize;
		this.currentSize = currentSize;
		System.out.println("Scaler: " + currentSize + "; " + maxSize);
	}
	
	public boolean verifySizeChange(float deltaTime)
	{
		float prevSize = currentSize;
		if (markToIncrease)
		{
			increase(deltaTime * SIZE_CHANGE_PER_SECOND);
		}
		else if (markToShorten)
		{
			shorten(deltaTime * SIZE_CHANGE_PER_SECOND);
		}
		return prevSize != currentSize;
	}

	private void increase(float increaseSize)
	{
		markToIncrease = false;
		setCurrentSize(currentSize + increaseSize);
	}

	private void shorten(float shortenSize)
	{
		markToShorten = false;
		setCurrentSize(currentSize - shortenSize);
	}

	public void shorten()
	{
		markToShorten = true;
	}

	public void increase()
	{
		markToIncrease = true;
	}

	public float getCurrentSize()
	{
		return currentSize;
	}
	
	public float getMaxSize()
	{
		return maxSize;
	}

	public void setCurrentSize(float newSize)
	{
		if (newSize > maxSize)
			newSize = maxSize;
		else if (newSize < MIN_LINK_SIZE)
			newSize = MIN_LINK_SIZE;
		currentSize = newSize;
	}
	
}
