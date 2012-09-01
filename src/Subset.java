/**
 * User: Gary Katsevman
 * Date: 8/31/12
 * Time: 11:30 PM
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String[] inputs = StdIn.readStrings();
        StdRandom.shuffle(inputs);
        for (int i = 0; i < k; i++) {
            StdOut.println(inputs[i]);
        }
    }
}
