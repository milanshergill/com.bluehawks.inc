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
	static public double[][] calculateAverage(Gesture gesture) 
    {
		int gestureLength = gesture.getAccelerationArray().length;
		int gestureComponents = 3;
		// time is in milliseconds
		Double AveragingWindow = 70.0;
		int NumberOfWindows = (int) ((int) gestureLength / AveragingWindow);
		
		Acceleration[] gestureArray = gesture.getAccelerationArray();
		//position is global pointer
		int position = 0;
		double[][] modifiedAccelerationList = new double[NumberOfWindows][gestureComponents];
		// Calculating the Average for each Window
		for (int i = 0; i < NumberOfWindows;i++)
		{
			Double AverageX = 0.0;
			Double AverageY = 0.0;
			Double AverageZ = 0.0;
			//calculate the average for x, y, z axis
			for(int j=0;j<AveragingWindow;j++)
			{
				AverageX += gestureArray[position].getAccelerationX();
				AverageY += gestureArray[position].getAccelerationY();
				AverageZ += gestureArray[position].getAccelerationZ();
				// recording data at 30ms
				position += 30;
				j+=30;
			}
			modifiedAccelerationList[i][0] = AverageX;
			modifiedAccelerationList[i][1] = AverageY;
			modifiedAccelerationList[i][2] = AverageZ;
		}
	
		return modifiedAccelerationList;
	}
}
