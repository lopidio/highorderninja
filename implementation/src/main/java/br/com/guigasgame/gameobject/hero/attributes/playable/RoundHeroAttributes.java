package br.com.guigasgame.gameobject.hero.attributes.playable;

public class RoundHeroAttributes 
{
	protected int life;
	protected int numShurikens;
	protected int numSmokeBomb;
	protected float shurikenTimeInterval;
	protected float smokeBombTimeInterval;
	
	public RoundHeroAttributes(int life, int numShurikens, int numSmokeBomb, float shurikenTimeInterval, float smokeBombTimeInterval) 
	{
		this.life = life;
		this.numShurikens = numShurikens;
		this.numSmokeBomb = numSmokeBomb;
		this.shurikenTimeInterval = shurikenTimeInterval;
		this.smokeBombTimeInterval = smokeBombTimeInterval;
	}

	private RoundHeroAttributes(RoundHeroAttributes other) 
	{
		this.life = other.life;
		this.numShurikens = other.numShurikens;
		this.numSmokeBomb = other.numSmokeBomb;
		this.shurikenTimeInterval = other.shurikenTimeInterval;
		this.smokeBombTimeInterval = other.smokeBombTimeInterval;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public int getNumShurikens()
	{
		return numShurikens;
	}
	
	public int getNumSmokeBomb()
	{
		return numSmokeBomb;
	}

	public float getShurikenTimeInterval() 
	{
		return shurikenTimeInterval;
	}

	public float getSmokeBombTimeInterval() 
	{
		return smokeBombTimeInterval;
	}

	public void setLife(int life) 
	{
		this.life = life;
	}

	public void setNumShurikens(int numShurikens) 
	{
		this.numShurikens = numShurikens;
	}

	public void setNumSmokeBomb(int numSmokeBomb) 
	{
		this.numSmokeBomb = numSmokeBomb;
	}

	public void setShurikenTimeInterval(float shurikenTimeInterval) 
	{
		this.shurikenTimeInterval = shurikenTimeInterval;
	}

	public void setSmokeBombTimeInterval(float smokeBombTimeInterval) 
	{
		this.smokeBombTimeInterval = smokeBombTimeInterval;
	}
	
	@Override
	protected RoundHeroAttributes clone()
	{
		return new RoundHeroAttributes(this);
	}
	
}
