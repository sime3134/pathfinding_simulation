package base;

import java.util.Objects;
import java.util.PriorityQueue;

public class CustomPriorityQueue<T> {
    private PriorityQueue<QueueElement<T>> queue = new PriorityQueue<>();

    public void add(T element, int priority) {
        queue.add(new QueueElement<>(element, priority));
    }

    public T poll() {
        return Objects.requireNonNull(queue.poll()).getElement();
    }

    public void clear() {
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
