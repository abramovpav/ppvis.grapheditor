package by.bsuir.iit.abramov.ppvis.grapheditor.view.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import by.bsuir.iit.abramov.ppvis.grapheditor.view.forms.MainForm;


public class ToolButtonsKeyListener extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		System.out.println("ToolButtonsKeyListener: keyPressed(" + arg0.getKeyCode() + ")");
		if (arg0.getKeyCode() == 27) //Key "Escape"
		{
			MainForm.getCurrentLayeredPane().unselectAll();
		}
		if (arg0.getKeyCode() == 127) //Key "Delete"
		{
			MainForm.getCurrentLayeredPane().deleteSelectedItems();
		}
	}
}
