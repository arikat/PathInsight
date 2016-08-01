package org.cytoscape.PModel.internal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;
import org.cytoscape.view.presentation.customgraphics.Cy2DGraphicLayer;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import org.cytoscape.PModel.internal.CustomGraphicsManager;

public class ImageAnno implements Cy2DGraphicLayer {
	CustomGraphicsManager manager;
	Rectangle2D bounds;
	BufferedImage image;

	public ImageAnno(CustomGraphicsManager manager, BufferedImage image) {
		this.manager = manager;
		this.image = image;
		if (image != null)
			bounds = new Rectangle2D.Double(0.0, 0.0, (double)image.getWidth(), (double)image.getHeight());
		else
			bounds = new Rectangle2D.Double(0.0, 0.0, 150, 150);
	}

	public void draw(Graphics2D g, Shape shape,
	                 CyNetworkView networkView, View<? extends CyIdentifiable> view) {
		if (! (view.getModel() instanceof CyNode) ) return;
		CyNetwork network = networkView.getModel();

		Paint fill = view.getVisualProperty(BasicVisualLexicon.NODE_FILL_COLOR);
		Paint background = networkView.getVisualProperty(BasicVisualLexicon.NETWORK_BACKGROUND_PAINT);
		boolean selected = false;
		if (network.getRow(view.getModel()).get(CyNetwork.SELECTED, Boolean.class))
			selected = true;
		DrawSphere ds = new DrawSphere((Color)fill, (Color)background, image, shape, selected);
		ds.draw(g, bounds);
	}

	public Rectangle2D getBounds2D() { return bounds; }

	public Paint getPaint(Rectangle2D bounds) { return null; }

	public CustomGraphicLayer transform(AffineTransform xform) { 
		final Shape s = xform.createTransformedShape(bounds);
		bounds = s.getBounds2D();

		return this;
	}

}












//**********//

/*import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;
import org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer;
import org.cytoscape.PModel.internal.TexturePaintFactory;

public class ImageAnno implements ImageCustomGraphicLayer {

	//private BufferedImage bufferedImage;
	private Rectangle2D rectangle2D;
	private int x;
	private int y;
	
	BufferedImage img = null;
	URL imageURL = null;


	public ImageAnno(BufferedImage img, int x, int y) {
		this.img = img;
		this.x = x;
		this.y = y;
		
		try {
			imageURL =  getClass().getClassLoader().getResource("/Green_Arrow_small.png"); 
				
			if(imageURL != null){
				img = ImageIO.read(imageURL);
			}
				
			} catch (IOException e) {
				System.out.println(e);
			}
	}

	public BufferedImage getBufferedImage() {
		return img;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.img = img;
	}

	public void convertBufferedImageToRectangle() {
		rectangle2D = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
	}

	@Override
	public Rectangle2D getBounds2D() {
		return this.rectangle2D;
	}

	@Override
	public TexturePaint getPaint(Rectangle2D rectangle2D) {
		return new TexturePaint(getBufferedImage(),
				new Rectangle(this.x, this.y, getBufferedImage().getWidth(), getBufferedImage().getHeight()));
	}

	@Override
	public CustomGraphicLayer transform(AffineTransform affineTransform) {
		return this;
	}
}*/