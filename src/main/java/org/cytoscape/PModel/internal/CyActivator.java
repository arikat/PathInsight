package org.cytoscape.PModel.internal;

import java.util.Properties;
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
		//CustomGraphicsManager customGraphicsManager = getService(context, CustomGraphicsManager.class);
		//CustomGraphicsManagerImpl customGraphicsManagerImpl = getService(context, CustomGraphicsManagerImpl.class);
		
		//shape change
		InhibitionTaskFactory InhibitionShapeTask = new InhibitionTaskFactory(cyApplicationManagerService);
		Properties cprops = new Properties();
		cprops.setProperty("preferredMenu", "Apps.Pathway Model");
		cprops.setProperty("title", "Auto Edge Distinction");
		cprops.setProperty("inMenuBar", "true");
		cprops.setProperty("menuGravity", "8.2");
		registerService(context, InhibitionShapeTask, EdgeViewTaskFactory.class, cprops);
		
		//create column activation
		ActivationEdgeFactory createcolumn = new ActivationEdgeFactory(cyApplicationManagerService);
		Properties aprops = new Properties();
		aprops.setProperty("preferredMenu", "Apps.Pathway Model.Edge Label");
		aprops.setProperty("title", "Activation");
		aprops.setProperty("inMenuBar", "true");
		aprops.setProperty("menuGravity", "8.3");
		registerService(context, createcolumn, TaskFactory.class, aprops);
		
		//label edge inhibition
		InhibitionEdgeFactory inhibit = new InhibitionEdgeFactory(cyApplicationManagerService);
		Properties iprops = new Properties();
		iprops.setProperty("preferredMenu", "Apps.Pathway Model.Edge Label");
		iprops.setProperty("title", "Inhibition");
		iprops.setProperty("inMenuBar", "true");
		iprops.setProperty("menuGravity", "8.4");
		registerService(context, inhibit, TaskFactory.class, iprops);
		
		//Node Output algorithm attempt
		NodeOutputFactory creety = new NodeOutputFactory(cyApplicationManagerService);
		Properties cops = new Properties();
		cops.setProperty("preferredMenu", "Apps.Pathway Model");
		cops.setProperty("title", "Node Analysis");
		cops.setProperty("inMenuBar", "true");
		cops.setProperty("menuGravity", "8.8");
		registerService(context, creety, TaskFactory.class, cops);

		
		//annotate with image - maybe works
		/*NeighborFinderFactory cg = new NeighborFinderFactory();
		Properties gprops = new Properties();
		gprops.setProperty("preferredMenu", "Apps.Pathway Model.Draw");
		gprops.setProperty("title", "Drowsy");
		gprops.setProperty("inMenuBar", "true");
		gprops.setProperty("menuGravity", "8.4");
		registerService(context, cg, NodeViewTaskFactory.class, gprops);*/
		
		//ImageAnnotationNode ian = new ImageAnnotationNode(cyApplicationManagerService, currentLexicon);
		//registerService(context, ian, CyAction.class, new Properties());
		
		//ImageAnnoViz vizan = new ImageAnnoViz(cyApplicationManagerService, mappy, fact, currentLexicon);
		//registerService(context, vizan, CyAction.class, new Properties());
		
		
		CustomGraphicsManager manager = new CustomGraphicsManager(registrar);
		CyCustomGraphicsFactory<ImageAnno> stringLookFactory = new CustomGraphicsFactory(manager);
		Properties stringProps = new Properties();
		registerService(context, stringLookFactory, CyCustomGraphicsFactory.class, stringProps);
		
		
		TaskFactory paintStructure = new PaintFactory(registrar, cyApplicationManagerService);
		Properties paintStructureProps = new Properties();
		paintStructureProps.setProperty("preferredMenu", "Apps.Pathway Model");
		paintStructureProps.setProperty("title", "Paint Nodes");
		//paintStructureProps.setProperty(ENABLE_FOR, "networkAndView");
		paintStructureProps.setProperty("inMenuBar", "true");
		paintStructureProps.setProperty("menuGravity", "8.6");
		registerService(context, paintStructure, NodeViewTaskFactory.class, paintStructureProps);
		
		
	/*	VisualMappingManager vmmServiceRef = getService(context,VisualMappingManager.class);
		
		VisualStyleFactory visualStyleFactoryServiceRef = getService(context,VisualStyleFactory.class);
		
		VisualMappingFunctionFactory vmfFactoryC = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualMappingFunctionFactory vmfFactoryD = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		VisualMappingFunctionFactory vmfFactoryP = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");
		CreateVisualStyleAction createVisualStyleAction = new CreateVisualStyleAction(cyApplicationManagerService, vmmServiceRef, visualStyleFactoryServiceRef, 
				vmfFactoryC, vmfFactoryD, vmfFactoryP);
		
		registerService(context,createVisualStyleAction,CyAction.class, new Properties());
		*/
		
		
	}
	
}