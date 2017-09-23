
public class DataMatrix implements BarcodeIO
{
	public static final char BLACK_CHAR='*';
	public static final char WHITE_CHAR=' ';
	
	private BarcodeImage image;
	private String text;
	private int actualWidth;
	private int actualHeight;
	
	
	DataMatrix()
	{
		BarcodeImage image= new BarcodeImage();
		String text="";
		actualWidth=0;
		actualHeight=0;
	}
	
	DataMatrix(BarcodeImage bc)
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
	
	DataMatrix(String text)
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
	
	public boolean readText(String text)
	{
		boolean returnValue;
		if(text==null)
		{
			returnValue=false;
		}
		else
		{   this.text=text;
			returnValue=true;
		}
		return returnValue;
	}
	
	public boolean scan(BarcodeImage image)
	{
		boolean returnValue=false;
		try
		{
			this.image=(BarcodeImage)image.clone();
			cleanImage();
			//actualWidth=computeSignalWidth();
			//actualHeight=computerSignalHeight();
			translateImageToText();
			returnValue=true;
		}
		catch (CloneNotSupportedException e)
		{
			returnValue=false;
		}
		return returnValue;
	}
	
	public int getActualWidth()
	{
		return actualWidth;
	}
	
	public int getActualHeight()
	{
		return actualHeight;
	}
	
	private int computeSignalWidth()
	{
		int widthCount=0;
		for(int col=0; col<BarcodeImage.MAX_WIDTH; col++)
		{
			if(!image.getPixel(BarcodeImage.MAX_HEIGHT-1,col))
			{
				break;
			}
			else
			{
				widthCount++;
			}
		}
		return widthCount;
	}
	
	public int computeSignalHeight()
	{
		int rowCount=0;
		for(int row=BarcodeImage.MAX_HEIGHT-1; rowCount<BarcodeImage.MAX_HEIGHT; row--)
		{
			if(!image.getPixel(row, 0))
			{
				break;
			}
			else
			{
				rowCount++;
			}
		}
		return rowCount;
	}
	

	
	private void cleanImage()
	{
		
	}

	
	public void displayRawImage()
	{
		for(int row=0;row<BarcodeImage.MAX_HEIGHT;row++)
		{
			for (int column=0;column<BarcodeImage.MAX_WIDTH;column++)
			{
				System.out.println(image.getPixel(row, column));
			}
		}
		
	}
	
	private void clearImage()
	{

	}

	@Override
	public boolean generateImageFromText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean translateImageToText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void displayTextToConsole() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayImageToConsole() {
		// TODO Auto-generated method stub
		
	}
}