package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Shape {

	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("vertices")
	@Expose
	private List<Vertex> vertices = new ArrayList<Vertex>();

	/**
	 * 
	 * @return The type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The vertices
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * 
	 * @param vertices
	 *            The vertices
	 */
	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}

}
