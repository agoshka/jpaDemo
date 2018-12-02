package org.agoshka.jpa.demo.data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author go
 */
@Entity
public class RentableResource extends Resource {
    public enum State {
        AVAILABLE,
        BUSY,
        DEPRECATED
    }
    @Enumerated(EnumType.STRING)
    private State state;

    public RentableResource() {
        super();
        state = State.AVAILABLE;
    }

    public RentableResource(String name) {
        super(name);
        state = State.AVAILABLE;
    }
    

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        return String.format("%d %s status=%s", getId(), getName(), state.name());
    }
    
    public void checkout() {
        setState(State.AVAILABLE);
    }
    
    public void checkin() {
        setState(State.BUSY);
    }
}
