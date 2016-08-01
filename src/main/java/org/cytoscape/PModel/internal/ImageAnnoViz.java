package org.cytoscape.PModel.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;

public class ImageAnnoViz extends AbstractCyAction {

	private CyApplicationManager applicationManager;
	private VisualMappingManager vmm;
	private VisualMappingFunctionFactory vmfFactoryP;
	private VisualLexicon lex;
	private VisualStyleFactory vsf;

	//public ImageAnnoViz(CyApplicationManager applicationManager, VisualMappingManager vmm, VisualMappingFunctionFactory vmfFactoryP, VisualLexicon lex) {
		
	public ImageAnnoViz(CyApplicationManager applicationManager, VisualMappingManager vmm, VisualStyleFactory vsf, VisualMappingFunctionFactory vmfFactoryP){
		super("PASSTHROUGH IMAGE ATTEMPT");
		setPreferredMenu("Apps.Aarya");
		this.applicationManager = applicationManager;
		this.vmm = vmm;
		this.vsf = vsf;
		this.vmfFactoryP = vmfFactoryP;
		this.lex = lex;

	}

	/** Invoked when an action occurs. */
	@Override
	public void actionPerformed(ActionEvent e) {
		CyNetworkView networkView = applicationManager.getCurrentNetworkView();

		VisualStyle style = vmm.getVisualStyle(networkView);
		String column = "/Green_Arrow_small.png";
		String property = "NODE_CUSTOMGRAPHICS_1";
		//CustomGraphicsFactory factory = new CustomGraphicsFactory();
		CyTable attrForTest = applicationManager.getCurrentNetwork().getDefaultNodeTable();
	//try {

			// Get the appropriate property
			VisualProperty cg1 = lex.lookup(CyNode.class, property);
			// Activate the mapper 
			PassthroughMapping pMapping = (PassthroughMapping) vmfFactoryP.createVisualMappingFunction(column, String.class, BasicVisualLexicon.NODE_LABEL);
			style.apply(networkView);
			networkView.updateView();
	//	} catch (Exception ex) {
		//	System.out.println("problems!" + ex.getMessage());
	//	}
		//System.out.println("done applying style of passthrough mapper");
	}
}
