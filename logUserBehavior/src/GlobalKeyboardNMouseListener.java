import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyboardNMouseListener implements NativeKeyListener, NativeMouseInputListener {
    public boolean key = false;
//    public boolean mouseMoved = false;
    public boolean mousePressed = false;

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }

    public static void main(String[] args) throws IOException {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
        GlobalScreen.addNativeKeyListener(new GlobalKeyboardNMouseListener());
        GlobalKeyboardNMouseListener example = new GlobalKeyboardNMouseListener();
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(example);
        GlobalScreen.addNativeMouseMotionListener(example);
    }

    private void record(String str){
        try {
            FileWriter pw = new FileWriter(new File("test.csv"), true);
            StringBuilder sb = new StringBuilder();
            sb.append(getCurrentTimeStamp());
            sb.append(',');
            if (key){
                sb.append("Keyboard Pressed:");
                key = false;
            }
            if (mousePressed){
                sb.append("Mouse Pressed");
                mousePressed = false;
            }
//            if (mouseMoved){
//                sb.append("Mouse Moved");
//                mouseMoved = false;
//            }
            sb.append(',');
            sb.append(str);
            sb.append('\n');
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
//        System.out.println(getCurrentTimeStamp() + " Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        String str = NativeKeyEvent.getKeyText(e.getKeyCode());
        key = true;
        record(str);
//        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
//            try {
//                GlobalScreen.unregisterNativeHook();
//            } catch (NativeHookException e1) {
//                e1.printStackTrace();
//            }
//        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
//        System.out.println(getCurrentTimeStamp() + " Mouse Moved: " + e.getX() + ", " + e.getY());
//        mouseMoved = true;
//        String str = e.getX() + ", " + e.getY();
//        record(str);
    }

    public void nativeMousePressed(NativeMouseEvent e) {
//        System.out.println(getCurrentTimeStamp() + " Mouse Pressed: " + e.getButton());
        mousePressed = true;
        String str = Integer.toString(e.getButton());
        record(str);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
//        setString("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
//        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
//        setString("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
//        System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
//        System.out.println("Mouse Released: " + e.getButton());
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
//        System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
}