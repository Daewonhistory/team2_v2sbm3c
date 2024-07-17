package dev.mvc.admitperson;

class P {
  P(){
    System.out.println(1);
  }
  
  P(int i){
    System.out.println(i);
  }
}

class C extends P{
  C(){
    System.out.println(2);
  }
  C(int i){
    super();
    System.out.println(i+1);
  }
}
public class Test {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    P p1 = new P();
    System.out.println();
    
    P p2 = new P(100);
    System.out.println();
    
    C c1 = new C();
    System.out.println();
    
    C c2 = new C(100);
    
  }

}
