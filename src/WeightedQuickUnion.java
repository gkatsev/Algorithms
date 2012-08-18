/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/18/12
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */

public class WeightedQuickUnion extends UnionFind
{
    private int[] sz;
    private boolean path;

    public WeightedQuickUnion (int n)
    {
        this(n, false);
    }

    public WeightedQuickUnion (int n, boolean path)
    {
        this.path = path;
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++)
        {
            id[i] = i;
            sz[i] = 1;
        }
    }

    private int root (int i)
    {
        while (i != id[i])
        {
            if (path)
            {
                id[i] = id[id[i]];
            }
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
        if (sz[i] < sz[j])
        {
            id[i] = j;
            sz[j] += sz[i];
        } else
        {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
}
