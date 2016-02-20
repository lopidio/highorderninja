package br.com.guigasgame.collision;

public class CollidableConstants 
{
	public enum Category
	{
		hero(true),
		scene,
		rope,
		projectile;
		
		CollidableCategory category;
		
		Category(boolean hero)
		{
			if (hero)
				category = getAllPlayerCategory();
			else
				category = getNextCategory();
		}

		Category()
		{
			this(false);
		}
		
		public static Category fromCollidableFilter(CollidableFilter collidableFilter)
		{
			for( Category category : Category.values() )
			{
				if (collidableFilter.getCategory() == category.category)
					return category;
			}
			return null;
		}
		
	}
	
	///What I am
	public final static CollidableCategory herosCategory		= CollidableConstants.getAllPlayerCategory();
	public final static CollidableCategory sceneryCategory 		= CollidableConstants.getNextCategory();
	public final static CollidableCategory projectileCategory	= CollidableConstants.getNextCategory();
	public final static CollidableCategory ropeBodyCategory		= CollidableConstants.getNextCategory();
	
	public static CollidableCategory getPlayerCategory(int playerID)
	{
		return herosCategory.matching(1 << (playerID - 1));
	}
	
	public static CollidableCategory getOtherPlayersCategory(int playerID)
	{
		return herosCategory.matching(~(1 << (playerID - 1)));
	}

	private static int categoriesUsed = 0; 
	private final static int NUM_MAX_PLAYERS = 4;
	
	public static CollidableCategory getAllPlayerCategory()
	{
		int playersMask = 1;
		for (int i = 0; i < NUM_MAX_PLAYERS - 1; ++i) 
		{
			playersMask = (playersMask << 1) + 1;
		}
		return new CollidableCategory(playersMask);
	}
	
	public static CollidableCategory getNextCategory()
	{
		CollidableCategory retorno = new CollidableCategory((1 << categoriesUsed) << NUM_MAX_PLAYERS );
		System.out.println(Integer.toBinaryString(retorno.getValue()));
		++categoriesUsed;
		return retorno;
	}	
	
	///What I collide with
	private final static CollidableFilter sceneryCollidableFilter		= CollidableFilterManipulator.createFromCategory(sceneryCategory).collidesWithEveryThing();
	
	private final static CollidableFilter projectileCollidableFilter	= CollidableFilterManipulator.createFromCategory(projectileCategory).
																						collidesWith(herosCategory).and(sceneryCategory).and(ropeBodyCategory);
	
	private final static CollidableFilter ropeBodyCollidableFilter 		= CollidableFilterManipulator.createFromCategory(ropeBodyCategory).
																						collidesWith(projectileCategory).and(sceneryCategory);

	public static CollidableFilter getPlayerFilter(int playerID)
	{
		return CollidableFilterManipulator.createFromCategory(getPlayerCategory(playerID)).collidesWith(projectileCategory).and(sceneryCategory).and(herosCategory).clone();
	}

	public static CollidableFilter getRopeBodyCollidableFilter()
	{
		return ropeBodyCollidableFilter.clone();
	}

	public static CollidableFilter getProjectileCollidableFilter()
	{
		return projectileCollidableFilter.clone();
	}

	public static CollidableFilter getSceneryCollidablefilter()
	{
		return sceneryCollidableFilter.clone();
	}
	
}
