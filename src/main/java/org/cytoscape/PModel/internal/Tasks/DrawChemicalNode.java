package org.cytoscape.PModel.internal.Tasks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class DrawChemicalNode extends AbstractTask {

	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	private CyNode cyNode;
	private CyNetwork network;
	String col = "shape";
	private VisualMappingManager vmm;
	//get the network
	//select the node, if node not selected, create the node
	
	public DrawChemicalNode(CyApplicationManager appMgr, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network, VisualMappingManager vmm) {
		this.applicationManager = appMgr;
		this.netView = netView;
		//this.nodeView = nodeView;
		this.registrar = registrar;
		this.network = network;
		this.vmm = vmm;
	}
		

	@Override
	public void run(TaskMonitor task) throws Exception {

		CyNetworkView netoView = applicationManager.getCurrentNetworkView();	
		List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true);
		
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

		VisualStyle style = vmm.getVisualStyle(netView);
		
		for (CyNode node : Nodes) {
			
			// draw circle for chemical node
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 10);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_WIDTH, 110.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 1.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 120);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 240);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_HEIGHT, 30.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_WIDTH, 30.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, new Color(0xFFFFFF, false));
			
			task.setStatusMessage("replacing with chemical node...");
		}
		
	}
	
	
}
