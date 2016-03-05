package br.com.guigasgame.collision;

public class CollidableFilterManipulator 
{

	public static class CollidableFilterAddingCollision extends CollidableFilter
	{
		CollidableFilterAddingCollision(IntegerMask collidableCategory, IntegerMask collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		public CollidableFilterAddingCollision and(CollidableCategory category)
		{
			return new CollidableFilterAddingCollision(this.category, collider.set(category.getCategoryMask().value));
		}
		public CollidableFilterRemovingCollision except(CollidableCategory category)
		{
			return new CollidableFilterRemovingCollision(this.category, collider.clear(category.getCategoryMask().value));
		}
	}
	
	public static class CollidableFilterRemovingCollision extends CollidableFilter
	{
		CollidableFilterRemovingCollision(IntegerMask collidableCategory, IntegerMask collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		
		public CollidableFilterRemovingCollision and(CollidableCategory category)
		{
			return new CollidableFilterRemovingCollision(this.category, collider.clear(category.getCategoryMask().value));
		}		

		public CollidableFilterAddingCollision except(CollidableCategory category)
		{
			return new CollidableFilterAddingCollision(this.category, collider.set(category.getCategoryMask().value));
		}		

	}

}
