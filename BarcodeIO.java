
public interface BarcodeIO    //It only contains abstract methods to be inherited and implemented by DataMatrix class
{
	public boolean scan(BarcodeImage bc);
	
	public boolean readText(String text);
	
	public boolean generateImageFromText();
	
	public boolean translateImageToText();
	
	public void displayTextToConsole();
	
	void displayImageToConsole();
}
