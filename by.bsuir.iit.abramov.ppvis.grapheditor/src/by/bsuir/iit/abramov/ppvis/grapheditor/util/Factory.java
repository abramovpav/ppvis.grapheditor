package by.bsuir.iit.abramov.ppvis.grapheditor.util;

import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

public final class Factory {
	public static JPanel createJPanel(Border border, LayoutManager layout, Rectangle bounds)
	{
		JPanel panel = new JPanel();
		if (border != null)
			panel.setBorder(border);
		
		panel.setLayout(layout);
		
		if (bounds != null)
			panel.setBounds(bounds);
		return panel;
	}
	
	public static JButton createJButton(String caption, boolean focusable, ActionListener listener, KeyListener keyListener, JComponent cont)
	{
		JButton button = new JButton(caption);
		button.setFocusable(focusable);
		if (listener != null)
			button.addActionListener(listener);
		if (keyListener != null)
			button.addKeyListener(keyListener);
		cont.add(button);
		return button;
	}
}
