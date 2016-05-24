package org.cytoscape.PModel.internal;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.PModel.internal.InhibitionShapeTask;

public class TutorialClassAttempt extends AbstractTask { // originally
															// implements Task
	public static final String BoolVal = "BoolValue";
	private CyNetwork network;

	// String columnName, Class<? extends T> type, boolean isImmutable, T
	// defaultValue
	public void plswork(CyNetwork net) {

		if (net != null)
			this.network = net;

		List<String> columns = new ArrayList<>();
		for (CyColumn column : network.getDefaultEdgeTable().getColumns()) {
			if (column.getType().equals(String.class)) {
				columns.add(column.getName());
			}
		}

	}

	public static void createColumn(CyTable table, String columnName, Class<?> type, Class<?> elementType) {
		CyColumn column = table.getColumn(columnName);
		if (column != null) {
			if (!column.getType().equals(type)) {
				throw new RuntimeException("Column " + columnName + " already exists, but has a different type");
			}
			if ((column.getType().equals(List.class)) && (!column.getListElementType().equals(elementType))) {
				throw new RuntimeException(
						"List column " + columnName + " already exists, but has a different element type");
			}
			return;

		}
		if (type.equals(List.class))
			table.createListColumn(columnName, elementType, false);
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

	public void runny(TaskMonitor tasky, CyTable table, String idColumn, double affinityCutoff, CyEdge edge)
			throws Exception {
		TutorialClassAttempt.createColumn(table, idColumn, String.class, getClass());
		table.getRow(edge.getSUID()).set(BoolVal, -1);

	}

	@Override
	public void run(TaskMonitor arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
