package by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PLine;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PNode;


public class NodeListener extends MouseAdapter{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		PNode node = (PNode)arg0.getSource();
		if (node.getLayeredPane().getEditMode() == 0)
		{
			if (arg0.getButton() == MouseEvent.BUTTON1)
			{
				System.out.println("NodeListener: mouseClicked");
				if (!arg0.isControlDown())
					node.getLayeredPane().unselectAll();
				node.select();
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if (arg0.getButton() == MouseEvent.BUTTON1)
		{
			System.out.println("NodeListener: mousePressed");
			PNode node = (PNode)arg0.getSource();
			node.setMouseX(arg0.getX());
			node.setMouseY(arg0.getY());
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		PNode node = (PNode)arg0.getSource();
		System.out.println("NodeListener: mouseEntered");
		if (!node.isSelected())
		{
			node.setColor(Color.ORANGE);
			node.repaint();
		}
	}
	
	@Override
	public void mouseExited(MouseEvent arg0)
	{
		PNode node = (PNode)arg0.getSource();
		System.out.println("NodeListener: mouseExited");
		if (!node.isSelected())
		{
			node.setColor(Color.BLACK);
			node.repaint();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		System.out.println("NodeListener: mouseDragged(mode = " + arg0.getModifiers() + ")");
		PNode node = (PNode)arg0.getSource();
		if (arg0.getModifiers() == 16 || arg0.getModifiers() == 18)
		{
			if (!node.isSelected() && (arg0.isControlDown() || node.getLayeredPane().getSelectedCount() <= 0))
			{
				node.select();
			}
			if (node.isSelected())
			{
				
				System.out.println("\tmoveTo(" + (arg0.getX() - node.getMouseX()) + "," + (arg0.getY() - node.getMouseY()) + ")");
				node.getLayeredPane().moveSelectedNodes(arg0.getX() - node.getMouseX(), arg0.getY() - node.getMouseY());
			}
		}
		
	}
}
