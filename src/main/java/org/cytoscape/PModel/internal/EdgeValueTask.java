package org.cytoscape.PModel.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;

public class EdgeValueTask {

	public static void createColumn(CyTable table, String columnName, Class<?> type, Class<?> elementType) {
		CyColumn column = table.getColumn(columnName);
		if (column != null) {
			if (!column.getType().equals(type))
				return;
		}
		else
			table.createColumn(columnName, type, false);
		return;
	}

	public static boolean checkColumn(CyTable table, String columnName, Class<?> type, Class<?> elementType) {
		CyColumn column = table.getColumn(columnName);
		if (column == null || column.getType() != type)
			return false;
		if (type.equals(List.class) && column.getListElementType() != elementType)
			return false;
		return true;
	}

	public static void getName(CyNetwork network, CyEdge edge, String column, String value) {
		network.getRow(edge).set(column, value);
		return;
	}
}