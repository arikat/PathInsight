package org.cytoscape.PModel.internal.Tasks;

import java.util.List;
import java.util.Set;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.application.CyApplicationManager;

public class ActivationEdgeView extends AbstractTask {
	
	private CyNetworkView netView;
	private View<CyEdge> edgeView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URL";
	public String columnName = "setBool";
	private CyNetwork network;
	
	public ActivationEdgeView(CyServiceRegistrar registrar) {
		this.registrar = registrar;
	}
	
	
	public ActivationEdgeView(View<CyEdge> edgeView, CyNetworkView netView) {
		//super(nodeView, netView);
		this.netView = netView;
		this.edgeView = edgeView;
		//this.network = network;
		// TODO Auto-generated constructor stub
	}



	/*public ActivationNodeView(CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) { 
		this.network = network;
		this.registrar = registrar;
		this.netView = netView;
		
	}*/
	
	String two = "http://i.imgur.com/gBkmqwX.png";
	String one = "http://i.imgur.com/y1VbITV.png";
	String zero = "http://i.imgur.com/UfTJo4N.png";
	String negone = "http://i.imgur.com/fsEggSs.png";
	String negtwo = "http://i.imgur.com/qQ5JvTy.png";
	String plusplus = "http://i.imgur.com/mWmyPNl.png";
	String negneg = "http://i.imgur.com/MXvZ8rG.png";
	
	public void run(TaskMonitor monitor) {
		
		monitor.setTitle("Adding activation Node values");

		CyNetwork net = netView.getModel();
		CyTable edgeTable = net.getDefaultEdgeTable();
		
		if (edgeTable.getColumn(IMAGE_COLUMN) == null) {
			edgeTable.createColumn(IMAGE_COLUMN, String.class, false);
			monitor.setStatusMessage("Warning: creating column and setting values");
		}
		
		if (edgeTable.getColumn(columnName) == null) {
			edgeTable.createColumn(columnName, Integer.class, false);
			monitor.setStatusMessage("creating bool col");
		}

			List<CyEdge> Edges = CyTableUtil.getEdgesInState(net, "selected", true);
			monitor.setStatusMessage("selecting edges");

			for (CyEdge edge : Edges) {
				edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(1));
				netView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.CIRCLE);
			}
			
	} 
}