package br.com.guigasgame.collision;

import org.jbox2d.dynamics.Filter;

public class CollidableFilter 
{
	///What I collide with
	private final static CollidableFilter sceneryCollidableFilter 		= CollidableFilter.categoryCollider(CollidableCategory.sceneryCategory).collidesWithEveryThing();
	private final static CollidableFilter projectileCollidableFilter 	= CollidableFilter.categoryCollider(CollidableCategory.projectileCategory).collidesWith(CollidableCategory.herosCategory).and(CollidableCategory.sceneryCategory);

	public static CollidableFilter getPlayerFilter(int playerID)
	{
		return CollidableFilter.categoryCollider(CollidableCategory.getPlayerCategory(playerID)).collidesWith(CollidableCategory.projectileCategory).and(CollidableCategory.sceneryCategory).and(CollidableCategory.herosCategory);
	}

	
	public static CollidableFilter getProjectileCollidableFilter()
	{
		return projectileCollidableFilter.clone();
	}

	public static CollidableFilter getSceneryCollidablefilter()
	{
		return sceneryCollidableFilter.clone();
	}

	public static class ColliderDefinitionStart
	{
		private CollidableCategory collidableCategory;
		private ColliderDefinitionStart(CollidableCategory collidableCategory)
		{
			this.collidableCategory = collidableCategory;
		}
		
		public CollidableFilter collidesWith(CollidableCategory collidableCategory)
		{
			return new CollidableFilter(this.collidableCategory, collidableCategory);
		}

		public CollidableFilter collidesWithEveryThing()
		{
			return new CollidableFilter(this.collidableCategory, new CollidableCategory(new IntegerMask().setAll().value));
		}

		public CollidableFilter collidesWithNothing()
		{
			return new CollidableFilter(this.collidableCategory, new CollidableCategory(0));
		}
	}
	
	///What I am
	private final CollidableCategory category;

	///What I collide with
	private IntegerMask collider;

	public CollidableFilter(CollidableCategory collidableCategory, CollidableCategory collidesWith) {
		category = collidableCategory;
		collider = new IntegerMask(collidesWith.getValue());
	}

	public CollidableFilter(Filter filter) 
	{
		category = new CollidableCategory(filter.categoryBits);
		collider = new IntegerMask(filter.maskBits);
	}
	
	public CollidableFilter clone() 
	{
		return new CollidableFilter(category, new CollidableCategory(collider.value));
	}
	public static ColliderDefinitionStart categoryCollider(CollidableCategory collidableCategory)
	{
		return new ColliderDefinitionStart(collidableCategory);
	}
	
	public CollidableFilter and(CollidableCategory collidableCategory) 
	{
		collider = collider.set(collidableCategory.getValue());
		return this;
	}
	
	public CollidableFilter except(CollidableCategory collidableCategory) 
	{
		collider = collider.clear(collidableCategory.getValue());
		return this;
	}
	
	public boolean matches(CollidableCategory collidableCategory) 
	{
		return collider.matches(collidableCategory.getValue());
	}
	
	public Filter toFilter()
	{
		Filter filter = new Filter();
		filter.categoryBits = category.getValue();
		filter.maskBits = collider.value;
		return filter;
	}

	
	public CollidableCategory getCategory()
	{
		return category;
	}

	
	public IntegerMask getCollider()
	{
		return collider;
	}
	
}
