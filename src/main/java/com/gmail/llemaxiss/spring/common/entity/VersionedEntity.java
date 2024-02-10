package com.gmail.llemaxiss.spring.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class VersionedEntity implements Serializable {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_ts")
	private Date createTs;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_ts")
	private Date updateTs;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "delete_ts")
	private Date deleteTs;
	
	@Column(name = "deleted_by")
	private String deletedBy;
	
	@PrePersist
	protected void prePersist() {
		setCreateTs(new Date());
		setCreatedBy("admin");
	}
	
	@PreUpdate
	protected void preUpdate() {
		setUpdateTs(new Date());
		setUpdatedBy("admin");
	}
	
	public Date getCreateTs() {
		return createTs;
	}
	
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getUpdateTs() {
		return updateTs;
	}
	
	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public Date getDeleteTs() {
		return deleteTs;
	}
	
	public void setDeleteTs(Date deleteTs) {
		this.deleteTs = deleteTs;
	}
	
	public String getDeletedBy() {
		return deletedBy;
	}
	
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}
}
