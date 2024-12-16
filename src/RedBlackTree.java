import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree extends BinarySearchTree {

    public RedBlackTree() {
        super();
    }

    public void add(RedBlackNode addNode) {
        if (root == null) {
            addNode.color = 1;
            root = addNode;
        } else {
            if (super.contains(addNode.value)) return;
            add(root, null, null, null, addNode);
        }
    }

    private void add(BinaryNode node, BinaryNode g, BinaryNode a, BinaryNode aa, RedBlackNode addNode) {
        //check for color swap
        RedBlackNode r = (RedBlackNode) root;
        r.color = 1;
        if (node.left != null && node.right != null) {
            RedBlackNode left = (RedBlackNode) node.left;
            RedBlackNode right = (RedBlackNode) node.right;
            RedBlackNode parent = (RedBlackNode) node;
            if (left.color == 0 && right.color == 0) {
                //colorSwap
                colorSwap(parent);
                if (node == root) {
                    parent.color = 1;
                } else {
                    //check for rotations
                    RedBlackNode grandParent = (RedBlackNode) g;
                    if (grandParent.color == 0) {
                        //identify which rotation
                        RedBlackNode ancestor = (RedBlackNode) a;

                        if (a.value.compareTo(g.value) < 0) {
                            //right
                            if (g.value.compareTo(parent.value) < 0) {
                                //right right
                                RedBlackNode n = rightRightRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    root = n;
                                } else {
                                    if (aa.value.compareTo(n.value) > 0) {
                                        aa.left = n;
                                    } else {
                                        aa.right = n;
                                    }
                                }

                            } else {
                                //right left
                                RedBlackNode n = rightLeftRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    root = n;
                                } else {
                                    if (aa.value.compareTo(n.value) > 0) {
                                        aa.left = n;
                                    } else {
                                        aa.right = n;
                                    }
                                }
                            }
                        } else {
                            //left
                            if (g.value.compareTo(parent.value) < 0) {
                                //left right
                                RedBlackNode n = leftRightRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    root = n;
                                } else {
                                    if (aa.value.compareTo(n.value) > 0) {
                                        aa.left = n;
                                    } else {
                                        aa.right = n;
                                    }
                                }
                            } else {
                                //left left
                                RedBlackNode n = leftLeftRotation(ancestor, grandParent, parent);
                                if (aa == null) {
                                    root = n;
                                } else {
                                    if (aa.value.compareTo(n.value) > 0) {
                                        aa.left = n;
                                    } else {
                                        aa.right = n;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //continue adding
        if (addNode.value.compareTo(node.value) > 0) {
            if (node.right == null) {
                node.right = addNode;
                //check rotation after add
                RedBlackNode parent = (RedBlackNode) node;
                if (parent.color == 0) {
                    if (g.value.compareTo(parent.value) > 0) {
                        RedBlackNode n = leftRightRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            root = n;
                        } else {
                            if (a.value.compareTo(n.value) > 0) {
                                a.left = n;
                            } else {
                                a.right = n;
                            }
                        }

                    } else {
                        RedBlackNode n = rightRightRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            root = n;
                        } else {
                            if (a.value.compareTo(n.value) > 0) {
                                a.left = n;
                            } else {
                                a.right = n;
                            }
                        }
                    }
                }
            } else {
                add(node.right, node, g, a, addNode);
            }
        } else {
            if (node.left == null) {
                node.left = addNode;
                //check rotation after add
                RedBlackNode parent = (RedBlackNode) node;
                if (parent.color == 0) {
                    if (g.value.compareTo(parent.value) > 0) {
                        RedBlackNode n = leftLeftRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            root = n;
                        } else {
                            if (a.value.compareTo(n.value) > 0) {
                                a.left = n;
                            } else {
                                a.right = n;
                            }
                        }
                    } else {
                        RedBlackNode n = rightLeftRotation((RedBlackNode) g, parent, addNode);
                        if (a == null) {
                            root = n;
                        } else {
                            if (a.value.compareTo(n.value) > 0) {
                                a.left = n;
                            } else {
                                a.right = n;
                            }
                        }
                    }
                }
            } else {
                add(node.left, node, g, a, addNode);
            }
        }


    }

    public RedBlackNode remove(Comparable c) {
        if (root == null) return null;
        if (!contains(c)) return null;

        RedBlackNode target = get(c);

        if (target.left == null && target.right == null) {
            leafRemove(target);
        } else if (target.right == null) {
            OCRemoveLeft(target);
        } else if (target.left == null) {
            OCRemoveRight(target);
        } else {
            RedBlackNode successor = (RedBlackNode) successor(target);
            remove1(successor);
            target.value = successor.value;
        }
        return new RedBlackNode(c);
    }

    private RedBlackNode remove1(RedBlackNode target) {
        if (root == null) return null;
        if (target.left == null && target.right == null) {
            leafRemove(target);
        } else if (target.right == null) {
            OCRemoveLeft(target);
        } else if (target.left == null) {
            OCRemoveRight(target);
        }
        return new RedBlackNode(target.value);
    }

    public RedBlackNode get(Comparable s) {
        return get((RedBlackNode) root, s);
    }

    private RedBlackNode get(RedBlackNode node, Comparable s) {
        if (node.value.equals(s)) {
            return node;
        } else if (node.value.compareTo(s) > 0) {
            if (node.left != null) {
                return get((RedBlackNode) node.left, s);
            }
        } else {
            if (node.right != null) {
                return get((RedBlackNode) node.right, s);
            }
        }
        return null;
    }


    private void OCRemoveRight(RedBlackNode node) {
        RedBlackNode p = (RedBlackNode) parent(root, node.value);
        RedBlackNode right = (RedBlackNode) node.right;

        if (right.color == 0) {
            if (p.isLeft(node)) p.left = simpleCaseDeletion(node, right);
            else p.right = simpleCaseDeletion(node, right);
        } else {
            right.color = 2;
            if (p.isLeft(node)) {
                p.left = right;
                restructure(p);
            } else {
                p.right = right;
                restructure(p);
            }
        }
    }

    private void OCRemoveLeft(RedBlackNode node) {
        RedBlackNode p = (RedBlackNode) parent(root, node.value);
        RedBlackNode left = (RedBlackNode) node.left;

        if (left.color == 0) {
            if (p.isLeft(node)) p.left = simpleCaseDeletion(node, left);
            else p.right = simpleCaseDeletion(node, left);
        } else {
            left.color = 2;
            if (p.isLeft(node)) {
                p.left = left;
                restructure(p);
            } else {
                p.right = left;
                restructure(p);
            }
        }
    }

    private void leafRemove(RedBlackNode node) {
        if (node.color == 0) {
            RedBlackNode p = (RedBlackNode) parent(root, node.value);
            if (p.isLeft(node)) {
                p.left = null;
            } else {
                p.right = null;
            }

        } else {
            RedBlackNode p = (RedBlackNode) parent(root, node.value);
            if (p.isLeft(node)) {
                p.left = null;
                restructure(p);
            } else {
                p.right = null;
                restructure(p);
            }
        }
    }

    private void restructure(RedBlackNode parent) {
        if (parent == null) {
            RedBlackNode r = (RedBlackNode) root;
            if (r == null) return;
            r.color = 1;
            return;
        }
        boolean isRoot = parent.value.equals(root.value);
        RedBlackNode left = (RedBlackNode) parent.left;
        RedBlackNode right = (RedBlackNode) parent.right;
        if (left == null || left.color == 2) {
            RedBlackNode gp = (RedBlackNode) parent(root, parent.value);
            RedBlackNode sibling = (RedBlackNode) parent.right;
            RedBlackNode temp = parent;
            if (rrr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.right;
                temp = rightRightRestructure(parent, sibling, r);
            } else if (rlr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.left;
                temp = rightLeftRestructure(parent, sibling, r);
            } else if (al(parent)) {
                temp = adjustmentLeft(parent, sibling);
            } else if (prop(parent, true)) {
                recolor(parent);
                restructure1(gp);
            } else if (sl(parent)) {
                temp = specialCaseLeftNoRight(parent, sibling);
            } else {
                temp = specialCaseLeft(parent, sibling);
            }
            if (isRoot) root = temp;
            else {
                if (gp.isLeft(parent)) gp.left = temp;
                else gp.right = temp;
            }

        } else if (right == null || right.color == 2) {
            RedBlackNode gp = (RedBlackNode) parent(root, parent.value);
            RedBlackNode sibling = (RedBlackNode) parent.left;
            RedBlackNode temp = parent;
            if (llr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.left;
                temp = leftLeftRestructure(parent, sibling, r);
            } else if (lrr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.right;
                temp = leftRightRestructure(parent, sibling, r);
            } else if (ar(parent)) {
                temp = adjustmentRight(parent, sibling);
            } else if (prop(parent, false)) {
                recolor(parent);
                restructure1(gp);
            } else if (sr(parent)) {
                temp = specialCaseRightNoLeft(parent, sibling);
            } else {
                temp = specialCaseRight(parent, sibling);
            }
            if (isRoot) root = temp;
            else {
                if (gp.isLeft(parent)) gp.left = temp;
                else gp.right = temp;
            }
        }
        RedBlackNode r = (RedBlackNode) root;
        r.color = 1;
    }

    private void restructure1(RedBlackNode parent) {
        if (parent == null) {
            RedBlackNode r = (RedBlackNode) root;
            if (r == null) return;
            r.color = 1;
            return;
        }
        boolean isRoot = parent.value.equals(root.value);
        RedBlackNode left = (RedBlackNode) parent.left;
        RedBlackNode right = (RedBlackNode) parent.right;
        if (left.color == 2) {
            RedBlackNode gp = (RedBlackNode) parent(root, parent.value);
            RedBlackNode sibling = (RedBlackNode) parent.right;
            RedBlackNode temp = parent;
            if (rrr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.right;
                temp = rightRightRestructure(parent, sibling, r);
            } else if (rlr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.left;
                temp = rightLeftRestructure(parent, sibling, r);
            } else if (al(parent)) {
                temp = adjustmentLeft(parent, sibling);
            } else if (prop(parent, true)) {
                recolor(parent);
                restructure1(gp);
            } else if (sl(parent)) {
                temp = specialCaseLeftNoRight(parent, sibling);
            } else {
                temp = specialCaseLeft(parent, sibling);
            }
            if (isRoot) root = temp;
            else {
                if (gp.isLeft(parent)) gp.left = temp;
                else gp.right = temp;
            }

        } else if (right.color == 2) {
            RedBlackNode gp = (RedBlackNode) parent(root, parent.value);
            RedBlackNode sibling = (RedBlackNode) parent.left;
            RedBlackNode temp = parent;
            if (llr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.left;
                temp = leftLeftRestructure(parent, sibling, r);
            } else if (lrr(parent)) {
                RedBlackNode r = (RedBlackNode) sibling.right;
                temp = leftRightRestructure(parent, sibling, r);
            } else if (ar(parent)) {
                temp = adjustmentRight(parent, sibling);
            } else if (prop(parent, false)) {
                recolor(parent);
                restructure1(gp);
            } else if (sr(parent)) {
                temp = specialCaseRightNoLeft(parent, sibling);
            } else {
                temp = specialCaseRight(parent, sibling);
            }
            if (isRoot) root = temp;
            else {
                if (gp.isLeft(parent)) gp.left = temp;
                else gp.right = temp;
            }
        }
        RedBlackNode r = (RedBlackNode) root;
        r.color = 1;
    }

    private int redChildCount(RedBlackNode n) {
        RedBlackNode temp;
        int i = 0;
        if (n.left != null) {
            temp = (RedBlackNode) n.left;
            if (temp.color == 0) i++;
        }
        if (n.right != null) {
            temp = (RedBlackNode) n.right;
            if (temp.color == 0) i++;
        }
        return i;
    }

    private boolean sr(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.left;
        if (parent.color == 0) {
            if (sibling.color == 1 && sibling.right != null && sibling.left == null) {
                RedBlackNode x = (RedBlackNode) sibling.right;
                return x.color == 0;
            }
        }
        return false;
    }

    private boolean sl(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.right;
        if (parent.color == 0) {
            if (sibling.color == 1 && sibling.left != null && sibling.right == null) {
                RedBlackNode x = (RedBlackNode) sibling.left;
                return x.color == 0;
            }
        }
        return false;
    }

    private boolean prop(RedBlackNode parent, boolean isLeft) {
        RedBlackNode sibling;
        if (isLeft) sibling = (RedBlackNode) parent.right;
        else sibling = (RedBlackNode) parent.left;
        return redChildCount(sibling) == 0;
    }

    private boolean ar(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.left;
        if (parent.color == 1) {
            if (sibling.color == 0) {
                if (sibling.childCount() == 2) {
                    RedBlackNode l = (RedBlackNode) sibling.left;
                    RedBlackNode r = (RedBlackNode) sibling.right;
                    if (l.color == r.color && r.color == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean al(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.right;
        if (parent.color == 1) {
            if (sibling.color == 0) {
                if (sibling.childCount() == 2) {
                    RedBlackNode l = (RedBlackNode) sibling.left;
                    RedBlackNode r = (RedBlackNode) sibling.right;
                    if (l.color == r.color && r.color == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean rrr(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.right;
        if (parent.color == 1) {
            if (sibling.color == 1) {
                if (sibling.right != null) {
                    RedBlackNode r = (RedBlackNode) sibling.right;
                    if (r.color == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean llr(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.left;
        if (parent.color == 1) {
            if (sibling.color == 1) {
                if (sibling.left != null) {
                    RedBlackNode r = (RedBlackNode) sibling.left;
                    if (r.color == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean rlr(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.right;
        if (parent.color == 1) {
            if (sibling.color == 1) {
                if (sibling.left != null) {
                    RedBlackNode r = (RedBlackNode) sibling.left;
                    if (r.color == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean lrr(RedBlackNode parent) {
        RedBlackNode sibling = (RedBlackNode) parent.left;
        if (parent.color == 1) {
            if (sibling.color == 1) {
                if (sibling.right != null) {
                    RedBlackNode r = (RedBlackNode) sibling.right;
                    if (r.color == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private RedBlackNode specialCaseLeftNoRight(RedBlackNode p, RedBlackNode s) {
        RedBlackNode left = (RedBlackNode) s.left;
        p.right = leftLeftRotation(s, left, (RedBlackNode) left.left);
        return specialCaseLeft(p, (RedBlackNode) p.right);
    }

    private RedBlackNode specialCaseRightNoLeft(RedBlackNode p, RedBlackNode s) {
        RedBlackNode right = (RedBlackNode) s.right;
        p.left = rightRightRotation(s, right, (RedBlackNode) right.right);
        return specialCaseRight(p, (RedBlackNode) p.left);
    }

    private RedBlackNode specialCaseLeft(RedBlackNode p, RedBlackNode s) {
        colorSwap(s);
        p.right = s.left;
        s.left = p;
        recolor(p);
        return s;
    }

    private RedBlackNode specialCaseRight(RedBlackNode p, RedBlackNode s) {
        colorSwap(s);
        p.left = s.right;
        s.right = p;
        recolor(p);
        return s;
    }

    private RedBlackNode adjustmentLeft(RedBlackNode p, RedBlackNode s) {
        rightRightRotation(p, s, (RedBlackNode) s.right);
        recolor(p);
        return s;
    }

    private RedBlackNode adjustmentRight(RedBlackNode p, RedBlackNode s) {
        leftRightRotation(p, s, (RedBlackNode) s.left);
        recolor(p);
        return s;
    }

    private RedBlackNode rightRightRestructure(RedBlackNode p, RedBlackNode s, RedBlackNode r) {
        rightRightRotation(p, s, r);
        colorSwap(s);
        if (p.left != null) {
            RedBlackNode temp = (RedBlackNode) p.left;
            temp.color--;
        }
        return s;
    }

    private RedBlackNode rightLeftRestructure(RedBlackNode p,
                                              RedBlackNode s, RedBlackNode r) {
        p.right = leftLeftRotation(s, r, (RedBlackNode) r.left);
        return rightRightRestructure(p, r, s);
    }

    private RedBlackNode leftLeftRestructure(RedBlackNode p, RedBlackNode s, RedBlackNode r) {
        leftLeftRotation(p, s, r);
        colorSwap(s);
        if (p.right != null) {
            RedBlackNode temp = (RedBlackNode) p.left;
            temp.color--;
        }
        return s;
    }

    private RedBlackNode leftRightRestructure(RedBlackNode p, RedBlackNode s, RedBlackNode r) {
        p.left = rightRightRotation(s, r, (RedBlackNode) r.right);
        return leftLeftRestructure(p, r, s);
    }

    private void recolor(RedBlackNode p) {
        p.color++;
        RedBlackNode temp = (RedBlackNode) p.right;
        if (temp != null)
            temp.color--;
        temp = (RedBlackNode) (p.left);
        if (temp != null)
            temp.color--;
    }

    private RedBlackNode simpleCaseDeletion(RedBlackNode node, RedBlackNode child) {
        node.right = null;
        node.left = null;
        child.color = 1;
        return child;
    }

    private void colorSwap(RedBlackNode node) {
        RedBlackNode left = (RedBlackNode) node.left;
        RedBlackNode right = (RedBlackNode) node.right;
        if (left != null) left.color++;
        if (right != null) right.color++;
        node.color--;
    }

    private RedBlackNode rightRightRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        gp.right = p.left;
        p.left = gp;
        gp.color--;
        p.color++;
        return p;
    }

    private RedBlackNode leftLeftRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        gp.left = p.right;
        p.right = gp;
        gp.color--;
        p.color++;
        return p;
    }

    private RedBlackNode rightLeftRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        p.left = x.right;
        x.right = p;
        return rightRightRotation(gp, x, p);
    }

    private RedBlackNode leftRightRotation(RedBlackNode gp, RedBlackNode p, RedBlackNode x) {
        p.right = x.left;
        x.left = p;
        return leftLeftRotation(gp, x, p);
    }

    public RedBlackNode[] forDrawRB() {
        RedBlackNode[] lst = new RedBlackNode[63];
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(root);
        int idx = 0;
        for (int x = 0; x < 6; x++) {
            int length = queue.size();
            for (int i = 0; i < length; i++) {
                if (queue.peek() == null) {
                    queue.remove();
                    lst[idx] = null;
                    queue.add(null);
                    queue.add(null);
                } else {
                    RedBlackNode n = (RedBlackNode) queue.remove();
                    lst[idx] = n;
                    queue.add(n.left);
                    queue.add(n.right);
                }
                idx++;
            }
        }
        return lst;
    }
}
