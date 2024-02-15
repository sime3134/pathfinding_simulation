import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Handles input from mouse and keyboard.
 */
public class Input implements KeyListener {

    private static Input input;

    private final boolean[] currentlyPressed;
    private final boolean[] pressed;
    private final List<Integer> typedKeyBuffer;
    
    //region getters and setters (click to view)
    public static Input getInstance() {
        if(input == null){
            input = new Input();
        }
        return input;
    }

    public List<Integer> getTypedKeyBuffer() {
        return typedKeyBuffer;
    }

    //endregion

    private Input() {
        currentlyPressed = new boolean[1000];
        pressed = new boolean[1000];
        typedKeyBuffer = new ArrayList<>();
    }

    public boolean isKeyPressed(int keyCode) {
        if(!pressed[keyCode] && currentlyPressed[keyCode]) {
            pressed[keyCode] = true;
            return true;
        }

        return false;
    }

    public boolean isKeyCurrentlyPressed(int keyCode) {
        return currentlyPressed[keyCode];
    }

    public void cleanUp() {
        typedKeyBuffer.clear();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;
        typedKeyBuffer.add(e.getKeyCode());
    }
}
