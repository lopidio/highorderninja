package br.com.guigasgame.scenery;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Circle {

	@SerializedName("cx")
	@Expose
	private Double cx;
	@SerializedName("cy")
	@Expose
	private Double cy;
	@SerializedName("r")
	@Expose
	private Double r;

	/**
	 * 
	 * @return The cx
	 */
	public Double getCx() {
		return cx;
	}

	/**
	 * 
	 * @param cx
	 *            The cx
	 */
	public void setCx(Double cx) {
		this.cx = cx;
	}

	/**
	 * 
	 * @return The cy
	 */
	public Double getCy() {
		return cy;
	}

	/**
	 * 
	 * @param cy
	 *            The cy
	 */
	public void setCy(Double cy) {
		this.cy = cy;
	}

	/**
	 * 
	 * @return The r
	 */
	public Double getR() {
		return r;
	}

	/**
	 * 
	 * @param r
	 *            The r
	 */
	public void setR(Double r) {
		this.r = r;
	}

}
