package br.com.guigasgame.gameobject.hero.state;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import br.com.guigasgame.math.Vector2;

@XmlAccessorType(XmlAccessType.FIELD)
final class HeroStatesProperties {
	@XmlAttribute
	public final boolean canShoot;
	@XmlAttribute
	public final boolean canJump;
	@XmlAttribute
	public final boolean canUseRope;
	@XmlElement
	public final Vector2 maxSpeed;
	@XmlElement
	public final float horizontalAcceleration;
	@XmlElement
	public final float jumpImpulse;

	public HeroStatesProperties(boolean canShoot, boolean canJump, boolean canUseRope, Vector2 maxSpeed,
			float horizontalAcceleration, float jumpImpulse) {
		super();
		this.canShoot = canShoot;
		this.canJump = canJump;
		this.canUseRope = canUseRope;
		this.maxSpeed = maxSpeed;
		this.horizontalAcceleration = horizontalAcceleration;
		this.jumpImpulse = jumpImpulse;
	}

	public HeroStatesProperties() {
		this.canShoot = true;
		this.canJump = true;
		this.canUseRope = true;
		this.maxSpeed = new Vector2();
		this.horizontalAcceleration = 0;
		this.jumpImpulse = 0;
	}
	
	
}
