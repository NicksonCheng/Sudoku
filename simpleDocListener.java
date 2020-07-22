import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface simpleDocListener extends DocumentListener {
    abstract void update(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {

    }

    @Override
    default void removeUpdate(DocumentEvent e) {

    }

    @Override
    default void changedUpdate(DocumentEvent e) {
        System.out.println(e);
        update(e);
    }
}