package org.firstinspires.ftc.teamcode.util;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds the width and position of the pole in the camera view.
 */
public class PolePositionFinder {
     Size kernelsize = new Size(15,15);
     // Create a set of masks to identify the yellow areas.
     Scalar redtop = new Scalar(60,0, 0);
     Scalar redbottom = new Scalar(0, 0, 0);

    public MatOfPoint  findLargestContour(Mat image)
    {
         // transform the image data into yCrCb color format.
         Mat yCrCb = new Mat();
         Imgproc.cvtColor(image, yCrCb, Imgproc.COLOR_BGR2YCrCb);
 
         // Extract the red channel and save for reference.
         //Mat red = new Mat();
         Core.extractChannel(yCrCb, yCrCb, 2);
 
         // Apply the masks to find the yellow areas.
         //Mat ycrcbmask = new Mat();
         Core.inRange(yCrCb, redbottom, redtop, yCrCb);
 
         // Blur the edges to reduce the number of contours found
         //Mat blurred = new Mat();

         Imgproc.GaussianBlur(yCrCb, yCrCb, kernelsize, 0);
 
         // Find thresholds to be used for finding contours.
         Mat threshold = new Mat();
         Imgproc.threshold(yCrCb, threshold,  127, 255, Imgproc.THRESH_BINARY);
 
         // Find the contours in the image.
         List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
         Imgproc.findContours(yCrCb, contours, threshold, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
         System.out.println("Found " + contours.size() + " Contours");
 
         // find biggest contour
         int indexOfMaxArea = 0;
         double maxArea = 0;

         if(contours.size() == 0){
              return null;
         }
         
         for(int i = 0; i < contours.size(); i++)
         {   
            // Get area of current contour
             double areaOfCurrentContour = Imgproc.contourArea(contours.get(i));

             // Is the area bigger than the largest area we know about?
             if(areaOfCurrentContour > maxArea)
             {
                // Save the max and area of the new largest contour
                 maxArea = areaOfCurrentContour;
                 indexOfMaxArea = i;
             }
         }

        return contours.get(indexOfMaxArea);
    }

    /**
     * Given a Mat with an image, find the position of the largest pole.
     * @param image The image mat to find the position in.
     * @return A PolePosition object that contains the position of the largest pole.
     */
    public PolePosition findPolePosition(Mat image)
    {
        MatOfPoint largestContour = findLargestContour(image);

        if (largestContour == null){
             return new PolePosition(-1,0);
        }
        
        // Find the bounding rectangle of the largest contour. Does not take angle into account at this time.
        Rect bounds = Imgproc.boundingRect(largestContour);
        
        // Find center of biggest contour
        int center = bounds.x + (bounds.width/2);
        int centerOfImage = image.width() / 2;
        int offset = center - centerOfImage;

        return new PolePosition(bounds.width, offset);
    }
}
