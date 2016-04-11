package org.cytoscape.PModel.internal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.model.CyEdge;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.values.ArrowShape;
import org.cytoscape.work.TaskMonitor;
//This class is for me to demo the Swing function - confused why this works, but not CyActivator.
public class CtxMenuEdge /*extends AbstractEdgeViewTask*/ implements CyEdgeViewContextMenuFactory, ActionListener  {

	public CyMenuItem createMenuItem(CyNetworkView netView,View<CyEdge> edgeView) {
		JMenuItem MenuItem = new JMenuItem("Activation");
		MenuItem.addActionListener(this); //Note when changing NetArrow, change this as well
		CyMenuItem edgeMenu = new CyMenuItem(MenuItem, 1);
		return edgeMenu;
	}

		/*public CtxMenuEdge(View<CyEdge> edgeView, CyNetworkView netView) {
			super(edgeView, netView);
			// TODO Auto-generated constructor stub
		}

		public void run(TaskMonitor taskbp) throws Exception {
			edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE,  ArrowShapeVisualProperty.CIRCLE);
		}*/
	
	public void actionPerformed(ActionEvent e) {
		// Change to change network arrow.
		JOptionPane.showMessageDialog(null, "Work in Progress.");
		
	}
}