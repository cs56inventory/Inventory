import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;


public class ObjectWrapper implements Serializable {

	String key; 
	Object obj;
	
	public ObjectWrapper(String key, Object obj){
		this.key = key;
		this.obj = obj;
	}

	public String getKey() {
		return key;
	}

	public Object getObj() {
		return obj;
	}
	
	public ByteBuffer getBuffer(){
	    ObjectOutputStream ostream;
	    ByteArrayOutputStream bstream = new ByteArrayOutputStream();
	 
	    try {
	        ostream = new ObjectOutputStream(bstream);
	        ostream.writeObject(this);
	        ByteBuffer buffer = ByteBuffer.allocate(bstream.size());
	        buffer.put(bstream.toByteArray());
	        buffer.flip();
	        return buffer;
	    }
	    catch(IOException e){
	        e.printStackTrace();
	    }
	    return null;
	}
}
