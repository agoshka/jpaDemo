
package org.agoshka.jpa.demo.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author go
 */
@Entity
public class UserInfo {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
    }
    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @Override
    public String toString() {
        return String.format("%d %s", id, name);
    }
}
