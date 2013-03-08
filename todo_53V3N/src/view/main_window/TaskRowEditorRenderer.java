package view.main_window;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TaskRowEditorRenderer extends AbstractCellEditor implements
		TableCellEditor, TableCellRenderer {

	// private TaskRow curEditedTaskRow;
	private TaskRow curEditedTaskRow;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		// System.out.println("[Renderer - getTableCellRendererComponent] "
		// + row + " -> " + isSelected);

		TaskRow tr = (TaskRow) value;
		tr.setSelected(isSelected);
		return tr;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		System.out.println("[Editor - getTableCellRendererComponent] " + row
				+ " -> " + isSelected);
		curEditedTaskRow = (TaskRow) value;

		return curEditedTaskRow;
	}

	public Object getCellEditorValue() {

		System.out.println("[Editor - getCellEditorValue]");

		// curEditedTaskRow.setSelected(false);

		return curEditedTaskRow;
	}

}