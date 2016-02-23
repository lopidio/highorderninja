package br.com.guigasgame.collision;



public class CollidableFilterManipulator 
{

	public static class CollidableFilterAddingCollision extends CollidableFilter
	{
		CollidableFilterAddingCollision(CollidableCategory collidableCategory, CollidableCategory collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		public CollidableFilterAddingCollision and(CollidableCategory collidableCategory)
		{
			return new CollidableFilterAddingCollision(this.category, new CollidableCategory(collider.set(collidableCategory.getValue()).value));
		}
		public CollidableFilterRemovingCollision except(CollidableCategory collidableCategory)
		{
			return new CollidableFilterRemovingCollision(this.category, new CollidableCategory(collider.clear(collidableCategory.getValue()).value));
		}
	}
	
	public static class CollidableFilterRemovingCollision extends CollidableFilter
	{
		CollidableFilterRemovingCollision(CollidableCategory collidableCategory, CollidableCategory collidesWith) 
		{
			super(collidableCategory, collidesWith);
		}
		
		public CollidableFilterRemovingCollision and(CollidableCategory collidableCategory)
		{
			return new CollidableFilterRemovingCollision(this.category, new CollidableCategory(collider.clear(collidableCategory.getValue()).value));
		}		

		public CollidableFilterAddingCollision except(CollidableCategory collidableCategory)
		{
			return new CollidableFilterAddingCollision(this.category, new CollidableCategory(collider.set(collidableCategory.getValue()).value));
		}		

	}

}
