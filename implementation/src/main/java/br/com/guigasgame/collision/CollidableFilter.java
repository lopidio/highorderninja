package br.com.guigasgame.collision;

import org.jbox2d.dynamics.Filter;


public class CollidableFilter
{
	
	public static class Category
	{
		public int value;
		Category(int value)
		{
			this.value = value;
		}
		public Category matching(int value)
		{
			return new Category(this.value & value);
		}
		public CollidableFilter collidesWith(Category category)
		{
			return new CollidableFilter(this).and(category);
		}
		public CollidableFilter collidesWithEverything()
		{
			return new CollidableFilter(this).and(new Category(-1));
		}
	}

	///What I am
	private Category category;
	///What I collide with
	private final int maskValue;
	
	public CollidableFilter(Category category, int maskValue)
	{
		this.maskValue = maskValue;
		this.category = category;
	}
	
	public CollidableFilter(int maskValue)
	{
		this.maskValue = maskValue;
		this.category = new Category(0);
	}
	
	private CollidableFilter(Category category)
	{
		this.maskValue = 0;
		this.category = category;
	}
	
	public CollidableFilter(Filter filter)
	{
		category = new Category(filter.categoryBits);
		maskValue = filter.maskBits;
	}

	public CollidableFilter and(Category category) 
	{
		return new CollidableFilter(this.category, maskValue | category.value);
	}
	
	public CollidableFilter except(Category category)
	{
		return new CollidableFilter(this.category, maskValue & ~category.value);
	}
	
	public CollidableFilter matching(Category category)
	{
		return new CollidableFilter(maskValue & category.value);
	}
	
	public boolean matches(Category category)
	{
		return this.matching(category).value() > 0;
	}
	
	public int value()
	{
		return maskValue;
	}

	public Filter toFilter() 
	{
		Filter filter = new Filter();
		filter.categoryBits = category.value;
		filter.maskBits = maskValue;
		return filter;
	}

	public Category getCategory() 
	{
		return category;
	}

	public int getMaskValue() 
	{
		return maskValue;
	}
}
