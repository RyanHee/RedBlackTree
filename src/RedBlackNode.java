public class RedBlackNode extends BinaryNode{

    private int color;
    public RedBlackNode(Comparable value){
        super(value);
        color=0;
    }

    public RedBlackNode(Comparable value, int i){
        super(value);
        color=i;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int i){
        color=i;
    }

    public String toString(){
        String s="";
        if(color==0){
            s+="r";
        }
        else if (color==1){
            s+="b";
        }
        else{
            s+="db";
        }
        s+=super.value();
        return s;
    }
}
