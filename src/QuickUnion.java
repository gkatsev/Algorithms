/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/18/12
 * Time: 12:36 AM
 * To change this template use File | Settings | File Templates.
 */

public class QuickUnion extends UnionFind
{

    public QuickUnion (int n)
    {
        id = new int[n];
        for (int i = 0; i < n; i++)
        {
            id[i] = i;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }
    public boolean connected (int p, int q)
    {
        return root(p) == root(q);
    }

    public void union (int p, int q)
    {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
}
