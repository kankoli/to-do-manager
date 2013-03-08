package view.urgent_task_dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import control.ControllerInterface;

import model.Task;

public class UrgentCellRenderer implements ListCellRenderer<Object> {
	
	public UrgentCellRenderer() {}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Component getListCellRendererComponent(JList arg0, Object obj,
			int arg2, boolean isSelected, boolean hasFocus) {
		Task t = (Task) obj;
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(270,32));
		panel.setPreferredSize(new Dimension(270,32));
		panel.setLayout(new GridLayout(0,2));
		
		JLabel lbl = new JLabel(t.getName());
		lbl.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
		panel.add(lbl);
		
		lbl = new JLabel(ControllerInterface.getDateFormat().format(
				t.getDate()));
		lbl.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
		panel.add(lbl);
		
		if (isSelected)
			panel.setBackground(Color.cyan);
		
		return panel;
	}
}