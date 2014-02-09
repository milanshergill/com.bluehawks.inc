package com.example.eng4kgestures;
import java.util.ArrayList;
/*
 * 
     |<-------------- Averaging window ---------------->|<-------------- Averaging window ---------------->|
     |-a--b--c--d--e--f--g--h--i--j--k--l--m--n--o--p--q|--r--s--t--u--v--w--x--y--z--1--2--3--4--5--6--7--|                              
          <--> -->sample step size

 * */
public class TemporalCompresssion 
{
	static public Acceleration[] calculateAverage(Gesture gesture) 
    {
		int gestureLength = gesture.getAccelerationArray().length;
		System.out.println(" length of the gesture is "  + gestureLength);
		int gestureComponents = 3;
		
		// Time is in milliseconds
		double sampleTime = 25.0;
		Double AveragingWindow = 75.0;
		
		int NumberOfWindows = (int) ((int) gestureLength * sampleTime/ AveragingWindow) ;
		Acceleration[] gestureArray = gesture.getAccelerationArray();
		Acceleration[] modifiedAccelerationList = new Acceleration[NumberOfWindows+1];
		
		// intialising the modified list
		for(int i = 0; i < modifiedAccelerationList.length ; i++)
			modifiedAccelerationList[i] = gestureArray[i];
		
	
		//position is global pointer
		int globalPosition = 0;
	
		
		// Calculating the Average for each Window
		Double AverageX = 0.0;Double AverageY = 0.0;Double AverageZ = 0.0;int counter = 0;
		
		for (int i = 0; i < NumberOfWindows;i++)
		{
			AverageX = 0.0;AverageY = 0.0;AverageZ = 0.0;counter = 0;
			//calculate the average for x, y, z axis
			for(int localTime=0;localTime<AveragingWindow;)
			{
				// recording data at 25ms
				localTime+=25;
				AverageX += gestureArray[globalPosition].getAccelerationX();
				AverageY += gestureArray[globalPosition].getAccelerationY();
				AverageZ += gestureArray[globalPosition].getAccelerationZ();
				globalPosition++;
				counter++;
			}
			float x = (float) (AverageX/counter);
			float y = (float) (AverageY/counter);
			float z = (float) (AverageZ/counter);
			modifiedAccelerationList[i].setAccelerationXYZ(x, y, z);
			System.out.println("i" + i+ "AccelerationX" + modifiedAccelerationList[i].getAccelerationX() + "AccelerationY" + modifiedAccelerationList[i].getAccelerationY() + "AccelerationZ" + modifiedAccelerationList[i].getAccelerationZ() );
		}

		AverageX = 0.0;AverageY = 0.0;AverageZ = 0.0;counter = 0;	
		while(globalPosition < (gestureLength))
		{
			AverageX += gestureArray[globalPosition].getAccelerationX();
			AverageY += gestureArray[globalPosition].getAccelerationY();
			AverageZ += gestureArray[globalPosition].getAccelerationZ();
			counter++;
			globalPosition++;
		}
		if(counter!=0)
		{
			modifiedAccelerationList[NumberOfWindows].setAccelerationX((float) (AverageX/counter)); 
			modifiedAccelerationList[NumberOfWindows].setAccelerationX((float) (AverageY/counter));
			modifiedAccelerationList[NumberOfWindows].setAccelerationX((float) (AverageZ/counter));
			System.out.println("i" + NumberOfWindows+ "AccelerationX" + modifiedAccelerationList[NumberOfWindows].getAccelerationX() + "AccelerationY" + modifiedAccelerationList[NumberOfWindows].getAccelerationY() + "AccelerationZ" + modifiedAccelerationList[NumberOfWindows].getAccelerationZ() );
		}
		return modifiedAccelerationList;
	}
}
