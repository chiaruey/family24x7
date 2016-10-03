package com.family.util;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.family.db.domain.AbstractBaseDomain;
import com.family.db.util.DbUtils;

/**
 * This class was created as base listener
 * 
 * The EntityListeners seems to have bugs for SimpleJpa, may play around later
 * 
 * @author Daddy
 * @deprecated
 */
public class MySdbEntityListener {

    @PrePersist
    public void prePersist(Object object) {
        if(object instanceof AbstractBaseDomain){
        	AbstractBaseDomain baseEntity = (AbstractBaseDomain) object;
//        	baseEntity.setId(SdbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
            baseEntity.setUpdated(now);
            baseEntity.setCreated(now);
        }
    }

    @PreUpdate
    public void preUpdate(Object object) {
        if(object instanceof AbstractBaseDomain){
        	AbstractBaseDomain baseEntity = (AbstractBaseDomain) object;
        	String now = DbUtils.getCurrentTimestamp();
            baseEntity.setUpdated(now);
        }
    }

}
