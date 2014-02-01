package com.example.testgestureapp;
import java.util.ArrayList;
public class DynamicTimeWarpingWithXYZCombined
{
	static public float calcDistance(Gesture_Object gesture1, Gesture_Object gesture2) 
    {
		float distMatrix[][] = calculateDistanceMatrix(gesture1, gesture2);
		float costMatrix[][] = calculateCostMatrix(gesture1,gesture2, distMatrix);
		
		// the distance between gesture a and b is the end'th value of the cost matrix
        return costMatrix[gesture1.getAccelerationArray().length-1][gesture2.getAccelerationArray().length-1];
	}

	// creating matrix thats is gesture1Length x gesture2Length in size
		static private float[][] initializeMatrix(Gesture_Object gesture1, Gesture_Object gesture2)
		{
			// length of the gesture1 and gesture2 sequence
			int gesture1Length = gesture1.getAccelerationArray().length;
			int gesture2Length = gesture2.getAccelerationArray().length;
			float Matrix[][] ;
			Matrix = new float[gesture1Length][];
			for (int i = 0; i < gesture1Length; i++) 
			{
				Matrix[i] = new float[gesture2Length];
			}
			return Matrix;
		}	
		
	//distMatrix determines the distance between the two vectors at each time interval.
	static private float[][] calculateDistanceMatrix(Gesture_Object gesture1, Gesture_Object gesture2)
	{
		/* gestureComponents is axis components used to compare signal.Accelerometer provides data in x,y,z axis*/
///		int gestureComponents = 3;
		float distMatrix[][] = initializeMatrix(gesture1, gesture2);
		Acceleration[] gesture1Array = gesture1.getAccelerationArray();
		Acceleration[] gesture2Array = gesture2.getAccelerationArray();
		for (int i = 0; i < distMatrix.length; i++) 
        {
			for (int j = 0; j < distMatrix[0].length; j++) 
            { 
				ArrayList<Double> differenceVector = new ArrayList<Double>();
                // calculate the difference for each component
				// combine the accelerations
				double square1= Math.pow(gesture1Array[i].getAccelerationX(), 2) +Math.pow(gesture1Array[i].getAccelerationY(), 2) +Math.pow(gesture1Array[i].getAccelerationZ(), 2);
				double root1 = Math.pow(square1,0.5);
				double square2= Math.pow(gesture2Array[i].getAccelerationX(), 2) +Math.pow(gesture2Array[i].getAccelerationY(), 2) +Math.pow(gesture2Array[i].getAccelerationZ(), 2);
				double root2 = Math.pow(square2,0.5);
				differenceVector.add((double) root1 -  root2);
                distMatrix[i][j] = normaliseVector(differenceVector);
                
            }
        }
		return distMatrix;
	}
	
	 // costMatrix determines the least expensive path
	static private float[][] calculateCostMatrix(Gesture_Object gesture1, Gesture_Object gesture2,float distMatrix[][])
	{
		float costMatrix[][] = initializeMatrix(gesture1, gesture2);
		float OFFSET_PENALTY = .5f;
		// The first column of the cost matrix is the same as the first column of the distance matrix
        for (int i = 0; i < costMatrix.length; i++) 
        {
        	costMatrix[i][0] = distMatrix[i][0];
            //System.out.println(" costMatrix[i][j] " + costMatrix[i][0]);
        }
        
        // The algorithm below is used to create the remaining column of the cost matrix
        for (int j = 1; j < costMatrix[0].length;j++) 
        {
        	for (int i = 0; i < costMatrix.length; i++) 
            {
        		if (i == 0) 
                {
        			// calculate 
                    costMatrix[i][j] =  (costMatrix[i][j - 1] + distMatrix[i][j]);
                } else 
        		{
                	float minCost, cost;
                	// i-1, j-1
                    minCost = (float) (costMatrix[i - 1][j - 1] + distMatrix[i][j]);
                    // i-1, j
                    if ((cost = (float) (costMatrix[i - 1][j] + distMatrix[i][j])) < minCost) 
                    {
                    	minCost = cost + OFFSET_PENALTY;
                    }
                    // i, j-1
                    if ((cost = (float) (costMatrix[i][j - 1] + distMatrix[i][j])) < minCost) 
                    {
                    	minCost = cost + OFFSET_PENALTY;
                    }
                    costMatrix[i][j] = minCost;
                    //System.out.println("costMatrix[i][j] " + costMatrix[i][j] + " i= " + i + " j= " + j );
                }
            }
        	
        }
		return costMatrix;
	}
	
	 // This method is used to acquire the unit vector
    static private float normaliseVector(ArrayList<Double> differenceVector)
    {
    	double sum=0;
        for(Double VectorComponent : differenceVector)
        {
        	sum += Math.pow(VectorComponent, 2);
        }
        //System.out.println (Math.pow(sum, 0.5));
        return (float) Math.pow(sum, 0.5);
    }

}