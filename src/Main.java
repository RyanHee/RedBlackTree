import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        RedBlackTree tree = new RedBlackTree();

        System.out.println("enter command");
        Scanner sc = new Scanner(System.in);
        //sc.nextLine();
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
        System.out.println(Arrays.toString(tree.testFind(9)));
        System.out.println(Arrays.toString(tree.testFind(46)));
    }
}
