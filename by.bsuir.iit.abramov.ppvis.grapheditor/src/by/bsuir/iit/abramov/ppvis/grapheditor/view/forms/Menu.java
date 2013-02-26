package by.bsuir.iit.abramov.ppvis.grapheditor.view.forms;

public enum Menu {

	FILE("File")/*, OPEN("Open..."), SAVE("Save..."), SAVEAS("Save as..."), EXIT("Exit")*/,
	EDIT("Edit"), /*SELECT_ALL("Select All"), UNSELECT_ALL("Unselect All"), */INSTRUMENTS("Instruments")/*,
	NODE("Node"), LINE("Line")*/;
	private String label;
	private Menu(String label) {
		
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
