package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RigidBody {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("imagePath")
	@Expose
	private String imagePath;
	@SerializedName("origin")
	@Expose
	private Origin origin;
	@SerializedName("polygons")
	@Expose
	private List<List<Polygon>> polygons = new ArrayList<List<Polygon>>();
	@SerializedName("circles")
	@Expose
	private List<Circle> circles = new ArrayList<Circle>();
	@SerializedName("shapes")
	@Expose
	private List<Shape> shapes = new ArrayList<Shape>();

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * 
	 * @param imagePath
	 *            The imagePath
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * 
	 * @return The origin
	 */
	public Origin getOrigin() {
		return origin;
	}

	/**
	 * 
	 * @param origin
	 *            The origin
	 */
	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	/**
	 * 
	 * @return The polygons
	 */
	public List<List<Polygon>> getPolygons() {
		return polygons;
	}

	/**
	 * 
	 * @param polygons
	 *            The polygons
	 */
	public void setPolygons(List<List<Polygon>> polygons) {
		this.polygons = polygons;
	}

	/**
	 * 
	 * @return The circles
	 */
	public List<Circle> getCircles() {
		return circles;
	}

	/**
	 * 
	 * @param circles
	 *            The circles
	 */
	public void setCircles(List<Circle> circles) {
		this.circles = circles;
	}

	/**
	 * 
	 * @return The shapes
	 */
	public List<Shape> getShapes() {
		return shapes;
	}

	/**
	 * 
	 * @param shapes
	 *            The shapes
	 */
	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}

}
