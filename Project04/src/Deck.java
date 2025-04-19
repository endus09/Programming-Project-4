// @author Markus Gulla

import java.util.*;

public class Deck
{

    Stack<String> drawPile = new Stack<>();
    Stack<String> discardPile = new Stack<>();
    List<String> list = new ArrayList<>();

    public Deck(String[] s)
    {
        for (int i = 0; i < s.length; i++)
        {
            this.drawPile.push(s[i]);
        }
        shuffle(); // shuffles draw pile initially
    }

    public void addCard(String s)
    {
        this.drawPile.push(s);
    }

    public void shuffle()
    {
        while (this.drawPile.isEmpty() == false)
        {
            list.add(drawPile.pop());
        }
        Collections.shuffle(list);

        for (int i = this.list.size(); i > 0; i--)
        {
            drawPile.push(this.list.remove(i - 1));
        }
    }

    public String draw()
    {

        if (this.drawPile.isEmpty() && this.discardPile.isEmpty())
        {
            System.out.println("CANNOT DRAW CARD");
            return null;
        }

        if (this.drawPile.isEmpty() == true)
        {
            while (this.discardPile.isEmpty() == false)
            {
                drawPile.push(discardPile.pop());
            }
            shuffle();
            System.out.println("CARDS SHUFFLED");
        }
        String card = this.drawPile.pop();

        return card;
    }

    public void discard(String s)
    {
        discardPile.push(s);
    }

    @Override
    public String toString()
    {
        return "Draw Pile: " + drawPile.toString() + "\n" + "Discard Pile: " + discardPile.toString();
    }

}
