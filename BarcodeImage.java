
class BarcodeImage implements Cloneable
{
   private boolean[][] image_data;
   /*This is where to store your image. If the incoming memory is instantiated anyway, but leaves it 
    * blank (white). This data will be false for elements that are white, and true for elements that 
    * are black.
    */

   public BarcodeImage() 
   {
	  //Default constructor - //instantiates a 2D array (DataMatrix.MAX_HEIGHT x DataMatrix.MAX_WIDTH) and stuffs it 
	  //with blanks (false).
	    
      image_data = new boolean[DataMatrix.MAX_HEIGHT][DataMatrix.MAX_WIDTH];
    
      for (int i = 0; i < DataMatrix.MAX_HEIGHT; i++)
      {
         for (int j = 0; j < DataMatrix.MAX_WIDTH; j++)
         {
            image_data[i][j] = false;
         }
      }
   }

   public BarcodeImage(String[] str_data)//takes a 1D array of Strings and converts it to the internal 2D array of booleans.
   {  
	  //intialize rows and columns for image_data    
	   
      int ll = DataMatrix.MAX_HEIGHT - str_data.length; //lower left = 30 - (length of str_data array
      image_data = new boolean[DataMatrix.MAX_HEIGHT][DataMatrix.MAX_WIDTH];
      
      // for loop storing 1d array str_data into 2s array image_data
      for (int i = 0; i < str_data.length; i++)
      {
         for (int j = 0; j < str_data[i].length(); j++)
         {
        	 // if statement makes sure that there is no extra space below or left of the image so 
        	 //this constructor can put it into the lower-left corner of the array.
        	
            if (str_data[i].charAt(j) == DataMatrix.WHITE_CHAR)
            {
               image_data[ll + i][j] = false;
     
            } 
            else 
            {
               image_data[ll + i][j] = true;     
            } 
         } 
      } 
   } 
   
   public boolean getPixel(int i, int j)
   {
	   /*return values for both the actual data and also the error condition -- so that we don't "create 
	    * a scene" if there is an error; we just return false.
	    */
      boolean b = false;
      if ((i < 0) || (i < DataMatrix.MAX_HEIGHT) || (j < 0) || (j < DataMatrix.MAX_WIDTH))
      {
         b = image_data[i][j]; //error
      }
      else 
      {
         b = false;// no error found
      }     
      return b;     
   }
   
   public boolean setPixel(int i, int j, boolean inRange)
   {
	   //returns true if the row and columns are in range, and false if not
      boolean b = false;

      if ((i < 0) || (i > DataMatrix.MAX_HEIGHT ) || (j < 0) || (j > DataMatrix.MAX_WIDTH))
      {
         b = true; //error with range of columns
      }
      else 
      {
         b = false; // rows and columns in range
         image_data[i][j] = inRange;
      }      
      return b;
   } 

   boolean checkSize(String[] data) {	
      boolean returnValue = false;
	  int str_data_height = data.length;
      if (str_data_height <= DataMatrix.MAX_HEIGHT) // check if size of input array
											// string is less than equal to
											// DataMatrix.MAX_HEIGHT
	  {
         for (int i = 0; i < str_data_height; i++) {	
            String str_data_row = data[i];
			if (str_data_row.length() > DataMatrix.MAX_WIDTH || str_data_row == null) // check
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
 
   public String displayToConsole()
   {
	  //method useful for debugging this class
      String s = new String(" "); 
      return s;
   } 
   
   public BarcodeImage clone() throws CloneNotSupportedException 
   {
	   BarcodeImage barcodeClone = (BarcodeImage)super.clone();
       barcodeClone.image_data = image_data.clone();
       return barcodeClone;
   } 
} 