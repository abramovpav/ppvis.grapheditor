package by.bsuir.iit.abramov.ppvis.grapheditor.view.forms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import by.bsuir.iit.abramov.ppvis.grapheditor.util.Factory;

public class SimpleDialog extends JDialog{
	private JPanel contentPane;
	private String label;
	private String labelButton;
	private JTextField textField;
	private JButton button;
	
	public SimpleDialog(String title, String label, String labelButton){
		super();
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setModal(true);
		setTitle(title);
		this.label = label;
		this.labelButton = labelButton;
		initialize();
	}
	
	private void initialize()
	{
		contentPane = Factory.createJPanel(new EmptyBorder(5, 5, 5, 5), new BorderLayout(0, 0), null);
		setContentPane(contentPane);
		
		JLabel Label = new JLabel(label);
		contentPane.add(Label, BorderLayout.NORTH);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button.setEnabled(textField.getText().length() != 0);
			}
		});
		
		button = new JButton(labelButton);
		contentPane.add(button, BorderLayout.SOUTH);
	}
}
