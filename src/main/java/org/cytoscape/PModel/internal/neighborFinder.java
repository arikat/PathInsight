package org.cytoscape.PModel.internal;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;

public class neighborFinder extends AbstractCyAction { //prev abstractTask
	
	private CyApplicationManager applicationManager;
	private static final long serialVersionUID = 1L;
	private VisualLexicon lexicon;
	public neighborFinder(CyApplicationManager appManager, VisualLexicon lexicon) {
		super("Draw Images on Nodes");
        setPreferredMenu("Apps.Aarya"); //Check if correct
        

        this.applicationManager = appManager;
        this.lexicon = lexicon;
	}

	public String columnName = "bool";
	//public neighborFinder(CyNetwork network) {
		//this.network = network;
	//}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		
		CyNetwork network = applicationManager.getCurrentNetwork();
        CyNetworkView networkView = applicationManager.getCurrentNetworkView();
        
        View<CyNode> nodeView = null;
		if (network == null) {
			System.out.println("There is no network.");
			return;
		}
		
		VisualProperty Green = lexicon.lookup(CyNode.class, "Green_Arrow_Up.png");
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();

		if (edgeTable.getColumn(columnName) == null) {
			System.out.println("There is no column bool");
		} else if (edgeTable.getColumn(columnName) != null) {
			List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true); //change selected to all later.
			CyEdge.Type Incoming = null;
			for (CyNode node : Nodes) {
				List<CyEdge> Edges = network.getAdjacentEdgeList(node, Incoming);
				for (CyEdge edge : Edges) {
					if (network.getRow(edge).get(columnName, int.class) == 1) {
						//ImageIcon icons = new ImageIcon(getClass().getResource("Green_Arrow_Up.png"));
						node.setVisualProperty(BasicVisualLexicon.NETWORK_BACKGROUND_PAINT, Color.green);
						//nodeView.setVisualProperty(Green, icons);
						System.out.println("Green Arrow on Node.");
						//nodeView.setLockedValue();
					}
				}
			}

		}
	}

}
