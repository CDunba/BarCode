class DataMatrix implements  BarcodeIO 
{
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;
   public static final char BLACK_CHAR='*';
   public static final char WHITE_CHAR=' '; 
   

   public DataMatrix()
   {
	   image = new BarcodeImage();
	   text = "";
	   actualWidth = 0;
	   actualHeight = 0;
   }

   public DataMatrix(BarcodeImage image)
   {
      text = "";
      if (!scan(image))
      {
    	  image = new BarcodeImage();
          text = "";
          actualWidth = 0;
          actualHeight = 0;
      }
      else
      {
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
      }
   } 
   
   public DataMatrix(String text)
   {
      image = new BarcodeImage();
      if (!readText(text))
      {
         text = "";
         actualWidth = 0;
         actualHeight = 0;
      } 
      else
      {
         generateImageFromText();
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
      }
   }
   
   
   public int getActualWidth()
   {
      return actualWidth;
   } 
   
   public int getActualHeight()
   {
      return actualHeight;
   } 
   
   public boolean readText(String text)
   {
      if (text == null)
      {
         return false;
      }
      else
      {
         this.text = text;
         return true;
      }
   } 
   
   public boolean scan(BarcodeImage image)
   {
      boolean returnBoolValue = true;
      try
      {
         this.image = (BarcodeImage) image.clone();
         cleanImage();
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
         translateImageToText();
         returnBoolValue = true;
      }
      catch (CloneNotSupportedException e)
      {
         returnBoolValue = false;
      }     
      return returnBoolValue;
   } 
   

   private int computeSignalWidth()
   {     
	   int count=0;
		for(int j=0; j<BarcodeImage.MAX_WIDTH; j++)
		{
			if(image.getPixel(BarcodeImage.MAX_HEIGHT-1,j))
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
   

   private int computeSignalHeight()
   {
	   int count=0;
		for(int row=BarcodeImage.MAX_HEIGHT-1; count<BarcodeImage.MAX_HEIGHT; row--)
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
      int leftOffset = 0;
      int bottomOffset = 0;
      
      leftOffset = findLeftSide();
      
      if (leftOffset > 0)
      {     
          int stopPosition;       
          boolean setValue; 

          if ((leftOffset > 1) && (leftOffset < BarcodeImage.MAX_WIDTH))
          {  
             stopPosition = BarcodeImage.MAX_WIDTH - leftOffset; 
             for (int i = 0; i < BarcodeImage.MAX_WIDTH; i++)
             {
                for (int j = 0; j < BarcodeImage.MAX_HEIGHT; j++)
                {
                   if (i < stopPosition)
                   {
                      setValue = image.getPixel(j, i + leftOffset);
                   }
                   else
                   {
                      setValue = false;
                   } 
                   image.setPixel(j, i, setValue);
                }
             }
          }
      }
      
      
      bottomOffset = findBottomSide();
      if (bottomOffset < (BarcodeImage.MAX_HEIGHT - 1))
      {

          int stopPosition;       
          boolean value; 

          if ((bottomOffset > 1) && (bottomOffset < BarcodeImage.MAX_HEIGHT))
          {
             stopPosition = bottomOffset - 1;

             for (int i = BarcodeImage.MAX_HEIGHT - 1; i > 0; i--)
             {
                for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
                {
                   if (i > stopPosition)
                   {
                	   value = image.getPixel(i - bottomOffset, j);
                   }
                   else
                   {
                      value = false;
                   } 
                   image.setPixel(i, j, value);
                   
                }
             }
          }
          
      }
   }
   

   private int findLeftSide()
   {
	   int leftDistance=0;
		boolean checkValue=false;
		for(int row=BarcodeImage.MAX_HEIGHT-1; row>=0 && !checkValue; row--)
		{
			for(int col=0; col<BarcodeImage.MAX_WIDTH && !checkValue; col++)
			{
				checkValue=image.getPixel(row, col);
				if(checkValue)
				{
					leftDistance=col;
				}
			}
		}
		return leftDistance;
   }
   
   
   private int findBottomSide()
   {
	   int bottomDistance = 0;
		boolean checkValue=false;
		for(int col=0; col<BarcodeImage.MAX_WIDTH && !checkValue; col++)
		{
			for(int row=BarcodeImage.MAX_HEIGHT-1; row>=0 && !checkValue; row--)
			{
				checkValue=image.getPixel(row,col);
				if(checkValue)
				{
					bottomDistance=BarcodeImage.MAX_HEIGHT-1-row;
				}
			}
		}
		return bottomDistance;
    }


   public boolean generateImageFromText()
   {
      image=new BarcodeImage();
      if (text == null)
      {
         return false;
      }
      
      setBottomSpine();
      setLeftSpine();

      boolean resultValue=setRestData();
      return resultValue;
   } 
   

   public void setBottomSpine()
   {
	   for(int i=0; i<=text.length()+1;i++)
	      {
	         image.setPixel(BarcodeImage.MAX_HEIGHT-1, i, true);
	      }
   }
   
   
   public void setLeftSpine()
   {
	   writeCharToCol(0, 255);      
	   image.setPixel(BarcodeImage.MAX_HEIGHT - 9, 0, true);
   }
   
   
   public boolean setRestData()
   {
	   int i;
	   boolean returnValue;
	   boolean topBorderBit=true;
	   for(i = 1; i <= text.length(); i++)
	      {
	         
	         if(!writeCharToCol(i, (int)text.charAt(i - 1)))
	         returnValue=false;
	         topBorderBit = !topBorderBit;
	         image.setPixel(BarcodeImage.MAX_HEIGHT - 9, i, topBorderBit);
	      }
	      
	      writeCharToCol(i, 170);
	      topBorderBit = !topBorderBit;
	      image.setPixel(BarcodeImage.MAX_HEIGHT - 10, i, topBorderBit);
	      returnValue=true;
	      return returnValue;
   }
   
   
   public boolean translateImageToText()
   {
	   String tempTextValue = "";
	   int cValue=0;
	      
	      if(image == null)
	      {
	         return false;
	      } 
	      
	      for (int i= 1; i<actualWidth-1;i++)
	      {
	         for (int j = BarcodeImage.MAX_HEIGHT - 2; 
	               j > (BarcodeImage.MAX_HEIGHT - actualHeight); j--)
	         {
	            if (image.getPixel(j,i))
	            {
	               cValue += Math.pow(2, (BarcodeImage.MAX_HEIGHT-2)-j);
	            }
	           
	         } 
	         tempTextValue += (char) cValue;
	         cValue = 0;
	      } 
	      text = tempTextValue;
	      return true;
   }
  

   private boolean writeCharToCol(int col, int code)
   {
      boolean returnValue = false;                             
      String bS,finalBS;                       
      int lenBS = 0;                      
      int row= 0;                               
      Boolean setvalue = false;
      
      bS = "00000000" + Integer.toBinaryString(code);
      finalBS = bS.substring(bS.length()-8);
      lenBS = finalBS.length();

      if (code < 0 || code > 255)
      {
         returnValue=false;
      }
      else if(lenBS > 8)
      {
    	  returnValue=false;
      }
      else
      {         
         for(int i=lenBS; i> 0; i--)
         {        
            if(finalBS.charAt(i-1)=='1')
            {
               setvalue = true;
            }
            else
            {
               setvalue = false;
            } 
            row= BarcodeImage.MAX_HEIGHT - 2 - (8-i);
            image.setPixel(row, col, setvalue);       
         }                       
         returnValue = true;
       } 
      return returnValue;
   }
   
   

   public void displayTextToConsole()
   {
      System.out.println(text);
      //add dashes
      for(int i=0; i<=2*actualWidth+2; i++)
      {	
    	  if(i%2==0)
    	  {
    		  System.out.print("-");
    	  }
          else
          {
        	  System.out.print("");
          }
      } 
      System.out.println();
   }

   
   public void displayImageToConsole()
   {
      for (int i=BarcodeImage.MAX_HEIGHT - actualHeight; 
             i< BarcodeImage.MAX_HEIGHT ; i++)
      {
         System.out.print("|");
         for (int j=0; j<=actualWidth; j++)
         {           
            if(image.getPixel(i,j))
            {
               System.out.print(BLACK_CHAR);
            }
            else
            {
               System.out.print(WHITE_CHAR);
            }
         }
         System.out.print("|");
         System.out.println();
      }
      //add dashes
      for(int i=0; i<=2*actualWidth+2; i++)
      {	
    	  if(i%2==0)
    	  {
    		  System.out.print("-");
    	  }
          else
          {
        	  System.out.print("");
          }
      } 
      System.out.println();
   }

   public void displayRawImage()
   {
      for (int i=0;i<=BarcodeImage.MAX_HEIGHT;i++)
      {
         for(int j=0;j<=BarcodeImage.MAX_WIDTH;j++)
         {        
            if(image.getPixel(i,j))
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
}