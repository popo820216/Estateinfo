package cn.com.example.domain;

public class CItem {
	private String ID;
	private String value;
	
	public CItem(){
		ID = "";
		value = "";
	}
	
	public CItem(String ID, String value){
		this.ID = ID;
		this.value = value;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return getValue();
	}
}
