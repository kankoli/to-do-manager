package marco_tests.taskScrollImproved;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Category;

import org.jdom2.JDOMException;

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
public final class AddCategoryDialog extends JFrame {

	private dbConnectorImproved dataModel;
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
	public AddCategoryDialog(final dbConnectorImproved dataModel) {
		super();
		this.dataModel = dataModel;

		// setPreferredSize(new Dimension(width, height));
		// setMinimumSize(new Dimension(width, height));

		basePanel = new JPanel();
		basePanel.setBackground(Color.white);
		basePanel.setLayout(new GridBagLayout());

		// Category name input
		categoryField = new JTextField("", 20);

		// TODO needed?
		categoryField.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				categoryField.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

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

		// TODO fix layout!

		cancelBut = new JButton("Cancel");

		cancelBut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// TODO chiudi easy
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

				// TODO qui agisci sul datamodel: verifica se esiste
				// se si warning con dialog, altrimenti salva in categoria!
				// poi revalida e repainta frame

				// System.out.println("asd");
				//
				// for(Category c: dataModel.getCategories().values())
				// System.out.println(c.getName());

				if (dataModel.getCategories().containsKey(
						categoryField.getText())) {
					JOptionPane.showMessageDialog(null,
							"Category is already existing. Change name.",
							"Invalid Category", JOptionPane.WARNING_MESSAGE);

					categoryField.setFocusable(true);
				}
				// if(datamModel.)
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
		setDefaultCloseOperation(EXIT_ON_CLOSE); // TODO OVERRIDE WTIH CUSTOM
													// CODE TO SAVE ETC.
	}

	public static void main(String[] args) {
		try {
			new AddCategoryDialog(new dbConnectorImproved());
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
