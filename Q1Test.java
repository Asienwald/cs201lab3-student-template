public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){
          // put the 3 strings in an array so we can access them by index
          String[] t = { traversal1, traversal2, traversal3 };

          // try every way of assigning inorder, preorder, postorder to the 3 strings
          // e.g. {0,1,2} means t[0] is inorder, t[1] is preorder, t[2] is postorder
          int[][] perms = { {0,1,2}, {0,2,1}, {1,0,2}, {1,2,0}, {2,0,1}, {2,1,0} };
          for (int[] p : perms) {
               int in = p[0], pre = p[1], post = p[2];

               // build the BST by inserting nodes in preorder sequence
               // (inserting a BST's preorder one by one gives back the same tree)
               Node root = null;
               for (String s : t[pre].split("-")) {
                    root = insert(root, Integer.parseInt(s));
               }

               // check if the tree's traversals match the strings
               // all 3 must match, otherwise this guess is wrong
               if (inorder(root).equals(t[in]) && preorder(root).equals(t[pre])
                         && postorder(root).equals(t[post])) {
                    // found a valid guess, label each string by its position
                    String[] names = new String[3];
                    names[in] = "Inorder";
                    names[pre] = "Preorder";
                    names[post] = "Postorder";
                    return "Traversal 1 - " + names[0] + ", Traversal 2 - " + names[1]
                              + ", Traversal 3 - " + names[2];
               }
          }
          // none of the 6 guesses worked
          return "Invalid traversals";
     }

     // a node in the BST
     static class Node {
          int val;
          Node left, right;

          Node(int val) {
               this.val = val;
          }
     }

     // normal BST insert: smaller goes left, bigger goes right
     static Node insert(Node node, int val) {
          if (node == null) return new Node(val); // empty spot found, create node here
          if (val < node.val) node.left = insert(node.left, val);
          else node.right = insert(node.right, val);
          return node;
     }

     // inorder: left, root, right (gives sorted order for a BST)
     static String inorder(Node n) {
          if (n == null) return "";
          return join(join(inorder(n.left), "" + n.val), inorder(n.right));
     }

     // preorder: root, left, right
     static String preorder(Node n) {
          if (n == null) return "";
          return join(join("" + n.val, preorder(n.left)), preorder(n.right));
     }

     // postorder: left, right, root
     static String postorder(Node n) {
          if (n == null) return "";
          return join(join(postorder(n.left), postorder(n.right)), "" + n.val);
     }

     // joins two strings with "-", skipping empty ones
     // e.g. join("1", "2") = "1-2", join("", "2") = "2" (no extra dash)
     static String join(String a, String b) {
          if (a.isEmpty()) return b;
          if (b.isEmpty()) return a;
          return a + "-" + b;
     }
}
