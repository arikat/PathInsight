package org.cytoscape.PModel.internal;

import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TexturePaintFactory implements PaintFactory {
	
	private BufferedImage imagine;
	
	public TexturePaintFactory(final BufferedImage imagine) {
		this.imagine = imagine;
	}

	@Override
	public TexturePaint getPaint(Rectangle2D bound) {
		return new TexturePaint(imagine, bound);
	}

	public BufferedImage getImage() {
		return imagine;
	}
}