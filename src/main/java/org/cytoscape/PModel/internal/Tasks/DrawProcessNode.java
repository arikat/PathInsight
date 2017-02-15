package org.cytoscape.PModel.internal.Tasks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.event.CyEventHelper;
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
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

public class DrawProcessNode extends AbstractTask {

	public static final String label = "Label";
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	private CyNode cyNode;
	private CyNetwork network;
	String col = "setShape";
	private VisualMappingManager vmm;
	
	public DrawProcessNode(CyApplicationManager appMgr, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network, VisualMappingManager vmm) {
		this.applicationManager = appMgr;
		this.netView = netView;
		//this.nodeView = nodeView;
		this.registrar = registrar;
		this.network = network;
		this.vmm = vmm;
	}
		

	@Override
	public void run(TaskMonitor task) {
		
		
		CyTable nodeTable = network.getDefaultNodeTable();
		if (nodeTable.getColumn(col) == null) {
			nodeTable.createColumn(col, String.class, false);
			task.setStatusMessage("shape column created...");
			
		}
		
		CyNetworkView netoView = applicationManager.getCurrentNetworkView();	
		
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
			
			CyNetwork net = netoView.getModel();
			final CyNode node = net.addNode();
			
			task.setStatusMessage("Node is created. Parameterizing...");
			
			if (nodeTable.getColumn(col) != null) {
				network.getRow(node).set(col, "process");
			}
			
			netoView.updateView();
			
			insertTasksAfterCurrentTask(new ParameterizeProcessNode());
			
	}
	
	public final class ParameterizeProcessNode extends AbstractTask {

		@Override
		public void run(TaskMonitor task) {
			List<CyNode> nodes = network.getNodeList();

			CyNetworkView netoView = applicationManager.getCurrentNetworkView();	
				
			CyTable nodeTable = network.getDefaultNodeTable();
				
			CharSequence hip = "process";
				
			for (CyNode node : nodes) {
					
			String lab = network.getRow(node).get(col, String.class);
				
				if (lab.contains(hip)) {
			
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 10);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_LABEL_WIDTH, 110.0);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 1.0);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 220);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 220);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_SIZE, 15.0);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.RECTANGLE);
					netoView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, new Color(0x654321, true));
			
					task.setStatusMessage("Process node parameterized.");
		
				}
			}	
		}	
	}
}