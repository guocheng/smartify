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
@Table(name="gadget_log")
public class GadgetLog extends Model
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="access_time")
	private Timestamp accessTime;
	
	@Column(name="command_type")
	private String commandType;
	
	@Column(name="connection_type")
	private String connectionType;
	
	@Column(name="trigger_id")
	private String triggerId;
	
	@Column(name="trigger_type")
	private String triggerType;
	
	@ManyToOne
	@JoinColumn(name="gadget_id")
	private Gadget gadget;

	public static Finder<String, GadgetLog> find = new Model.Finder<>(String.class, GadgetLog.class);
	
	public static List<GadgetLog> findAll()
	{
		return new ArrayList<GadgetLog>(GadgetLog.find.all());
	}
	
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Timestamp getAccessTime()
	{
		return accessTime;
	}

	public void setAccessTime(Timestamp accessTime)
	{
		this.accessTime = accessTime;
	}

	public String getCommandType()
	{
		return commandType;
	}

	public void setCommandType(String commandType)
	{
		this.commandType = commandType;
	}

	public String getConnectionType()
	{
		return connectionType;
	}

	public void setConnectionType(String connectionType)
	{
		this.connectionType = connectionType;
	}

	public String getTriggerId()
	{
		return triggerId;
	}

	public void setTriggerId(String triggerId)
	{
		this.triggerId = triggerId;
	}

	public String getTriggerType()
	{
		return triggerType;
	}

	public void setTriggerType(String triggerType)
	{
		this.triggerType = triggerType;
	}

	public Gadget getGadget()
	{
		return gadget;
	}

	public void setGadget(Gadget gadget)
	{
		this.gadget = gadget;
	}
}
