package com.mettl.poc.model;

public class TagValue {

	private Long valueId;
	private Integer keyId;
	private String valueName;
	
	public Long getValueId() {
		return valueId;
	}
	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}
	public Integer getKeyId() {
		return keyId;
	}
	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	
	public TagValue(Integer keyId, String valueName) {
		super();
		this.keyId = keyId;
		this.valueName = valueName;
	}
	
	@Override
	public String toString() {
		return "TagValue [valueId=" + valueId + ", keyId=" + keyId + ", valueName=" + valueName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyId == null) ? 0 : keyId.hashCode());
		result = prime * result + ((valueName == null) ? 0 : valueName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagValue other = (TagValue) obj;
		if (keyId == null) {
			if (other.keyId != null)
				return false;
		} else if (!keyId.equals(other.keyId))
			return false;
		if (valueName == null) {
			if (other.valueName != null)
				return false;
		} else if (!valueName.equals(other.valueName))
			return false;
		return true;
	}
	
}