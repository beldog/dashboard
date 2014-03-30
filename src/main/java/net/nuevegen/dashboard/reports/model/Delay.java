package net.nuevegen.dashboard.reports.model;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;


//@Entity
@XmlRootElement
public class Delay implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String country;
	private String month;
	private String ticket;
	private String project_type;
	private Boolean launched;
	private String realLaunchDate;
	private String plannedLaunchDate;
	private Integer delay;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public Boolean getLaunched() {
		return launched;
	}
	public void setLaunched(Boolean launched) {
		this.launched = launched;
	}
	public String getRealLaunchDate() {
		return realLaunchDate;
	}
	public void setRealLaunchDate(String realLaunchDate) {
		this.realLaunchDate = realLaunchDate;
	}
	public String getPlannedLaunchDate() {
		return plannedLaunchDate;
	}
	public void setPlannedLaunchDate(String plannedLaunchDate) {
		this.plannedLaunchDate = plannedLaunchDate;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	
	
}
