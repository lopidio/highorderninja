package br.com.guigasgame.collision;

import org.jbox2d.dynamics.Filter;

public class CollidableLanguage 
{
	public static class ColliderDefinitionStart
	{
		private CollidableCategory collidableCategory;
		private ColliderDefinitionStart(CollidableCategory collidableCategory)
		{
			this.collidableCategory = collidableCategory;
		}
		
		public CollidableLanguage collidesWith(CollidableCategory collidableCategory)
		{
			return new CollidableLanguage(this.collidableCategory, collidableCategory);
		}

		public CollidableLanguage collidesWithEveryThing()
		{
			return new CollidableLanguage(this.collidableCategory, new CollidableCategory(new IntegerMask().setAll().value));
		}

		public CollidableLanguage collidesWithNothing()
		{
			return new CollidableLanguage(this.collidableCategory, new CollidableCategory(0));
		}
	}
	
	///What I am
	private final CollidableCategory category;

	///What I collide with
	private IntegerMask collider;

	public CollidableLanguage(CollidableCategory collidableCategory, CollidableCategory collidesWith) {
		category = collidableCategory;
		collider = new IntegerMask(collidesWith.getValue());
	}

	public CollidableLanguage(Filter collidableFilter) {
		// TODO Auto-generated constructor stub
	}
	public static ColliderDefinitionStart categoryCollider(CollidableCategory collidableCategory)
	{
		return new ColliderDefinitionStart(collidableCategory);
	}
	
	public CollidableLanguage and(CollidableCategory collidableCategory) 
	{
		collider = collider.set(collidableCategory.getValue());
		return this;
	}
	
	public CollidableLanguage except(CollidableCategory collidableCategory) 
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
	
}
