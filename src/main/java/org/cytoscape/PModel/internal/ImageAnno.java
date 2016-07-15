package org.cytoscape.PModel.internal;

import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;
import org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer;
import org.cytoscape.PModel.internal.TexturePaintFactory;

public class ImageAnno implements ImageCustomGraphicLayer {
	
	private Rectangle2D bounds;
	private TexturePaintFactory pf;

	public ImageAnno(Rectangle2D bounds, TexturePaintFactory factory) {
		this.bounds = bounds;
		this.pf = factory;
	}

	public Rectangle2D getBounds2D() { return bounds; }

	// TODO: at some point, we should just bring all of the TexturePaintFactory
	// stuff into here....
	@Override
	public TexturePaint getPaint(Rectangle2D bounds) {
		return pf.getPaint(bounds);
	}

	@Override
	public CustomGraphicLayer transform(AffineTransform xform) {
		Shape s = xform.createTransformedShape(bounds);
		return new ImageAnno(s.getBounds2D(), pf);
	}
}