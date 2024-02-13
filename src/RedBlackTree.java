public class RedBlackTree extends BinarySearchTree{

    public RedBlackTree(){super();}


    public void add(RedBlackNode addNode){
        if (super.getRoot()==null){
            addNode.setColor(addNode.getColor()+1);
            super.setRoot(addNode);
        }
        else{
            add(super.getRoot(), null, addNode);
        }
    }

    private void add(BinaryNode node, BinaryNode g, RedBlackNode addNode){
        //check for color swap
        if (node.left()!=null&&node.left()!=null){
            RedBlackNode left = (RedBlackNode) node.left();
            RedBlackNode right = (RedBlackNode) node.right();
            RedBlackNode parent = (RedBlackNode) node;
            if (left.getColor()==0&&right.getColor()==0){
                //colorSwap

                if(node==super.getRoot()){
                    parent.setColor(1);
                }
                else{
                    //check for rotations
                    RedBlackNode grandParent=(RedBlackNode) g;
                    if (grandParent.getColor()==0){
                        //identify which rotation
                    }
                }
            }
        }

        //continue adding
    }
}
