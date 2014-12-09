import java.io.Serializable;
import java.util.HashMap;


public class Status_Map implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1953990327373416312L;
	private int status_id;
	private String status_description;
	
	public Status_Map(){
		
	}
	public Status_Map(HashMap<String, String> row){

//		this.setProperties(row);
	}
	public Status_Map(int status_id, String status_description){
		
		this.status_id = status_id;
		this.status_description = status_description ;
		
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	
	

}
