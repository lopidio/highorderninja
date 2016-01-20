package br.com.guigasgame.gamemachine;

import javax.xml.bind.JAXBException;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationDefinition;
import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.ForwardSide.Side;
import br.com.guigasgame.time.TimeMaster;

public class MainGameState implements GameStateMachine 
{
	World world;
	TimeMaster timeMaster;

    Animation animation;
	private Body body;

    public MainGameState() throws JAXBException 
    {
    	timeMaster = new TimeMaster();
        AnimationDefinition animationDefinition = AnimationDefinition.loadFromFile("AnimationDefinition.xml");
        animation = Animation.createAnimation(animationDefinition);
    
		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		createGround(new Vec2(15, 20), new Vec2(10, 3));
		createBox(new Vec2(15, 0));
		
		
		GameHero gameHero = new GameHero(Side.LEFT, new Vec2(10, 3));
		gameHero.attachBody(world.createBody(gameHero.getBodyDef()));
    }

    private void createGround(Vec2 position, Vec2 size) {
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

	private void createBox(Vec2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		body.setUserData(null);

		PolygonShape topShape = new PolygonShape();
		topShape.setAsBox(0.3f, 0.4f, new Vec2(0, -1.4f), 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10.f;
		fixtureDef.restitution = 0.3f;
		fixtureDef.friction = 0.7f;
		fixtureDef.shape = topShape;
		
		PolygonShape bottomShape = new PolygonShape();
		bottomShape.setAsBox(0.4f, 0.5f, new Vec2(0, -0.5f), 0);
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.density = 10.f;
		fixtureDef2.restitution = 0.3f;
		fixtureDef2.friction = 0.7f;
		fixtureDef2.shape = bottomShape;

		CircleShape feetShape = new CircleShape();
		feetShape.setRadius(0.5f);
		FixtureDef fixtureDef3 = new FixtureDef();
		fixtureDef3.restitution = 0.3f;
		fixtureDef3.shape = feetShape;

		PolygonShape bottomLeftShape = new PolygonShape();
		bottomLeftShape.setAsBox(0.01f, 0.5f, new Vec2(-0.5f - 0.01f, -0.5f), 0);
		FixtureDef fixtureDef4 = new FixtureDef();
		fixtureDef4.isSensor = true;
		fixtureDef4.shape = bottomLeftShape;

		
		body.createFixture(fixtureDef);
		body.createFixture(fixtureDef2);
		body.createFixture(fixtureDef3);
		body.createFixture(fixtureDef4);
	}

    @Override
    public void enterState(RenderWindow renderWindow)
    {
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(new OBBViewportTransform(), renderWindow);
		world.setDebugDraw(sfmlDebugDraw);
		sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
    }
    
	@Override
	public void update() {
		float deltaTime = timeMaster.getElapsedTime().asSeconds();
		world.step(1 / 60.f, 8, 3);

		animation.update(deltaTime);
		animation.setPosition(new Vector2f(20, 90));
		if (!body.isAwake())
		{
			System.out.println("Morreu");
			body.applyLinearImpulse(new Vec2(0, 50).mul(body.getMass()), body.getWorldCenter());
		}
	}

	@Override
	public void draw(RenderWindow renderWindow) {
		world.drawDebugData();
		renderWindow.draw(animation.getSprite());
	}

}
