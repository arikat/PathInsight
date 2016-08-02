package org.cytoscape.PModel.internal;

import java.awt.Color;
import java.util.List;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualPropertyDependency;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;

import org.cytoscape.PModel.internal.CustomGraphicsManager;

public class ViewUtils {
	public static String STYLE_NAME = "STRING style";

	public static CyNetworkView styleNetwork(CustomGraphicsManager manager, CyNetwork network) {
		// First, let's get a network view
		CyNetworkView netView = manager.createNetworkView(network);
		VisualStyle stringStyle = createStyle(manager, network);

		updateColorMap(manager, stringStyle, netView);

		VisualMappingManager vmm = manager.getService(VisualMappingManager.class);
		vmm.setVisualStyle(stringStyle, netView);
		vmm.setCurrentVisualStyle(stringStyle);
		manager.getService(CyNetworkViewManager.class).addNetworkView(netView);
		manager.getService(CyApplicationManager.class).setCurrentNetworkView(netView);
		
		return netView;
	}

	public static void reapplyStyle(CustomGraphicsManager manager, CyNetworkView view) {
		VisualMappingManager vmm = manager.getService(VisualMappingManager.class);
		VisualStyle style = vmm.getVisualStyle(view);
		style.apply(view);
	}

	public static void updateNodeStyle(CustomGraphicsManager manager, 
	                                   CyNetworkView view, List<CyNode> nodes) {
		manager.flushEvents();
		VisualMappingManager vmm = manager.getService(VisualMappingManager.class);
		VisualStyle style = vmm.getVisualStyle(view);
		for (CyNode node: nodes) {
			style.apply(view.getModel().getRow(node), view.getNodeView(node));
		}
		// style.apply(view);
	}

	public static void updateEdgeStyle(CustomGraphicsManager manager, CyNetworkView view, List<CyEdge> edges) {
		manager.flushEvents();
		VisualMappingManager vmm = manager.getService(VisualMappingManager.class);
		VisualStyle style = vmm.getVisualStyle(view);
		for (CyEdge edge: edges) {
			style.apply(view.getModel().getRow(edge), view.getEdgeView(edge));
		}
		// style.apply(view);
	}

	public static VisualStyle createStyle(CustomGraphicsManager manager, CyNetwork network) {
		String networkName = manager.getNetworkName(network);
		String styleName = STYLE_NAME;
		if (networkName.startsWith("String Network")) {
			String[] parts = networkName.split("_");
			if (parts.length == 1) {
				String[] parts2 = networkName.split(" - ");
				if (parts2.length == 2)
					styleName = STYLE_NAME+" - "+parts2[1];
			} else if (parts.length == 2)
				styleName = STYLE_NAME+"_"+parts[1];
		}


		VisualMappingManager vmm = manager.getService(VisualMappingManager.class);
		for (VisualStyle style: vmm.getAllVisualStyles()) {
			if (style.getTitle().equals(styleName)) {
				return style;
			}
		}

		VisualStyleFactory vsf = manager.getService(VisualStyleFactory.class);

		VisualStyle stringStyle = vsf.createVisualStyle(vmm.getCurrentVisualStyle());
		stringStyle.setTitle(styleName);

		// Set the default node size
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_SIZE, 45.0);

		// Set the shape to an ellipse
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);

		// And set the color to white
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.WHITE);

		// And set the edge color to blue
		stringStyle.setDefaultValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, new Color(31,41,61));

		// And set the label color to black
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.BLACK);

		// And set the node border width to zero
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 0.0);

		// And set the label color to black
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 10);

		// Lock node width and height
		for(VisualPropertyDependency<?> vpd: stringStyle.getAllVisualPropertyDependencies()) {
			if (vpd.getIdString().equals("nodeSizeLocked"))
				vpd.setDependency(true);
		}

		// Set up the passthrough mapping for the glass style
		VisualMappingFunctionFactory passthroughFactory = 
		                 manager.getService(VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");
		VisualLexicon lex = manager.getService(RenderingEngineManager.class).getDefaultVisualLexicon();
		VisualProperty customGraphics = lex.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");
		PassthroughMapping pMapping = 
			(PassthroughMapping) passthroughFactory.createVisualMappingFunction("STRING Style", String.class, customGraphics);
		stringStyle.addVisualMappingFunction(pMapping);

		// Finally, set the edge width to be dependent on the total score
		VisualMappingFunctionFactory continuousFactory = 
		                 manager.getService(VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		{
			ContinuousMapping<Double,Double> cMapping = 
				(ContinuousMapping) continuousFactory.createVisualMappingFunction("score", Double.class, 
											                                                  BasicVisualLexicon.EDGE_WIDTH);
			cMapping.addPoint(0.2, new BoundaryRangeValues<Double>(0.8,0.8,0.8));
			cMapping.addPoint(0.5, new BoundaryRangeValues<Double>(2.0,2.0,2.0));
			cMapping.addPoint(1.0, new BoundaryRangeValues<Double>(4.0,4.0,4.0));
			stringStyle.addVisualMappingFunction(cMapping);
		}

		{
			ContinuousMapping<Double,Integer> cMapping = 
				(ContinuousMapping) continuousFactory.createVisualMappingFunction("score", Double.class, 
											   	                                               BasicVisualLexicon.EDGE_TRANSPARENCY);
			cMapping.addPoint(0.2, new BoundaryRangeValues<Integer>(34,34,34));
			cMapping.addPoint(0.5, new BoundaryRangeValues<Integer>(85,85,85));
			cMapping.addPoint(1.0, new BoundaryRangeValues<Integer>(170,170,170));
			stringStyle.addVisualMappingFunction(cMapping);
		}

		vmm.addVisualStyle(stringStyle);
		return stringStyle;
	}

	private static void updateColorMap(CustomGraphicsManager manager, VisualStyle style, CyNetworkView view) {
		// Build the color list
		VisualMappingFunctionFactory discreteFactory = 
		                 manager.getService(VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		{
			DiscreteMapping<String,Color> dMapping = 
				(DiscreteMapping) discreteFactory.createVisualMappingFunction("Name", String.class, 
				                                                              BasicVisualLexicon.NODE_FILL_COLOR);

			// Set the node colors around the color wheel
			float h = 0.0f;
			float s = 1.0f;
			float stepSize = 1.0f/(float)view.getModel().getNodeCount();
			for (View<CyNode> nv: view.getNodeViews()) {
				Color c = Color.getHSBColor(h, s, 1.0f);
				h += stepSize;
				if (s == 1.0f)
					s = 0.5f;
				else
					s = 1.0f;
				String name = view.getModel().getRow(nv.getModel()).get(CyNetwork.NAME, String.class);
				dMapping.putMapValue(name, c);
			}
			style.addVisualMappingFunction(dMapping);
		}
	}

}












//*****************************//

/*import java.awt.Color;
import java.util.List;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualPropertyDependency;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;

public class ViewUtils {
	public static String STYLE_NAME = "STRING style";
	private static CyServiceRegistrar registrar;
	private static CyApplicationManager applicationManager;
	
	public static CyNetworkView styleNetwork(CyNetwork network) {
		// First, let's get a network view
		
		CyNetworkView netView = applicationManager.getCurrentNetworkView(); //createNetworkView
		VisualStyle stringStyle = createStyle(network);

		updateColorMap(stringStyle, netView);

		VisualMappingManager vmm = registrar.getService(VisualMappingManager.class); //getservice
		vmm.setVisualStyle(stringStyle, netView);
		vmm.setCurrentVisualStyle(stringStyle);
		registrar.getService(CyNetworkViewManager.class).addNetworkView(netView);
		registrar.getService(CyApplicationManager.class).setCurrentNetworkView(netView);
		
		return netView;
	}

	public static void reapplyStyle(CyNetworkView view) {		
		VisualMappingManager vmm = registrar.getService(VisualMappingManager.class); //getService static??
		VisualStyle style = vmm.getVisualStyle(view);
		style.apply(view);
	}

	public static void updateNodeStyle(CyNetworkView view, List<CyNode> nodes) {
		//manager.flushEvents();
		VisualMappingManager vmm = registrar.getService(VisualMappingManager.class);
		VisualStyle style = vmm.getVisualStyle(view);
		for (CyNode node: nodes) {
			style.apply(view.getModel().getRow(node), view.getNodeView(node));
		}
		// style.apply(view);
	}

	public static void updateEdgeStyle(CyNetworkView view, List<CyEdge> edges) {
		//manager.flushEvents();
		VisualMappingManager vmm = registrar.getService(VisualMappingManager.class); //getservice
		VisualStyle style = vmm.getVisualStyle(view);
		for (CyEdge edge: edges) {
			style.apply(view.getModel().getRow(edge), view.getEdgeView(edge));
		}
		// style.apply(view);
	}

	public static VisualStyle createStyle(CyNetwork network) {
		String networkName = network.getRow(network).get(CyNetwork.NAME, String.class);  //applicationManager.getNetworkName(network); //getcurrentnetwork
		String styleName = STYLE_NAME;
		if (networkName.startsWith("String Network")) {
			String[] parts = networkName.split("_");
			if (parts.length == 1) {
				String[] parts2 = networkName.split(" - ");
				if (parts2.length == 2)
					styleName = STYLE_NAME+" - "+parts2[1];
			} else if (parts.length == 2)
				styleName = STYLE_NAME+"_"+parts[1];
		}


		VisualMappingManager vmm = registrar.getService(VisualMappingManager.class);
		for (VisualStyle style: vmm.getAllVisualStyles()) {
			if (style.getTitle().equals(styleName)) {
				return style;
			}
		}

		VisualStyleFactory vsf = registrar.getService(VisualStyleFactory.class);

		VisualStyle stringStyle = vsf.createVisualStyle(vmm.getCurrentVisualStyle());
		stringStyle.setTitle(styleName);

		// Set the default node size
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_SIZE, 45.0);

		// Set the shape to an ellipse
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);

		// And set the color to white
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.WHITE);

		// And set the edge color to blue
		stringStyle.setDefaultValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, new Color(31,41,61));

		// And set the label color to black
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.BLACK);

		// And set the node border width to zero
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 0.0);

		// And set the label color to black
		stringStyle.setDefaultValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 10);

		// Lock node width and height
		for(VisualPropertyDependency<?> vpd: stringStyle.getAllVisualPropertyDependencies()) {
			if (vpd.getIdString().equals("nodeSizeLocked"))
				vpd.setDependency(true);
		}

		// Set up the passthrough mapping for the glass style
		VisualMappingFunctionFactory passthroughFactory = 
				registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");
		VisualLexicon lex = registrar.getService(RenderingEngineManager.class).getDefaultVisualLexicon();
		VisualProperty customGraphics = lex.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");
		PassthroughMapping pMapping = 
			(PassthroughMapping) passthroughFactory.createVisualMappingFunction("STRING Style", String.class, customGraphics);
		stringStyle.addVisualMappingFunction(pMapping);

		// Finally, set the edge width to be dependent on the total score
		VisualMappingFunctionFactory continuousFactory = 
				registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		{
			ContinuousMapping<Double,Double> cMapping = 
				(ContinuousMapping) continuousFactory.createVisualMappingFunction("score", Double.class, 
											                                                  BasicVisualLexicon.EDGE_WIDTH);
			cMapping.addPoint(0.2, new BoundaryRangeValues<Double>(0.8,0.8,0.8));
			cMapping.addPoint(0.5, new BoundaryRangeValues<Double>(2.0,2.0,2.0));
			cMapping.addPoint(1.0, new BoundaryRangeValues<Double>(4.0,4.0,4.0));
			stringStyle.addVisualMappingFunction(cMapping);
		}

		{
			ContinuousMapping<Double,Integer> cMapping = 
				(ContinuousMapping) continuousFactory.createVisualMappingFunction("score", Double.class, 
											   	                                               BasicVisualLexicon.EDGE_TRANSPARENCY);
			cMapping.addPoint(0.2, new BoundaryRangeValues<Integer>(34,34,34));
			cMapping.addPoint(0.5, new BoundaryRangeValues<Integer>(85,85,85));
			cMapping.addPoint(1.0, new BoundaryRangeValues<Integer>(170,170,170));
			stringStyle.addVisualMappingFunction(cMapping);
		}

		vmm.addVisualStyle(stringStyle);
		return stringStyle;
	}

	private static void updateColorMap(VisualStyle style, CyNetworkView view) {
		// Build the color list
		VisualMappingFunctionFactory discreteFactory = 
				registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		{
			DiscreteMapping<String,Color> dMapping = 
				(DiscreteMapping) discreteFactory.createVisualMappingFunction("Name", String.class, 
				                                                              BasicVisualLexicon.NODE_FILL_COLOR);

			// Set the node colors around the color wheel
			float h = 0.0f;
			float s = 1.0f;
			float stepSize = 1.0f/(float)view.getModel().getNodeCount();
			for (View<CyNode> nv: view.getNodeViews()) {
				Color c = Color.getHSBColor(h, s, 1.0f);
				h += stepSize;
				if (s == 1.0f)
					s = 0.5f;
				else
					s = 1.0f;
				String name = view.getModel().getRow(nv.getModel()).get(CyNetwork.NAME, String.class);
				dMapping.putMapValue(name, c);
			}
			style.addVisualMappingFunction(dMapping);
		}
	}

}*/