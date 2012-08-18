/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/18/12
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */

public class QuickFind extends UnionFind
{
    public QuickFind (int n)
    {
        id = new int[n];
        for (int i = 0; i < n; i++)
        {
            id[i] = i;
        }
    }

    public boolean connected (int p, int q)
    {
        return id[p] == id[q];
    }

    public void union (int p, int q)
    {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++)
        {
            if (id[i] == pid)
            {
                id[i] = qid;
            }
        }
    }
}
