package org.cytoscape.PModel.internal;
import java.util.Collection;
import java.util.List;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractEdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.model.SavePolicy;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
//temp code edit later
public class EdgeTableTaskFactory {

	public static void createColumn(CyTable table, String columnName, Class<?> type, Class<?> elementType) {
	
		CyColumn column = table.getColumn(columnName);
	    if (column != null)
	    {
	      if (!column.getType().equals(type)) {
	        throw new RuntimeException("Column " + columnName + " already exists, but has a different type");
	      }
	      if ((column.getType().equals(List.class)) && (!column.getListElementType().equals(elementType))) {
	        throw new RuntimeException("List column " + columnName + " already exists, but has a different element type");
	      }
	      return;
	    }
	    if (type.equals(List.class)) {
	      table.createListColumn(columnName, elementType, false);
	    } else {
	      table.createColumn(columnName, type, false);
	    }
	}
	  public static boolean checkColumn(CyTable table, String columnName, Class<?> type, Class<?> elementType)
	  {
	    CyColumn column = table.getColumn(columnName);
	    if ((column == null) || (column.getType() != type)) {
	      return false;
	    }
	    if ((type.equals(List.class)) && (column.getListElementType() != elementType)) {
	      return false;
	    }
	    return true;
	  }
	  
	  public static String getName(CyNetwork network, CyIdentifiable id)
	  {
	    return (String)network.getRow(id).get("name", String.class);
	  }
	  
	  public static CyIdentifiable getIdentifiable(CyNetwork network, Long suid)
	  {
	    if (network.getNode(suid.longValue()) != null) {
	      return network.getNode(suid.longValue());
	    }
	    if (network.getEdge(suid.longValue()) != null) {
	      return network.getEdge(suid.longValue());
	    }
	    return network;
	  }
}
