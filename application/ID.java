package application;

public abstract class ID {
	private String ID;
	private long creationTime;
	
	public ID(String ID) {
		this.ID = ID;
		this.creationTime = System.currentTimeMillis();
	}
	
	public void setID(String ID) {
		this.ID = ID; 
	}
	
	public String getID() {
		return ID;
	}
	
	public long getCreationTime() {
		return creationTime; 
	}
}
