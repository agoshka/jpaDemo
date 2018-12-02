
package org.agoshka.jpa.demo.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author go
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Resource {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Resource() {
    }

    public Resource(String name) {
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
    
}
