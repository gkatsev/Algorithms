/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/18/12
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class UnionFind
{
    protected int[] id;

    public abstract boolean connected(int p, int q);
    public abstract void union(int p, int q);

    public String getString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < id.length; i++)
        {
            sb.append(id[i] + " ");
        }
        return sb.toString();
    }
}
