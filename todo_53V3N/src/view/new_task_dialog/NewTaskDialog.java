/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package view.new_task_dialog;

/*
 * GridBagLayoutDemo.java requires no other files.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Category;
import model.Task;

import view.custom_components.PriorityBar;

import control.ControllerInterface;

public class NewTaskDialog extends JDialog {

	private JTextField nameField;
	private JTextArea descriptionField;
	private JTextField dateField;
	private JComboBox<String> cmbCategory;
	private PriorityBar bar;

	private ControllerInterface controller;

	public NewTaskDialog(final ControllerInterface controller) {
		super();
		this.controller = controller;
		setTitle("New Task");
		setPreferredSize(new Dimension(160,275));
		setMinimumSize(new Dimension(160,275));
		
		JPanel pane = (JPanel) getContentPane();
		pane.setBackground(Color.WHITE);
		
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		
		nameField = new JTextField();
		nameField.setBorder(BorderFactory.createTitledBorder("Name"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		pane.add(nameField, c);


		// ImageButton imageButton = new ImageButton("assets/def.png",
		// "assets/hover1.png", "assets/pressed1.png",
		// "assets/pressed1.png");
		// c.gridx = 1;
		// c.gridy = 1;
		// pane.add(imageButton, c);

		descriptionField = new JTextArea();
		descriptionField.setBorder(BorderFactory.createTitledBorder("Description"));
		descriptionField.setRows(3);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;

		descriptionField.setLineWrap(true);
		descriptionField.setWrapStyleWord(true);
		pane.add(descriptionField, c);

		dateField = new JTextField();
		dateField.setBorder(BorderFactory.createTitledBorder("Date"));
		dateField.setText(controller.getDateFormat().toPattern());
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		pane.add(dateField, c);

		cmbCategory = new JComboBox<String>();

		for (Category ca : controller.getCategories().values())
			cmbCategory.addItem(ca.getName());

		cmbCategory.setBorder(BorderFactory.createTitledBorder("Category")); 
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		cmbCategory.setBackground(Color.WHITE);
		pane.add(cmbCategory, c);

		bar = new PriorityBar(Task.Priority.NOT_SET, controller);
		bar.setBorder(BorderFactory.createTitledBorder("Priority"));
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		bar.setBackground(Color.WHITE);
		pane.add(bar, c);

		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 0; // aligned with button 2
		c.gridy = 5; // third row
		c.gridwidth = 1;
		pane.add(button, c);

		button = new JButton("Ok");

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {


				String name = nameField.getText();

				String description = descriptionField.getText();

				// TODO date picker
				Date date = null;
				try {
					date = controller.getDateFormat()
							.parse(dateField.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Task.Priority priority = bar.getPriority();

				boolean completed = false;

				String categoryName = ((String) cmbCategory.getSelectedItem());

				controller.addTask(name, date, priority, completed,
						categoryName, description);

				dispose();
			}
		});

		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 1; // aligned with button 2
		c.gridy = 5; // third row
		c.gridwidth = 1;
		pane.add(button, c);

		pack();
		setVisible(true);
	}
}
