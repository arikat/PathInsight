package org.cytoscape.PModel.internal;

import java.awt.Color;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.cytoscape.view.model.CyNetworkView;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class ImageAnnotationNode extends AbstractNodeViewTask { // implements
														// ImageCustomGraphicLayer
		
    private static final long serialVersionUID = 1L;
    private CyApplicationManager applicationManager;
    private VisualLexicon lexicon;
    private VisualMappingManager vmm;
    private VisualStyleFactory vsf;
    private VisualMappingFunctionFactory vmfFactoryP;
	//private View<CyNode> nodeView;
	//private CyNetworkView netView;
	private CyNode cyNode;
	private CyNetwork network;
	private CyNetworkView networkView;

    public ImageAnnotationNode(View<CyNode> nodeView, CyNetworkView netView){ //VisualMappingManager vmm, VisualStyleFactory vsf, VisualMappingFunctionFactory vmfFactoryP
       super(nodeView, netView);
    	
    	// super("Drowsy");
       // setPreferredMenu("Apps.Aarya");
//passthrough attempt
       // this.applicationManager = appManager;
       // this.lexicon = lexicon;
    }

    public void run(TaskMonitor tasky) throws Exception {
    	//CyNetwork network = applicationManager.getCurrentNetwork();
    	//List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
    	//List<CyNode> nodes = network.getNodeList();
    	//for (CyNode node : nodes) {
    		nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.BLUE);
    //	}
    	//nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.BLUE);
    	// CyNetwork network = applicationManager.getCurrentNetwork();
       // CyNetworkView networkView = applicationManager.getCurrentNetworkView();
       // VisualStyle style = vmm.getVisualStyle(networkView);
        
      //  List<CyNode> nodes = network.getNodeList();
		//List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);

       // VisualProperty<?> cg1 = lexicon.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");

        URL url = null;
        String urlLocation = "/Green_Arrow_small.png";
      //  View<CyNode> nodeView;
        //CustomGraphicsFactory factory = new CustomGraphicsFactory();

        //.for(CyNode cyNode : nodes) {
           // nodeView = networkView.getNodeView(cyNode);
            //get a particular node "column" to generate the node url
            //urlLocation = applicationManager.getCurrentNetwork().getRow(cyNode).get("/Green_Arrow_small.png", String.class);
             //  try {
            		//CustomGraphics mcg = factory.getInstance(url);
            		//VisualProperty<CyCustomGraphics> cg1 = (VisualProperty<CyCustomGraphics>) lexicon.lookup(CyNode.class,"NODE_CUSTOMGRAPHICS_1");
            		//VisualMappingFunction<String, CyCustomGraphics> map = vmfFactoryP.createVisualMappingFunction(urlLocation, String.class, cg1);
                    //PassthroughMapping<String, CyCustomGraphics> map = (PassthroughMapping) vmfFactoryP.createVisualMappingFunction(urlLocation, String.class, cg1);
                    //style.addVisualMappingFunction(map);
					//style.apply(networkView);
					//networkView.updateView();
               // } catch (Exception ex) {
                    //System.err.println("Problems setting the custom graphics! ");
              // }
     //   }
    }
}
	
	
/*
	private static final long serialVersionUID = 1L;
	private CyApplicationManager applicationManager;
	private VisualLexicon lexicon;
	public String columnName = "bool";
	private View<CyNode> nodeView;
	private CyNetworkView netView;
	private CyNode cyNode;
	private CyNetwork network;
	private CyNetworkView networkView;

	public ImageAnnotationNode(CyNetwork network) {
		this.network = network;
	}

	@Override
	public void run(TaskMonitor tasky) throws Exception {
		//CyNetwork network = applicationManager.getCurrentNetwork();
		//CyNetworkView networkView = applicationManager.getCurrentNetworkView();
		this.nodeView = nodeView;
		this.netView = netView;
		nodeView = networkView.getNodeView(cyNode);

		List<CyNode> nodes = network.getNodeList();

		VisualProperty customGraphicsVP = lexicon.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");

		URL url = null;
		//String urlLocation;
		String urlLocation = "/Green_Arrow_small.png";
		CustomGraphicsFactory factory = new CustomGraphicsFactory();

		if (network == null) {
			System.out.println("There is no network.");
			return;
		}

		// VisualProperty Green = lexicon.lookup(CyNode.class,
		// "Green_Arrow_Up.png");
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();

		if (edgeTable.getColumn(columnName) == null) {
			System.out.println("There is no column bool");

		} else if (edgeTable.getColumn(columnName) != null) {
			List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true); // change
																							// to
																							// all
			CyEdge.Type Incoming = null;

			for (CyNode node : Nodes) {
				List<CyEdge> Edges = network.getAdjacentEdgeList(node, Incoming);
				for (CyEdge edge : Edges) {
					if (network.getRow(edge).get(columnName, int.class) == 1) {
						try {
			                url = new URL(urlLocation);
			            }
			            catch (MalformedURLException mue) {
			                System.err.println("Can't retrieve image from this url! "+urlLocation+"\n\n"+mue.getStackTrace());
			            }


			            if(url != null) {
			                try {
			                    CustomGraphics mcg = factory.getInstance(url);
			                    nodeView.setVisualProperty(customGraphicsVP,mcg);
			                } catch (Exception ex) {
			                    System.err.println("\n\n\nProblems setting the custom graphics! "+ex.getCause()+":"+ex.getMessage());
			                    StackTraceElement[] elements = ex.getStackTrace();

			                    for(int i=0; i<elements.length; i++) {
			                        System.out.println(elements[i]);
			                    }
			                }
					}
				}
			}
		}
	}
}
}*/

/*
 * private static final long serialVersionUID = 1L; private CyApplicationManager
 * applicationManager; private VisualLexicon lexicon;
 * 
 * public ImageAnnotationNode(CyApplicationManager appManager, VisualLexicon
 * lexicon){ super("Draw"); setPreferredMenu("Apps.Aarya");
 * 
 * this.applicationManager = appManager; this.lexicon = lexicon; }
 * 
 * public void actionPerformed(ActionEvent e) { CyNetwork network =
 * applicationManager.getCurrentNetwork(); CyNetworkView networkView =
 * applicationManager.getCurrentNetworkView();
 * 
 * List<CyNode> nodes = network.getNodeList();
 * 
 * VisualProperty customGraphicsVP = lexicon.lookup(CyNode.class,
 * "NODE_CUSTOMGRAPHICS_1");
 * 
 * URL url = null; String urlLocation; // String urlLocation =
 * "/Green_Arrow_small.png"; View<CyNode> nodeView; CustomGraphicsFactory
 * factory = new CustomGraphicsFactory();
 * 
 * for(CyNode cyNode : nodes) { nodeView = networkView.getNodeView(cyNode);
 * //get a particular node "column" to generate the node url urlLocation =
 * applicationManager.getCurrentNetwork().getRow(cyNode).get("Image URL",
 * String.class); try { url = new URL(urlLocation); } catch
 * (MalformedURLException mue) {
 * System.err.println("Can't retrieve image from this url! "+urlLocation+"\n\n"+
 * mue.getStackTrace()); }
 * 
 * 
 * if(url != null) { try { CustomGraphics mcg = factory.getInstance(url);
 * nodeView.setVisualProperty(customGraphicsVP,mcg); } catch (Exception ex) {
 * System.err.println("\n\n\nProblems setting the custom graphics! "+ex.getCause
 * ()+":"+ex.getMessage()); StackTraceElement[] elements = ex.getStackTrace();
 * 
 * for(int i=0; i<elements.length; i++) { System.out.println(elements[i]); } } }
 * } } }
 */
