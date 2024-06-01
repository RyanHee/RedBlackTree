public class BinaryNode{
    public BinaryNode left;
    public BinaryNode right;
    public Comparable value;

    public BinaryNode(Comparable val){
        value=val;
        left=null;
        right=null;
    }







    public String toString(){
        return (String) value;
    }

    public int childCount(){
        int cnt=0;
        if (left!=null)cnt++;
        if (right!=null)cnt++;
        return cnt;
    }

    public boolean isLeft(BinaryNode n){
        if (left==n)return true;
        return false;
    }


}
