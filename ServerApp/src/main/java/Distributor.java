
public class Distributor {
	
	private int distributor_id;
	private String distributor_name;
	private int distributor_status_id;
	
	public Distributor(){
		
	}
	
	public Distributor(int distributor_id, String distributor_name, int distributor_status_id){
		this.distributor_id = distributor_id;
		this.distributor_name = distributor_name;
		this.distributor_status_id = distributor_status_id;
		
	}
	
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	public String getDistributor_name() {
		return distributor_name;
	}
	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}
	public int getDistributor_status_id() {
		return distributor_status_id;
	}
	public void setDistributor_status_id(int distributor_status_id) {
		this.distributor_status_id = distributor_status_id;
	}


}
