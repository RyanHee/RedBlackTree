import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class RedBlackTree extends BinarySearchTree {

    public RedBlackTree() {
        super();
    }


    public void add(RedBlackNode addNode) {
        if (super.getRoot() == null) {
            addNode.setColor(addNode.getColor() + 1);
            super.setRoot(addNode);
        } else {
            add(super.getRoot(), null, null, null, addNode);
        }
    }

    private void add(BinaryNode node, BinaryNode g, BinaryNode a, BinaryNode aa, RedBlackNode addNode) {
        //check for color swap
        RedBlackNode root = (RedBlackNode) super.getRoot();
        root.setColor(1);
        if (node.left() != null && node.right() != null) {
            RedBlackNode left = (RedBlackNode) node.left();
            RedBlackNode right = (RedBlackNode) node.right();
            RedBlackNode parent = (RedBlackNode) node;
            if (left.getColor() == 0 && right.getColor() == 0) {
                //colorSwap
                colorSwap(parent);
                if (node == super.getRoot()) {
                    parent.setColor(1);
                } else {
                    //check for rotations
                    RedBlackNode grandParent = (RedBlackNode) g;
                    if (grandParent.getColor() == 0) {
                        //identify which rotation
                        RedBlackNode ancestor = (RedBlackNode) a;

                        if (a.value().compareTo(g.value()) < 0) {
                            //right
                            if (g.value().compareTo(parent.value()) < 0) {
                                //right right
                                RedBlackNode n = rightRightRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }

                            } else {
                                //right left
                                RedBlackNode n = rightLeftRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                            }
                        } else {
                            //left
                            if (g.value().compareTo(parent.value()) < 0) {
                                //left right
                                RedBlackNode n = leftRightRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                            } else {
                                //left left
                                RedBlackNode n = leftLeftRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
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
        if (addNode.value().compareTo(node.value()) > 0) {
            if (node.right() == null) {
                node.setRight(addNode);
                //check rotation after add
                RedBlackNode parent = (RedBlackNode) node;
                if (parent.getColor() == 0) {
                    if (g.value().compareTo(parent.value()) > 0) {
                        RedBlackNode n = leftRightRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            super.setRoot(n);
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                        }

                    } else {
                        RedBlackNode n = rightRightRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            super.setRoot(n);
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                        }
                    }
                }
            } else {
                add(node.right(), node, g, a, addNode);
            }
        } else {
            if (node.left() == null) {
                node.setLeft(addNode);
                //check rotation after add
                RedBlackNode parent = (RedBlackNode) node;
                if (parent.getColor() == 0) {
                    if (g.value().compareTo(parent.value()) > 0) {
                        RedBlackNode n = leftLeftRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            super.setRoot(n);
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                        }

                    } else {
                        RedBlackNode n = rightLeftRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            super.setRoot(n);
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                        }
                    }
                }
            } else {
                add(node.left(), node, g, a, addNode);
            }
        }


    }


    public RedBlackNode remove(Comparable c) {
        if (super.getRoot() == null) {
            return null;
        }
        update(c, (RedBlackNode) super.getRoot(), null, null, null);
        System.out.println(fullLevelOrder());
        RedBlackNode[] lst = findStuff(c);
        System.out.println(Arrays.toString(lst));
        //0 node, 1 g, 2 sibling, 3 a, 4 aa
        //0 node, 1 g, 2 s, 3 a, 4 aa
        RedBlackNode node = lst[0];
        System.out.println(node);
        RedBlackNode g = lst[1];
        RedBlackNode a = lst[3];
        RedBlackNode aa = lst[4];
        //System.out.println("hehee: "+fullLevelOrder());
        Stack<RedBlackNode> rbStack = findAllAncestorsRB(c);
        //RedBlackNode node =lst[0];
        System.out.println(rbStack);
        //rbStack.pop();
        //check for color swap


        //leaf
        if (node.left() == null && node.right() == null) {
            if (node.getColor() == 0) {
                System.out.println("red leaf");
                if (g.left() == node) {
                    System.out.println("remove left");
                    g.setLeft(null);
                } else {
                    g.setRight(null);
                }
                return node;
            } else {
                System.out.println(node);
                System.out.println(g);
                if (node.value().compareTo(g.value()) < 0) {
                    System.out.println("black leaf at left");

                    return checkLeft(node, null, g, a, rbStack);
                } else {
                    System.out.println("black leaf at right");
                    System.out.println(rbStack);
                    return checkRight(node, null, g, a, rbStack);
                }

            }
        }

        //has left child
        else if (node.left() != null && node.right() == null) {
            RedBlackNode child = (RedBlackNode) node.left();
            return checkLeft(node, child, g, a, rbStack);

        } else if (node.left() == null && node.right() != null) {
            RedBlackNode child = (RedBlackNode) node.right();
            return checkRight(node, child, g, a, rbStack);
        } else {
            RedBlackNode iosuccessor = (RedBlackNode) super.successor(node);
            swap(node, iosuccessor);
            System.out.println("here");
            System.out.println(super.getRoot());
            if (node.right() == iosuccessor) {
                System.out.println("heyew");
                RedBlackNode r = (RedBlackNode) iosuccessor.right();
                rbStack.push(node);
                return checkRight(iosuccessor, r, node, a, rbStack);
            }
            System.out.println("fhaw");
            System.out.println(rbStack);
            RedBlackNode temp = node;
            rbStack.add(temp);
            if (node.right() != null) {
                temp = (RedBlackNode) node.right();
                rbStack.add(temp);
            }
            while (temp.left() != null) {
                rbStack.add((RedBlackNode) temp.left());
                temp = (RedBlackNode) temp.left();
            }
            rbStack.pop();
            RedBlackNode r = (RedBlackNode) iosuccessor.right();
            System.out.println(rbStack);
            RedBlackNode a2 = rbStack.pop();
            RedBlackNode a3 = rbStack.pop();
            System.out.println(rbStack);
            rbStack.add(a3);
            rbStack.add(a2);
            return checkLeft(iosuccessor, r, a2, a3, rbStack);
        }

    }


    private void swap(RedBlackNode a, RedBlackNode b) {
        Comparable c = a.value();
        a.setValue(b.value());
        b.setValue(c);
    }

    private RedBlackNode checkLeft(RedBlackNode node, RedBlackNode child, RedBlackNode g, RedBlackNode a, Stack<RedBlackNode> rbStack) {
        //leaf
        if (child == null) {
            if (node.getColor() == 0) {
                //red leaf
                System.out.println("red leaf to delete");
                if (node.value().compareTo(g.value()) < 0) {
                    g.setLeft(null);
                } else {
                    g.setRight(null);
                }
            } else {
                System.out.println("black leaf");
                //RedBlackNode n = rbStack.pop();
                //System.out.println("n: " + n);

                if (node==g.left()) {
                    g.setLeft(null);
                    RedBlackNode s = (RedBlackNode) g.right();
                    return rightSiblingCheck(a, g, node, rbStack);


                } else {
                    g.setRight(null);
                    return leftSiblingCheck(a, g, node, rbStack);
                }

            }
        }
        //red child
        else if (child.getColor() == 0) {
            System.out.println("red child");
            System.out.println("node: "+node);
            System.out.println("child: "+child);
            System.out.println("g:  "+g);
            if (node == g.left()) {
                RedBlackNode n = simpleCaseDeletion(node, child);
                g.setLeft(n);
            } else {
                RedBlackNode n = simpleCaseDeletion(node, child);
                g.setRight(n);
            }
            return node;
        }
        //black child
        else {
            System.out.println("black child");
            System.out.println("node: "+node);
            System.out.println("child: "+child);
            //del node is red
            if (node.getColor() == 0) {
                System.out.println("del node is red");
                if (node == g.left()) {
                    RedBlackNode n = simpleCaseDeletion(node, child);
                    g.setLeft(n);
                } else {
                    RedBlackNode n = simpleCaseDeletion(node, child);
                    g.setRight(n);
                }
                return node;
            }
            //del node is black
            else {
                System.out.println("del node is black");
                if (node == g.left()) {
                    //node is left
                    System.out.println("del node is left");
                    g.setLeft(child);
                    node.setLeft(null);
                    RedBlackNode s = (RedBlackNode) g.right();
                    return rightSiblingCheck(a, g, node, rbStack);
                } else {
                    //node is right
                    g.setRight(child);
                    node.setLeft(null);
                    return leftSiblingCheck(a,g,node, rbStack);
                }
            }
        }
        return node;
    }


    private RedBlackNode checkRight(RedBlackNode node, RedBlackNode child, RedBlackNode g, RedBlackNode a, Stack<RedBlackNode> rbStack) {
        //leaf
        if (child == null) {
            if (node.getColor() == 0) {
                //red leaf
                System.out.println("red leaf to delete");
                if (node.value().compareTo(g.value()) < 0) {
                    g.setLeft(null);
                } else {
                    g.setRight(null);
                }
            } else {
                System.out.println("black leaf");

                if (node==g.left()) {
                    return rightSiblingCheck(a, g, node, rbStack);
                } else {
                    return leftSiblingCheck(a, g, node, rbStack);
                    //g.setRight(null);
                }
                //return node;
            }
        }
        //red child
        else if (child.getColor() == 0) {
            System.out.println("red child");
            System.out.println("node: "+node);
            System.out.println("child: "+child);
            System.out.println("g:  "+g);
            if (node == g.left()) {
                RedBlackNode n = simpleCaseDeletion(node, child);
                g.setLeft(n);
            } else {
                RedBlackNode n = simpleCaseDeletion(node, child);
                g.setRight(n);
            }
            return node;
        }
        //black child
        else {
            System.out.println("black child");
            System.out.println("node: "+node);
            System.out.println("child: "+child);
            //del node is red
            if (node.getColor() == 0) {
                System.out.println("del node is red");
                if (node == g.left()) {
                    RedBlackNode n = simpleCaseDeletion(node, child);
                    g.setLeft(n);
                } else {
                    RedBlackNode n = simpleCaseDeletion(node, child);
                    g.setRight(n);
                }
                return node;
            }
            //del node is black
            else {
                System.out.println("del node is black");
                if (node == g.left()) {
                    //node is left
                    System.out.println("del node is left");
                    g.setLeft(child);
                    node.setRight(null);
                    RedBlackNode s = (RedBlackNode) g.right();
                    return rightSiblingCheck(a, g, node, rbStack);
                } else {
                    //node is right
                    System.out.println("del node is left");
                    g.setRight(child);
                    node.setRight(null);
                    RedBlackNode s = (RedBlackNode) g.left();
                    return leftSiblingCheck(a, g, node, rbStack);
                }
            }
        }
        return node;
    }

    public RedBlackNode[] testFind(Comparable c) {
        return findStuff(c);
    }

    private void update(Comparable c, RedBlackNode node, RedBlackNode g, RedBlackNode a, RedBlackNode aa) {
        RedBlackNode root = (RedBlackNode) super.getRoot();
        root.setColor(1);
        boolean shift = false;
        if (node.left() != null && node.right() != null) {
            RedBlackNode left = (RedBlackNode) node.left();
            RedBlackNode right = (RedBlackNode) node.right();
            if (left.getColor() == 0 && right.getColor() == 0) {
                //colorSwap
                colorSwap(node);
                if (node == super.getRoot()) {
                    node.setColor(1);
                } else {
                    //check for rotations
                    if (g.getColor() == 0) {
                        //identify which rotation

                        if (a.value().compareTo(g.value()) < 0) {
                            //right
                            if (g.value().compareTo(node.value()) < 0) {
                                //right right
                                RedBlackNode n = rightRightRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }

                                }
                                shift=true;

                            } else {
                                //right left
                                RedBlackNode n = rightLeftRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                                shift=true;
                            }
                        } else {
                            //left
                            if (g.value().compareTo(node.value()) < 0) {
                                //left right
                                RedBlackNode n = leftRightRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                                shift=true;
                            } else {
                                //left left
                                RedBlackNode n = leftLeftRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                                shift=true;
                            }
                        }
                    }
                }
            }
        }
        if (shift){
            update(c,(RedBlackNode) super.getRoot(),null,null,null);
        }
        else{
            System.out.println(node);
            System.out.println(fullLevelOrder());
            if (node.value().equals(c)){
                return;
            }
            if (node.value().compareTo(c) < 0) {
                update(c, (RedBlackNode) node.right(), node, g, a);
            }

            else {
                update(c, (RedBlackNode) node.left(), node, g, a);
            }
        }

    }


    private RedBlackNode[] findStuff(Comparable c) {
        RedBlackNode[] lst = new RedBlackNode[5];
        //0 node, 1 g, 2 sibling, 3 a, 4 aa
        if (super.getRoot() == null) {
            System.out.println("wtf");
            return lst;
        }
        RedBlackNode root = (RedBlackNode) super.getRoot();
        if (root.value().equals(c)) {
            lst[0] = root;
            return lst;
        }
        return find(root, null, null, null, c, lst);
    }

    private RedBlackNode[] find(RedBlackNode node, RedBlackNode g, RedBlackNode a, RedBlackNode aa, Comparable c, RedBlackNode[] lst) {
        /*
        RedBlackNode root = (RedBlackNode) super.getRoot();
        root.setColor(1);
        if (node.left()!=null&&node.right()!=null){
            RedBlackNode left = (RedBlackNode) node.left();
            RedBlackNode right = (RedBlackNode) node.right();
            if (left.getColor()==0&&right.getColor()==0){
                //colorSwap
                colorSwap(node);
                if(node==super.getRoot()){
                    node.setColor(1);
                }
                else{
                    //check for rotations
                    if (g.getColor()==0){
                        //identify which rotation

                        if (a.value().compareTo(g.value())<0){
                            //right
                            if (g.value().compareTo(node.value())<0){
                                //right right
                                RedBlackNode n = rightRightRotation(a, g, node);
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
                                RedBlackNode n = rightLeftRotation(a, g, node);
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
                            if (g.value().compareTo(node.value())<0){
                                //left right
                                RedBlackNode n = leftRightRotation(a, g, node);
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
                                RedBlackNode n = leftLeftRotation(a, g, node);
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
        */
        if (node.value().compareTo(c) < 0) {
            if (node.right() != null) {
                if (node.right().value().equals(c)) {
                    System.out.println(node + " " + " " + g + " " + a + " " + aa);
                    lst[0] = (RedBlackNode) node.right();
                    lst[1] = node;
                    lst[2] = (RedBlackNode) node.left();
                    lst[3] = g;
                    lst[4] = a;
                    System.out.println("at right " + Arrays.toString(lst));
                    System.out.println(fullLevelOrder());
                    return lst;
                }
                System.out.println("go right " + Arrays.toString(lst));
                System.out.println(node + " " + " " + g + " " + a + " " + aa);
                System.out.println(fullLevelOrder());
                return find((RedBlackNode) node.right(), node, g, a, c, lst);
            }
            return lst;
        } else {
            if (node.left() != null) {
                if (node.left().value().equals(c)) {
                    //0 node, 1 g, 2 s, 3 a, 4 aa
                    System.out.println(node + " " + " " + g + " " + a + " " + aa);
                    lst[0] = (RedBlackNode) node.left();
                    lst[1] = node;
                    lst[2] = (RedBlackNode) node.right();
                    lst[3] = g;
                    lst[4] = a;
                    System.out.println("at left " + node + Arrays.toString(lst));
                    return lst;
                } else {
                    System.out.println("go left " + Arrays.toString(lst));
                    System.out.println(node + " " + " " + g + " " + a + " " + aa);
                    System.out.println(fullLevelOrder());
                    return find((RedBlackNode) node.left(), node, g, a, c, lst);
                }
            }
            return lst;
        }

    }

    private RedBlackNode specialCaseLeft(RedBlackNode p, RedBlackNode s) {
        recolor(s);
        p.setRight(s.left());
        s.setLeft(p);
        recolor(p);
        return s;
    }


    private RedBlackNode specialCaseRight(RedBlackNode p, RedBlackNode s) {
        recolor(s);
        p.setLeft(s.right());
        s.setRight(p);
        recolor(p);
        return s;
    }

    private RedBlackNode adjustmentLeft(RedBlackNode p, RedBlackNode s) {
        rightRightRotation(p, s, (RedBlackNode) s.right());
        recolor(p);
        return s;
    }

    private RedBlackNode adjustmentRight(RedBlackNode p, RedBlackNode s) {
        leftRightRotation(p, s, (RedBlackNode) s.left());
        recolor(p);
        return s;
    }

    private RedBlackNode rightRightRestructure(RedBlackNode p,
                                               RedBlackNode s, RedBlackNode r) {
        rightRightRotation(p, s, r);
        colorSwap(s);
        if (p.left() != null) {
            RedBlackNode temp = (RedBlackNode) (p.left());
            temp.setColor(temp.getColor() - 1);
        }
        return s;
    }

    private RedBlackNode rightLeftRestructure(RedBlackNode p,
                                              RedBlackNode s, RedBlackNode r) {
        p.setRight(leftLeftRotation(s, r, (RedBlackNode) r.left()));
        return rightRightRestructure(p, r, s);
    }

    private RedBlackNode leftLeftRestructure(RedBlackNode p, RedBlackNode s, RedBlackNode r) {
        leftLeftRotation(p, s, r);
        colorSwap(s);
        if (p.right() != null) {
            RedBlackNode temp = (RedBlackNode) (p.left());
            temp.setColor(temp.getColor() - 1);
        }
        return s;
    }

    private RedBlackNode leftRightRestructure(RedBlackNode p, RedBlackNode s, RedBlackNode r) {
        p.setLeft(rightRightRotation(s, r, (RedBlackNode) r.right()));
        return leftLeftRestructure(p, r, s);
    }

    private void recolor(RedBlackNode p) {
        p.setColor(p.getColor() + 1);
        RedBlackNode temp = (RedBlackNode) (p.right());
        if (temp != null)
            temp.setColor(temp.getColor() - 1);
        temp = (RedBlackNode) (p.left());
        if (temp != null)
            temp.setColor(temp.getColor() - 1);
    }

    private RedBlackNode simpleCaseDeletion(RedBlackNode node, RedBlackNode child) {
        node.setRight(null);
        node.setLeft(null);
        child.setColor(1);
        return child;
    }


    private void colorSwap(RedBlackNode node) {
        RedBlackNode left = (RedBlackNode) node.left();
        RedBlackNode right = (RedBlackNode) node.right();
        left.setColor(left.getColor() + 1);
        right.setColor(right.getColor() + 1);
        node.setColor(node.getColor() - 1);
    }

    private RedBlackNode rightRightRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        gp.setRight(p.left());
        p.setLeft(gp);
        gp.setColor(gp.getColor() - 1);
        p.setColor(p.getColor() + 1);
        return p;
    }

    private RedBlackNode leftLeftRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        gp.setLeft(p.right());
        p.setRight(gp);
        gp.setColor(gp.getColor() - 1);
        p.setColor(p.getColor() + 1);
        return p;
    }

    private RedBlackNode rightLeftRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        p.setLeft(x.right());
        x.setRight(p);
        return rightRightRotation(gp, x, p);
    }

    private RedBlackNode leftRightRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        p.setRight(x.left());
        x.setLeft(p);
        return leftLeftRotation(gp, x, p);
    }


    public String fullLevelOrder() {
        String s = "";
        if (super.getRoot() == null) {
            return "==";
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(super.getRoot());
        //System.out.println(queue);
        int x = 0;
        //System.out.println(getNumLevels());
        while (x < getNumLevels()) {
            int length = queue.size();
            for (int i = 0; i < length; i++) {
                if (queue.peek() == null) {
                    queue.remove();
                    s += "--";
                    queue.add(null);
                    queue.add(null);
                } else {
                    RedBlackNode n = (RedBlackNode) queue.remove();
                    s += n;
                    queue.add(n.left());
                    queue.add(n.right());
                }
                s += "|";
            }
            s = s.substring(0, s.length() - 1);
            if (x != getNumLevels() - 1) {
                s += "\n";
            }
            x++;
        }
        return s;

    }

    public Stack<RedBlackNode> findAllAncestorsRB(Comparable s) {
        if (super.getRoot() == null) {
            return new Stack<>();
        }
        if (super.getRoot().value().equals(s)) {
            return new Stack<>();
        }
        return findAllAncestors((RedBlackNode) super.getRoot(), null, null, null, s, new Stack<RedBlackNode>());
    }

    private Stack<RedBlackNode> findAllAncestors(RedBlackNode node, RedBlackNode g, RedBlackNode a, RedBlackNode aa, Comparable s, Stack<RedBlackNode> lst) {
        RedBlackNode root = (RedBlackNode) super.getRoot();
        root.setColor(1);
        if (node.left() != null && node.right() != null) {
            RedBlackNode left = (RedBlackNode) node.left();
            RedBlackNode right = (RedBlackNode) node.right();
            if (left.getColor() == 0 && right.getColor() == 0) {
                //colorSwap
                colorSwap(node);
                if (node == super.getRoot()) {
                    node.setColor(1);
                } else {
                    //check for rotations
                    if (g.getColor() == 0) {
                        //identify which rotation

                        if (a.value().compareTo(g.value()) < 0) {
                            //right
                            if (g.value().compareTo(node.value()) < 0) {
                                //right right
                                RedBlackNode n = rightRightRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }

                            } else {
                                //right left
                                RedBlackNode n = rightLeftRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                            }
                        } else {
                            //left
                            if (g.value().compareTo(node.value()) < 0) {
                                //left right
                                RedBlackNode n = leftRightRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                            } else {
                                //left left
                                RedBlackNode n = leftLeftRotation(a, g, node);
                                if (aa == null) {
                                    super.setRoot(n);
                                } else {
                                    if (aa.value().compareTo(n.value()) > 0) {
                                        aa.setLeft(n);
                                    } else {
                                        aa.setRight(n);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (node == null) {
            return lst;
        }
        if (node.left() != null) {
            if (node.left().value().equals(s)) {
                lst.add(node);
                return lst;
            }
        }
        if (node.right() != null) {
            if (node.right().value().equals(s)) {
                lst.add(node);
                return lst;
            }
        }
        if (s.compareTo(node.value()) < 0) {
            lst.add(node);
            return findAllAncestors((RedBlackNode) node.left(), node, g, a, s, lst);
        } else {
            lst.add(node);
            return findAllAncestors((RedBlackNode) node.right(), node, g, a, s, lst);
        }
    }

    public String[] forDraw() {
        String[] lst = new String[63];
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(super.getRoot());
        int idx = 0;
        for (int x = 0; x < 6; x++) {
            int length = queue.size();
            //String s="";
            for (int i = 0; i < length; i++) {
                //s="";
                if (queue.peek() == null) {
                    queue.remove();
                    lst[idx] = " ";
                    queue.add(null);
                    queue.add(null);
                } else {
                    RedBlackNode n = (RedBlackNode) queue.remove();
                    lst[idx] = n.toString();
                    queue.add(n.left());
                    queue.add(n.right());
                }
                idx++;
            }
            //s=s.substring(0,s.length()-1);
            //lst[x]=s;
        }

        return lst;
    }



    private RedBlackNode leftSiblingCheck(RedBlackNode a, RedBlackNode g, RedBlackNode node, Stack<RedBlackNode> rbStack){
        RedBlackNode s=(RedBlackNode) g.left();
        if (g.getColor() == 1) {
            if (s.getColor() == 0) {
                //red sibling
                //adjustment left
                System.out.println("adjustment left");
                RedBlackNode n = adjustmentLeft(g, s);
                if (n.value().compareTo(a) < 0) {
                    a.setLeft(n);
                } else {
                    a.setRight(n);
                }
                return node;
            } else {
                //black sibling
                if (s.left() == null && s.right() == null) {
                    RedBlackNode n = rbStack.pop();
                    System.out.println("black sibling with no child");
                    System.out.println("n: " + n);
                    recolor(n);
                    rbStack.push(n);
                    System.out.println(fullLevelOrder());
                    while (!rbStack.isEmpty()) {
                        RedBlackNode nd = rbStack.pop();
                        if (nd.getColor() == 2) {
                            if (nd.value().equals(super.getRoot().value())) {
                                nd.setColor(1);
                                super.setRoot(nd);
                                break;
                            } else {
                                RedBlackNode nd1 = rbStack.pop();
                                recolor(nd1);
                                rbStack.push(nd1);
                            }

                        }
                    }
                    return node;
                } else if (s.left() != null && s.right() == null) {
                    System.out.println("black sibling with left child");
                    RedBlackNode l = (RedBlackNode) s.left();
                    if (l.getColor() == 1) {
                        //recolor, black black
                        System.out.println("black left child");
                        Stack<BinaryNode> stack = super.findAllAncestors(g.value());
                        recolor(g);
                        //stack.pop();
                        while (!stack.isEmpty()) {
                            RedBlackNode n = (RedBlackNode) stack.pop();
                            if (n.getColor() == 2) {
                                if (n.value().equals(super.getRoot().value())) {
                                    n.setColor(1);
                                } else {
                                    recolor(n);
                                }
                            } else {
                                break;
                            }
                        }
                        return node;
                    } else {
                        //right left restructure
                        System.out.println("Red left child");
                        RedBlackNode n = rightLeftRestructure(g, s, l);
                        if (a == null) {
                            super.setRoot(n);
                            return node;
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                            return node;
                        }
                    }
                } else if (s.left() == null && s.right() != null) {
                    System.out.println("Sibling with red child");
                    RedBlackNode r = (RedBlackNode) s.right();
                    if (r.getColor() == 1) {
                        //recolor, black black
                        System.out.println("Sibling with black child");
                        Stack<BinaryNode> stack = super.findAllAncestors(g.value());
                        recolor(g);
                        //stack.pop();
                        while (!stack.isEmpty()) {
                            RedBlackNode n = (RedBlackNode) stack.pop();
                            if (n.getColor() == 2) {
                                if (n.value().equals(super.getRoot().value())) {
                                    n.setColor(1);
                                } else {
                                    recolor(n);
                                }
                            } else {
                                break;
                            }
                        }
                        return node;
                    } else {
                        //right right restructure
                        System.out.println("Sibling with red child");
                        RedBlackNode n = rightRightRestructure(g, s, r);
                        if (a == null) {
                            super.setRoot(n);
                            return node;
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                            return node;
                        }
                    }
                } else {
                    System.out.println("Sibling with 2 children");
                    RedBlackNode r = (RedBlackNode) s.right();
                    if (r.getColor() == 0) {
                        RedBlackNode n = rightRightRestructure(g, s, r);
                        if (a == null) {
                            super.setRoot(n);
                            return node;
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                            return node;
                        }
                    } else {
                        RedBlackNode l = (RedBlackNode) s.left();
                        if (l.getColor() == 0) {
                            RedBlackNode n = rightLeftRestructure(g, s, l);
                            if (a == null) {
                                super.setRoot(n);
                                return node;
                            } else {
                                if (a.value().compareTo(n.value()) > 0) {
                                    a.setLeft(n);
                                } else {
                                    a.setRight(n);
                                }
                                return node;
                            }
                        }
                    }
                }


            }
        } else {
            //red parent
            System.out.println("red parent");
            if (s.right() == null && s.left() == null) {
                recolor(g);
                return node;
            }
            else {
                //specialcaseright
                System.out.println("special case right");
                RedBlackNode n = specialCaseRight(g, s);
                if (a.value().compareTo(n) > 0) {
                    a.setLeft(n);
                } else {
                    a.setRight(n);
                }
                return node;
            }
        }
        return node;
    }


    private RedBlackNode rightSiblingCheck(RedBlackNode a, RedBlackNode g, RedBlackNode node, Stack<RedBlackNode> rbStack){
        RedBlackNode s = (RedBlackNode) g.right();
        if (g.getColor() == 1) {
            if (s.getColor() == 0) {
                //red sibling
                //adjustment left
                System.out.println("adjustment left");
                RedBlackNode n = adjustmentLeft(g, s);
                if (n.value().compareTo(a) < 0) {
                    a.setLeft(n);
                } else {
                    a.setRight(n);
                }
                return node;
            } else {
                //black sibling
                if (s.left() == null && s.right() == null) {
                    RedBlackNode n = rbStack.pop();
                    System.out.println("black sibling with no child");
                    System.out.println("n: " + n);
                    recolor(n);
                    rbStack.push(n);
                    System.out.println(fullLevelOrder());
                    while (!rbStack.isEmpty()) {
                        RedBlackNode nd = rbStack.pop();
                        if (nd.getColor() == 2) {
                            if (nd.value().equals(super.getRoot().value())) {
                                nd.setColor(1);
                                super.setRoot(nd);
                                break;
                            } else {
                                RedBlackNode nd1 = rbStack.pop();
                                recolor(nd1);
                                rbStack.push(nd1);
                            }

                        }
                    }
                    return node;
                } else if (s.left() != null && s.right() == null) {
                    System.out.println("black sibling with left child");
                    RedBlackNode l = (RedBlackNode) s.left();
                    if (l.getColor() == 1) {
                        //recolor, black black
                        System.out.println("black left child");
                        Stack<BinaryNode> stack = super.findAllAncestors(g.value());
                        recolor(g);
                        //stack.pop();
                        while (!stack.isEmpty()) {
                            RedBlackNode n = (RedBlackNode) stack.pop();
                            if (n.getColor() == 2) {
                                if (n.value().equals(super.getRoot().value())) {
                                    n.setColor(1);
                                } else {
                                    recolor(n);
                                }
                            } else {
                                break;
                            }
                        }
                        return node;
                    } else {
                        //right left restructure
                        System.out.println("Red left child");
                        RedBlackNode n = rightLeftRestructure(g, s, l);
                        if (a == null) {
                            super.setRoot(n);
                            return node;
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                            return node;
                        }
                    }
                } else if (s.left() == null && s.right() != null) {
                    System.out.println("Sibling with right child");
                    RedBlackNode r = (RedBlackNode) s.right();
                    if (r.getColor() == 1) {
                        //recolor, black black
                        System.out.println("Sibling with black child");
                        Stack<BinaryNode> stack = super.findAllAncestors(g.value());
                        recolor(g);
                        //stack.pop();
                        while (!stack.isEmpty()) {
                            RedBlackNode n = (RedBlackNode) stack.pop();
                            if (n.getColor() == 2) {
                                if (n.value().equals(super.getRoot().value())) {
                                    n.setColor(1);
                                } else {
                                    recolor(n);
                                }
                            } else {
                                break;
                            }
                        }
                        return node;
                    } else {
                        //right right restructure
                        System.out.println("Sibling with red child");
                        RedBlackNode n = rightRightRestructure(g, s, r);
                        if (a == null) {
                            super.setRoot(n);
                            return node;
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                            return node;
                        }
                    }
                } else {
                    System.out.println("Sibling with 2 children");
                    RedBlackNode r = (RedBlackNode) s.right();
                    if (r.getColor() == 0) {
                        RedBlackNode n = rightRightRestructure(g, s, r);
                        if (a == null) {
                            super.setRoot(n);
                            return node;
                        } else {
                            if (a.value().compareTo(n.value()) > 0) {
                                a.setLeft(n);
                            } else {
                                a.setRight(n);
                            }
                            return node;
                        }
                    } else {
                        RedBlackNode l = (RedBlackNode) s.left();
                        if (l.getColor() == 0) {
                            RedBlackNode n = rightLeftRestructure(g, s, l);
                            if (a == null) {
                                super.setRoot(n);
                                return node;
                            } else {
                                if (a.value().compareTo(n.value()) > 0) {
                                    a.setLeft(n);
                                } else {
                                    a.setRight(n);
                                }
                                return node;
                            }
                        }
                    }
                }


            }
        }
        else {
            //red parent
            System.out.println("red parent");
            if (s.right() == null && s.left() == null) {
                recolor(g);
                return node;
            } else {
                //specialcaseleft
                System.out.println("special case left");
                RedBlackNode n = specialCaseLeft(g, s);
                if (a.value().compareTo(n) > 0) {
                    a.setLeft(n);
                } else {
                    a.setRight(n);
                }
                return node;
            }
        }
        return node;
    }

}
