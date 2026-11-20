package org.firstinspires.ftc.teamcode.TeleOp.PollenBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "PollenBot")
public class PollenBotTeleOp extends OpMode{
    private final PollenHardware robot = new PollenHardware();

    private boolean directionalToggle;
    private boolean previousDirectional;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        updateDrive();
        updateIntake();
        updateFlywheel();
    }

    public void intakeSwitch(float m) {
        if (m > 0.06) {
            robot.intake1.setPower(m);
            robot.intake2.setPower(m);
        } else if (m < 0.06) {
            robot.intake1.setPower(0);
            robot.intake2.setPower(0);
        }
    }


    public void updateDrive() {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  -gamepad1.right_stick_x;

        // Toggle direction mode when left bumper is pressed
        if (gamepad1.left_bumper && !previousDirectional) {
            directionalToggle = !directionalToggle;
        }
        previousDirectional = gamepad1.left_bumper;

        // Apply direction toggle to movement inputs (but not yaw)
        if (directionalToggle) {
            axial = -axial;
            lateral = -lateral;
        }

        // Calculate motor powers
        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral - yaw;

        // Normalize powers if any exceed 1.0
        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        // Send calculated power to wheels every frame
        robot.fl.setPower(frontLeftPower);
        robot.fr.setPower(frontRightPower);
        robot.bl.setPower(backLeftPower);
        robot.br.setPower(backRightPower);
    }

    public void updateIntake() {
        if (gamepad1.left_trigger > 0.01) {
            intakeSwitch(gamepad1.left_trigger);
        } else {
            intakeSwitch(0);
        }
    }

    public void updateFlywheel() {
        if (gamepad1.right_trigger > 0.01) {
            robot.gate.setPosition(0.1);
            robot.flywheel1.setPower(0.75);
            robot.flywheel2.setPower(0.75);
            intakeSwitch(0.6f);
        } else {
            robot.gate.setPosition(0.15);
            robot.flywheel1.setPower(0);
            robot.flywheel2.setPower(0);
            intakeSwitch(0);
        }
    }


}
