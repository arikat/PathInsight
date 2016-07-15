package org.cytoscape.PModel.internal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;

public class CustomGraphics implements CyCustomGraphics<ImageAnnotationNode> {

	protected Long id = null;
	protected float fitRatio = 1.0f;
	protected String displayName;
	protected int width = 30;
	protected int height = 30;
	
	public static final String CENTER = "cy_center";
	public static final String RADIUS = "cy_radius";
	
	private BufferedImage renderedImg;
	private volatile boolean dirty = true;
	
	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return displayName;
	}

	@Override
	public float getFitRatio() {
		// TODO Auto-generated method stub
		return fitRatio;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	@Override
	public Long getIdentifier() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public List<ImageAnnotationNode> getLayers(CyNetworkView networkView, View<? extends CyIdentifiable> nodeView) {
		CyNetwork network = networkView.getModel();
		if (!(nodeView.getModel() instanceof CyNode))
			return null;

		return Collections.singletonList(new ImageAnnotationNode());
	}

	@Override
	public Image getRenderedImage() {
		if (dirty) {
			updateRendereredImage();
			dirty = false;
		}
		
		return renderedImg;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	@Override
	public void setDisplayName(final String displayName) {
		// TODO Auto-generated method stub
		this.displayName = displayName;
		
	}

	@Override
	public void setFitRatio(float fitRatio) {
		this.fitRatio = fitRatio;
		
	}

	@Override
	public void setHeight(final int height) {
		this.height = height;
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIdentifier(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
		
	}

	@Override
	public void setWidth(final int width) {
	
		this.width = width;
		// TODO Auto-generated method stub
		
	}
	
	public Class<?> getSettingType(final String key) {
		if (key.equalsIgnoreCase(CENTER)) return Point2D.class;
		if (key.equalsIgnoreCase(RADIUS)) return Float.class;
		
		return getSettingType(key);
	}

	@Override
	public String toSerializableString() {
		// TODO Auto-generated method stub
		return displayName;
	}
	
	private ImageAnnotationNode createLayer() {
		ImageAnnotationNode layer = null;
		layer = new ImageAnnotationNode();
		
		return layer;
	}
	
	private void updateRendereredImage() {
		final ImageAnnotationNode layer = createLayer();
		
		if (layer != null) {
			// Create a rectangle and fill it with our current paint
			Rectangle rect = layer.getBounds2D().getBounds();
			rect = new Rectangle(rect.x, rect.y, 100, 100);
			final Shape shape = new Ellipse2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
			renderedImg = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = renderedImg.createGraphics();
			g2d.setPaint(layer.getPaint(rect));
			g2d.fill(shape);
		} else {
			renderedImg = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
		}
	}

}
