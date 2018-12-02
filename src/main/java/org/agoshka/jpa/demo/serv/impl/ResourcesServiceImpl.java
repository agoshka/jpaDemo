
package org.agoshka.jpa.demo.serv.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.agoshka.jpa.demo.Common;
import org.agoshka.jpa.demo.data.ConsumableResource;
import org.agoshka.jpa.demo.data.Rent;
import org.agoshka.jpa.demo.data.RentableResource;
import org.agoshka.jpa.demo.data.RentableResource.State;
import org.agoshka.jpa.demo.data.Resource;
import org.agoshka.jpa.demo.data.UserActionLogRecord;
import org.agoshka.jpa.demo.repo.ConsumableResourceRepo;
import org.agoshka.jpa.demo.repo.RentRepo;
import org.agoshka.jpa.demo.repo.RentableResourceRepo;
import org.agoshka.jpa.demo.repo.ResourceRepo;
import org.agoshka.jpa.demo.serv.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author go
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class ResourcesServiceImpl implements ResourcesService {

    @Autowired
    ConsumableResourceRepo crRepo;
    
    @Autowired
    RentableResourceRepo rrRepo;

    @Autowired
    ResourceRepo reRepo;
    
    @Autowired
    RentRepo rentRepo;
    
    @Override
    public List<Resource> listAllAvailableResources(UserActionLogRecord log) {
        List<Resource> lst =  reRepo.findAll();
        log.success();
        return lst;
    }

    @Override
    public List<ConsumableResource> listResourcesForConsumption(UserActionLogRecord log) {
        List<ConsumableResource> lst = crRepo.getAvailableResources();
        log.success();
        return lst;
    }

    @Override
    public void consumeResource(ConsumableResource resource, int amount, UserActionLogRecord log) {
        amount = resource.decAmount(amount);
        log.add(Common.CONSUMED_AMOUNT, amount);
        log.success();
    }

    @Override
    public List<ConsumableResource> listConsumedResources(UserActionLogRecord log) {
        List<ConsumableResource> lst = crRepo.getConsumedResources();
        log.success();
        return lst;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Throwable.class)
    public ConsumableResource replenishResource(ConsumableResource resource, int amount, UserActionLogRecord log) {
        resource.incAmount(amount);
        crRepo.save(resource);
        log.addResource(resource);
        log.success();
        return resource;
    }

    @Override
    public Rent checkinResource(RentableResource resource, UserActionLogRecord log) {
        Rent rent = rentRepo.findById(resource.getId()).orElse(null);
        if (rent == null) {
            rent = new Rent(resource, log.getUser());
            resource.checkin();
            rrRepo.save(resource);
            rentRepo.save(rent);
        }
        log.success();
        return rent;
    }

    @Override
    public void checkoutResource(RentableResource resource, UserActionLogRecord log) {
        Rent rent = rentRepo.findById(resource.getId()).orElse(null);
        if (rent != null) {
            resource.checkout();
            rrRepo.save(resource);
            rentRepo.delete(rent);
            log.success();
        }
    }

    @Override
    public void addResourceToInventory(RentableResource resource, UserActionLogRecord log) {
        rrRepo.save(resource);
        log.addResource(resource);
        log.success();
    }

    @Override
    public List<RentableResource> listResourcesForRent(UserActionLogRecord log) {
        List<RentableResource> lst = rrRepo.getResourcesByState(State.AVAILABLE.name());
        log.success();
        return lst;
    }
    
    @Override
    public void writeOffResourceFromInventory(RentableResource resource, UserActionLogRecord log) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
