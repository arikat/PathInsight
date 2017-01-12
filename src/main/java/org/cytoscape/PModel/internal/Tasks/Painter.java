package org.cytoscape.PModel.internal.Tasks;

import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class Painter extends AbstractTask {
//	private View<CyNode> nodeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URL";
	public static final String label = "Label";
	public static final String name = "name";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";
	private VisualMappingManager vmm;
	
	//private StyleTemp sbuild; //let's see if this works?
	private VisualStyle stylo = null;
	
	//note to self -- introduce stylo before style, see if the thing recognizes style and implements on top of that.

	public Painter(CyApplicationManager appMgr, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network, VisualMappingManager vmm) {
		this.applicationManager = appMgr;
		this.netView = netView;
		//this.nodeView = nodeView;
		this.registrar = registrar;
		this.network = network;
		this.vmm = vmm;
		//this.sbuild = sbuild;
		// this.edgeView = edgeView;
	}

	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle("Painting Image");
		taskMonitor.setStatusMessage("Painting image ...");
		
		CyNetworkView netoView = applicationManager.getCurrentNetworkView();
		
		String two = "http://i.imgur.com/gBkmqwX.png";
		String one = "http://i.imgur.com/y1VbITV.png";
		String zero = "http://i.imgur.com/UfTJo4N.png";
		String negone = "http://i.imgur.com/fsEggSs.png";
		String negtwo = "http://i.imgur.com/qQ5JvTy.png";
		String plusplus = "http://i.imgur.com/mWmyPNl.png";
		String negneg = "http://i.imgur.com/MXvZ8rG.png";

		// getRow(cyNode).set(IMAGE_COLUMN, imageString)  // netView.getModel()
		//CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, true);
			taskMonitor.setStatusMessage("Image URL column created...");
		}
		
		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
			taskMonitor.setStatusMessage("bool column created...");
		}
		
		if (nodeTable.getColumn(label) == null) {
			nodeTable.createColumn(label, Integer.class, true);
			taskMonitor.setStatusMessage("label column created...");
			
		}

		if (nodeTable.getColumn(columnName) != null) {
			
			VisualMappingManager vmm = registrar.getService(VisualMappingManager.class);

			Set<VisualLexicon> lexSet = vmm.getAllVisualLexicon();
			VisualProperty<?> cgProp = null;
			for (VisualLexicon vl : lexSet) {
				cgProp = vl.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");
				if (cgProp != null)
					break;
			}
			if (cgProp == null) {
				System.out.println("Can't find the CUSTOMGRAPHICS visual property!!!!");
				return;
			}

			VisualStyle style = vmm.getVisualStyle(netView); //make this null temporarily and fix painting or create new private VisualStyle stylo = null 

			String columnName = "bool";

			//List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
			List<CyNode> nodes = network.getNodeList();
			
			for (CyNode node : nodes) {		
				
				
				if (stylo == null) {
					
					taskMonitor.setStatusMessage("visual style added...");
					
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 10);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_WIDTH, 110.0);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 2.0);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 120);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 225);
					
					//remember to make stylo != null
			
				}
				
				
				if (nodeTable.getColumn(label) != null) {
					String john = network.getRow(node).get(label, String.class);
					network.getRow(node).set(name, john);
				}
				
				///WRITE CODE HERE LOOKING AT NEIGHBOR NODES - THEN GET THOSE ADJACENT EDGE LISTS - THEN REPEAT ONCE MORE FOR TERTIARY INTERACTIONS?
					 
				if (nodeTable.getRow(node.getSUID()).get(columnName, Integer.class) == null) {
					//print something or do something so this doesn't end in an error
					taskMonitor.setStatusMessage("Warning: null node - breaking loop");
					//break;
					network.getRow(node).set(columnName, Integer.valueOf(0));
					/*network.getRow(node).set(IMAGE_COLUMN, zero); //fixed original issue with only one writing - now fix overwriting arrows
					
					VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
							"(mapping.type=passthrough)");
					PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
							cgProp);
					
					style.addVisualMappingFunction(map);
					style.apply(netView);*/
					//nvm paint null on this node so no issues occur
				}
				
				
					if (network.getRow(node).get(columnName, Integer.class) == 1) { //change to integer if necessary
						// Create a passthrough mapping to that column
						//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
						network.getRow(node).set(IMAGE_COLUMN, one); //fixed original issue with only one writing - now fix overwriting arrows

						//style.apply(netView);
					}
						
					if (network.getRow(node).get(columnName, Integer.class) > 2) { //change to integer if necessary
						// Create a passthrough mapping to that column
						//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
						network.getRow(node).set(IMAGE_COLUMN, plusplus); //fixed original issue with only one writing - now fix overwriting arrows

						//style.apply(netView);
					}
					
						if (network.getRow(node).get(columnName, Integer.class) < -2) { //change to integer if necessary
							// Create a passthrough mapping to that column
							//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
							network.getRow(node).set(IMAGE_COLUMN, negneg); //fixed original issue with only one writing - now fix overwriting arrows

						}
					
					
					
						if (network.getRow(node).get(columnName, Integer.class) == 2) { //change to integer if necessary
							// Create a passthrough mapping to that column
							//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
							network.getRow(node).set(IMAGE_COLUMN, two); //fixed original issue with only one writing - now fix overwriting arrows
							//style.apply(netView);
						}
							
							if (network.getRow(node).get(columnName, Integer.class) == 0) { //change to integer if necessary
								// Create a passthrough mapping to that column
								//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
								network.getRow(node).set(IMAGE_COLUMN, zero); //fixed original issue with only one writing - now fix overwriting arrows
								//style.apply(netView);
							}
							
							if (network.getRow(node).get(columnName, Integer.class) == -1) { //change to integer if necessary
								// Create a passthrough mapping to that column
								//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
								network.getRow(node).set(IMAGE_COLUMN, negone); //fixed original issue with only one writing - now fix overwriting arrows
								//style.apply(netView);
							}
							
							if (network.getRow(node).get(columnName, Integer.class) == -2) { //change to integer if necessary
								// Create a passthrough mapping to that column
								//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
								network.getRow(node).set(IMAGE_COLUMN, negtwo); //fixed original issue with only one writing - now fix overwriting arrows
							}
							//style.apply(netView);
			}
			
			VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
					"(mapping.type=passthrough)");
			PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
					cgProp);
			
			style.addVisualMappingFunction(map);
			style.apply(netView);
			netView.updateView();
			
			//remember to add mini function to bypass passthrough mapping with my own -- currently it's created, but it won't replace existing mapping
		}
		
	}
}