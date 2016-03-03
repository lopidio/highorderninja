package br.com.guigasgame.scenery;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Origin {

	@SerializedName("x")
	@Expose
	private Integer x;
	@SerializedName("y")
	@Expose
	private Integer y;

	/**
	 * 
	 * @return The x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * 
	 * @param x
	 *            The x
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * 
	 * @return The y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * 
	 * @param y
	 *            The y
	 */
	public void setY(Integer y) {
		this.y = y;
	}

}
