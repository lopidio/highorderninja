package br.com.guigasgame.box2d;

import java.io.IOException;
import java.nio.file.Paths;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import br.com.guigasgame.box2d.debug.SFMLDebugDraw;


/**
 * Hello world!
 *
 */
public class JsfmlBox2d
{

	/**
	 * We need this to easily convert between pixel and real-world coordinates
	 */
	private static float SCALE = 30;

	public static void main(String[] args) throws IOException
	{
		// the distance joint we are going to build on the fly during the game
		DistanceJoint hookJoint = null;

		/** Prepare the window */
		final RenderWindow window = new RenderWindow(
				new VideoMode(800, 600, 32), "Test");
		window.setFramerateLimit(60);

		/** Prepare the world */
		Vec2 gravity = new Vec2(0, (float) 9.8);

		World world = new World(gravity);
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(
				new OBBViewportTransform(), window);
		world.setDebugDraw(sfmlDebugDraw);
		sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);

		world.setContactListener(new ContactListener()
		{

			public void preSolve(Contact contact, Manifold oldManifold)
			{
				// TODO Auto-generated method stub

			}

			public void postSolve(Contact contact, ContactImpulse impulse)
			{
				// TODO Auto-generated method stub

			}

			public void endContact(Contact contact)
			{
				// TODO Auto-generated method stub

			}

			public void beginContact(Contact contact)
			{
				// TODO Auto-generated method stub

			}
		});

		Body lastBody = null;

		createGround(world, 400.f, 500.f);
		Body gancho = createGancho(world);

		/** Prepare textures */
		Texture groundTexture = new Texture();
		// groundTexture.
		Texture boxTexture = new Texture();
		boxTexture.loadFromFile(Paths.get("boxImage.jpg"));

		while (window.isOpen())
		{
			handleEvents(window);
			if (Mouse.isButtonPressed(Mouse.Button.LEFT))
			{
				int MouseX = Mouse.getPosition(window).x;
				int MouseY = Mouse.getPosition(window).y;
				lastBody = createBox(world, MouseX, MouseY);
			}

			// if (Mouse.isButtonPressed(Mouse.Button.RIGHT)) {
			// if (lastBody != null && gancho == null)
			// addHook(lastBody, world, gancho);
			// }
			world.step(1 / 60.f, 8, 3);

			window.clear();
			for( Body bodyIterator = world.getBodyList(); bodyIterator != null; bodyIterator = bodyIterator
					.getNext() )
			{
				if (bodyIterator.getType() == BodyType.DYNAMIC)
				{
					Sprite sprite = new Sprite();
					sprite.setTexture(boxTexture);
					sprite.setOrigin(16.f, 16.f);
					sprite.setPosition(SCALE * bodyIterator.getPosition().x,
							SCALE * bodyIterator.getPosition().y);
					sprite.setRotation((float) (bodyIterator.getAngle() * 180 / Math.PI));
					window.draw(sprite);
				}
				else
				{
					// Sprite groundSprite = new Sprite();
					// groundSprite.setTexture(groundTexture);
					// groundSprite.setOrigin(400.f, 8.f);
					//
					// groundSprite
					// .setPosition(SCALE * bodyIterator.getPosition().x, SCALE
					// * bodyIterator.getPosition().y);
					// groundSprite.setRotation((float) (bodyIterator.getAngle()
					// * 180 / Math.PI));
					// window.draw(groundSprite);
					// window.draw(groundSprite);
				}
			}

			// // if there is a distance joint...
			// if (hookJoint != null) {
			// // shorten its distance
			// hookJoint.setLength((float) (hookJoint.getLength() * 0.99));
			// }
			world.drawDebugData();
			window.display();
		}

	}

	static void handleEvents(RenderWindow renderWindow)
	{

		Iterable<Event> events = renderWindow.pollEvents();
		for( Event event : events )
		{

			if (event.type == Event.Type.KEY_PRESSED)
			{
				if (event.asKeyEvent().key != Keyboard.Key.ESCAPE) break;
				renderWindow.close();
			}
			if (event.type == Event.Type.CLOSED)
			{
				renderWindow.close();
			}
		}
	}

	static DistanceJoint addHook(Body body, World world, Body gancho)
	{
		// if there's a collision and the colliding game object has been tagged
		// as "Wall"...
		// adding a distance joint to the player
		DistanceJointDef hookJointDef = new DistanceJointDef();

		// connecting the distance joint to the object under the mouse
		// hookJointDef.collideConnected =
		// hit.collider.GetComponent<Rigidbody2D>;
		hookJointDef.bodyA = body;
		hookJointDef.bodyB = gancho;

		// calculating the distance between the player and the object under the
		// mouse
		float distance = body.getPosition().sub(gancho.getPosition()).length();

		// setting distance joint distance accordingly
		hookJointDef.length = distance;

		// objects connected by the joint can collide
		hookJointDef.collideConnected = true;

		DistanceJoint joint = (DistanceJoint) DistanceJoint.create(world,
				hookJointDef);
		return joint;
	}

	static Body createBox(World world, int MouseX, int MouseY)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = new Vec2(MouseX / SCALE, MouseY / SCALE);
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.fixedRotation = true;
		Body body = world.createBody(bodyDef);
		body.setUserData(null);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((32 / 2) / SCALE, (32 / 2) / SCALE);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.f;
		fixtureDef.friction = 0.7f;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
		return body;
	}

	static Body createGancho(World world)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = new Vec2(0 / SCALE, 0 / SCALE);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((800 / 2) / SCALE, (16 / 2) / SCALE);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
		return body;
	}

	static void createGround(World world, float X, float Y)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = new Vec2(X / SCALE, Y / SCALE);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((800 / 2) / SCALE, (16 / 2) / SCALE);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
	}

}
