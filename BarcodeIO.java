public interface BarcodeIO {
	//method signatures
	public boolean scan(BarcodeImage bc);
	/*
	 * accepts an image, represented as a BarcodeImage object and stores a copy 
	 * of this image.  Depending on the sophistication of the implementing class
	 * internally stored image is a clone. 
	 * Any text string that might be part of an implementing class is not touched,
	 * updated or defined during the scan.
	 */
	public boolean readText(String text);
	/*Accepts a text string to be eventually encoded in an image. Any 
	 * BarcodeImage that might be part of an implementing class is not touched, 
	 * updated or defined during the reading of the text.
	 */
	public boolean generateImageFromText();
	/*Not technically an I/O operation, this method looks at the internal text 
	 * stored in the implementing class and produces a companion. 
	 */
	public boolean translateImageToText();
	/*Not technically an I/O operation, this method looks at the internal image 
	 *stored in the implementing class, and produces a companion text string, 
	 *internally. After this is called, the implementing object contains a fully
	 *defined image and text that are in agreement with each other.
	 */
	public void displayTextToConsole();
	//prints out the text string to the console.
	public void displayImageToConsole();
	/*prints out the image to the console in the form of a dot-matrix of blanks
	 *and asterisks, e.g.
	 */
}
