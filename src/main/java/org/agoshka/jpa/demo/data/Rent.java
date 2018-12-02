
package org.agoshka.jpa.demo.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author go
 */
@Entity
public class Rent {
    //TODO  id should be resource_id
    @Id
    int id;
    @OneToOne
    RentableResource resource;
    @ManyToOne
    UserInfo user;

    public Rent() {
    }

    public Rent(RentableResource resource, UserInfo user) {
        this.resource = resource;
        this.id = resource.getId();
        this.user = user;
    }

    
    public RentableResource getResource() {
        return resource;
    }

    public void setResource(RentableResource resource) {
        this.resource = resource;
        this.id = resource.getId();
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return resource.toString() + " " + user.toString();
    }
    
    
}
