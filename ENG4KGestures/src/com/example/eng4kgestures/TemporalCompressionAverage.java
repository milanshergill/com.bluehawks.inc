package com.example.eng4kgestures;

public class TemporalCompressionAverage 
{
	static public Acceleration[] calculateAverage(Gesture gesture) throws Exception 
    {
		int gestureLength = gesture.getAccelerationArray().length;
		System.out.println(" length of the gesture is "  + gestureLength);
		int gestureComponents = 3;
		
		Acceleration[] gestureArray = gesture.getAccelerationArray();
		//print the array
//		for(int i=0; i < gestureArray.length;i++)
//			System.out.println("initialArray i=" + i + 
//					 " X=" + gestureArray[i].getAccelerationX() +
//					 " Y=" + gestureArray[i].getAccelerationY() +
//					 " Z=" + gestureArray[i].getAccelerationZ() );
		int avgElements=5;
		
	
		// Calculating the Average for each Window
		Double AverageX = 0.0;Double AverageY = 0.0;Double AverageZ = 0.0;
				
		if(gestureLength <5)
		{
			throw new Exception();
		}
		else
		{
			for (int i = 2; i < gestureLength-2;i++)
			{
				AverageX = 0.0;AverageY = 0.0;AverageZ = 0.0;
				 AverageX = calculateAverageX(gestureArray,i);
				 AverageY = calculateAverageY(gestureArray,i);
				 AverageZ = calculateAverageZ(gestureArray,i);
				 System.out. println(" avgX " +  AverageX + " avgY " +  AverageY + "  AvgZ " +  AverageZ);
				 float x = (float) (AverageX/avgElements);
				 float y = (float) (AverageY/avgElements);
				 float z = (float) (AverageZ/avgElements);
				 gestureArray[i].setAccelerationXYZ(x, y, z);

			}
		}
		//print array
//		for(int i=0; i < gestureArray.length;i++)
//			System.out.println("FinalArray i=" + i + 
//					 " X=" + gestureArray[i].getAccelerationX() +
//					 " Y=" + gestureArray[i].getAccelerationY() +
//					 " Z=" + gestureArray[i].getAccelerationZ() );
		return gestureArray;
	}
		
		
	

	static private double calculateAverageX(Acceleration[] gestureArray,int position) 
	{
		double AverageX = 0.0;
		for(int j=0; j<5;j++)
		  {
		      switch (j)
		      {
		      case 0 : {AverageX += gestureArray[position-2].getAccelerationX();break;}
		      case 1 : {AverageX += gestureArray[position-1].getAccelerationX();break;}
		      case 2 : {AverageX += gestureArray[position].getAccelerationX();break;}
		      case 3 : {AverageX += gestureArray[position+1].getAccelerationX();break;}
		      case 4 : {AverageX += gestureArray[position+2].getAccelerationX();break;}
		      }

		  }
		return AverageX;
	}
	static private double calculateAverageY(Acceleration[] gestureArray,int position) 
	{
		double AverageY = 0.0;
		for(int j=0; j<5;j++)
		  {
			
		      switch (j)
		      {
		      case 0 : {AverageY += gestureArray[position-2].getAccelerationY();break;}
		      case 1 : {AverageY += gestureArray[position-1].getAccelerationY();break;}
		      case 2 : {AverageY += gestureArray[position].getAccelerationY();break;}
		      case 3 : {AverageY += gestureArray[position+1].getAccelerationY();break;}
		      case 4 : {AverageY += gestureArray[position+2].getAccelerationY();break;}
		      }
		      
		  }
		return AverageY;
	}
	static private double calculateAverageZ(Acceleration[] gestureArray,int position) 
	{
		double AverageZ = 0.0;
		for(int j=0; j<5;j++)
		  {
		      switch (j)
		      {
		      case 0 : {AverageZ += gestureArray[position-2].getAccelerationZ();break;}
		      case 1 : {AverageZ += gestureArray[position-1].getAccelerationZ();break;}
		      case 2 : {AverageZ += gestureArray[position].getAccelerationZ();break;}
		      case 3 : {AverageZ += gestureArray[position+1].getAccelerationZ();break;}
		      case 4 : {AverageZ += gestureArray[position+2].getAccelerationZ();break;}
		      }

		  }
		return AverageZ;
	}
}
