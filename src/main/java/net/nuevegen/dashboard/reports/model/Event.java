package net.nuevegen.dashboard.reports.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;


//@Entity
@XmlRootElement
//@NamedQuery(name="events",
//			query="SELECT * "
//				+ "FROM ukint_project_delay_reports")
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@FormParam("country")
	private String country;
	@FormParam("project_type")
	private String project_type;
	@FormParam("project_id")
	private String project_id;
	@FormParam("date")
	private Date date;
	@FormParam("type")
	private String type;
	@FormParam("event")
	private String event;
	@FormParam("impact")
	private Integer impact;
	@FormParam("reason")
	private String reason;
	@FormParam("timelines")
	private String timelines;
	private Timestamp autotimestamp;
	@FormParam("event_id")
	private Integer event_id;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Integer getImpact() {
		return impact;
	}
	public void setImpact(Integer impact) {
		this.impact = impact;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTimelines() {
		return timelines;
	}
	public void setTimelines(String timelines) {
		this.timelines = timelines;
	}
	public Timestamp getAutotimestamp() {
		return autotimestamp;
	}
	public void setAutotimestamp(Timestamp autotimestamp) {
		this.autotimestamp = autotimestamp;
	}
	public Integer getEvent_id() {
		return event_id;
	}
	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}
}
