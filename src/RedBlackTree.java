import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree extends BinarySearchTree{

    public RedBlackTree(){super();}


    public void add(RedBlackNode addNode){
        if (super.getRoot()==null){
            addNode.setColor(addNode.getColor()+1);
            super.setRoot(addNode);
        }
        else{
            add(super.getRoot(), null, null, null, addNode);
        }
    }

    private void add(BinaryNode node, BinaryNode g, BinaryNode a, BinaryNode aa, RedBlackNode addNode){
        //check for color swap
        RedBlackNode root = (RedBlackNode) super.getRoot();
        root.setColor(1);
        if (node.left()!=null&&node.right()!=null){
            RedBlackNode left = (RedBlackNode) node.left();
            RedBlackNode right = (RedBlackNode) node.right();
            RedBlackNode parent = (RedBlackNode) node;
            if (left.getColor()==0&&right.getColor()==0){
                //colorSwap
                colorSwap(parent);
                if(node==super.getRoot()){
                    parent.setColor(1);
                }
                else{
                    //check for rotations
                    RedBlackNode grandParent=(RedBlackNode) g;
                    if (grandParent.getColor()==0){
                        //identify which rotation
                        RedBlackNode ancestor = (RedBlackNode) a;

                        if (a.value().compareTo(g.value())<0){
                            //right
                            if (g.value().compareTo(parent.value())<0){
                                //right right
                                RedBlackNode n = rightRightRotation(ancestor, grandParent, parent);
                                if (aa==null){
                                    super.setRoot(n);
                                }
                                else{
                                    if (aa.value().compareTo(n.value())>0){
                                        aa.setLeft(n);
                                    }
                                    else{
                                        aa.setRight(n);
                                    }
                                }

                            }
                            else{
                                //right left
                                RedBlackNode n = rightLeftRotation(ancestor, grandParent, parent);
                                if (aa==null){
                                    super.setRoot(n);
                                }
                                else{
                                    if (aa.value().compareTo(n.value())>0){
                                        aa.setLeft(n);
                                    }
                                    else{
                                        aa.setRight(n);
                                    }
                                }
                            }
                        }
                        else{
                            //left
                            if (g.value().compareTo(parent.value())<0){
                                //left right
                                RedBlackNode n = leftRightRotation(ancestor, grandParent, parent);
                                if (aa==null){
                                    super.setRoot(n);
                                }
                                else{
                                    if (aa.value().compareTo(n.value())>0){
                                        aa.setLeft(n);
                                    }
                                    else{
                                        aa.setRight(n);
                                    }
                                }
                            }
                            else{
                                //left left
                                RedBlackNode n = leftLeftRotation(ancestor, grandParent, parent);
                                if (aa==null){
                                    super.setRoot(n);
                                }
                                else{
                                    if (aa.value().compareTo(n.value())>0){
                                        aa.setLeft(n);
                                    }
                                    else{
                                        aa.setRight(n);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //continue adding
        if (addNode.value().compareTo(node.value())>0){
            if (node.right()==null){
                node.setRight(addNode);
                //check rotation after add
                RedBlackNode parent = (RedBlackNode) node;
                if (parent.getColor()==0){
                    if (g.value().compareTo(parent.value())>0){
                        RedBlackNode n = leftRightRotation((RedBlackNode) g, parent, addNode);
                        if (a==null){
                            super.setRoot(n);
                        }
                        else{
                            if (a.value().compareTo(n.value())>0){
                                a.setLeft(n);
                            }
                            else{
                                a.setRight(n);
                            }
                        }

                    }
                    else{
                        RedBlackNode n=rightRightRotation((RedBlackNode) g, parent, addNode);
                        if (a==null){
                            super.setRoot(n);
                        }
                        else{
                            if (a.value().compareTo(n.value())>0){
                                a.setLeft(n);
                            }
                            else{
                                a.setRight(n);
                            }
                        }
                    }
                }
            }
            else{
                add(node.right(), node, g, a, addNode);
            }
        }
        else{
            if (node.left()==null){
                node.setLeft(addNode);
                //check rotation after add
                RedBlackNode parent = (RedBlackNode) node;
                if (parent.getColor()==0){
                    if (g.value().compareTo(parent.value())>0){
                        RedBlackNode n = leftLeftRotation((RedBlackNode) g, parent, addNode);
                        if (a==null){
                            super.setRoot(n);
                        }
                        else{
                            if (a.value().compareTo(n.value())>0){
                                a.setLeft(n);
                            }
                            else{
                                a.setRight(n);
                            }
                        }

                    }
                    else{
                        RedBlackNode n=rightLeftRotation((RedBlackNode) g, parent, addNode);
                        if (a==null){
                            super.setRoot(n);
                        }
                        else{
                            if (a.value().compareTo(n.value())>0){
                                a.setLeft(n);
                            }
                            else{
                                a.setRight(n);
                            }
                        }
                    }
                }
            }
            else{
                add(node.left(), node, g, a, addNode);
            }
        }


    }


    public RedBlackNode[] testFind(Comparable c){
        return findStuff(c);
    }

    private RedBlackNode[] findStuff(Comparable c){
        RedBlackNode[]lst = new RedBlackNode[3];
        //0 node, 1 parent, 2 sibling
        if (super.getRoot()==null){
            return lst;
        }
        RedBlackNode root = (RedBlackNode) super.getRoot();
        if (root.value().equals(c)){
            lst[0]=root;
            return lst;
        }
        return find(root, c, lst);
    }

    private RedBlackNode[] find(RedBlackNode node, Comparable c, RedBlackNode[]lst){
        if (node.value().compareTo(c)<0){
            if (node.right()!=null){
                if (node.right().value().equals(c)){
                    lst[0]=(RedBlackNode) node.right();
                    lst[1]=node;
                    lst[2]=(RedBlackNode) node.left();
                    return lst;
                }
                return find((RedBlackNode) node.right(), c, lst);
            }
            return lst;
        }
        else{
            if (node.left()!=null){
                if (node.left().value().equals(c)){
                    lst[0]=(RedBlackNode) node.left();
                    lst[1]=node;
                    lst[2]=(RedBlackNode) node.right();
                }
                else{
                    return find((RedBlackNode) node.left(), c, lst);
                }
            }
            return lst;
        }

    }





    private void colorSwap(RedBlackNode node){
        RedBlackNode left = (RedBlackNode) node.left();
        RedBlackNode right = (RedBlackNode) node.right();
        left.setColor(left.getColor()+1);
        right.setColor(right.getColor()+1);
        node.setColor(node.getColor()-1);
    }

    private RedBlackNode rightRightRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x){
        gp.setRight(p.left());
        p.setLeft(gp);
        gp.setColor(gp.getColor()-1);
        p.setColor(p.getColor()+1);
        return p;
    }

    private RedBlackNode leftLeftRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x){
        gp.setLeft(p.right());
        p.setRight(gp);
        gp.setColor(gp.getColor()-1);
        p.setColor(p.getColor()+1);
        return p;
    }

    private RedBlackNode rightLeftRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x){
        p.setLeft(x.right());
        x.setRight(p);
        return rightRightRotation(gp, x, p);
    }

    private RedBlackNode leftRightRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x){
        p.setRight(x.left());
        x.setLeft(p);
        return leftLeftRotation(gp, x, p);
    }


    public String fullLevelOrder(){
        String s="";
        if (super.getRoot()==null){
            return "==";
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(super.getRoot());
        //System.out.println(queue);
        int x=0;
        //System.out.println(getNumLevels());
        while (x<getNumLevels()){
            int length = queue.size();
            for (int i=0;i<length;i++){
                if (queue.peek()==null){
                    queue.remove();
                    s+="--";
                    queue.add(null);
                    queue.add(null);
                }
                else{
                    RedBlackNode n =(RedBlackNode) queue.remove();
                    s+=n;
                    queue.add(n.left());
                    queue.add(n.right());
                }
                s+="|";
            }
            s=s.substring(0,s.length()-1);
            if (x!=getNumLevels()-1){
                s+="\n";
            }
            x++;
        }
        return s;

    }

}
