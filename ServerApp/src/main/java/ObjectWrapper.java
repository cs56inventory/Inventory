import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class ObjectWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3284332358893283862L;
	private String key;
	private Object obj;
	private int userId;
	public ObjectWrapper(String key, Object obj) {
		this.key = key;
		this.obj = obj;
	}
	public ObjectWrapper(int userId, String key, Object obj) {
		this.userId = userId;
		this.key = key;
		this.obj = obj;
	}
	public int getUserId() {
		return userId;
	}
	public String getKey() {
		return key;
	}

	public Object getObj() {
		return obj;
	}

	public ByteBuffer getBuffer() {
		ObjectOutputStream ostream;
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();

		try {
			ostream = new ObjectOutputStream(bstream);
			ostream.writeObject(this);
			ByteBuffer buffer = ByteBuffer.allocate(bstream.size());
			buffer.put(bstream.toByteArray());
			buffer.flip();
			return buffer;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
