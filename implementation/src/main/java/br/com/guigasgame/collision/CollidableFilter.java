package br.com.guigasgame.collision;


public class CollidableFilter 
{
	///What I am
	protected final CollidableCategory category;

	///What I collide with
	protected IntegerMask collider;

	public CollidableFilter(CollidableCategory collidableCategory, CollidableCategory collidesWith) {
		category = collidableCategory;
		collider = new IntegerMask(collidesWith.getValue());
	}

	public CollidableFilter clone() 
	{
		return new CollidableFilter(category, new CollidableCategory(collider.value));
	}
	
	public boolean matches(CollidableCategory collidableCategory) 
	{
		return collider.matches(collidableCategory.getValue());
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
