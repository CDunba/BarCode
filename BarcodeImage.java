


public class BarcodeImage implements Cloneable {
	public static final int MAX_HEIGHT = 30;
	public static final int MAX_WIDTH = 65;
	private boolean[][] image_data;

	BarcodeImage() // Default Constructor
	{
		image_data = new boolean[MAX_HEIGHT][MAX_WIDTH]; // instantiate boolean
															// 2D array
		for (int i = 0; i < MAX_HEIGHT; i++) // loop through rows
		{
			for (int j = 0; j < MAX_WIDTH; j++) // loop through columns
			{
				image_data[i][j] = false; // assign false to whole boolean 2D
											// array
			}
		}

	}

	BarcodeImage(String[] str_data) // parameterized constructor
	{
		if (str_data.length == MAX_HEIGHT) // if the dimensions of str_data and
											// image_data are same then
											// simply loop through the str_data
											// to assign it to image_data
		{
			for (int i = 0; i < str_data.length; i++) {
				String str_data_row = str_data[i];
				if (str_data.length == MAX_WIDTH) {
					for (int j = 0; j < str_data_row.length(); j++) {
						if (str_data_row.charAt(j) == ' ') {
							image_data[i][j] = false;
						} else if (str_data_row.charAt(j) == '*') {
							image_data[i][j] = true;
						}
					}
				}
			}
		} else {
			int stringImageRowDifference = MAX_HEIGHT - str_data.length ; // Difference
																			// in
																			// rows
																			// of
																			// str_data
																			// and
																			// image_data
			
			
			int row = stringImageRowDifference ;
			for (int i = 0; i < str_data.length; i--) // loop through rows of
														// str_data
			{
				String str_data_row = str_data[i]; // assign row to str_data_row
													// variable

				for (int j = 0; j < str_data_row.length(); j++) // loop through
																// that
																// particular
																// row to get
																// column values
				{
					if (str_data_row.charAt(j) == ' ') {
						image_data[row + j][j] = false;
					} // to understand the logic of row+j read this-->>
					// say, str_data has 10 rows and MAX_HEIGHT is 30. row will
					// be 30-10=20. Then start assigning str_data to
					// img_data from 20 to 29 which are 10 rows that too lower
					// justified.
					else if (str_data_row.charAt(j) == '*') {
						image_data[row + j][j] = true;
					} // * in str_data will be true in image_data
				}
			}
		}
	}

	public boolean getPixel(int row, int col) {
		boolean getPixelResult;
		if (!checkRange(row, col)) // if checkRange returns false
		{
			getPixelResult = false;
		} else {
			getPixelResult = true;
		}
		return getPixelResult;
	}

	public boolean setPixel(int row, int col, boolean value) {
		boolean setPixelResult;
		if (!checkRange(row, col)) {
			setPixelResult = false;
		} else {
			setPixelResult = true;
			image_data[row][col] = value; // if row and col in range then assign
											// value to image_data
		}
		return setPixelResult;
	}

	private boolean checkSize(String[] data) {
		boolean returnValue = false;
		int str_data_height = data.length;
		if (str_data_height <= MAX_HEIGHT) // check if size of input array
											// string is less than equal to
											// MAX_HEIGHT
		{
			for (int i = 0; i < str_data_height; i++) {
				String str_data_row = data[i];
				if (str_data_row.length() > MAX_WIDTH || str_data_row == null) // check
																				// for
																				// length
																				// of
																				// data
																				// in
																				// a
																				// row
					returnValue = false;
				else
					returnValue = true;
			}
		} else {
			returnValue = false;
		}

		return returnValue;
	}

	public boolean checkRange(int row, int col) {
		boolean range;
		if ((row < MAX_HEIGHT && row >= 0) && (col < MAX_WIDTH && col >= 0)) // check
																				// input
																				// row
																				// and
																				// col
																				// against
																				// max
																				// values
		{
			range = true;
		} else {
			range = false;
		}
		return range;
	}

	/* private void displayToConsole(){
		int image_data

	 }
*/
	public Object clone() throws CloneNotSupportedException {
		try {
			BarcodeImage image_copy = (BarcodeImage) super.clone(); // create
																	// clone
																	// image
			image_copy.image_data = image_data.clone(); // copy image_data to
														// new copy
			return image_copy;
		} catch (CloneNotSupportedException e) // handle exception
		{
			return null;
		}
	}
}
