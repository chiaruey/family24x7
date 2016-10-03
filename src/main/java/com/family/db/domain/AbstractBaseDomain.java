package com.family.db.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;

@MappedSuperclass
abstract public class AbstractBaseDomain{
	

	private String id;
    private String created;
    private String updated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public String getId() {
		return id;
	}

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String param) {
        this.created = param;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String param) {
        this.updated = param;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("created", created)
                .append("updated", updated)
                .toString();
    }
}
