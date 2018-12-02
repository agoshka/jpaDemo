package org.agoshka.jpa.demo.data;

import javax.persistence.Entity;

/**
 *
 * @author go
 */
@Entity
public class ConsumableResource extends Resource {
    private int amount;

    public ConsumableResource() {
         super();
    }

    public ConsumableResource(String name) {
        super(name);
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public void incAmount(int amount) {
        this.amount += amount;
    }
    public int decAmount(int amount) {
        if (amount > this.amount) {
            amount = this.amount;
        }
        this.amount -= amount;
        return amount;
    }
    
    @Override
    public String toString() {
        return String.format("%d %s amount=%d", getId(), getName(), amount);
    }
    
}
