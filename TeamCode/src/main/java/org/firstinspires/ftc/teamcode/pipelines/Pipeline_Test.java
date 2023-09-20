package org.firstinspires.ftc.teamcode.pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.GlobalVariables;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Pipeline_Test extends OpenCvPipeline {
     GlobalVariables constants = new GlobalVariables();
     public static GlobalVariables.ObjectPos pos;
    /*
        Detector for Power Play autonomous
        This pipeline detect one of three difference possible color at a single location
        we choose three different colors that are distinguishable from each other
        the location is define as a rectangle box REGION1

     */

     static private int m_frameWidth = 640;
     static private int m_frameHeight = 480;

     /*
      * Some color constants
      */
     static final Scalar BLUE = new Scalar(0, 0, 255);
     static final Scalar GREEN = new Scalar(0, 255, 0);

     /*
      * The core values which define the location and size of the sample region
      */

     static final int REGION_WIDTH = 30;
     static final int REGION_HEIGHT = 50;
     static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point((m_frameWidth + REGION_WIDTH) / 2 - 100,
             (m_frameHeight + REGION_HEIGHT) / 2);

     Point region1_pointA = new Point(
             REGION1_TOPLEFT_ANCHOR_POINT.x,
             REGION1_TOPLEFT_ANCHOR_POINT.y);
     Point region1_pointB = new Point(
             REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
             REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

     //region 2
     static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point((m_frameWidth + REGION_WIDTH) / 2,
             (m_frameHeight + REGION_HEIGHT) / 2);

     Point region2_pointA = new Point(
             REGION2_TOPLEFT_ANCHOR_POINT.x,
             REGION2_TOPLEFT_ANCHOR_POINT.y);
     Point region2_pointB = new Point(
             REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
             REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
     static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point((m_frameWidth + REGION_WIDTH) / 2 + 100,
             (m_frameHeight + REGION_HEIGHT) / 2);

     //region 3
     Point region3_pointA = new Point(
             REGION3_TOPLEFT_ANCHOR_POINT.x,
             REGION3_TOPLEFT_ANCHOR_POINT.y);
     Point region3_pointB = new Point(
             REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
             REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);


     /*
      * Working variables
      */

     Mat region1_Cb;
     Mat region2_Cb;
     Mat region3_Cb;
     Mat YCrCb = new Mat();
     Mat Cb = new Mat();

     int avg1;
     int avg2;
     int avg3;

     // Volatile since accessed by OpMode thread w/o synchronization
     private volatile int position = 3;

     /*
      * This function takes the RGB frame, converts to YCrCb,
      * and extracts the Cb channel to the 'Cb' variable
      * For channel, 1 is Cr, 2 is Cb
      */
     void inputToCb(Mat input, int channel) {
          Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
          Core.extractChannel(YCrCb, Cb, channel);
     }


     @Override
     public void init(Mat firstFrame) {
          /*
           * We need to call this in order to make sure the 'Cb'
           * object is initialized, so that the submats we make
           * will still be linked to it on subsequent frames. (If
           * the object were to only be initialized in processFrame,
           * then the submats would become delinked because the backing
           * buffer would be re-allocated the first time a real frame
           * was crunched)
           */
          inputToCb(firstFrame, 1);

          /*
           * Submats are a persistent reference to a region of the parent
           * buffer. Any changes to the child affect the parent, and the
           * reverse also holds true.
           */
          region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
          region2_Cb = Cb.submat(new Rect(region2_pointA, region2_pointB));
          region3_Cb = Cb.submat(new Rect(region3_pointA, region3_pointB));

     }

     @Override
     public Mat processFrame(Mat input) {
          /*
           * Overview of what we're doing:
           *
           * We first convert to YCrCb color space, from RGB color space.
           * Why do we do this? Well, in the RGB color space, chroma and
           * luma are intertwined. In YCrCb, chroma and luma are separated.
           * YCrCb is a 3-channel color space, just like RGB. YCrCb's 3 channels
           * are Y, the luma channel (which essentially just a B&W image), the
           * Cr channel, which records the difference from red, and the Cb channel,
           * which records the difference from blue. Because chroma and luma are
           * not related in YCrCb, vision code written to look for certain values
           * in the Cr/Cb channels will not be severely affected by differing
           * light intensity, since that difference would most likely just be
           * reflected in the Y channel.
           *
           * After we've converted to YCrCb, we extract just the 2nd channel, the
           * Cb channel. We do this because stones are bright yellow and contrast
           * STRONGLY on the Cb channel against everything else, including SkyStones
           * (because SkyStones have a black label).
           *
           * We then take the average pixel value of 3 different regions on that Cb
           * channel, one positioned over each stone. The brightest of the 3 regions
           * is where we assume the SkyStone to be, since the normal stones show up
           * extremely darkly.
           *
           * We also draw rectangles on the screen showing where the sample regions
           * are, as well as drawing a solid rectangle over top the sample region
           * we believe is on top of the SkyStone.
           *
           * In order for this whole process to work correctly, each sample region
           * should be positioned in the center of each of the first 3 stones, and
           * be small enough such that only the stone is sampled, and not any of the
           * surroundings.
           */


          /*
           * Get the Cb channel of the input frame after conversion to YCrCb
           */
          inputToCb(input, 2);

          /*
           * Compute the average pixel value of each submat region. We're
           * taking the average of a single channel buffer, so the value
           * we need is at index 0. We could have also taken the average
           * pixel value of the 3-channel image, and referenced the value
           * at index 2 here.
           */
          avg1 = (int) Core.mean(region1_Cb).val[0];
          avg2 = (int) Core.mean(region2_Cb).val[0];
          avg3 = (int) Core.mean(region3_Cb).val[0];
          boolean rect1 = false;
          boolean rect2 = false;
          boolean rect3 = false;
          /*
           * Draw a rectangle showing sample region 1 on the screen.
           * Simply a visual aid. Serves no functional purpose.
           */
          //draw 3 rectangles
          Imgproc.rectangle(
                  input, // Buffer to draw on
                  region1_pointA, // First point which defines the rectangle
                  region1_pointB, // Second point which defines the rectangle
                  BLUE, // The color the rectangle is drawn in
                  2); // Thickness of the rectangle lines

          Imgproc.rectangle(
                  input,
                  region2_pointA,
                  region2_pointB,
                  BLUE,
                  2);

          Imgproc.rectangle(
                  input,
                  region3_pointA,
                  region3_pointB,
                  BLUE,
                  2);

          if (avg1 > 120 && avg1 < 140) {
               Imgproc.rectangle(
                       input,
                       region1_pointA,
                       region1_pointB,
                       GREEN,
                       -1);
               rect1 = true;
          } else {
               rect1 = false;
          }
          if (avg2 > 120 && avg2 < 140) {
               Imgproc.rectangle(
                       input,
                       region2_pointA,
                       region2_pointB,
                       GREEN,
                       -1);
               rect2 = true;
          } else {
               rect2 = false;
          }
          if (avg3 > 120 && avg3 < 133) {
               Imgproc.rectangle(
                       input,
                       region3_pointA,
                       region3_pointB,
                       GREEN,
                       -1);
               rect3 = true;
          } else {
               rect3 = false;
          }

          if (rect1 && rect2 && rect3) {
               pos = GlobalVariables.ObjectPos.ALL;
          } else if (rect1 && rect2) {
               pos = GlobalVariables.ObjectPos.LEFT2;
          } else if (rect2 && rect3) {
               pos = GlobalVariables.ObjectPos.RIGHT2;
          } else if (rect1 && !rect3) {
               pos = GlobalVariables.ObjectPos.LEFT;
          } else if (rect3 && ! rect1) {
               pos = GlobalVariables.ObjectPos.RIGHT;
          } else {
               pos = GlobalVariables.ObjectPos.NONE;
          }

          /*
           * Render the 'input' buffer to the viewport. But note this is not
           * simply rendering the raw camera feed, because we called functions
           * to add some annotations to this buffer earlier up.
           */
          GlobalVariables.setObjectPos(pos);
          return input;
     }

     /*
      * Call this from the OpMode thread to obtain the latest analysis
      */

     public int getAvg1() {
          return avg1;
     }

     public int getAvg2() {
          return avg2;
     }

     public int getAvg3() {
          return avg3;
     }

     public int getAnalysis() {
          return 1;
     }

     public GlobalVariables.ObjectPos getPos(){
          return pos;
     }
}
