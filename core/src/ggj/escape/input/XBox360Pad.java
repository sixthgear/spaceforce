package ggj.escape.input;

// This code was taken from http://www.java-gaming.org/index.php?topic=29223.0
// With thanks that is!

public class XBox360Pad
{
    /*
     * It seems there are different versions of gamepads with different ID
     Strings.
     * Therefore its IMO a better bet to check for:
     * if (controller.getName().toLowerCase().contains("xbox") &&
                   controller.getName().contains("360"))
     *
     * Controller (Gamepad for Xbox 360)
       Controller (XBOX 360 For Windows)
       Controller (Xbox 360 Wireless Receiver for Windows)
       Controller (Xbox wireless receiver for windows)
       XBOX 360 For Windows (Controller)
       Xbox 360 Wireless Receiver
       Xbox Receiver for Windows (Wireless Controller)
       Xbox wireless receiver for windows (Controller)
     */
    //public static final String ID = "XBOX 360 For Windows (Controller)";
    public static final int BUTTON_X = 13;
    public static final int BUTTON_Y = 14;
    public static final int BUTTON_A = 11;
    public static final int BUTTON_B = 12;
    public static final int BUTTON_BACK = 5;
    public static final int BUTTON_START = 4;
    public static final int BUTTON_GUIDE = 10;
    public static final int BUTTON_DPAD_UP = 0;
    public static final int BUTTON_DPAD_DOWN = 1;
    public static final int BUTTON_DPAD_LEFT = 2;
    public static final int BUTTON_DPAD_RIGHT = 3;
    public static final int BUTTON_LB = 8;
    public static final int BUTTON_L3 = 6;
    public static final int BUTTON_RB = 9;
    public static final int BUTTON_R3 = 7;
    public static final int AXIS_LEFT_X = 1; //-1 is left | +1 is right
    public static final int AXIS_LEFT_Y = 0; //-1 is up | +1 is down
    public static final int AXIS_LEFT_TRIGGER = 4; //value 0 to 1f
    public static final int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
    public static final int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
    public static final int AXIS_RIGHT_TRIGGER = 4; //value 0 to -1f
}

//Controllers.addListener(this);
//, ControllerListener
//// connected and disconnect dont actually appear to work for XBox 360 controllers.
//@Override
//public void connected(Controller controller) {
//        System.out.println("Controlled Connected");
//        }
//
//@Override
//public void disconnected(Controller controller) {
//        System.out.println("Controlled Disconnected");
//        }
//
//@Override
//public boolean buttonDown(Controller controller, int buttonCode) {
//
//        if(buttonCode == XBox360Pad.BUTTON_Y)
//        System.out.println("Y");
//        else if(buttonCode == XBox360Pad.BUTTON_A)
//        System.out.println("A");
//        else if(buttonCode == XBox360Pad.BUTTON_X)
//        System.out.println("X");
//        else if(buttonCode == XBox360Pad.BUTTON_B)
//        System.out.println("B");
//        else if(buttonCode == XBox360Pad.BUTTON_LB)
//        System.out.println("LB");
//        else if(buttonCode == XBox360Pad.BUTTON_RB)
//        System.out.println("RB");
//        else if(buttonCode == XBox360Pad.BUTTON_L3)
//        System.out.println("L3");
//        else if(buttonCode == XBox360Pad.BUTTON_R3)
//        System.out.println("R3");
//        else if(buttonCode == XBox360Pad.BUTTON_GUIDE)
//        System.out.println("GUIDE");
//        else if(buttonCode == XBox360Pad.BUTTON_BACK)
//        System.out.println("BACK");
//        else if(buttonCode == XBox360Pad.BUTTON_START)
//        System.out.println("START");
//        else if(buttonCode == XBox360Pad.BUTTON_DPAD_LEFT)
//        System.out.println("L");
//        else if(buttonCode == XBox360Pad.BUTTON_DPAD_RIGHT)
//        System.out.println("R");
//        else if(buttonCode == XBox360Pad.BUTTON_DPAD_UP)
//        System.out.println("U");
//        else if(buttonCode == XBox360Pad.BUTTON_DPAD_DOWN)
//        System.out.println("D");
//        else
//        System.out.println(buttonCode);
//
//        return false;
//        }
//
//@Override
//public boolean buttonUp(Controller controller, int buttonCode) {
//        return false;
//        }
//
//@Override
//public boolean axisMoved(Controller controller, int axisCode, float value) {
//        // This is your analog stick
//        // Value will be from -1 to 1 depending how far left/right, up/down the stick is
//        // For the Y translation, I use a negative because I like inverted analog stick
//        // Like all normal people do! ;)
//
////        // Left Stick
////        if(axisCode == XBox360Pad.AXIS_LEFT_X)
////            System.out.printf("%d: %f\n", axisCode, value);
////        if(axisCode == XBox360Pad.AXIS_LEFT_Y)
////            System.out.printf("%d: %f\n", axisCode, value);
////
////        // Right stick
////        if(axisCode == XBox360Pad.AXIS_RIGHT_X)
////            System.out.printf("%d: %f\n", axisCode, value);
////        if(axisCode == XBox360Pad.AXIS_RIGHT_Y)
////            System.out.printf("%d: %f\n", axisCode, value);
//
//        return false;
//        }
//
//@Override
//public boolean povMoved(Controller controller, int povCode, PovDirection value) {
//        // This is the dpad
//        return false;
//        }
//
//@Override
//public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
//        return false;
//        }
//
//@Override
//public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
//        return false;
//        }
//
//@Override
//public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
//        return false;
//        }
