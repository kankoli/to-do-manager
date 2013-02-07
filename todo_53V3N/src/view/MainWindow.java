package view;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// This comment is just a test.. Marco
// im testing egit plugin

public class MainWindow extends JFrame {


	public static void main(String[] args) {
		MainWindow w = new MainWindow();
    }
	
	public MainWindow() {
		setBounds(300, 300, 300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new FlowLayout());
		
		
		JPanel panel = new JPanel();
		JButton btn = new JButton("New Task");
		panel.add(btn);
		add(panel);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showDialog();
			}
		});
	}

	private void showDialog() {

		JDialog dialog = new JDialog();
		NewTaskDialog.addComponentsToPane(dialog.getContentPane());
		
		//OR, you can do the following...
		//JDialog dialog = new JDialog();
		//dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		dialog.setBounds(350, 350, 220, 220);
		dialog.setVisible(true);
	}
	
}