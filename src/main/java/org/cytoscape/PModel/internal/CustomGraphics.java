package org.cytoscape.PModel.internal;

import java.awt.Image;
import java.util.Collections;
import java.util.List;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;

import org.cytoscape.PModel.internal.CustomGraphicsManager;

public class CustomGraphics implements CyCustomGraphics<ImageAnno> {
	CustomGraphicsManager manager;
	String input;
	Long id = null;
	float fitRatio = 1.0f;
	List<ImageAnno> layers;
	String displayName;
	int width = 50;
	int height = 50;
	BufferedImage bi = null;

	public CustomGraphics(CustomGraphicsManager manager, String input) {
		this.manager = manager;
		this.input = input;
		/*if (input != null && input.length() > 0) {
			if (input.startsWith("data:")) {
				input = input.substring(input.indexOf(","));
			}
			byte[] byteStream = Base64.decodeBase64(input);
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(byteStream);
				bi = ImageIO.read(bis);
				bis.close();
			} catch (Exception e) { 
				bi = null;
				e.printStackTrace(); 
			}
		}*/
	}

	public Long getIdentifier() { return id; }

	public void setIdentifier(Long id) { this.id = id; }

	public void setWidth(final int width) { this.width = width; }
 	public void setHeight(final int height) { this.height = height; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public List<ImageAnno> getLayers(CyNetworkView networkView, View<? extends CyIdentifiable> nodeView) { 
		if (manager.showImage())
			return Collections.singletonList(new ImageAnno(manager, bi)); 
		else
			return Collections.singletonList(new ImageAnno(manager, null)); 
	}
	public String getDisplayName() { return displayName; }
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}
	public float getFitRatio() { return fitRatio; }
	public void setFitRatio(float fitRatio) { this.fitRatio = fitRatio; }
	public String toString() {
		if (displayName != null)
			return displayName;
		return "String Custom Graphics";
	}

	public Image getRenderedImage() {return null;}
	public String toSerializableString() {return null;}


}



/*
import java.awt.Image;
import java.util.Collections;
import java.util.List;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

//import org.apache.commons.codec.binary.Base64;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;

public class CustomGraphics implements CyCustomGraphics<ImageAnno> {
	//CustomGraphicsManager manager;
	String input;
	Long id = null;
	float fitRatio = 1.0f;
	List<ImageAnno> layers;
	String displayName;
	int width = 50;
	int height = 50;
	BufferedImage bi = null;

	public CustomGraphics(CustomGraphicsManager manager, String input) {
		this.input = input;
		/*if (input != null && input.length() > 0) {
			if (input.startsWith("data:")) {
				input = input.substring(input.indexOf(","));
			}
			byte[] byteStream = Base64.decodeBase64(input);
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(byteStream);
				bi = ImageIO.read(bis);
				bis.close();
			} catch (Exception e) { 
				bi = null;
				e.printStackTrace(); 
			}
		}
	}

	public Long getIdentifier() { return id; }

	public void setIdentifier(Long id) { this.id = id; }

	public void setWidth(final int width) { this.width = width; }
 	public void setHeight(final int height) { this.height = height; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public List<ImageAnno> getLayers(CyNetworkView networkView, View<? extends CyIdentifiable> nodeView) {
		return layers; 
		
		//if (manager.showImage())
			//return Collections.singletonList(new ImageAnno(manager, bi)); 
		//else
			//return Collections.singletonList(new ImageAnno(manager, null)); 
	}
	public String getDisplayName() { return displayName; }
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}
	public float getFitRatio() { return fitRatio; }
	public void setFitRatio(float fitRatio) { this.fitRatio = fitRatio; }
	public String toString() {
		if (displayName != null)
			return displayName;
		return "String Custom Graphics";
	}

	public Image getRenderedImage() {return null;}
	public String toSerializableString() {return null;}


}*/




















//*******************************//

/*
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
import java.util.ArrayList;
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

public class CustomGraphics implements CyCustomGraphics<ImageAnno> {

//<ImageCustomGraphicLayer> extends GraphicsFig {


//implements CyCustomGraphics<ImageAnno> { //prev ImageAnnotationNode extends abstract task
	
	/*private static final String ImageFile = "/Green_Arrow_small.png";
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
	private URL url;
	//private URL sourceUrl;	
	private Long identifier;
	protected Long id = null;
	private float ratio;
	protected float fitRatio = 1.0f;
	protected String displayName;
	protected int width = 30;
	protected int height = 30;
	
	public static final String CENTER = "cy_center";
	public static final String RADIUS = "cy_radius";
	
	private BufferedImage renderedImg;
	private volatile boolean dirty = true;
	

    private URL url;
    private Long identifier;
    private String displayName;
    private int width;
    private int height;
    private float ratio;
	//private static final String IMAGEFILE = "images/no_image.png";
	//protected static BufferedImage Image;
		
		public CustomGraphics(URL url) {
        this.url = url;
		}
	
	    @Override
	    public List<ImageAnno> getLayers(CyNetworkView networkView, View view) {
	        List<ImageAnno> list = new ArrayList<ImageAnno>();
	        BufferedImage image = this.getRenderedImage();
	        int x = 0-image.getWidth()/2;
	        int y = 0-image.getHeight()/2;
	        ImageAnno micgl = new ImageAnno(image,x,y);
	        micgl.convertBufferedImageToRectangle();
	        list.add(micgl);

	        return list;
	    }

	    @Override
	    public Long getIdentifier() {
	        return this.identifier;
	    }

	    
	    @Override
	    public void setIdentifier(Long id) {
	        this.identifier = id;
	    }

	    
	    @Override
	    public String getDisplayName() {
	        return this.displayName;
	    }

	    @Override
	    public void setDisplayName(String displayName) {
	        this.displayName = displayName;
	    }

	    
	    @Override
	    public String toSerializableString() {
	        return this.toString();
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
	    public void setWidth(int width) {
	        this.width = width;
	    }

	    @Override
	    public void setHeight(int height) {
	        this.height = height;
	    }

	    @Override
	    public float getFitRatio() {
	        return this.ratio;
	    }

	    
	    @Override
	    public void setFitRatio(float ratio) {
	        this.ratio = ratio;
	    }

	    
	    @Override
	    public BufferedImage getRenderedImage() {
	        BufferedImage nodeImage = null;
	        
	        url =  getClass().getClassLoader().getResource("/Green_Arrow_small.png");
	        
	        try {
	            nodeImage = ImageIO.read(url);
	        } catch (IOException ioe) {
	            System.err.println("ERROR:  Can't make image from url: "+url);
	        }

	        return nodeImage;
	    }
		
		
		
		
		
		
		
		
		/*
		static  {
			try {
				Image =ImageIO.read(CustomGraphics.class
						.getClassLoader().getResource(ImageFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		
		public CustomGraphics(Long id, String url) throws IOException {
			super(id, url);
			fitRatio = FIT_RATIO;
			
			// Special case.  We include a number of images as part of our bundles.  The
			// resulting URL's are not really helpful, so we need to massage the displayName
			// here.
			if (displayName.startsWith("bundle:")) {
				int index = displayName.lastIndexOf("/");
				displayName = displayName.substring(index+1);
			}
			// System.out.println("URLImageCustomGraphics: "+displayName+"("+id+") = "+url.toString());

			tags.add(TAG);
			createImage(url);
			buildCustomGraphics(originalImage);
		}


		/**
		 * 
		 * @param name
		 *            - display name of this object. NOT UNIQUE!
		 * @param img
		 
		public CustomGraphics(Long id, String name, BufferedImage img) {
			super(id, name);
			
			if (img == null)
				throw new IllegalArgumentException("Image cannot be null.");

			fitRatio = FIT_RATIO;
			
			if (displayName.startsWith("bundle:")) {
				int index = displayName.lastIndexOf("/");
				displayName = displayName.substring(index+1);
			}

			// System.out.println("URLImageCustomGraphics: "+displayName+"("+id+")");

			// System.out.println("URLImageCustomGraphics: name = "+name);
			tags.add(TAG);
			this.originalImage = img;
			buildCustomGraphics(originalImage);
		}

		private void buildCustomGraphics(BufferedImage targetImg) {
			layers.clear();

			Rectangle2D bound = null;
			width = targetImg.getWidth();
			height = targetImg.getHeight();

			bound = new Rectangle2D.Double(-width / 2, -height / 2, width, height);
			final TexturePaintFactory paintFactory = new TexturePaintFactory(targetImg);

			ImageAnno cg = new ImageAnno(bound, paintFactory);
			
			layers.add(cg);
		}

		private void createImage(String url) throws MalformedURLException {
			if (url == null)
				throw new IllegalStateException("URL string cannot be null.");

			URL imageLocation = new URL(url);
			
			sourceUrl = imageLocation;
			try {
				originalImage = ImageIO.read(imageLocation);
			} catch (IOException e) {
				originalImage = DEF_IMAGE;
			}

			if (originalImage == null) {
				originalImage = DEF_IMAGE;
			}
		}

		@Override
		public Image getRenderedImage() {
			
			if (width == originalImage.getWidth() && height == originalImage.getHeight()) {
				return originalImage;
			}
			
			if(scaledImage == null) {
				resizeImage(width, height);
			} else if (scaledImage.getWidth() != width || scaledImage.getHeight() != height) {
				resizeImage(width, height);
			} 
			
			return scaledImage;
		}

		
		
		private Image resizeImage(int width, int height) {
			final Image img = originalImage.getScaledInstance(width, height,
					Image.SCALE_AREA_AVERAGING);
			try {
				scaledImage = ImageUtil.toBufferedImage(img);
			} catch (InterruptedException e) {
				// Could not get scaled one
				e.printStackTrace();
				return originalImage;
			}
			buildCustomGraphics(scaledImage);
			return scaledImage;
		}

		public Image resetImage() {
			if (scaledImage != null) {
				scaledImage.flush();
				scaledImage = null;
			}
			buildCustomGraphics(originalImage);
			return originalImage;
		}
		

		public URL getSourceURL() {
			return this.sourceUrl;
		}

		@Override
		public String toSerializableString() {
			if (sourceUrl != null)
				return makeSerializableString(sourceUrl.toString());

			return makeSerializableString(displayName);
		}

		@Override
		public String toString() {
			if (this.sourceUrl == null && displayName == null) {
				return "Empty image";
			} else if (this.sourceUrl != null && !this.sourceUrl.toString().startsWith("bundle")) {
				return "Image: "+this.sourceUrl.toString();
			} else {
				return "Image: "+displayName;
			}
		}*/
	
	
	
	
	
	
	
	
	
	
	
	/*
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
	public List<ImageAnno> getLayers(CyNetworkView networkView, View<? extends CyIdentifiable> nodeView) {
		CyNetwork network = networkView.getModel();
		if (!(nodeView.getModel() instanceof CyNode))
			return null;

		return Collections.singletonList(new ImageAnno(null, null)); //change if poss...
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
	
	private ImageAnno createLayer() {
		ImageAnno layer = null;
		layer = new ImageAnno(null, null);
		
		return layer;
	}
	
	private void updateRendereredImage() {
		final ImageAnno layer = createLayer();
		
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
							System.out.println("Working");
							
							//createImage(ImageFile); //possible issue as createImage does not directly call image?
							//buildImage(Image);
							
								}
						
						}
					}
				}
		
	}
	
	//builds the image
	
	private void buildImage(BufferedImage imagine) {
		//final ImageAnno layer = createLayer();
		
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
	/*private void createImage(String image) throws MalformedURLException {
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
		
	}*/


