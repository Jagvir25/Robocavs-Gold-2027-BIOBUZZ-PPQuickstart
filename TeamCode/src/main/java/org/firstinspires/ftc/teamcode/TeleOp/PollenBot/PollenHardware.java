package org.firstinspires.ftc.teamcode.TeleOp.PollenBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PollenHardware {
    public ElapsedTime runtime = new ElapsedTime();

    public DcMotorEx bl, br, fl, fr;
    public DcMotorEx intake1, intake2, flywheel1, flywheel2;
    public Servo gate;
    public HardwareMap hardwareMap;

    public PollenHardware() {

    }

    public void init (HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        bl = hardwareMap.get(DcMotorEx.class, "chm0");
        br = hardwareMap.get(DcMotorEx.class, "chm1");
        fl = hardwareMap.get(DcMotorEx.class, "chm2");
        fr = hardwareMap.get(DcMotorEx.class, "chm3");
        intake1 = hardwareMap.get(DcMotorEx.class, "ehm0");
        intake2 = hardwareMap.get(DcMotorEx.class, "ehm1");
        flywheel1 = hardwareMap.get(DcMotorEx.class, "ehm2");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "ehm3");

        gate = hardwareMap.get(Servo.class, "chs0");

        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        bl.setDirection(DcMotorEx.Direction.FORWARD);
        br.setDirection(DcMotorEx.Direction.REVERSE);
        fl.setDirection(DcMotorEx.Direction.FORWARD);
        fr.setDirection(DcMotorEx.Direction.REVERSE);

        flywheel1.setDirection(DcMotorEx.Direction.REVERSE);

        flywheel2.setDirection(DcMotorEx.Direction.FORWARD);
    }
    public static double headingPos(double targetPoseX, double targetPoseY, double currX, double currY) {
        double deltaX = targetPoseX - currX;
        double deltaY = targetPoseY - currY;
        return Math.atan(deltaY / deltaX);
    }
}
