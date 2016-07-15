package org.cytoscape.PModel.internal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class CustomGraphics extends AbstractTask implements CyCustomGraphics<ImageAnnotationNode> {
	
	private static final String ImageFile = "/Green_Arrow_small.png";
	protected static BufferedImage Image;
	private static final String TAG = "bitmap image";
	private static final float FIT_RATIO = 1.0f;
	private CyNetwork network;
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
	protected Long id = null;
	protected float fitRatio = 1.0f;
	protected String displayName;
	protected int width = 30;
	protected int height = 30;
	
	public static final String CENTER = "cy_center";
	public static final String RADIUS = "cy_radius";
	
	private BufferedImage renderedImg;
	private volatile boolean dirty = true;
	
	public CustomGraphics(CyNetwork network) {
		this.network = network;
	}
	
	
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

	@Override
	public void run(TaskMonitor arg0) throws Exception {
			CyNetwork network = applicationManager.getCurrentNetwork();
			CyNetworkView networkView = applicationManager.getCurrentNetworkView(); //NOTE CALL THE NETWORK VIEW

			if (network == null) {
				System.out.println("There is no network.");
				return;
			}
			
			CyTable edgeTable = network.getDefaultEdgeTable();
			CyTable nodeTable = network.getDefaultNodeTable();

			if (edgeTable.getColumn(columnName) == null) {
				System.out.println("There is no column bool");
				
			} else if (edgeTable.getColumn(columnName) != null) {
				
				List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true); // change to all
				
				CyEdge.Type Incoming = null;
				for (CyNode node : Nodes) {
					List<CyEdge> Edges = network.getAdjacentEdgeList(node, Incoming);
					for (CyEdge edge : Edges) {
						if (network.getRow(edge).get(columnName, int.class) == 1) {
							createImage(ImageFile); //possible issue as createImage does not directly call image?
							buildImage(Image);
							
								}
						
						}
					}
				}
		
	}
	
	//builds the image
	
	private void buildImage(BufferedImage imagine) {
		//final ImageAnnotationNode layer = createLayer();
		
		//layer.clear();

		Rectangle2D bound = null;
		int width = imagine.getWidth();
		int height = imagine.getHeight();

		bound = new Rectangle2D.Double(width, height, width, height);
		final TexturePaintFactory paintFactory = new TexturePaintFactory(imagine);

		ImageAnno cg = new ImageAnno(bound, paintFactory);
		
		//layer.add();
		
	}

	
	//reads image location for calling to allow building of annotation
	private void createImage(String image) throws MalformedURLException {
		if (image == null)
			throw new IllegalStateException("URL string cannot be null.");

		URL imageLocation = new URL(image);
		
		sourceUrl = imageLocation;
		try {
			originalImage = ImageIO.read(imageLocation);
		} catch (IOException e) {
			originalImage = Image;
		}

		if (originalImage == null) {
			originalImage = Image;
		}
		
	}

}
