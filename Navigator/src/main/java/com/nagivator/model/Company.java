package com.nagivator.model;

import java.util.List;
import java.util.Set;



public class Company{

	private Long id;
	private String name;
	
	private Set<User> userList;
	
	private Set<Branch> branchList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUserList() {
		return userList;
	}

	public void setUserList(Set<User> userList) {
		this.userList = userList;
	}

	public Set<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(Set<Branch> branchList) {
		this.branchList = branchList;
	}
	
	
	
}
