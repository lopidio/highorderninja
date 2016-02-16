package br.com.guigasgame.collision;


public class CollidableConstants 
{
	///What I am
	private final static CollidableCategory herosCategory		= CollidableCategory.getAllPlayerCategory();
	private final static CollidableCategory sceneryCategory 	= CollidableCategory.getNextCategory();
	private final static CollidableCategory projectileCategory	= CollidableCategory.getNextCategory();
	
	///What I collide with
	public final static CollidableLanguage sceneryCollidableFilter 		= CollidableLanguage.categoryCollider(sceneryCategory).collidesWithEveryThing();
	public final static CollidableLanguage projectileCollidableFilter 	= CollidableLanguage.categoryCollider(projectileCategory).collidesWith(herosCategory).and(sceneryCategory);
	
	public static CollidableLanguage getPlayerCategory(int playerID)
	{
		return CollidableLanguage.categoryCollider(herosCategory.matching(1 << playerID)).collidesWith(sceneryCategory);		
	}
	
	
}
