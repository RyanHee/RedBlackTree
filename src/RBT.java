import java.util.LinkedList;
import java.util.Queue;

public class RBT extends BinarySearchTree{

    public RBT(){
        super();
    }

    public void add(RedBlackNode addNode) {
        if (super.getRoot() == null) {
            addNode.setColor(addNode.getColor() + 1);
            super.setRoot(addNode);
        } else {
            if (super.contains(addNode.value()))return;
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

    private void print(String s){
        System.out.println(s);
    }


    public RedBlackNode remove(Comparable c){
        if (getRoot()==null)return null;
        if (!contains(c))return null;

        RedBlackNode target = (RedBlackNode) get(c);

        if (target.left()==null && target.right()==null){ // leaf
            print("leaf remove");
            leafRemove(target);
        }
        else if (target.right()==null){
            print("target has left child");
            OCRemoveLeft(target);
        }
        else if (target.left()==null){
            print("target has right child");
            OCRemoveRight(target);
        }
        else{
            print("target has 2 children");
            RedBlackNode successor = (RedBlackNode) successor(target);
            remove1(successor);
            target.setValue(successor.value());
        }
        return new RedBlackNode(c);
    }

    private RedBlackNode remove1(RedBlackNode target){
        if (getRoot()==null)return null;
        print(target.toString());
        print(levelOrder());
        //RedBlackNode target = (RedBlackNode) get(c);
        //print(target.left().toString());

        if (target.left()==null && target.right()==null){ // leaf
            print("leaf remove");
            leafRemove(target);
        }
        else if (target.right()==null){
            print("target has left child");
            OCRemoveLeft(target);
        }
        else if (target.left()==null){
            print("target has right child");
            OCRemoveRight(target);
        }
        return new RedBlackNode(target.value());
    }

    public RedBlackNode get(Comparable s){
        return get((RedBlackNode) getRoot(), s);
    }

    private RedBlackNode get(RedBlackNode node, Comparable s){
        if (node.value().equals(s)){
            return node;
        }
        else if (node.value().compareTo(s)>0){
            if (node.left()!=null){
                print("go left");
                return get((RedBlackNode) node.left(),s);
            }
        }
        else{
            if (node.right()!=null){
                print("go right");
                return get((RedBlackNode) node.right(),s);
            }
        }
        print("return null");
        return null;
    }



    private void swap(RedBlackNode a, RedBlackNode b) {
        Comparable c = a.value();
        a.setValue(b.value());
        b.setValue(c);
    }

    private void OCRemoveRight(RedBlackNode node){
        RedBlackNode p = (RedBlackNode) parent(getRoot(), node.value());
        RedBlackNode right = (RedBlackNode) node.right();
        if (node.getColor()==0){
            print("What?");
        }
        else{
            if (right.getColor()==0){
                print("simple case deletion");
                if (p.isLeft(node))p.setLeft(simpleCaseDeletion(node, right));
                else p.setRight(simpleCaseDeletion(node, right));
            }
            else{
                print("target child black");
                right.setColor(2);
                if (p.isLeft(node)){
                    print("target at right");
                    p.setLeft(right);
                    restructure(p);
                }
                else{
                    print("target at right");
                    p.setRight(right);
                    restructure(p);
                }
            }

        }
    }


    private void OCRemoveLeft(RedBlackNode node){
        RedBlackNode p = (RedBlackNode) parent(getRoot(), node.value());
        RedBlackNode left = (RedBlackNode) node.left();
        if (node.getColor()==0){
            print("What?");
        }
        else{
            if (left.getColor()==0){
                print("simple case deletion");
                if (p.isLeft(node))p.setLeft(simpleCaseDeletion(node, left));
                else p.setRight(simpleCaseDeletion(node, left));
            }
            else{
                print("target child black");
                left.setColor(2);
                if (p.isLeft(node)){
                    print("target at left");
                    p.setLeft(left);
                    restructure(p);
                }
                else{
                    print("target at right");
                    p.setRight(left);
                    restructure(p);
                }
            }

        }
    }


    private void leafRemove(RedBlackNode node){
        if (node.getColor()==0){
            //red leaf
            print("red leaf");
            RedBlackNode p = (RedBlackNode) parent(getRoot(), node.value());
            if (p.isLeft(node)){
                print("leaf is left");
                p.setLeft(null);
            }
            else{
                print("leaf is right");
                p.setRight(null);
            }

        }
        else{
            //black leaf
            print("black leaf");
            RedBlackNode p = (RedBlackNode) parent(getRoot(), node.value());
            if (p.isLeft(node)){
                print("leaf is left");
                p.setLeft(null);
                restructure(p);
            }
            else{
                print("leaf is right");
                p.setRight(null);
                restructure(p);
            }
        }
    }


    private void restructure(RedBlackNode parent){
        if (parent==null){
            RedBlackNode root = (RedBlackNode) getRoot();
            if (root==null)return;
            root.setColor(1);
            setRoot(root);
            return;
        }
        boolean isRoot=parent.value().equals(getRoot().value());
        RedBlackNode left = (RedBlackNode) parent.left();
        RedBlackNode right = (RedBlackNode) parent.right();
        if (left==null || left.getColor()==2){

            RedBlackNode gp = (RedBlackNode) parent(getRoot(), parent.value());
            //boolean left=gp.isLeft(parent);
            RedBlackNode sibling = (RedBlackNode) parent.right();
            RedBlackNode temp=null;
            if (rrr(parent)){
                print("right right restructure");
                RedBlackNode r = (RedBlackNode) sibling.right();
                temp=rightRightRestructure(parent, sibling, r);
            }
            else if (rlr(parent)){
                print("right left restructure");
                RedBlackNode r = (RedBlackNode) sibling.left();
                temp=rightLeftRestructure(parent, sibling, r);
            }
            else if (al(parent)){
                print("adj left");
                temp=adjustmentLeft(parent, sibling);
            }
            else if (prop(parent, true)){
                print("propagate");
                recolor(parent);
                restructure1(gp);
            }
            else{
                print("special left");
                temp=specialCaseLeft(parent, sibling);
            }
            if (isRoot) setRoot(temp);
            else{
                if (gp.isLeft(parent)) gp.setLeft(temp);
                else gp.setRight(temp);
            }

        }
        else if (right==null || right.getColor()==2){
            RedBlackNode gp = (RedBlackNode) parent(getRoot(), parent.value());
            //boolean left=gp.isLeft(parent);
            RedBlackNode sibling = (RedBlackNode) parent.left();
            RedBlackNode temp = null;
            if (llr(parent)){
                print("left left restructure");
                RedBlackNode r = (RedBlackNode) sibling.left();
                temp = leftLeftRestructure(parent, sibling, r);
            }
            else if (lrr(parent)){
                print("left right restructure");
                RedBlackNode r = (RedBlackNode) sibling.right();
                temp = leftRightRestructure(parent, sibling, r);
            }
            else if (ar(parent)){
                print("adj right");
                temp = adjustmentRight(parent, sibling);
            }
            else if (prop(parent, false)){
                print("propagate");
                recolor(parent);
                restructure1(gp);
            }
            else{
                print("special right");
                temp = specialCaseRight(parent, sibling);
            }
            if (isRoot) setRoot(temp);
            else{
                if (gp.isLeft(parent)) gp.setLeft(temp);
                else gp.setRight(temp);
            }
        }
        RedBlackNode root = (RedBlackNode) getRoot();
        root.setColor(1);
        setRoot(root);
    }

    private void restructure1(RedBlackNode parent){
        if (parent==null){
            RedBlackNode root = (RedBlackNode) getRoot();
            if (root==null)return;
            root.setColor(1);
            setRoot(root);
            return;
        }
        boolean isRoot=parent.value().equals(getRoot().value());
        RedBlackNode left = (RedBlackNode) parent.left();
        RedBlackNode right = (RedBlackNode) parent.right();
        if (left.getColor()==2){

            RedBlackNode gp = (RedBlackNode) parent(getRoot(), parent.value());
            //boolean left=gp.isLeft(parent);
            RedBlackNode sibling = (RedBlackNode) parent.right();
            RedBlackNode temp=null;
            if (rrr(parent)){
                print("right right restructure");
                RedBlackNode r = (RedBlackNode) sibling.right();
                temp=rightRightRestructure(parent, sibling, r);
            }
            else if (rlr(parent)){
                print("right left restructure");
                RedBlackNode r = (RedBlackNode) sibling.left();
                temp=rightLeftRestructure(parent, sibling, r);
            }
            else if (al(parent)){
                print("adj left");
                temp=adjustmentLeft(parent, sibling);
            }
            else if (prop(parent, true)){
                print("propagate");
                recolor(parent);
                restructure1(gp);
            }
            else{
                print("special left");
                temp=specialCaseLeft(parent, sibling);
            }
            if (isRoot) setRoot(temp);
            else{
                if (gp.isLeft(parent)) gp.setLeft(temp);
                else gp.setRight(temp);
            }

        }
        else if (right.getColor()==2){
            RedBlackNode gp = (RedBlackNode) parent(getRoot(), parent.value());
            //boolean left=gp.isLeft(parent);
            RedBlackNode sibling = (RedBlackNode) parent.left();
            RedBlackNode temp = null;
            if (llr(parent)){
                print("left left restructure");
                RedBlackNode r = (RedBlackNode) sibling.left();
                temp = leftLeftRestructure(parent, sibling, r);
            }
            else if (lrr(parent)){
                print("left right restructure");
                RedBlackNode r = (RedBlackNode) sibling.right();
                temp = leftRightRestructure(parent, sibling, r);
            }
            else if (ar(parent)){
                print("adj right");
                temp = adjustmentRight(parent, sibling);
            }
            else if (prop(parent, false)){
                print("propagate");
                recolor(parent);
                restructure1(gp);
            }
            else{
                print("special right");
                temp = specialCaseRight(parent, sibling);
            }
            if (isRoot) setRoot(temp);
            else{
                if (gp.isLeft(parent)) gp.setLeft(temp);
                else gp.setRight(temp);
            }
        }
        RedBlackNode root = (RedBlackNode) getRoot();
        root.setColor(1);
        setRoot(root);
    }

    private void swap(BinaryNode a, BinaryNode b){
        Comparable s=a.value();
        a.setValue(b.value());
        b.setValue(s);
    }

    private int redChildCount(RedBlackNode n){
        RedBlackNode temp;
        int i=0;
        if (n.left()!=null){
            temp=(RedBlackNode) n.left();
            if(temp.getColor()==0)i++;
        }
        if (n.right()!=null){
            temp=(RedBlackNode) n.right();
            if(temp.getColor()==0)i++;
        }
        return i;
    }

    private boolean prop(RedBlackNode parent, boolean isLeft){
        RedBlackNode sibling;
        if (isLeft) sibling=(RedBlackNode) parent.right();
        else sibling = (RedBlackNode) parent.left();
        return redChildCount(sibling) == 0;
    }

    private boolean ar(RedBlackNode parent){
        RedBlackNode sibling = (RedBlackNode) parent.left();
        if (parent.getColor()==1){
            if (sibling.getColor()==0){
                if (sibling.childCount()==2){
                    RedBlackNode l = (RedBlackNode) sibling.left();
                    RedBlackNode r = (RedBlackNode) sibling.right();
                    if (l.getColor()==r.getColor() && r.getColor()==1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean al(RedBlackNode parent){
        RedBlackNode sibling = (RedBlackNode) parent.right();
        if (parent.getColor()==1){
            //print("1");
            if (sibling.getColor()==0){
                //print("2");
                if (sibling.childCount()==2){
                    //print("3");
                    RedBlackNode l = (RedBlackNode) sibling.left();
                    RedBlackNode r = (RedBlackNode) sibling.right();
                    if (l.getColor()==r.getColor() && r.getColor()==1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean rrr(RedBlackNode parent){
        RedBlackNode sibling = (RedBlackNode) parent.right();
        if (parent.getColor()==1){
            if (sibling.getColor()==1){
                if (sibling.right()!=null){
                    RedBlackNode r = (RedBlackNode) sibling.right();
                    if (r.getColor()==0){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private boolean llr(RedBlackNode parent){
        RedBlackNode sibling = (RedBlackNode) parent.left();
        if (parent.getColor()==1){
            if (sibling.getColor()==1){
                if (sibling.left()!=null){
                    RedBlackNode r = (RedBlackNode) sibling.left();
                    if (r.getColor()==0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean rlr(RedBlackNode parent){
        RedBlackNode sibling = (RedBlackNode) parent.right();
        if (parent.getColor()==1){
            if (sibling.getColor()==1){
                if (sibling.left()!=null){
                    RedBlackNode r = (RedBlackNode) sibling.left();
                    if (r.getColor()==0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean lrr(RedBlackNode parent){
        RedBlackNode sibling = (RedBlackNode) parent.left();
        if (parent.getColor()==1){
            if (sibling.getColor()==1){
                if (sibling.right()!=null){
                    RedBlackNode r = (RedBlackNode) sibling.right();
                    if (r.getColor()==0){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private RedBlackNode specialCaseLeft(RedBlackNode p, RedBlackNode s) {
        colorSwap(s);
        p.setRight(s.left());
        s.setLeft(p);
        recolor(p);
        return s;
    }


    private RedBlackNode specialCaseRight(RedBlackNode p, RedBlackNode s) {
        colorSwap(s);
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
        if (left!=null)left.setColor(left.getColor() + 1);
        if (right!=null)right.setColor(right.getColor() + 1);
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


    public RedBlackNode[] forDrawRB() {
        RedBlackNode[] lst = new RedBlackNode[63];
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
                    lst[idx] = null;
                    queue.add(null);
                    queue.add(null);
                } else {
                    RedBlackNode n = (RedBlackNode) queue.remove();
                    lst[idx] = n;
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

}
