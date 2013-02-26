package by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PLine;


public class LineListener extends MouseAdapter{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		PLine line = (PLine)arg0.getSource();
		if (!arg0.isControlDown())
		{
			line.getLayeredPane().unselectAll();
		}
		line.select();
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		PLine line = (PLine)arg0.getSource();
		if (!line.isSelected())
		{
			line.setColor(Color.ORANGE);
			line.repaint();
		}
	}
	
	@Override
	public void mouseExited(MouseEvent arg0)
	{
		PLine line = (PLine)arg0.getSource();
		if (!line.isSelected())
		{
			line.setColor(Color.BLACK);
			line.repaint();
		}
	}
}
