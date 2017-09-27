class BarcodeImage implements Cloneable
{
	public static final int MAX_HEIGHT=30;
	public static final int MAX_WIDTH=65;
   private boolean[][] image_data;
 
   //constructor 1
   public BarcodeImage()
   {
      image_data = new boolean[BarcodeImage.MAX_HEIGHT][BarcodeImage.MAX_WIDTH];
      int i,j;
      
      for (i=0; i<image_data.length; i++)
      {
         for (j=0; j<image_data[i].length; j++)
         {
            image_data[i][j] = false;
         } 
      }
   }
   
   // constructor 2
   public BarcodeImage(String[] str_data)
   {
      int initialPos = BarcodeImage.MAX_HEIGHT - str_data.length;
      int row = 0;
      int col = 0;
      image_data = new boolean[BarcodeImage.MAX_HEIGHT][BarcodeImage.MAX_WIDTH];
      
      for (int i=0; i<str_data.length; i++)
      {
         for (int j=0; j<str_data[row].length(); j++)
         {

            if (str_data[row].charAt(col) == DataMatrix.WHITE_CHAR)
            {
               image_data[initialPos + row][col] = false;
               col++;
            } // end if
            else if (str_data[row].charAt(col) == DataMatrix.BLACK_CHAR)
            {
               image_data[initialPos + row][col] = true;
               col++;
            } 
         } 
         col=0;
         row++;
      }   
   } 

  
   public boolean getPixel(int row, int col)
   {
	   boolean getPixelResult;
		if(!checkRange(row,col)) // if checkRange returns false
		{
			getPixelResult=false;
		}
		else
		{
			getPixelResult=image_data[row][col];
		}
		return getPixelResult;

   } // end getPixel()
   

   public boolean setPixel(int row, int col, boolean value)
   {
	   boolean setPixelResult;
		if(!checkRange(row,col))
		{
			setPixelResult=false;
		}
		else
		{
			setPixelResult=true;
			image_data[row][col]=value; //if row and col in range then assign value to image_data
		}
		return setPixelResult;

   } 
   
   public boolean checkRange(int row, int col)
	{
		boolean range;
		if((row<MAX_HEIGHT && row>=0) && (col<MAX_WIDTH && col>=0)) //check input row and col against max values
		{
			range=true;
		}
		else
		{
			range=false;
		}
		return range;
	}
	

   
   
   

   public String displayToConsole()
   {
      String returnString = new String("");
      
      return returnString;
   } 
   

   public Object clone() throws CloneNotSupportedException 
   {
         BarcodeImage imageCopy = (BarcodeImage)super.clone();
         imageCopy.image_data = image_data.clone();
         return imageCopy;
   } 
} 