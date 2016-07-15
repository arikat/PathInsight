package org.cytoscape.PModel.internal;

import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;
import org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class ImageAnnotationNode implements ImageCustomGraphicLayer {
	
	private static final String ImageFile = "/Green_Arrow_small.png";
	protected static BufferedImage Image;
	private static final String TAG = "bitmap image";
	private static final float FIT_RATIO = 1.0f;
	
	private View<CyNode> nodeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private static final long serialVersionUID = 1L;
	private VisualLexicon lexicon;
	private CySwingApplication desktopApp;
	private ImageCustomGraphicLayer icg;
	public String columnName = "bool";
	private BufferedImage originalImage;
	private BufferedImage scaledImage;

	private URL sourceUrl;	
	
	protected Rectangle2D bounds = null;

	static  {
		try {
			Image = ImageIO.read(ImageAnnotationNode.class
					.getClassLoader().getResource(ImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return bounds;
	}

	public CustomGraphicLayer transform(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public TexturePaint getPaint(Rectangle2D bounds) {
		
		return null;
	}

}
