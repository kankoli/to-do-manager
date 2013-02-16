package marco_tests.old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import model.Category;

import org.jdom2.JDOMException;

import utility.GlobalValues;

public class LayOutTests {

	static String asd;
	
	public static void main(String[] args) {

		JFrame f = new JFrame("test");

		f.setPreferredSize(new Dimension(800, 640));
		f.setMinimumSize(new Dimension(800, 640));

		JPanel panel = (JPanel) f.getContentPane();
		panel.setLayout(new GridBagLayout());
		// TODO

		// ------------------------------

		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.weightx = 1.0;
		panel.add(addRow(), con);

		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 1;
		con.weightx = 1.0;
		panel.add(addRow(), con);
		// ------------------------------

		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static JPanel addRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		panel.setSize(new Dimension(1000, 640));
		
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		
		JButton doneBut = new JButton("Done");
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		 con.weightx = 1.0;
		// con.insets = new Insets(0, 100, 0, 0);
		 con.anchor = GridBagConstraints.LINE_START;
		// con.anchor = GridBagConstraints.LINE_END;
		panel.add(doneBut, con);

		JTextField nameField = new JTextField("aadsa");

		// nameField.setHorizontalAlignment(JTextField.TRAILING);
		//nameField.setEnabled(false);
		// nameField.setBackground(Color.LIGHT_GRAY);
		nameField.setFont(new Font(null, Font.BOLD, 30));
		// nameArea.setDisabledTextColor(Color.BLACK);
		// nameField.setBorder(null);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 0;
		con.weightx = 0;
		con.anchor = GridBagConstraints.LINE_START;
		panel.add(nameField, con);

		// date format
		JTextField dateField = new JTextField("asdsdasdasda");
		dateField.setEnabled(false);
		// dateField.setBackground(Color.GREEN);
		// dateField.setDisabledTextColor(Color.BLACK);
		// dateField.setBorder(null);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 0;
		// con.insets = new Insets(0, 0, 0, 300);
		// con.anchor = GridBagConstraints.LINE_START;
		panel.add(dateField, con);

		// now build category ComboBox
		// TODO come fare ad aggiungere categorie???
		// maybe is better a button? Then simple dialog with colorpicker???
		JComboBox<String> categoryBox = new JComboBox<String>();

		categoryBox.addItem("asdasd");
		categoryBox.addItem("adkjkdf");

		// Select actual category
		// categoryBox.setSelectedItem(t.getCategory());
		categoryBox.setEnabled(false);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 0;
		// con.insets = new Insets(0, 0, 0, 300);
		// con.anchor = GridBagConstraints.LINE_START;
		panel.add(categoryBox, con);

		JTextField priorityField = new JTextField("asdads");
		// priorityArea.addMouseListener(my);
		priorityField.setEnabled(false);
		// priorityField.setBackground(Color.GREEN);
		// priorityField.setDisabledTextColor(Color.BLACK);
		// priorityField.setBorder(null);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 0;
		// con.insets = new Insets(0, 0, 0, 300);
		// con.anchor = GridBagConstraints.LINE_START;
		panel.add(priorityField, con);

		JTextArea descriptionArea = new JTextArea(
				"sakjbsdj dnsfns,dfn s,dnf sdf nds,fn sd,f nsd,fnsfnd",
				GlobalValues.TASKROW_DESC_ROWS, GlobalValues.TASKROW_DESC_COLS);

		// descriptionArea = new JTextArea(t.getDescription());
		// descriptionArea.setBounds( 0, 0, 200, 200 );
		// descriptionArea.addMouseListener(my);
		descriptionArea.setEnabled(false);
		// descriptionArea.setBackground(Color.GREEN);
		// descriptionArea.setDisabledTextColor(Color.BLACK);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);

		// descriptionArea.setAlignmentX(CENTER_ALIGNMENT); // TODO not sure it
		// works.. once
		// again check
		// layout

		JScrollPane descriptionPane = new JScrollPane(descriptionArea);
		descriptionPane.setVisible(false);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 0;
		if (!descriptionArea.getText().isEmpty())
			descriptionPane.setVisible(true);

		panel.add(descriptionPane, con);

		// Now the edit button
		JButton editBut = new JButton("Edit");

		// editBut.setVisible(false);
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 0;
		//con.weightx = 1.0;
		//con.anchor = GridBagConstraints.LINE_END;

		panel.add(editBut, con);

		// Delete button apre un popup di conferma
		JButton deleteBut = new JButton("Delete");
		// deleteBut.setVisible(false);
		con = new GridBagConstraints();
		 con.gridx = 7;
		 con.gridy = 0;
		 //con.ipadx = 20;
//		 con.fill = GridBagConstraints.BOTH;
		// con.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(deleteBut, con);

		return panel;
	}

}
