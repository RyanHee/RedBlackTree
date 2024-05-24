public class BinaryNode{
    private BinaryNode left;
    private BinaryNode right;
    private Comparable value;

    public BinaryNode(Comparable val){
        value=val;
        left=null;
        right=null;
    }

    public void setRight(BinaryNode r){
        right=r;
    }
    public void setLeft(BinaryNode l){
        left=l;
    }

    public BinaryNode left(){
        return left;
    }

    public BinaryNode right(){
        return right;
    }
    public Comparable value(){
        return value;
    }

    public void setValue(Comparable x){
        value=x;
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
