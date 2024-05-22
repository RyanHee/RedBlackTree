import java.util.*;

public class Main {
    public static void main(String[] args) {


        ArrayList<Integer> list = new ArrayList<>();
        list.add(27);
        list.add(17);
        list.add(37);
        list.add(47);
        list.add(57);
        //Set s = new HashSet();
        ListIterator<Integer> iter = list.listIterator();
        iter.set(7);
        System.out.println(iter.next());
        //iter.remove(iter.next());
        System.out.println(list);
        RedBlackTree tree = new RedBlackTree();

        System.out.println("enter command");
        Scanner sc = new Scanner(System.in);
        //sc.nextLine();//insert 9 7 12 1 10 15 38 28 60 24 35 56 37 27 68 56
        String s = sc.nextLine();
        String[] lst = s.split(" ");
        if (lst[0].equals("insert")){
            //System.out.println(true);
            for (int i=1;i<lst.length;i++){
                tree.add(new RedBlackNode(Integer.parseInt(lst[i])));
                System.out.println(tree.fullLevelOrder());
            }
        }

        System.out.println(tree.fullLevelOrder());

        Stack<RedBlackNode>stack = new Stack<>();
        RedBlackNode r = new RedBlackNode(1);
        stack.add(r);
        r.setRight(new RedBlackNode(2));
        System.out.println(r.right());
        RedBlackNode rr = (RedBlackNode) r.right();
        rr.setColor(2);
        System.out.println((RedBlackNode)r.right());
        //tree.remove(10);
        //System.out.println(tree.fullLevelOrder());
        //System.out.println(Arrays.toString(tree.testFind(9)));
        //System.out.println(Arrays.toString(tree.testFind(46)));
    }
}