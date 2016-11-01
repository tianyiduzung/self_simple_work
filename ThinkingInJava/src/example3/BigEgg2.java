package example3;
/*
BigEgg2.Yolk 明确地扩展了 Egg2.Yolk,而且覆盖了它的方法。
方法 insertYolk()允许BigEgg2将它自己的某个Yolk对象上溯造型至Egg2的y句柄。
所以当 g()调用 y.f()的时候,就会使用 f()被覆盖版本。
 */
class Egg2 { 
	  protected class Yolk {  
	    public Yolk() { 
	      System.out.println("Egg2.Yolk()"); 
	    } 
	    public void f() { 
	      System.out.println("Egg2.Yolk.f()"); 
	    } 
	  } 
	  private Yolk y = new Yolk(); 
	  public Egg2() { 
	    System.out.println("New Egg2()"); 
	  } 
	  public void insertYolk(Yolk yy) { y = yy; } 
	  public void g() { y.f(); } 
	} 
	 
	public class BigEgg2 extends Egg2 { 
	  public class Yolk extends Egg2.Yolk { 
	    public Yolk() { 
	      System.out.println("BigEgg2.Yolk()"); 
	    } 
	 
	public void f() { 
	      System.out.println("BigEgg2.Yolk.f()"); 
	    } 
	  } 
	  public BigEgg2() { insertYolk(new Yolk()); } 
	  public static void main(String[] args) { 
	    Egg2 e2 = new BigEgg2(); 
	    e2.g(); 
	  } 
	}