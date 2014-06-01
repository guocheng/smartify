package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="hub_log")
public class HubLog extends Model
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="access_time")
	private Timestamp accessTime;
	
	@Column(name="command_type")
	private String commandType;
	
	@Column(name="connection_type")
	private String connectionType;
	
	@ManyToOne
	@JoinColumn(name="hub_id")
	private Hub hub;
	
	public static Finder<String, HubLog> find = new Model.Finder<>(String.class, HubLog.class);
	
	public static List<HubLog> findAll()
	{
		return new ArrayList<HubLog>(HubLog.find.all());
	}
}
