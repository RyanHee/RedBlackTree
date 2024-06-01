public class RedBlackNode extends BinaryNode{

    public byte color;
    public RedBlackNode(Comparable value){
        super(value);
        color=0;
    }

    public RedBlackNode(Comparable value, byte i){
        super(value);
        color=i;
    }

    public String toString(){
        String s="";
        if (color==0){
            s+="r";
        }
        if (color==1)
            s+="b";
        if (color==2)
            s+="db";
        s+=value;
        return s;
    }
}
