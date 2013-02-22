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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.custom_components.ImageButton;
import view.custom_components.PriorityBar;

import control.Helpers;

public class CopyOfNewTaskDialog {

	public static void addComponentsToPane(Container pane) {

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		JLabel label = new JLabel("Name");
		c.gridx = 0;
		c.gridy = 0;
		pane.add(label, c);

		JTextField textField = new JTextField();
		c.gridx = 1;
		c.gridy = 0;
		pane.add(textField, c);

		label = new JLabel("Description");
		c.gridx = 0;
		c.gridy = 1;
		pane.add(label, c);

		ImageButton imageButton = new ImageButton("assets/def.png",
				"assets/hover1.png", "assets/pressed1.png",
				"assets/pressed1.png");
		c.gridx = 1;
		c.gridy = 1;
		pane.add(imageButton, c);

		label = new JLabel("Date");
		c.gridx = 0;
		c.gridy = 2;
		pane.add(label, c);

		label = new JLabel("Category");
		c.gridx = 0;
		c.gridy = 3;
		pane.add(label, c);

		JComboBox<String> cmbCategory = new JComboBox<String>();

		// XXX Marco: here we will use the category provided by datamodel
		String[] items = Helpers.categoryItems();
		for (int i = 0; i < items.length; i++)
			cmbCategory.addItem(items[i]);

		
		c.gridx = 1;
		c.gridy = 3;
		pane.add(cmbCategory, c);

		label = new JLabel("Priority");
		c.gridx = 0;
		c.gridy = 4;
		pane.add(label, c);

		PriorityBar bar = new PriorityBar("assets/def.png",
				"assets/hover1.png", "assets/pressed1.png",
				"assets/pressed1.png", "assets/hover2.png",
				"assets/pressed2.png", "assets/pressed2.png",
				"assets/hover3.png", "assets/pressed3.png",
				"assets/pressed3.png");
		c.gridx = 1;
		c.gridy = 4;
		pane.add(bar, c);

		JButton button = new JButton("Cancel");
		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 0; // aligned with button 2
		c.gridy = 5; // third row
		pane.add(button, c);

		button = new JButton("Ok");
		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 1; // aligned with button 2
		c.gridy = 5; // third row
		pane.add(button, c);
	}
}
