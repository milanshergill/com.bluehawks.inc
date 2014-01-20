package DynamicTimeWarping;
import java.util.ArrayList;
public class DynamicTimeWarpingAlg
{			
	public static void main(String[] args) throws Exception 
	{
		double a[][] = {{1.0,2.0} , {2.0,6.0} , {4.0,6.0} , {5.0,10.0}};
		double b[][] = {{1.0,5.0}, { 5.0,7.0 }, { 4.0,9.0 }, { 2.0, 7.0}};
		double distance = (double) calcDistance(a,b);
	}
        static float OFFSET_PENALTY = .5f;
        
        // This method is used to acquire the unit vector
        static private float normaliseVector(ArrayList<Double> differenceVector)
        {
        	double sum=0;
        	for(Double VectorComponent : differenceVector)
        	{
        		sum += Math.pow(VectorComponent, 2);
        	}
            System.out.println (Math.pow(sum, 0.5));
            return (float) Math.pow(sum, 0.5);
        }

        static public float calcDistance(double a[][], double b [][]) 
        {
        		/* gestureDimension refers to the axis components in which we will compare the signal.The 
        		 * accelerometer provides data in x,y and z axis, thus its value is 3*/
        		int gestureDimension = a[0].length;
        		System.out.println("gestureDimension" + gestureDimension);
                int gesture1Length = a.length;
                int gesture2Length = b.length;
                
                // distMatrix determines the distance between the two vectors at each time interval.
                float distMatrix[][];
                // costmatrix determines the least expensive path
                float costMatrix[][];

                distMatrix = new float[gesture1Length][];
                costMatrix = new float[gesture2Length][];
                
                // creating matrix thats is gesture1Length x gesture2Length size
                for (int i = 0; i < gesture1Length; ++i) {
                        distMatrix[i] = new float[gesture2Length];
                        costMatrix[i] = new float[gesture2Length];
                }
                

                ArrayList<Double> differenceVector;

                // calculate dis
                for (int i = 0; i < gesture1Length; ++i) {
                        for (int j = 0; j < gesture2Length; ++j) { 
                        	differenceVector = new ArrayList<Double>();
                        	for(int k=0; k<gestureDimension ; ++k)
                        	{
                        		differenceVector.add((double) (a[i][k] - b[j][k]));
                        	}   
                                distMatrix[i][j] = normaliseVector(differenceVector);
                        }
                }

                // The first column of the cost matrix is the same as the first column of the distance matrix
                for (int i = 0; i < gesture1Length; ++i) {
                        costMatrix[i][0] = distMatrix[i][0];
                        System.out.println(" costMatrix[i][j] " + costMatrix[i][0]);
                }
                
             // The algorithm below is used to create the remaining column of the cost matrix
                for (int j = 1; j < gesture2Length; ++j) {
                        for (int i = 0; i < gesture1Length; ++i) {
                                if (i == 0) {
                                        costMatrix[i][j] = costMatrix[i][j - 1] + distMatrix[i][j];
                                       System.out.println("costmatrix[0][j] " + costMatrix[i][j] );
                                } else {
                                        float minCost, cost;
                                        // i-1, j-1
                                        minCost = costMatrix[i - 1][j - 1] + distMatrix[i][j];
                                        // i-1, j
                                        if ((cost = costMatrix[i - 1][j] + distMatrix[i][j]) < minCost) {
                                                minCost = cost + OFFSET_PENALTY;
                                        }
                                        // i, j-1
                                        if ((cost = costMatrix[i][j - 1] + distMatrix[i][j]) < minCost) {
                                                minCost = cost + OFFSET_PENALTY;
                                        }
                                        costMatrix[i][j] = minCost;
                                        System.out.println("costMatrix[i][j] " + costMatrix[i][j] + " i=" + i + " j=" + j );
                                }
                        }
                }
                System.out.println("costMatrix[gesture1Length - 1][gesture2Length - 1]"  + costMatrix[gesture1Length - 1][gesture2Length - 1]);
                // the distance between gesture a and b is the end'th value of the cost matrix
                return costMatrix[gesture1Length - 1][gesture2Length - 1];
        }

}