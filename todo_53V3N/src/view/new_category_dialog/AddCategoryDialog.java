package view.new_category_dialog;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import control.ControllerInterface;
import exceptions.InvalidCategoryException;

/**
 * This class represents a small dialog wich will be showed when users press add
 * category button. It allows choosing a name and a Color from a JColorChooser
 * component.
 * 
 * @author Marco Dondio
 * 
 */
@SuppressWarnings("serial")
// TODO JDialog o Jframe????
// public final class AddCategoryDialog extends JFrame {
public final class AddCategoryDialog extends JDialog {

	private ControllerInterface controller;
	private JPanel basePanel;
	private JTextField categoryField;
	private JColorChooser jcc;

	private JButton okBut;
	private JButton cancelBut;

	/**
	 * 
	 * @param width
	 *            width of frame in pixels
	 * @param height
	 *            height of frame in pixels
	 */
	public AddCategoryDialog(final ControllerInterface controller) {
		super();
		this.controller = controller;

		// setPreferredSize(new Dimension(width, height));
		// setMinimumSize(new Dimension(width, height));

		basePanel = new JPanel();
		basePanel.setBackground(Color.white);
		basePanel.setLayout(new GridBagLayout());

		// Category name input
		categoryField = new JTextField("", 20);

		/*
		 * // TODO needed? categoryField.addFocusListener(new FocusListener() {
		 * 
		 * public void focusGained(FocusEvent e) { // TODO Auto-generated method
		 * stub categoryField.selectAll(); }
		 * 
		 * @Override public void focusLost(FocusEvent e) { // TODO
		 * Auto-generated method stub
		 * 
		 * } });
		 */

		categoryField.setEnabled(true);

		categoryField.setBorder(BorderFactory
				.createTitledBorder("Choose a name"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;

		basePanel.add(categoryField, gbc);

		// Color chooser input
		jcc = new JColorChooser();

		// Remove panels: we only keep HSL view
		for (AbstractColorChooserPanel accp : jcc.getChooserPanels()) {
			String panelName = accp.getDisplayName();
			if (panelName.equals("Swatches")) {
				jcc.removeChooserPanel(accp);
			} else if (panelName.equals("HSV")) {
				jcc.removeChooserPanel(accp);
			} else if (panelName.equals("RGB")) {
				jcc.removeChooserPanel(accp);
			} else if (panelName.equals("CMYK")) {
				jcc.removeChooserPanel(accp);
			}
		}

		// Remove preview panel
		jcc.setPreviewPanel(new JPanel());

		// And set on change listener
		jcc.getSelectionModel().addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				// Set category name to picked color for a preview
				Color c = jcc.getSelectionModel().getSelectedColor();
				categoryField.setBackground(c);

				// categoryField.setText(Integer.toString(c.getRGB()));
			}
		});

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		jcc.setBorder(BorderFactory.createTitledBorder("Choose a Color"));
		basePanel.add(jcc, gbc);

		cancelBut = new JButton("Cancel");

		cancelBut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose(); // close dialog
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_END;
		basePanel.add(cancelBut, gbc);

		okBut = new JButton("Ok");
		okBut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String name = categoryField.getText();
				Color co = jcc.getSelectionModel().getSelectedColor();

				try {
					controller.addCategory(name, co);
					dispose(); // close dialog
				} catch (InvalidCategoryException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),
							"Invalid Category", JOptionPane.WARNING_MESSAGE);

					// TODO vorrei selezionare il testo
					// categoryField.setFocusable(true);
				}

			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_END;
		basePanel.add(okBut, gbc);

		setContentPane(basePanel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // TODO OVERRIDE WTIH
															// CUSTOM
		// CODE TO SAVE ETC.
	}
}
