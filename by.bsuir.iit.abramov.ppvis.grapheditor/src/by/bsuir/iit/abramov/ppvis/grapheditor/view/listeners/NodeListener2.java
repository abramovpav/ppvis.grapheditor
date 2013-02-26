package by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PLine;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PNode;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.PLayeredPane;


public class NodeListener2 extends MouseAdapter{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		if (arg0.getButton() == MouseEvent.BUTTON1)
		{
			PNode node = (PNode)arg0.getSource();
			if (node.getLayeredPane().getEditMode() == 1)
			{
				if (node.getLayeredPane().getCurrentLine() == null)
				{
					System.out.println("NodeListener2: line was created");
					node.selectRed();
					//node.getLayeredPane().addSelectedNode(node);
					PLine line = new PLine(node, node.getLayeredPane());					
				}
				else 
				{
					PLine line = node.getLayeredPane().getCurrentLine();
					line.setEndNode(node);
					line.addMouseListener(new LineListener());
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		PNode node = (PNode)arg0.getSource();
		System.out.println("NodeListener2: mouseEntered");
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
		System.out.println("NodeListener2: mouseExited");
		if (!node.isSelected())
		{
			node.setColor(Color.BLACK);
			node.repaint();
		}
	}
	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		PNode node = (PNode)arg0.getSource();
		if (node.getLayeredPane().getEditMode() == 1)
			if (node.getLayeredPane().getCurrentLine() != null)
			{
				System.out.println("FormListener: mouseMoved");
				node.getLayeredPane().getCurrentLine().setEndCoord(node.getX() + arg0.getX() - PNode.getBoundsSize() / 2, node.getY() + arg0.getY() - PNode.getBoundsSize() / 2);
			}
	}
}
