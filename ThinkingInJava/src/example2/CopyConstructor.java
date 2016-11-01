package example2;
/*
下面是输出结果: 
In ripen, t is a Tomato 
In slice, f is a Fruit  
In ripen, t is a Tomato 
In slice, f is a Fruit  

从中可以看出一个问题。在 slice()内部对 Tomato 进行了副本构建工作以后,结果便不再是
一个Tomato 对象,而只是一个 Fruit。它已丢失了作为一个 Tomato(西红柿)的所有特征。
此外,如果采用一个GreenZebra,ripen()和 slice()会把它分别转换成一个 Tomato 和一个 
Fruit。所以非常不幸,假如想制作对象的一个本地副本,Java 中的副本构建器便不是特别适合我们。 
*/

//: CopyConstructor.java 
//A constructor for copying an object 
//of the same type, as an attempt to create 
//a local copy. 

class FruitQualities { 
	private int weight; 
	private int color; 
	private int firmness; 
	private int ripeness; 
	private int smell; 
	// etc. 
	FruitQualities() { // Default constructor 
		// do something meaningful... 
	} 
	// Other constructors: 
	// ... 
	// Copy constructor: 
	FruitQualities(FruitQualities f) { 
		weight = f.weight; 
		color = f.color; 
		firmness = f.firmness; 
		ripeness = f.ripeness; 
		smell = f.smell; 
		// etc. 
	} 
} 

class Seed { 
	// Members... 
	Seed() { /* Default constructor */ } 

	Seed(Seed s) { /* Copy constructor */ } 
} 

class Fruit { 
	private FruitQualities fq; 
	private int seeds; 
	private Seed[] s; 
	Fruit(FruitQualities q, int seedCount) {  
		fq = q; 
		seeds = seedCount; 
		s = new Seed[seeds]; 
		for(int i = 0; i < seeds; i++) 
			s[i] = new Seed(); 
	} 
	// Other constructors: 
	// ... 
	// Copy constructor: 
	Fruit(Fruit f) { 
		fq = new FruitQualities(f.fq); 
		seeds = f.seeds; 
		// Call all Seed copy-constructors: 
		for(int i = 0; i < seeds; i++) 
			s[i] = new Seed(f.s[i]); 
		// Other copy-construction activities... 
	} 
	// To allow derived constructors (or other  
	// methods) to put in different qualities: 
	protected void addQualities(FruitQualities q) { 
		fq = q; 
	} 
	protected FruitQualities getQualities() { 
		return fq; 
	} 
} 

class Tomato extends Fruit { 
	Tomato() { 
		super(new FruitQualities(), 100); 
	} 
	Tomato(Tomato t) { // Copy-constructor 
		super(t); // Upcast for base copy-constructor 
		// Other copy-construction activities... 
	} 
} 

class ZebraQualities extends FruitQualities { 
	private int stripedness; 
	ZebraQualities() { // Default constructor 
		// do something meaningful... 
	} 
	ZebraQualities(ZebraQualities z) { 
		super(z); 

		stripedness = z.stripedness; 
	} 
} 

class GreenZebra extends Tomato { 
	GreenZebra() { 
		addQualities(new ZebraQualities()); 
	} 
	GreenZebra(GreenZebra g) { 
		super(g); // Calls Tomato(Tomato) 
		// Restore the right qualities: 
		addQualities(new ZebraQualities()); 
	} 
	void evaluate() { 
		ZebraQualities zq =  
				(ZebraQualities)getQualities(); 
		// Do something with the qualities 
		// ... 
	} 
} 

public class CopyConstructor { 
	public static void ripen(Tomato t) { 
		// Use the "copy constructor": 
		t = new Tomato(t);  
		System.out.println("In ripen, t is a " + 
				t.getClass().getName()); 
	} 
	public static void slice(Fruit f) { 
		f = new Fruit(f); // Hmmm... will this work? 
		System.out.println("In slice, f is a " + 
				f.getClass().getName()); 
	} 
	public static void main(String[] args) { 
		Tomato tomato = new Tomato(); 
		ripen(tomato); // OK 
		slice(tomato); // OOPS! 
		GreenZebra g = new GreenZebra(); 
		ripen(g); // OOPS! 
		slice(g); // OOPS! 
		g.evaluate(); 
	} 
}
