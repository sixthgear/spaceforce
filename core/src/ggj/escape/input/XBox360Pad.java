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
    public static final int AXIS_LEFT_X = 2; //-1 is left | +1 is right
    public static final int AXIS_LEFT_Y = 3; //-1 is up | +1 is down
    public static final int AXIS_LEFT_TRIGGER = 0; //value 0 to 1f
    public static final int AXIS_RIGHT_X = 4; //-1 is left | +1 is right
    public static final int AXIS_RIGHT_Y = 5; //-1 is up | +1 is down
    public static final int AXIS_RIGHT_TRIGGER = 1; //value 0 to -1f
}

