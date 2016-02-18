package br.com.guigasgame.collision;


public class CollidableFilter 
{
	///What I collide with
	private final static CollidableFilter sceneryCollidableFilter 					=  CollidableFilterManipulator.createFromCategory(CollidableCategory.sceneryCategory).collidesWithEveryThing();
	private final static CollidableFilter projectileCollidableFilter 				= CollidableFilterManipulator.createFromCategory(CollidableCategory.projectileCategory).collidesWith(CollidableCategory.herosCategory).and(CollidableCategory.sceneryCategory);

	public static CollidableFilter getPlayerFilter(int playerID)
	{
		return CollidableFilterManipulator.createFromCategory(CollidableCategory.getPlayerCategory(playerID)).collidesWith(CollidableCategory.projectileCategory).and(CollidableCategory.sceneryCategory).and(CollidableCategory.herosCategory);
	}

	public static CollidableFilter getProjectileCollidableFilter()
	{
		return projectileCollidableFilter.clone();
	}

	public static CollidableFilter getSceneryCollidablefilter()
	{
		return sceneryCollidableFilter.clone();
	}

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
