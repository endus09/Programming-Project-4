// @author Markus Gulla

public class Stack<T>
{

    //attributes
    private T[] stkarr;
    private int top;
    private int size = 10;

    // behaviors
    public Stack()
    {
        stkarr = (T[]) new Object[size];
        top = 0;
    }

    // push
    public void push(T n)
    {
        if (top == stkarr.length)
        {
            T[] grow = (T[]) new Object[stkarr.length + 10];
            System.arraycopy(stkarr, 0, grow, 0, stkarr.length);
            stkarr = grow;
        }
        stkarr[top++] = n;
    }

    // pop
    public T pop()
    {
        if (top == 0)
        {
            throw new IllegalStateException("Stack is empty");
        }
        if (stkarr.length <= 10)
        {
            return stkarr[--top];
        } else if (top < stkarr.length / 4)
        {
            T[] shrink = (T[]) new Object[stkarr.length / 2];
            System.arraycopy(stkarr, 0, shrink, 0, shrink.length);
            stkarr = shrink;
        }
        return stkarr[--top];
    }

    // checks for empty
    public boolean isEmpty()
    {
        return top == 0;
    }

    // checks for size
    public int size()
    {
        return top;
    }

    // toString
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(stkarr[0]);
        for (int i = 1; i < top; i++)
        {
            sb.append(", ").append(stkarr[i]);
        }
        return "[" + sb.toString() + "]";
    }
}

