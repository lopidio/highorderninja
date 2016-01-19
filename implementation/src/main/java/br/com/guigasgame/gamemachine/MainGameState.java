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
import org.jsfml.system.Vector2f;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationDefinition;
import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.time.TimeMaster;

public class MainGameState implements GameStateMachine 
{
	World world;
	TimeMaster timeMaster;

    Animation animation;

    public MainGameState() throws JAXBException 
    {
    	timeMaster = new TimeMaster();
        AnimationDefinition animationDefinition = AnimationDefinition.loadFromFile("AnimationDefinition.xml");
        animation = Animation.createAnimation(animationDefinition);
    
		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		createGround(new Vec2(15, 20), new Vec2(10, 3));
		createBox(new Vec2(15, 0), new Vec2(1, 1));
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

	private void createBox(Vec2 position, Vec2 size) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = position;
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.fixedRotation = true;
		Body body = world.createBody(bodyDef);
		body.setUserData(null);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(size.x, size.y);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 2.f;
		fixtureDef.restitution = 0.6f;
		fixtureDef.friction = 0.7f;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
	}

    @Override
    public void enterState(RenderWindow renderWindow)
    {
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(new OBBViewportTransform(), renderWindow);
		world.setDebugDraw(sfmlDebugDraw);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
//		sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
    }
    
	@Override
	public void update() {
		float deltaTime = timeMaster.getElapsedTime().asSeconds();
		world.step(1 / 60.f, 8, 3);

		animation.update(deltaTime);
		animation.setPosition(new Vector2f(20, 90));
	}

	@Override
	public void draw(RenderWindow renderWindow) {
		world.drawDebugData();
		renderWindow.draw(animation.getSprite());
	}

}
