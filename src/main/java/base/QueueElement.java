package base;

public class QueueElement<T> implements Comparable<QueueElement<T>> {
    private final T element;
    private final int priority;

    public QueueElement(T element, int priority) {
        this.element = element;
        this.priority = priority;
    }

    @Override
    public int compareTo(QueueElement<T> o) {
        return Integer.compare(priority, o.priority);
    }

    public T getElement() {
        return element;
    }
}
