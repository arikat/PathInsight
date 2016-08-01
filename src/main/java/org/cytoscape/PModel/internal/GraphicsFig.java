package org.cytoscape.PModel.internal;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.cytoscape.model.CyIdentifiable;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;

public abstract class GraphicsFig<T extends CustomGraphicLayer> implements CyCustomGraphics<T>, Taggable 
{
	protected static final String DELIMITER = ",";
	public static final String LIST_DELIMITER = "|";
	
	protected float fitRatio = 0.9f;

	// Unique ID
	protected final Long id;
	
	// Layers of Ding Custom Graphic objects.
	protected List<T> layers;
	
	// Human readable name
	protected String displayName;
	
	protected int width = 50;
	protected int height = 50;
	
	protected CyCustomGraphicsFactory factory;

	//protected ObjectPosition position;

	// For tags
	protected final SortedSet<String> tags;
	
	
	/**
	 * Create new object for a given ID.
	 * Used when restoring session.
	 * 
	 * @param id
	 * @param displayName
	 */
	public GraphicsFig(final Long id, final String displayName) {
		this.id = id;
		
		this.layers = new ArrayList<T>();
		this.displayName = displayName;

		this.tags = new TreeSet<String>();
		//this.position = new ObjectPositionImpl();
	}
	
	@Override
	public Long getIdentifier() {
		return id;
	}
	
	@Override
	public void setIdentifier(Long id) {
		// For our uses, the id is always assigned in our constructor
	}
	
	@Override
	public void setWidth(final int width) {
		this.width = width;
	}
	
	@Override
	public void setHeight(final int height) {
		this.height = height;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}

	@Override	
	public List<T> getLayers(CyNetworkView networkView, View<? extends CyIdentifiable> graphObject) {
		return layers;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	@Override abstract public Image getRenderedImage();
	@Override abstract public String toString();

	@Override
	public Collection<String> getTags() {
		return tags;
	}

	@Override
	abstract public String toSerializableString();

	// This will be used prop file.
	protected String makeSerializableString(String name) {
		String tagStr = "";
		// Build tags as a string
		if (tags.size() != 0) {
			final StringBuilder builder = new StringBuilder();
			for (String tag : tags)
				builder.append(tag + LIST_DELIMITER);
			String temp = builder.toString();
			tagStr = temp.substring(0, temp.length() - 1);
		}

		if (name == null) {
			name = displayName;
		}
		if (name.contains(",")) {
			// Replace delimiter
			name = name.replace(",", "___");
		}

		return getTypeName() + DELIMITER + this.getIdentifier() + DELIMITER + name + DELIMITER + tagStr;
	}
	
	protected String getTypeName() {
		return this.getClass().getCanonicalName();
	}
	
	@Override
	public void setFitRatio(float fitRatio) {
		this.fitRatio = fitRatio;
	}
	
	@Override
	public float getFitRatio() {
		return fitRatio;
	}

}