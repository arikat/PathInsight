package org.cytoscape.PModel.internal;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.events.SetCurrentNetworkEvent;
import org.cytoscape.application.events.SetCurrentNetworkListener;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.PModel.internal.InhibitionShapeTask;


/**
 * BindingDbManager
 * 
 */
public class EdgeManager implements SetCurrentNetworkListener {
	public static final String BoolVal = "BooleanValue";
	public static final String Column = "columnval";

	final CyApplicationManager appManager;
	final CyEventHelper eventHelper;
	final OpenBrowser openBrowser;
	final CyServiceRegistrar serviceRegistrar;

	public EdgeManager(CyApplicationManager appManager, OpenBrowser openBrowser, CyServiceRegistrar serviceRegistrar) {
		this.appManager = appManager;
		this.openBrowser = openBrowser;
		this.serviceRegistrar = serviceRegistrar;
		this.eventHelper = getService(CyEventHelper.class);
	}

	public CyNetwork getCurrentNetwork() {
		return appManager.getCurrentNetwork();
	}

	public CyNetworkView getCurrentNetworkView() {
		return appManager.getCurrentNetworkView();
	}

	public String getCurrentNetworkName() {
		CyNetwork network = getCurrentNetwork();
		return network.getRow(network).get(CyNetwork.NAME, String.class);
	}

	public <S> S getService(Class<S> serviceClass) {
		return serviceRegistrar.getService(serviceClass);
	}

	public <S> S getService(Class<S> serviceClass, String filter) {
		return serviceRegistrar.getService(serviceClass, filter);
	}

	public void registerService(Object service, Class<?> serviceClass, Properties props) {
		serviceRegistrar.registerService(service, serviceClass, props);
	}

	public void unregisterService(Object service, Class<?> serviceClass) {
		serviceRegistrar.unregisterService(service, serviceClass);
	}

	public void openURL(String url) {
		if (url != null || url.length() > 0)
			openBrowser.openURL(url);
	}

	public void loadAnnotations(TaskMonitor monitor, CyNetwork network, 
	                            String idColumn, double affinityCutoff, List<CyNode> nodes) {
		if (nodes == null) nodes = network.getNodeList();

		monitor.showMessage(TaskMonitor.Level.INFO, "Loading annotations");

		// Create all of our columns (if we need to)
		EdgeTableTaskFactory.createColumn(network.getDefaultEdgeTable(), BoolVal, List.class, String.class);
		for (CyNode node: nodes) {
			String id = network.getRow(node).get(idColumn, String.class);
			getAnnotation(monitor, network, node, id, affinityCutoff);
		}
	}

	public void handleEvent(SetCurrentNetworkEvent scne) {
	}

	void getAnnotation(TaskMonitor monitor, CyNetwork network, CyNode node, String id, double cutoff) {
		String nodeName = EdgeTableTaskFactory.getName(network, node);
		monitor.showMessage(TaskMonitor.Level.INFO, "Loading annotations for "+nodeName+"("+id+")");

		int inhibition = -1;
		int activation = 1;

		// Now, set the values for this node
		network.getRow(node).set(Column, inhibition);

	}

	String getContent(Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE && node.hasChildNodes()) {
			Node child = node.getFirstChild();
			if (child.getNodeType() == Node.TEXT_NODE)
				return child.getNodeValue();
		}
 		return null;
	}

}