import java.io.Serializable;


public class ObjectWrapper implements Serializable {
	String key; 
	Object obj;
	
	public ObjectWrapper(String key, Object obj){
		this.key = key;
		this.obj = obj;
	}
}
