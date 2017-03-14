package org.cytoscape.PModel.internal.Tasks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Set;

import org.cytoscape.PModel.internal.Tasks.DrawProcessNode.ParameterizeProcessNode;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
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
	String col = "setShape";
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
	public void run(TaskMonitor task) {
		
		CyNetworkView netoView = applicationManager.getCurrentNetworkView();	
		List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true);
		CyTable nodeTable = network.getDefaultNodeTable();
		CharSequence hip = "process";
		List<CyNode> nodes = network.getNodeList();
		
		if (nodeTable.getColumn(col) == null) {
			nodeTable.createColumn(col, String.class, false);
			task.setStatusMessage("shape column created...");
		}
		
		if (Nodes == null) {
			insertTasksAfterCurrentTask(new CreateChem());
			task.setStatusMessage("creating chemical node...");
			//need to put something here to prevent it from creating a node unnecessarily
		}
		
		if (Nodes != null) {
			for (CyNode node : Nodes) {
			
			// draw circle for chemical node
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 10);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_WIDTH, 110.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 1.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 120);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 240);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, new Color(0x000000, true));
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_HEIGHT, 30.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_WIDTH, 30.0);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
			netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, new Color(0xFFFFFF, false));
			
			task.setStatusMessage("replacing with chemical node...");
			
			if (nodeTable.getColumn(col) != null) {
				network.getRow(node).set(col, "chem");
			}
			
			}
		}
		
	}
	
	public final class CreateChem extends AbstractTask {
		
		public CreateChem() {
		}
		
		@Override
		public void run(TaskMonitor tasky) {
				
			CyNetworkView netoView = applicationManager.getCurrentNetworkView();	
			List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", false);
			CyTable nodeTable = network.getDefaultNodeTable();
			CharSequence hip = "process";
			List<CyNode> nodes = network.getNodeList();
			
			//currently no nodes selected no if statement	
			
				//if (Nodes == null) { //may need to remove
				CyNetwork net = netoView.getModel();
				final CyNode node = net.addNode();
				
				if (nodeTable.getColumn(col) != null) {
					network.getRow(node).set(col, "chem");
				}
			
				netoView.updateView();
				
				insertTasksAfterCurrentTask(new ParameterizeChemNode());
			
		//	}
		
}

public final class ParameterizeChemNode extends AbstractTask {

	@Override
	public void run(TaskMonitor task) {
		
		CyNetworkView netoView = applicationManager.getCurrentNetworkView();	
		List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true);
		CyTable nodeTable = network.getDefaultNodeTable();
		CharSequence hip = "chem";
		List<CyNode> nodes = network.getNodeList();
		
		for (CyNode node : nodes) {
			
			String lab = network.getRow(node).get(col, String.class);
				
				if (lab.contains(hip)) {
			
		// draw circle for chemical node
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 10);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_WIDTH, 110.0);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 1.0);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 120);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 240);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, new Color(0x000000, true));
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_HEIGHT, 30.0);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_WIDTH, 30.0);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
		netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, new Color(0xFFFFFF, false));
		
		task.setStatusMessage("painting chemical node...");
	
			}
		}	
	}	
}
		
	}
	
	
}
