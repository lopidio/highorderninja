package br.com.guigasgame.gameobject.hero.state;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
final class StateProperties {
	@XmlAttribute
	public final boolean canShoot;
	@XmlAttribute
	public final boolean canJump;
	@XmlAttribute
	public final boolean canUseRope;
	@XmlElement
	public final float maxSpeedX;
	@XmlElement
	public final float maxSpeedY;
	@XmlElement
	public final float horizontalAcceleration;
	@XmlElement
	public final float jumpAcceleration;

	public StateProperties(boolean canShoot, boolean canJump, boolean canUseRope, float maxSpeedX, float maxSpeedY,
			float horizontalAcceleration, float jumpAcceleration) {
		super();
		this.canShoot = canShoot;
		this.canJump = canJump;
		this.canUseRope = canUseRope;
		this.maxSpeedX = maxSpeedX;
		this.maxSpeedY = maxSpeedY;
		this.horizontalAcceleration = horizontalAcceleration;
		this.jumpAcceleration = jumpAcceleration;
	}

	public StateProperties() {
		this.canShoot = true;
		this.canJump = true;
		this.canUseRope = true;
		this.maxSpeedX = 0;
		this.maxSpeedY = 0;
		this.horizontalAcceleration = 0;
		this.jumpAcceleration = 0;
	}
	
	
}
