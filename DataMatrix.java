class DataMatrix implements  BarcodeIO 
{

   private BarcodeImage image;
   //a single internal copy of any image scanned-in OR passed-into the constructor OR created by BarcodeIO's 
   //generateImageFromText().
   
   private String text;
   // single internal copy of any text read-in OR passed-into the constructor OR created by BarcodeIO's 
   // translateImageToText().
 
   private int actualWidth;
   private int actualHeight;
   /*
    * Two ints that are typically less than BarcodeImage.MAX_WIDTH and BarcodeImage.MAX_HEIGHT which represent 
    * the actual portion of the BarcodeImage that has the real signal.  This is dependent on the data in the 
    * image, and can change as the image changes through mutators.  It can be computed from the "spine" of the 
    * image.
    */
   
   public static final int MAX_HEIGHT = 30;    
   public static final int MAX_WIDTH = 65;   
   public static final char BLACK_CHAR='*';
   public static final char WHITE_CHAR=' ';
   
   DataMatrix()
   {
	   /*
	    * Constructs an empty, but non-null, image and text value. The initial image is all 
	    * white, however, actualWidth and actualHeight starts at 0. Text set to undefined.
	    */
	   BarcodeImage image= new BarcodeImage();
	   String text="undefined";
	   actualWidth=0;
	   actualHeight=0;
   } 
   
   DataMatrix(BarcodeImage bc)//Sets the image and leaves the text at its default value.  Calls scan()
	{

		if(!scan(bc))
		{
			BarcodeImage image= new BarcodeImage();
		}
		else
		{
			actualWidth=computeSignalWidth();
			actualHeight=computeSignalHeight();
		}
		text="";
	}
   
   DataMatrix(String text)//sets the text but leaves the image at its default value
	{
		if(!readText(text))
		{
			text="";
			actualWidth=0;
			actualHeight=0;
		}
		else
		{
			computeSignalWidth();
			computeSignalHeight();
			generateImageFromText();
		}
		image= new BarcodeImage();
	}
   
   public int getActualWidth()
   {
      return actualWidth;
   }
   
   public int getActualHeight()
   {
      return actualHeight;
   } 
   
   public boolean readText(String text) //called by the constructor.
	{
		boolean b2;
		if(text==null)
		{
			b2=false;
		}
		else
		{   this.text=text;
			b2=true;
		}
		return b2;
	}
   
   public boolean scan(BarcodeImage image)
	{
	   /*
	    *Called by the constructor.  Calls the clone() method of the BarcodeImage 
	    *class, calls cleanImage() and then sets the actualWidth and actualHeight.  
	    *Deal with the CloneNotSupportedException.
	    */
		boolean b2=false;
		try
		{
			this.image=(BarcodeImage)image.clone();
			cleanImage();
			translateImageToText();
			b2=true;
		}
		catch (CloneNotSupportedException e)
		{
			b2=false;
		}
		return b2;
	} 
   
   private int computeSignalWidth()
	{
	   //use the "spine" of the array (bottom) to determine the actual size.
		int count=0;
		for(int j=0; j<MAX_WIDTH; j++)
		{
			if(image.getPixel(MAX_HEIGHT-1,j))
			{
				count++;	
			}
			else
			{
				break;
			}
		}
		return count;
	}
   
   public int computeSignalHeight()
	{
	   //use the "spine" of the array bottom to determine the actual size.
		int count=0;
		for(int row=MAX_HEIGHT-1; count<MAX_HEIGHT; row--)
		{
			if(image.getPixel(row, 0))
			{
				count++;
			}
			else
			{
				break;
			}
		}
		return count;
	}
   
   private void cleanImage()
   {
	   //Makes no assumption about the placement of the "signal" within a passed-in 
	   //BarcodeImage
      int left;
      int bottom;

      left = -1;       
      int j = 0;              
      int i = 0;              
      boolean spine = false;   
      
      for (i = MAX_HEIGHT - 1; 
            ((i >= 0) && !(spine)); i--)
      {
         for (j = 0; 
               ((j < MAX_WIDTH) && !(spine)); j++)
         {
            spine = image.getPixel(i, j);

            if (spine)
            {
               left = j;
            } 
         }
      }
      
      if (left >= 0)
      {
         shiftImageLeft(left);
      } 
      
      bottom = 0;                     
      spine = false;   
      
      for (j = 0; 
            ((j < MAX_WIDTH) && !(spine)); j++)
      {
         for (i = MAX_HEIGHT - 1; 
               ((i >= 0) && !(spine)); i--)
         {
           spine = image.getPixel(i, j);
           
            if (spine)
            {
            	bottom = MAX_HEIGHT - i - 1;
            } 
         }
      }
      
      if (bottom < (MAX_HEIGHT - 1))
      {
         shiftImageDown(bottom);
      }
   } 
   
   private void shiftImageDown(int offset)
   {
      int j;     
      int i;     
      int current;      
      boolean b; 
      
      if ((offset > 1) && (offset < MAX_HEIGHT))
      {
         current = offset - 1;

         for (i = MAX_HEIGHT - 1; i > 0; i--)
         {
            for (j = 0; j < MAX_WIDTH; j++)
            {
               if (i > current)
               {
                  b = image.getPixel(i - offset, j);
               }
               else
               {
                  b = false;
               } 
               image.setPixel(i, j, b);  
            }
         }
      }
   } 
   
   private void shiftImageLeft(int offset)
   {
      int j;      
      int i;    
      int current;       
      boolean b;
      
      if ((offset >= 2) && (offset < MAX_WIDTH))
      {
         current = MAX_WIDTH - offset;
         for (j = 0; j < MAX_WIDTH; j++)
         {
            for (i = 0; i < MAX_HEIGHT; i++)
            {
               if (j >= current)
               {
            	  b = false; 
               }
               else
               {
            	   b = image.getPixel(i, j + offset);
               } 
               image.setPixel(i, j, b);
            }
         }
      }
   } 
   
   public boolean generateImageFromText()
   {
      image = new BarcodeImage();
      int j = 0;
      boolean binaryTop = true;
      
      if (text != null)
      {
         for(j = 1; j <= text.length(); j++)
         {
            if(!writeCharToCol(j, (int)text.charAt(j - 1)))
             return false;
             binaryTop = !binaryTop;
             image.setPixel(MAX_HEIGHT - 9, j, binaryTop);
         }
          
         for(j = 0; j <= text.length(); j++)
         {
            image.setPixel(MAX_HEIGHT - 1, j, true);
         }
          
         writeCharToCol(0, 256);
         image.setPixel(MAX_HEIGHT - 9, 0, binaryTop);
          
         writeCharToCol(j, 171);
         
         while(binaryTop = !binaryTop)
         {
         image.setPixel(MAX_HEIGHT - 10, j, binaryTop);
         }
         
         return false;
      }
      return true;
   } 
   
   public boolean translateImageToText()
   {
	  int letter;
      this.text = "";
      
      if(image != null)
      {
    	  for (int j = 1; j <= actualWidth; j++)
          {
             letter = 0;
             for (int i = MAX_HEIGHT - 2; i > (MAX_HEIGHT - actualHeight); i--)
             {
                if (image.getPixel(i, j))
                {
                   letter += Math.pow(2, (MAX_HEIGHT - 2) - i);
                }
             } 
             this.text = this.text + (char) letter;
          } 
         return true;
      } 
      return false;
   } 
   
   private boolean writeCharToCol(int j, int n)
   {  
	  String s;
      String zeroes = "00000000";           
                                               
      int i = 0;                              
      int end = 0;
      
      boolean b1 = false;
      boolean b2 = false; 
      
      s = zeroes + Integer.toBinaryString(n);
      end = s.length() - 8;
      s = s.substring(end);
      
      if ((s.length() != 8)||(n < 0 || n >= 256))
      {
         b2 = false;
      }
      else
      {
         for(int next = s.length(); next > 0; next--)
         {
                     
            if(s.charAt(next - 1) == '1')
            {
               b1 = true;
            }
            else
            {
               b1 = false;
            } 
            i = MAX_HEIGHT - 2  - (8 - next);
            image.setPixel(i, j, b1);            
         }          
         b2 = true;
      }
      
      return b2;
   } 
   
   public void displayTextToConsole()
   {
      System.out.println(text);
   } 
   
   public void displayImageToConsole()
   {   
      for (int i =  MAX_HEIGHT - actualHeight; 
            i < MAX_HEIGHT ; i++)
      {
         for (int j = 2; j <= actualWidth; j++)
         {           
            if(image.getPixel(i, j))
            {
               System.out.print(BLACK_CHAR);
            }
            else
            {
               System.out.print(WHITE_CHAR);
            }
         }
         System.out.println();
      }
   } 
   
   public void displayRawImage()
	{
		for(int i=0; i<=MAX_HEIGHT;i++)
		{
			for (int j=0;j<=MAX_WIDTH;j++)
			{
				System.out.println(image.getPixel(i, j));
			}
		}	
	}
} 

