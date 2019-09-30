// CSCI 1913 Project 3
// Michael Memory
// Prof. James Moen
// 15 Dec 2018

import java.util.Random;

class  ShuffleTree<Value>{

   private class Node{                                // Nested Node class
      private String key;
      private Value value;
      private Node left;
      private Node right;
      private Node(String key, Value value){
         this.key = key;
         this.value = value;
         this.left = null;
         this.right = null;
      }
   }
   
   private String[] keys;                              // BST Data Elements
   private Value[] values;
   private int count;
   private Node root;
   private Random generator;
   
   public ShuffleTree(int size){                      // ShuffleTree constructor
      if (size < 0)
         throw new IllegalArgumentException("Illegal Array Size");
      else{
         keys = new String[size];
         values = (Value[]) new Object[size];
         root = null;
         generator = new Random();
         count = 0; 
      }
   }
   
   private void flush(){                              // flush function
      for (int i = 0; i < count-1; i++){
         int j = Math.abs(generator.nextInt()%(count-i));
         String temp = keys[i];
         Value temp2 = values[i];
         keys[i] = keys[i+j];
         values[i] = values[i+j];
         keys[i+j] = temp;
         values[i+j] = temp2;
      }   
      for (int m = 0; m < count; m++){
         if (root == null)
            root = new Node(keys[m], values[m]);
         else{
            Node subtree = root;
            boolean flag = false;
            while(!flag){
               if(keys[m].compareTo(subtree.key) < 0){
                  if (subtree.left == null){
                     subtree.left = new Node(keys[m], values[m]);
                     flag = true;
                  }
                  else
                     subtree = subtree.left;
               }
               else if (keys[m].compareTo(subtree.key) > 0){
                  if (subtree.right == null){
                     subtree.right = new Node(keys[m], values[m]);
                     flag = true;
                  }
                  else
                     subtree = subtree.right;
               }
               else
                  throw new IllegalStateException("Key Already Added");
            }
         }
      }
      values = (Value[])new Object[count+1];
      keys = new String[count+1];
      count = 0;
   }
   
   private boolean isEmpty(){                         // HELPER FUNCTION (Empty BST check function)
      for (int i = 0; i < keys.length; i++){
         if (keys[i] != null || values[i] != null)
            return false;
         else
            continue;
      }
      return true;
   }   
   
   public Value get(String key){                     // get function
      if (!this.isEmpty())
         this.flush();
      Node goal = search(key, root);
      if (goal.key == key)
         return goal.value;            
      else
         throw new IllegalArgumentException("Key Not Found");
   }
   
   public Node search(String key, Node root){       // HELPER FUNCTION (Recursive BST search)
      if (root != null){
         if (root.key == key)
            return root;
         else{
            Node left = search(key, root.left);
            if (left != null)
               return left;
            else{
               Node right = search(key, root.right);
               return right;
            }
         }
      }
      return null;
   }
   
   public int height(){                             // height function
      if (!isEmpty())
         this.flush();
      Node subtree = root;
      int height = getHeight(root);
      return height;
   }
   
   public int getHeight(Node start){                // HELPER FUNCTION (Recursive BST height count)
      if (start == null)
         return 0;
      else if(start.left == null && start.right == null)
         return 1;
      else{
         int leftH = getHeight(start.left);
         int rightH = getHeight(start.right);
         if(leftH > rightH)
            return leftH + 1;
         else
            return rightH + 1;
      }
   }
   
   public void put(String key, Value value){        // put function
      if (key == null)
         throw new IllegalArgumentException("Null Key Error");
      else {
         if(count == keys.length-1)
            this.flush();
         keys[count] = key;
         values[count] = value;
         count++;
      }
   }
   
}


//  SHUFFLE BORED. Test the SHUFFLE TREE class.

class ShuffleBored
{

//  RESERVED. A sorted array of some Java reserved names.

  private final static String[] reserved =
   { "abstract",     "assert",    "boolean",     "break",
     "byte",         "case",      "catch",       "char",
     "class",        "const",     "continue",    "default",
     "do",           "double",    "else",        "extends",
     "final",        "finally",   "float",       "for",
     "goto",         "if",        "implements",  "import",
     "instanceof",   "int",       "interface",   "long",
     "native",       "new",       "package",     "private",
     "protected",    "public",    "return",      "short",
     "static",       "super",     "switch",      "synchronized",
     "this",         "throw",     "throws",      "transient",
     "try",          "void",      "volatile",    "while" };
     
  private final static String[] attempt =
   { "busy",         "bork",      "cork",        "dork",
     "elope",        "flubber",   "glop",        "hub",
     "nope",         "nut",       "yup",         "zup"};
     

//  MAIN. Main program. Make a SHUFFLE TREE, whose keys are RESERVED names, and
//  whose values are INTs. Print its height, keys, and their values.

  public static void main(String[] args)
  {
    ShuffleTree<Integer> tree = new ShuffleTree<Integer>(30);           // creating a new ShuffleTree of value type: Integer

    for (int index = 0; index < reserved.length; index += 1)            // inserting values of respective index in keys array as the key's value in ShuffleTree
    {
      tree.put(reserved[index], index);
    }

    System.out.println(tree.height());                                  // ~12 (average height of 48 string index)

    for (int index = 0; index < reserved.length; index += 1)            // Displaying [value, key] line by line
    {
      System.out.format("%02d %s", tree.get(reserved[index]), reserved[index]);
      System.out.println();
    }
    
    ShuffleTree<Integer> willow = new ShuffleTree<Integer>(4);
    
    for (int spot = 0; spot < attempt.length; spot += 1){
      willow.put(attempt[spot], spot);
    }
    
    System.out.println();
    System.out.println(willow.height());                                // ~ 5-6 (average height of 12 string index)
    
    for (int spot = 0; spot < attempt.length; spot += 1){
      System.out.format("%02d %s", willow.get(attempt[spot]), attempt[spot]);
      System.out.println();
    }
  }
}