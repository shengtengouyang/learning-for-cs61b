// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first=0;
        last=0;
        fillCount=0;
        this.capacity=capacity;
        rb=(T[])new Object[capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterater();
    }
    private class ArrayIterater implements Iterator<T>{
        private int position;
        public ArrayIterater(){
            position=0;
        }
        @Override
        public boolean hasNext() {
            return position<capacity;
        }

        @Override
        public T next() {
            int current=(first+position)%capacity;
            T item=rb[current];
            position++;
            return item;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if(isFull()){
            throw new RuntimeException("Ring buffer overflow");
        }
        fillCount+=1;
        rb[last]=x;
        last=last+1;
        if (last==capacity){
            last=0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if(isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        fillCount-=1;
        T item=rb[first];
        rb[first]=null;
        first=first+1;
        if (first==capacity){
            first=0;
        }
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if(isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }
}
