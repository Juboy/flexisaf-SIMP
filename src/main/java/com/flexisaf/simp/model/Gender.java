package com.flexisaf.simp.model;

public enum Gender {

	M{
		public String getValue() {
			return "M";
		}
	},F{
		public String getValue() {
			return "F";
		}
	};
	
	public abstract String getValue();
}
