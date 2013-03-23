package by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.components.PNode;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.MainForm;
import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.PLayeredPane;


public class FormListener extends MouseAdapter{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		System.out.println("FormListener: mouseClicked");
		PLayeredPane layeredPane = (PLayeredPane)arg0.getSource();
		if (layeredPane.getEditMode() == 0)
		{
			if (arg0.getClickCount() == 2)
				if (arg0.getButton() == MouseEvent.BUTTON1)
				{
					System.out.println("FormListener: mouseClicked: doubleClick");
					PNode node = new PNode(arg0.getX(), arg0.getY(), layeredPane);
					NodeListener listener = new NodeListener();
					node.addMouseListener(listener);
					node.addMouseMotionListener(listener);
				}
		}
	}
	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		/*PLayeredPane layeredPane = (PLayeredPane)arg0.getSource();
		if (layeredPane.getEditMode() == 1)
			if (layeredPane.getCurrentLine() != null)
			{
				System.out.println("FormListener: mouseMoved");
				layeredPane.getCurrentLine().setEndCoord(arg0.getX() - PNode.getBoundsSize() / 2, arg0.getY() - PNode.getBoundsSize() / 2);
			}*/
	}
}
