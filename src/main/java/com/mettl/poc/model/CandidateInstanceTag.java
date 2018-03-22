package com.mettl.poc.model;

public class CandidateInstanceTag {

	private boolean isCrf;
	private Key key;
	private Value value;
	
	public boolean isCrf() {
		return isCrf;
	}
	public void setCrf(boolean isCrf) {
		this.isCrf = isCrf;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	public CandidateInstanceTag() {
		super();
	}
	public CandidateInstanceTag(Key key, Value value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public CandidateInstanceTag(Value value) {
		super();
		this.value = value;
	}
	
	public CandidateInstanceTag(Key key) {
		super();
		this.key = key;
	}
	
	@Override
	public String toString() {
		return "CandidateInstanceTag [isCrf=" + isCrf + ", key=" + key + ", value=" + value + "]";
	}

	public class Key {

		private Integer keyId;
		private String keyName;

		public Integer getKeyId() {
			return keyId;
		}

		public void setKeyId(Integer keyId) {
			this.keyId = keyId;
		}

		public String getKeyName() {
			return keyName;
		}

		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}

		public Key(String keyName) {
			super();
			this.keyName = keyName;
		}

		@Override
		public String toString() {
			return "Key [keyId=" + keyId + ", keyName=" + keyName + "]";
		}

	}

	public class Value {

		private Integer valueId;
		private String valueName;

		public Integer getValueId() {
			return valueId;
		}

		public void setValueId(Integer valueId) {
			this.valueId = valueId;
		}

		public String getValueName() {
			return valueName;
		}

		public void setValueName(String valueName) {
			this.valueName = valueName;
		}

		public Value(String valueName) {
			super();
			this.valueName = valueName;
		}

		@Override
		public String toString() {
			return "Value [valueId=" + valueId + ", valueName=" + valueName + "]";
		}
	}

}