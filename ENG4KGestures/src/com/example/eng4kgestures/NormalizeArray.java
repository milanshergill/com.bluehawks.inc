package com.example.eng4kgestures;

public class NormalizeArray 
{

	static public Acceleration[] normalizeArray(Acceleration[] gestureArray) throws Exception 
    {
		int gestureLength = gestureArray.length;
		System.out.println(" length of the gesture is "  + gestureLength);
		
		Acceleration[] normalizedGestureArray = gestureArray;
		Double maxX = 0.0;Double maxY = 0.0;Double maxZ = 0.0; Double globalMax=0.0;
		
		maxX = (double) gestureArray[0].getAccelerationX();
		maxX = (double) gestureArray[0].getAccelerationX();
		maxX = (double) gestureArray[0].getAccelerationX();
		//determine the maximum acceleration in x,y,z components
		for (int i = 1; i < gestureLength;i++)
		{
			if (maxX < (double) gestureArray[i].getAccelerationX())
				maxX = (double)gestureArray[i].getAccelerationX();
			if (maxY < (double) gestureArray[i].getAccelerationY())
				maxY = (double)gestureArray[i].getAccelerationY();
			if (maxZ < (double) gestureArray[i].getAccelerationZ())
				maxZ = (double)gestureArray[i].getAccelerationZ();
		}
		
		//determining the globalmaximum
		if(maxX>=maxY && maxX>=maxZ)
			globalMax = maxX;
		else if(maxY>=maxX && maxY>=maxZ)
			globalMax = maxY;
		else globalMax = maxZ;
		
		float x,y,z;
		// normalizing the gestureArray
		for (int i = 0; i < gestureLength;i++)
		{
			x = (float) ((float) normalizedGestureArray[i].getAccelerationX()/globalMax);
			y = (float)( (float) normalizedGestureArray[i].getAccelerationY()/globalMax);
			z = (float)( (float) normalizedGestureArray[i].getAccelerationZ()/globalMax);
			
			normalizedGestureArray[i].setAccelerationXYZ(x, y, z);
		}
//		for(int i=0; i < gestureArray.length;i++)
//			System.out.println("initialArray i=" + i + 
//					 " X=" + normalizedGestureArray[i].getAccelerationX() +
//					 " Y=" + normalizedGestureArray[i].getAccelerationY() +
//					 " Z=" + normalizedGestureArray[i].getAccelerationZ() );
//			
		return normalizedGestureArray;
	}
		
}
