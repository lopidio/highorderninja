package br.com.guigasgame.scenery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SceneryFileJson {

	@SerializedName("rigidBodies")
	@Expose
	private List<RigidBody> rigidBodies = new ArrayList<RigidBody>();
	@SerializedName("dynamicObjects")
	@Expose
	private List<Object> dynamicObjects = new ArrayList<Object>();

	/**
	 * 
	 * @return The rigidBodies
	 */
	public List<RigidBody> getRigidBodies() {
		return rigidBodies;
	}

	/**
	 * 
	 * @param rigidBodies
	 *            The rigidBodies
	 */
	public void setRigidBodies(List<RigidBody> rigidBodies) {
		this.rigidBodies = rigidBodies;
	}

	/**
	 * 
	 * @return The dynamicObjects
	 */
	public List<Object> getDynamicObjects() {
		return dynamicObjects;
	}

	/**
	 * 
	 * @param dynamicObjects
	 *            The dynamicObjects
	 */
	public void setDynamicObjects(List<Object> dynamicObjects) {
		this.dynamicObjects = dynamicObjects;
	}
	
}
