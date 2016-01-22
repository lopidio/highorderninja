package br.com.guigasgame.gamemachine;

import javax.xml.bind.JAXBException;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.time.TimeMaster;


public class MainGameState implements GameState
{

	World world;
	TimeMaster timeMaster;
	GameHero gameHero;

	public MainGameState() throws JAXBException
	{
		timeMaster = new TimeMaster();

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		createGround(new Vec2(15, 20), new Vec2(10, 3));

		gameHero = new GameHero(1);
	}

	private void createGround(Vec2 position, Vec2 size)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(size.x, size.y);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);

	}

	@Override
	public void load()
	{
		gameHero.load();
		gameHero.attachBody(world, new Vec2(10,5));
	}
	
	
	@Override
	public void enterState(RenderWindow renderWindow)
	{
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(
				new OBBViewportTransform(), renderWindow);
		world.setDebugDraw(sfmlDebugDraw);
		sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
		
	}

	@Override
	public void update()
	{
		float deltaTime = timeMaster.getElapsedTime().asSeconds();
		world.step(1 / 60.f, 8, 3);
		world.clearForces();

		gameHero.update(deltaTime);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		world.drawDebugData();
		renderWindow.draw(gameHero.getSprite());
	}

}
