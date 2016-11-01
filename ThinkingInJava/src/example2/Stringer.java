package example2;
/*不变字串
q 传递进入 upcase()时,它实际是 q 的句柄的一个副本。
该句柄连接的对象实际只在一个统一的物理位置处。句柄四处传递的时候,它的句柄会得到复制。

若观察对 upcase()的定义,会发现传递进入的句柄有一个名字 s,而且该名字只有在 upcase()执行期间才会
存在。upcase()完成后,本地句柄 s 便会消失,而 upcase()返回结果——还是原来那个字串,只是所有字符
都变成了大写。当然,它返回的实际是结果的一个句柄。但它返回的句柄最终是为一个新对象的,同时原来
的 q 并未发生变化。
 */
public class Stringer { 
	static String upcase(String s) { 
		return s.toUpperCase(); 
	} 
	public static void main(String[] args) { 
		String q = new String("howdy"); 
		System.out.println(q); // howdy 
		String qq = upcase(q); 
		System.out.println(qq); // HOWDY 
		System.out.println(q); // howdy 
	} 
}