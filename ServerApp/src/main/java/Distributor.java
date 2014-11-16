import java.io.Serializable;
import java.util.HashMap;


public class Distributor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7149060961528614152L;
	private int distributor_id;
	private String distributor_name;
	private int distributor_status_id;
	
	public Distributor(){
		
	}
	
	public Distributor(HashMap<String, String> row){

		this.setProperties(row);
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

	private void setProperties(HashMap<String, String> row){
		this.setDistributor_id(new Integer(row.get(Db.DistributorMap.id)));
		this.setDistributor_name(row.get(Db.DistributorMap.name));
		this.setDistributor_status_id(new Integer(row.get(Db.DistributorMap.status_id)));
	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> distributorTable = new HashMap<String, String>();
		distributorTable.put(Db.DistributorMap.id, new Integer(this.getDistributor_id()).toString());
		distributorTable.put(Db.DistributorMap.name, this.getDistributor_name());
		distributorTable.put(Db.DistributorMap.status_id, new Integer(this.getDistributor_status_id()).toString());
		
		return distributorTable;
	}
}
