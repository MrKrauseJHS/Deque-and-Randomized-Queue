import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Card Dealer: deal k distinct random cards from a deck of strings read from standard input.
 *
 * This client uses reservoir sampling, so it does not need to know how many cards
 * are in the deck before it starts dealing. Its only storage is one
 * RandomizedQueue containing at most k cards.
 */
public class Permutation {

    /**
     * Read card names from standard input and print exactly k distinct cards
     * uniformly at random, one per line.
     *
     * @param args command-line arguments; args[0] is the number k of cards to deal
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> hand = new RandomizedQueue<String>();
        int cardsSeen = 0;

        // Deal with each arriving card in turn. Temporarily placing the new card
        // in the queue and randomly discarding one when over capacity performs
        // reservoir sampling: after i cards, every seen card has probability
        // min(k, i) / i of being in the reservoir.
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String card = scanner.next();
            cardsSeen++;

            if (k > 0) {
                hand.enqueue(card);
                if (hand.size() > k) {
                    // RandomizedQueue.dequeue() chooses uniformly among the
                    // k + 1 cards, so each card has the correct chance to stay.
                    hand.dequeue();
                }
            }
        }

        // A valid input has k <= the number of cards read, so the reservoir
        // contains exactly k cards. Its iterator deals them in random order.
        Iterator<String> dealtCards = hand.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(dealtCards.next());
        }
    }
}
