package com.dfx.reverseproxy.settings.userprovison.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TBL_DMS_USERS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="User_Id", unique = true, nullable = false)
	private int userId;
	
	@Column(name="StaffName", nullable = false)
	private String staffName;
	
	@Column(name="FirstName", nullable = true)
	private String firstName;
	
	@Column(name="LastName", nullable = true)
	private String lastName;
	
	@Column(name="Logonid", nullable = false)
	private String logonid;
	
	@Column(name="Email", nullable = true)
	private String email;
	
	@Column(name="SubDepartment", nullable = true)
	private String subDepartment;
	
	@Column(name="Title", nullable = true)
	private String title;
	
	@Column(name="Jurisdiction", nullable = true)
	private String jurisdiction;
	
	@Column(name="LaserficheGroup", nullable = true)
	private String laserficheGroup;
	
	@Column(name="Domain", nullable = true)
	private String domain;
	
	@Column(name="Folder", nullable = true)
	private String folder;
	
	@Column(name="DeletedRecord", nullable = true)
	private Character deletedRecord;
	
	@Column(name="Flag", nullable = true)
	private Character flag;
	
	@Column(name="Gender", nullable = true)
	private String gender;
	
	@Column(name="DepartmentName", nullable = true)
	private String departmentName;
	
	@Column(name="LineManagerId", nullable = false)
	private int lineManagerId;

	@Column(name="TelephoneNumber", nullable = true, length=50)
	private String telephoneNumber;

	@Column(name="IsLineManager", nullable = false)
	private boolean isLineManager;

	@Column(name="DomainUser", nullable = false,length=100)
	private String domainUser;

	@Column(name="DirectorID", nullable = false)
	private int directorID;
	
	@Column(name="IsDirector", nullable = false)
	private boolean isDirector;
	
	@Column(name="LineManagerName", nullable = true,length=100)
	private String lineManagerName;
	
	@Column(name="TotalLeaveCount", nullable = false)
	private int totalLeaveCount;
	
	@Column(name="TakenLeaveCount", nullable = false)
	private int takenLeaveCount;
	
	@Column(name="EmployeeImagePath", nullable = true,length=400)
	private String employeeImagePath;

	@Column(name="Office", nullable = true)
	private String office;

	@Column(name="Division", nullable = true)
	private String division;

	@Column(name="BalanceLeaveCount", nullable = false)
	private int balanceLeaveCount;
	
	@Column(name="ExecutionId", nullable = true, length=50)
	private String executionId;
	
	public TBL_DMS_USERS() {}
	
	public TBL_DMS_USERS(String logonid, char deletedRecord, char flag) {
		this.logonid=logonid;
		this.deletedRecord=deletedRecord;
		this.flag=flag;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogonid() {
		return logonid;
	}

	public void setLogonid(String logonid) {
		this.logonid = logonid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(String subDepartment) {
		this.subDepartment = subDepartment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getLaserficheGroup() {
		return laserficheGroup;
	}

	public void setLaserficheGroup(String laserficheGroup) {
		this.laserficheGroup = laserficheGroup;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Character getDeletedRecord() {
		return deletedRecord;
	}

	public void setDeletedRecord(Character deletedRecord) {
		this.deletedRecord = deletedRecord;
	}

	public Character getFlag() {
		return flag;
	}

	public void setFlag(Character flag) {
		this.flag = flag;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getLineManagerId() {
		return lineManagerId;
	}

	public void setLineManagerId(int lineManagerId) {
		this.lineManagerId = lineManagerId;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public boolean isLineManager() {
		return isLineManager;
	}

	public void setLineManager(boolean isLineManager) {
		this.isLineManager = isLineManager;
	}

	public String getDomainUser() {
		return domainUser;
	}

	public void setDomainUser(String domainUser) {
		this.domainUser = domainUser;
	}

	public int getDirectorID() {
		return directorID;
	}

	public void setDirectorID(int directorID) {
		this.directorID = directorID;
	}

	public boolean isDirector() {
		return isDirector;
	}

	public void setDirector(boolean isDirector) {
		this.isDirector = isDirector;
	}

	public String getLineManagerName() {
		return lineManagerName;
	}

	public void setLineManagerName(String lineManagerName) {
		this.lineManagerName = lineManagerName;
	}

	public int getTotalLeaveCount() {
		return totalLeaveCount;
	}

	public void setTotalLeaveCount(int totalLeaveCount) {
		this.totalLeaveCount = totalLeaveCount;
	}

	public int getTakenLeaveCount() {
		return takenLeaveCount;
	}

	public void setTakenLeaveCount(int takenLeaveCount) {
		this.takenLeaveCount = takenLeaveCount;
	}

	public String getEmployeeImagePath() {
		return employeeImagePath;
	}

	public void setEmployeeImagePath(String employeeImagePath) {
		this.employeeImagePath = employeeImagePath;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getBalanceLeaveCount() {
		return balanceLeaveCount;
	}

	public void setBalanceLeaveCount(int balanceLeaveCount) {
		this.balanceLeaveCount = balanceLeaveCount;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	
}
