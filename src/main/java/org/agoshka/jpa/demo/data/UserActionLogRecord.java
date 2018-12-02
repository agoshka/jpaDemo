
package org.agoshka.jpa.demo.data;

import java.time.LocalDateTime;
import java.util.Map;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.agoshka.jpa.demo.Common;
import org.agoshka.jpa.demo.repo.PropConverter;

/**
 *
 * @author go
 */
@Entity
public class UserActionLogRecord {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private UserInfo user;
    private LocalDateTime dt;
    private String ipAddress;
    @Convert(converter = PropConverter.class)
    private Map<String,String> props;

    public UserActionLogRecord() {
    }

    public UserActionLogRecord(UserInfo user, String ipAddress, Map<String,String> info) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.dt = LocalDateTime.now();
        this.props = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Map<String,String> getInfo() {
        return props;
    }

    public LocalDateTime getTime() {
        return dt;
    }
    
    public void add(String key, String val) {
        props.put(key,val); 
    }
    
    public void add(String key, int val) {
        props.put(key, String.valueOf(val)); 
    }
    
    public void success() {
        props.put(Common.RESULT, Common.SUCCESS);
    }

    @Override
    public String toString() {
        return String.format("id=%d user=%s time=%s props=%s", id, user.getName(), dt.toString(), props);
    }

    public void fail() {
        props.put(Common.RESULT, Common.FAILED);
    }

    public void addResource(Resource resource) {
        add(Common.RESOURCE, resource.getId());
    }
    
}
