package by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.MainForm;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.PLayeredPane;

public class NewTabListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		final JScrollPane scrollPane = new JScrollPane();
		JPanel panel = new JPanel();
		MainForm.getTabbedPane().addTab("New tab", null, scrollPane, null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));				
		panel.setPreferredSize(new Dimension(1500, 800));
		
		scrollPane.setViewportView(panel);
		PLayeredPane layeredPane = new PLayeredPane(MainForm.getTabbedPane().getSelectedIndex(), panel);
		panel.add(layeredPane);
		MainForm.addLayeredPane(layeredPane);
		panel.setLocation(-10, -10);
		System.out.println(panel.getBounds());
		
		panel.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent arg0) 
			{
				JPanel pan = (JPanel)arg0.getSource();
				System.out.println(pan.getBounds());
				Dimension preferredSize = pan.getPreferredSize();
				int x = pan.getWidth(), y = pan.getHeight();
				if (x > preferredSize.width)
				{ 
					preferredSize.width = x;
				}
				if (y > preferredSize.height)
				{
					preferredSize.height = y;
				}
				preferredSize.setSize(x, y);
				pan.setPreferredSize(preferredSize);
			}
		
		});
	}

}
