package marco_tests.taskScrollImproved;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DialogTest {
	private void displayGUI() {

		new ColorDialog(new JFrame(), false, "asd");
		/*
		// TODO
		if(
		JOptionPane.showConfirmDialog(null, getPanel(),
				"JOptionPane Example : ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION){
			
			System.out.println("occchei");
			// TODO something
		}
		*/
	}

	private JPanel getPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JTextField categoryField = new JTextField("", 20);
		categoryField.setBorder(BorderFactory
				.createTitledBorder("Choose a name"));

		categoryField.setEnabled(true);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(categoryField, gbc);

		JColorChooser jcc = new JColorChooser();
		jcc.getSelectionModel().addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				// TODO Auto-generated method stub

			}
		});

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;

		jcc.setBorder(BorderFactory.createTitledBorder("Choose a Color"));
		panel.add(jcc, gbc);

		// panel.add(label);

		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DialogTest().displayGUI();
			}
		});
	}
}