package org.cytoscape.PModel.internal;

import java.util.Properties;

import org.cytoscape.PModel.internal.TaskFactories.ActivationEdgeFactory;
import org.cytoscape.PModel.internal.TaskFactories.ActivationNodeFactory;
import org.cytoscape.PModel.internal.TaskFactories.ClearEdgeBoolTaskFactory;
import org.cytoscape.PModel.internal.TaskFactories.ClearImagesTaskFactory;
import org.cytoscape.PModel.internal.TaskFactories.ClearNodeBoolTaskFactory;
import org.cytoscape.PModel.internal.TaskFactories.EdgeShapeTaskFactory;
import org.cytoscape.PModel.internal.TaskFactories.InhibitionEdgeFactory;
import org.cytoscape.PModel.internal.TaskFactories.InhibitionNodeFactory;
import org.cytoscape.PModel.internal.TaskFactories.NodeOutputFactory;
import org.cytoscape.PModel.internal.TaskFactories.NodeOutputStageITaskFactory;
import org.cytoscape.PModel.internal.TaskFactories.PaintFactory;
import org.cytoscape.PModel.internal.TaskFactories.ResetEdgeFactory;
import org.cytoscape.PModel.internal.TaskFactories.ResetNodeFactory;
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {
	
	@Override
	public void start(BundleContext context) throws Exception {
		//change CyActivator for neighborFinderFactory
		CyServiceRegistrar registrar = getService(context, CyServiceRegistrar.class);
		VisualStyleFactory vsf = getService(context,VisualStyleFactory.class);
		VisualLexicon currentLexicon = getService(context, RenderingEngineManager.class).getDefaultVisualLexicon();
		VisualMappingManager mappy = getService(context, VisualMappingManager.class);
		CyApplicationManager cyApplicationManagerService = getService(context, CyApplicationManager.class);
		CyNetworkView cyNetworkView = getService(context, CyNetworkView.class);
		CySwingApplication cyApp = getService(context, CySwingApplication.class);
		VisualMappingFunctionFactory fact = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");
		VisualMappingFunctionFactory facty = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		CyEventHelper eventHelp = getService(context, CyEventHelper.class);
				
		//shape change
		EdgeShapeTaskFactory EdgeShapeTask = new EdgeShapeTaskFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties cprops = new Properties();
		cprops.setProperty("preferredMenu", "Apps.Pathway Modeller");
		cprops.setProperty("title", "Auto Edge Distinction");
		cprops.setProperty("inMenuBar", "true");
		cprops.setProperty("menuGravity", "8.0");
		registerService(context, EdgeShapeTask, TaskFactory.class, cprops);
		
		//Label edge activation
		ActivationEdgeFactory createcolumn = new ActivationEdgeFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties aprops = new Properties();
		aprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Edge Label");
		aprops.setProperty("title", "Activating");
		aprops.setProperty("inMenuBar", "true");
		aprops.setProperty("menuGravity", "8.3");
		registerService(context, createcolumn, TaskFactory.class, aprops);
		
		//Activate Node label
		ActivationNodeFactory activateNode = new ActivationNodeFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties anprops = new Properties();
		anprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Compound Label");
		anprops.setProperty("title", "Activated");
		anprops.setProperty("inMenuBar", "true");
		anprops.setProperty("menuGravity", "8.3");
		registerService(context, activateNode, TaskFactory.class, anprops);
		
		//Activate Node label
		ResetNodeFactory resetNode = new ResetNodeFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties reprops = new Properties();
		reprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Compound Label");
		reprops.setProperty("title", "Reset");
		reprops.setProperty("inMenuBar", "true");
		reprops.setProperty("menuGravity", "8.5");
		registerService(context, resetNode, TaskFactory.class, reprops);
		
		//Inhibit Node label
		InhibitionNodeFactory inhibitNode = new InhibitionNodeFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties inprops = new Properties();
		inprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Compound Label");
		inprops.setProperty("title", "Inhibited");
		inprops.setProperty("inMenuBar", "true");
		inprops.setProperty("menuGravity", "8.4");
		registerService(context, inhibitNode, TaskFactory.class, inprops);
		
		//label edge inhibition
		InhibitionEdgeFactory inhibit = new InhibitionEdgeFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties iprops = new Properties();
		iprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Edge Label");
		iprops.setProperty("title", "Inhibiting");
		iprops.setProperty("inMenuBar", "true");
		iprops.setProperty("menuGravity", "8.4");
		registerService(context, inhibit, TaskFactory.class, iprops);
		
		//label edge Reset
		ResetEdgeFactory reset = new ResetEdgeFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties rprops = new Properties();
		rprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Edge Label");
		rprops.setProperty("title", "Reset");
		rprops.setProperty("inMenuBar", "true");
		rprops.setProperty("menuGravity", "8.4");
		registerService(context, reset, TaskFactory.class, rprops);
		
		//Clear boolean values of all edges
		ClearEdgeBoolTaskFactory clears = new ClearEdgeBoolTaskFactory(cyApplicationManagerService);
		Properties ppropsy = new Properties();
		ppropsy.setProperty("preferredMenu", "Apps.Pathway Modeller.Reset Values");
		ppropsy.setProperty("title", "Clear Edges");
		ppropsy.setProperty("inMenuBar", "true");
		ppropsy.setProperty("menuGravity", "8.4");
		registerService(context, clears, TaskFactory.class, ppropsy);
		
		//Clear boolean values of all nodes
		ClearNodeBoolTaskFactory cleary = new ClearNodeBoolTaskFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties pprops = new Properties();
		pprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Reset Values");
		pprops.setProperty("title", "Clear Nodes");
		pprops.setProperty("inMenuBar", "true");
		pprops.setProperty("menuGravity", "8.5");
		registerService(context, cleary, TaskFactory.class, pprops);
		
		//Clear values of Images
		ClearImagesTaskFactory clearys = new ClearImagesTaskFactory(cyApplicationManagerService);
		Properties pcprops = new Properties();
		pcprops.setProperty("preferredMenu", "Apps.Pathway Modeller.Reset Values");
		pcprops.setProperty("title", "Clear Images");
		pcprops.setProperty("inMenuBar", "true");
		pcprops.setProperty("menuGravity", "8.6");
		registerService(context, clearys, TaskFactory.class, pcprops);
		
		//Node Output Stage I algorithm
		NodeOutputStageITaskFactory stageI = new NodeOutputStageITaskFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties corpse = new Properties();
		corpse.setProperty("preferredMenu", "Apps.Pathway Modeller.Node Analysis");
		corpse.setProperty("title", "One Step");
		corpse.setProperty("inMenuBar", "true");
		corpse.setProperty("menuGravity", "8.7");
		registerService(context, stageI, TaskFactory.class, corpse);
		
		//Node Output Stage II algorithm
		NodeOutputFactory creety = new NodeOutputFactory(cyNetworkView, registrar, cyApplicationManagerService);
		Properties cops = new Properties();
		cops.setProperty("preferredMenu", "Apps.Pathway Modeller.Node Analysis");
		cops.setProperty("title", "Two Steps");
		cops.setProperty("inMenuBar", "true");
		cops.setProperty("menuGravity", "8.8");
		registerService(context, creety, TaskFactory.class, cops);
		
		//Paint All Nodes
		TaskFactory paintStructure = new PaintFactory(cyNetworkView, registrar, cyApplicationManagerService, mappy); //figure out how implement sbuild into this
		Properties paintStructureProps = new Properties();
		paintStructureProps.setProperty("preferredMenu", "Apps.Pathway Modeller");
		paintStructureProps.setProperty("title", "Paint All Nodes");
		paintStructureProps.setProperty("inMenuBar", "true");
		paintStructureProps.setProperty("menuGravity", "8.6");
		registerService(context, paintStructure, TaskFactory.class, paintStructureProps);
		
	}
	
}