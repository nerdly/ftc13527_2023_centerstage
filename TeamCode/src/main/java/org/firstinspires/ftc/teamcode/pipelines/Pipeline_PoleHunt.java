package org.firstinspires.ftc.teamcode.pipelines;

import android.util.Log;

import org.firstinspires.ftc.teamcode.util.PolePosition;
import org.firstinspires.ftc.teamcode.util.PolePositionFinder;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Pipeline_PoleHunt extends OpenCvPipeline {

        private PolePositionFinder ppf = new PolePositionFinder();
        private PolePosition pp = null;

        public PolePosition getPolePosition(){
            return pp;
        }

        @Override
        public Mat processFrame(Mat input) {
            Log.d("SUB_OpenCVBase.PoleHuntPipeline", "Processing Frame");
            pp = ppf.findPolePosition(input);
            int linespot = (input.width() / 2) + pp.getOffsetFromCenter();
            Imgproc.line(input, new Point(linespot, 0), new Point(linespot, input.height()), new Scalar( 255, 0,0));
            return input;
        }


}
