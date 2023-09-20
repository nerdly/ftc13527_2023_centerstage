package org.firstinspires.ftc.teamcode;

public class Constants {


    public static final class ArmConstants {
        public static final double kPivotServoOffset = 0;
        public static final double k_grabberClosePosition = 0.60;
        public static final double k_grabberOpenPosition = 0.75;
        public static final double k_armFrontPosition = 0.91;
        public static final double k_armBackPosition = 0.1;//.1 on 16646 A, .09 on 16646 B

        public static final int k_armFrontID = 3;
        public static final int k_armBackID = 1;
    }

    public static final class ElevatorConstants {
        public static final int k_homeLevelID = 0;
        public static final int k_lowLevelID = 1;
        public static final int k_midLevelID = 2;
        public static final int k_highLevelID = 3;
        public static final int k_intakeLevelID = 4;
        public static final int k_groundLevelID = 5;

        public static final int k_homeLevelEncoderPosition = 0;
        public static final int k_lowLevelEncoderPosition = 925;//925 on 16646 A, 1150 on 16646 B
        public static final int k_midLevelEncoderPosition = 1525;//1525 on 16646 A, 1600 on 16646 B
        public static final int k_highLevelEncoderPosition = 2160;//2160 on 16646 A, 2220 on 16646 B
        public static final int k_intakeLevelEncoderPosition = 300;
        public static final int k_groundLevelEncoderPosition = 310;

        public static final int k_safetyEncoderPosition = 220;
    }
}

