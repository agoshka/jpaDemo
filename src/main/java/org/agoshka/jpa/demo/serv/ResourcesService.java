/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agoshka.jpa.demo.serv;

import org.agoshka.jpa.demo.data.RentableResource;
import java.util.List;
import org.agoshka.jpa.demo.data.ConsumableResource;
import org.agoshka.jpa.demo.data.Rent;
import org.agoshka.jpa.demo.data.Resource;
import org.agoshka.jpa.demo.data.UserActionLogRecord;

/**
 *
 * @author go
 */
public interface ResourcesService {
    
    List<Resource> listAllAvailableResources(UserActionLogRecord log);
    List<ConsumableResource> listResourcesForConsumption(UserActionLogRecord log);
    void consumeResource(ConsumableResource resource, int amount, UserActionLogRecord log);
    List<ConsumableResource> listConsumedResources(UserActionLogRecord log);
    void checkoutResource(RentableResource resource, UserActionLogRecord log);
    Rent checkinResource(RentableResource resource, UserActionLogRecord log);
    void writeOffResourceFromInventory(RentableResource resource, UserActionLogRecord log);
    List<RentableResource> listResourcesForRent(UserActionLogRecord log);
    //List<Rent> listMyActiveRents(UserActionLogRecord log);
    
    ConsumableResource replenishResource(ConsumableResource resource, int amount, UserActionLogRecord log);
    void addResourceToInventory(RentableResource resource, UserActionLogRecord log);
}
