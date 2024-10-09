public class QuestionNode {
   //stores object or questions depending on object
   public String data;
   // left of tree that represents no
   public QuestionNode no;
   // right of tree that represents yes
   public QuestionNode yes;
   
   //constructs leaf node stores answer node
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   //constructs branch node with the data, no and yes subtree
   // stores question node
   public QuestionNode(String data, QuestionNode no, QuestionNode yes) {
      this.data = data;
      this.no = no;
      this.yes = yes;
   }
}
