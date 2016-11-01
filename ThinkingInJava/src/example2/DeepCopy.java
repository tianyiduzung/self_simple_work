package example2;
/*
OceanReading 是由 DepthReading 和 TemperatureReading 对象合并而成的。
为了对其进行深层复制,clone()必须同时克隆 OceanReading 内的句柄。
为达到这个目标,super.clone()的结果必须造型成一个OceanReading 对象
(以便访问 depth 和 temperature 句柄)。 
 */
class DepthReading implements Cloneable { 
	private double depth; 
	public DepthReading(double depth) {  
		this.depth = depth; 
	} 
	public Object clone() { 
		Object o = null; 
		try { 
			o = super.clone(); 
		} catch (CloneNotSupportedException e) { 
			e.printStackTrace(); 
		} 
		return o; 
	} 
} 

class TemperatureReading implements Cloneable { 
	private long time; 
	private double temperature; 
	public TemperatureReading(double temperature) { 
		time = System.currentTimeMillis(); 
		this.temperature = temperature; 
	} 
	public Object clone() { 
		Object o = null; 
		try { 
			o = super.clone(); 
		} catch (CloneNotSupportedException e) { 
			e.printStackTrace(); 
		} 
		return o; 
	} 
} 

class OceanReading implements Cloneable { 
	private DepthReading depth; 
	private TemperatureReading temperature; 
	public OceanReading(double tdata, double ddata){ 
		temperature = new TemperatureReading(tdata); 
		depth = new DepthReading(ddata); 
	} 
	public Object clone() { 
		OceanReading o = null; 
		try { 
			o = (OceanReading)super.clone(); 
		} catch (CloneNotSupportedException e) { 
			e.printStackTrace(); 

		} 
		// Must clone handles: 
		o.depth = (DepthReading)o.depth.clone(); 
		o.temperature =  
				(TemperatureReading)o.temperature.clone(); 
		return o; // Upcasts back to Object 
	} 
} 

public class DeepCopy { 
	public static void main(String[] args) { 
		OceanReading reading =  
				new OceanReading(33.9, 100.5);
		// Now clone it: 
		OceanReading r =  
				(OceanReading)reading.clone(); 
	} 
}
