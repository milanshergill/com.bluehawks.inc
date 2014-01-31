package com.example.eng4kgestures;
import java.util.ArrayList;
public class DynamicTimeWarping
{
	/*public static void main(String[] args) throws Exception 
	{
	double a[][] = {{1.0,2.0} , {2.0,6.0} , {4.0,6.0} , {5.0,10.0}};
	double b[][] = {{1.0,5.0}, { 5.0,7.0 }, { 4.0,9.0 }, { 2.0, 7.0}};
	double distance = (double) calcDistance(a,b);
	System.out.println(distance);
}
	*/
//	static public float calcDistanceAfterConversion(ArrayList<Float> recordedX, ArrayList<Float> recordedY, ArrayList<Float> recordedZ,
//			ArrayList<Float> savedX, ArrayList<Float> savedY, ArrayList<Float> savedZ) {
//		double [][] recordedAccel = new double[3][recordedX.size()];
//		for (int i = 0; i < recordedAccel.length; i++) {
//			recordedAccel[0][i] = recordedX.get(i);
//			recordedAccel[1][i] = recordedX.get(i);
//			recordedAccel[2][i] = recordedX.get(i);
//		}
//		
//		double [][] savedAccel = new double[3][savedX.size()];
//		for (int i = 0; i < recordedAccel.length; i++) {
//			savedAccel[0][i] = savedX.get(i);
//			savedAccel[1][i] = savedY.get(i);
//			savedAccel[2][i] = savedZ.get(i);
//		}
//		return calcDistance(recordedAccel, savedAccel);
//	}

	static public float calcDistance(Gesture gesture1, Gesture gesture2) 
    {
		float distMatrix[][] = calculateDistanceMatrix(gesture1, gesture2);
		float costMatrix[][] = calculateCostMatrix(gesture1,gesture2, distMatrix);
		
		// the distance between gesture a and b is the end'th value of the cost matrix
        return costMatrix[gesture1.getAccelerationArray().length-1][gesture2.getAccelerationArray().length-1];
	}

	// creating matrix thats is gesture1Length x gesture2Length in size
		static private float[][] initializeMatrix(Gesture gesture1, Gesture gesture2)
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
	static private float[][] calculateDistanceMatrix(Gesture gesture1, Gesture gesture2)
	{
		/* gestureComponents is axis components used to compare signal.Accelerometer provides data in x,y,z axis*/
//		int gestureComponents = 3;
		float distMatrix[][] = initializeMatrix(gesture1, gesture2);
		Acceleration[] gesture1Array = gesture1.getAccelerationArray();
		Acceleration[] gesture2Array = gesture2.getAccelerationArray();
		for (int i = 0; i < distMatrix.length; i++) 
        {
			for (int j = 0; j < distMatrix[0].length; j++) 
            { 
				ArrayList<Double> differenceVector = new ArrayList<Double>();
                // calculate the difference for each component

				differenceVector.add((double) gesture1Array[i].getAccelerationX() -  gesture2Array[j].getAccelerationX());
				differenceVector.add((double) gesture1Array[i].getAccelerationY() -  gesture2Array[j].getAccelerationY());
				differenceVector.add((double) gesture1Array[i].getAccelerationZ() -  gesture2Array[j].getAccelerationZ());
               /* for(int k=0; k<gestureComponents ; k++)
                {
                    differenceVector.add((double) (gesture1[i][k] - gesture2[j][k]));
                }  */ 
                distMatrix[i][j] = normaliseVector(differenceVector);
                //System.out.println("distance matrix " + distMatrix[i][j] + " i" + i + " j" + j);
            }
        }
		return distMatrix;
	}
	
	 // costMatrix determines the least expensive path
	static private float[][] calculateCostMatrix(Gesture gesture1, Gesture gesture2,float distMatrix[][])
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