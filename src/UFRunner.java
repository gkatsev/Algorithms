/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/18/12
 * Time: 12:34 AM
 * To change this template use File | Settings | File Templates.
 */

public class UFRunner
{
    public static void main (String[] args)
    {
        int n = StdIn.readInt();
        StdIn.readChar();
        char type = StdIn.readChar();
        StdOut.println(n + " " + type);
        UnionFind uf;
        switch (type)
        {
            case 'Q': uf = new QuickFind(n); break;
            case 'U': uf = new QuickUnion(n); break;
            case 'W': uf = new WeightedQuickUnion(n); break;
            default: uf = new QuickFind(n);
        }
        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (p == -1 && q == -1)
            {
                break;
            }

            if (!uf.connected(p, q))
            {
                uf.union(p, q);
                StdOut.println("union " + p + " " + q);
            } else
            {
                StdOut.println("already existed " + p + " " + q);
            }
        }

        StdOut.println(uf.getString());
    }
}
