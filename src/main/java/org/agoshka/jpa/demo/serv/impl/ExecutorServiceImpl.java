
package org.agoshka.jpa.demo.serv.impl;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.agoshka.jpa.demo.Common;
import org.agoshka.jpa.demo.DemoException;
import org.agoshka.jpa.demo.data.ConsumableResource;
import org.agoshka.jpa.demo.data.Rent;
import org.agoshka.jpa.demo.data.RentableResource;
import org.agoshka.jpa.demo.data.Resource;
import org.agoshka.jpa.demo.data.UserActionLogRecord;
import org.agoshka.jpa.demo.data.UserInfo;
import org.agoshka.jpa.demo.repo.ConsumableResourceRepo;
import org.agoshka.jpa.demo.repo.RentableResourceRepo;
import org.agoshka.jpa.demo.repo.UserActionLogRecordRepo;
import org.agoshka.jpa.demo.repo.UserRepo;
import org.agoshka.jpa.demo.serv.ExecutorService;
import org.agoshka.jpa.demo.serv.OutputService;
import org.agoshka.jpa.demo.serv.ResourcesService;
import org.agoshka.jpa.demo.serv.UserActionsLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author go
 */
@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class ExecutorServiceImpl implements ExecutorService {
    final static Logger logger = LoggerFactory.getLogger(ExecutorServiceImpl.class);
    @Autowired
    OutputService out;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    UserActionsLogService logServ;
    
    @Autowired
    UserActionLogRecordRepo logRepo;
    
    @Autowired
    ResourcesService resServ;
     
    @Autowired
    ConsumableResourceRepo crRepo;
    @Autowired
    RentableResourceRepo rrRepo;

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, dontRollbackOn = Throwable.class)
    /**
     * выполняет команду, которая указана в props
     * UserActionLogRecord - создается в одной транзакции, а execute выполняется в другой,
     * для того чтоб  зафиксировать все действия пользователя, и удачные и неудачные
     * 
     * ??? Не разобралась еще как уговорить JPA не фейлить приложение, если у меня фейлится 
     * внутренняя транзакция
     */
    public void executeAction(UserInfo u, String ip, Map<String, String> props) {
        UserActionLogRecord log = logServ.recordUserAction(ip, u, props);
        try {
            execute(log);
        } catch (Exception e) {
            debug("Execution is failed: {}", e);
            log.fail();
        }
        //continue transaction with log action
        logRepo.save(log);
        debug("User action has been saved: {}", log);
    }
    
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Throwable.class)
    /**
     * выполнение команды в отдельной транзакции
     */
    public void execute(UserActionLogRecord log) {
        Map<String,String> prop = log.getInfo();
        String action = prop.getOrDefault(Common.ACTION, null);
        if (action == null) throw new DemoException("Action is not defined");
        switch (action) {
            case "replenishResource":
                replenishResource(prop.get("resource"), prop.get("amount"), log);
                break;
            case "addResourceToInventory":
                addResourceToInventory(prop.get("resource"), log);
                break;
            case "listConsumedResources":
                listConsumedResources(log);
                break;
            case "listResourcesForConsumption":
                listResourcesForConsumption(log);
                break;
            case "consumeResource":
                consumeResource(prop.get("resource"), prop.get("amount"), log);
                break;
            case "listAllAvailableResources":
                listAllAvailableResources(log);
                break;
            case "checkoutResource":
                checkoutResource(prop.get(Common.RESOURCE), log);
                break;
            case "checkinResource":
                checkinResource(prop.get(Common.RESOURCE), log);
                break;
            case "listResourcesForRent" :
                listResourcesForRent(log);
                break;
            default:
                throw new DemoException("Unknown Action " + action);
        
        }
    }

    /**
     * пополняет расходные ресурсы
     * @param resourceName
     * @param strAmount
     * @param log
     * @return 
     */
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Throwable.class)
    private ConsumableResource replenishResource(String resourceName, String strAmount, UserActionLogRecord log) {
        ConsumableResource res = getConsumableResource(resourceName, true);
        int amount = getAmount(strAmount);
        res = resServ.replenishResource(res, amount, log);
        debug ("resource after replenish: {}", res);
        out.output("=== replenishResource:");
        out.output(res);
        return res;
    }

    /**
     * по названию ресурса возвращает расходный ресурс
     * @param resourceName
     * @return 
     */
    private ConsumableResource getConsumableResource(String resourceName, boolean createIfNull) {
        ConsumableResource cr = crRepo.findByResourceName(resourceName);
        if (cr == null && createIfNull) {
            cr = new ConsumableResource(resourceName);
        }
        return cr;
    }
    
    /**
     * по названию ресурса возвращает объект для оренды
     * @param resourceName
     * @return 
     */
    private RentableResource getRentableResource(String resourceName, boolean createIfNull) {
        resourceName = Common.checkStr(resourceName);
        if (resourceName == null ) throw new DemoException("Empty resource name");
        RentableResource rr = rrRepo.findByResourceName(resourceName);
        if (rr == null && createIfNull) {
            rr = new RentableResource(resourceName);
        }
        return rr;
    }

    /**
     * преобразовует количество в целое
     * @param strAmount
     * @return 
     */
    private int getAmount(String strAmount) {
        try {
            return Integer.parseInt(strAmount);
        } catch(NumberFormatException ex) {
            throw new DemoException("Cannot transform amount " + strAmount + " to a number");
        }
    }
    
    protected void debug(String format, Object...args) {
        logger.debug(format, args);
    }

    private Object addResourceToInventory(String resource, UserActionLogRecord log) {
        RentableResource rr = getRentableResource(resource, true);
        resServ.addResourceToInventory(rr, log);
        debug ("resource after adding to inventory: {}", rr);
        out.output("=== addResourceToInventory: Resource " + resource + " was added to Inventory");
        return rr;
    }

    private void listConsumedResources(UserActionLogRecord log) {
        List<ConsumableResource> lst = resServ.listConsumedResources(log);
        out.output("=== listConsumedResources: ");
        lst.forEach((r) -> out.output(r));
    }

    private void listResourcesForConsumption(UserActionLogRecord log) {
        List<ConsumableResource> lst = resServ.listResourcesForConsumption(log);
        out.output("=== listResourcesForConsumption: ");
        lst.forEach((r) -> out.output(r));
    }
    
    private ConsumableResource consumeResource(String resourceName, String strAmount, UserActionLogRecord log) {
        ConsumableResource res = getConsumableResource(resourceName, false);
        int amount = getAmount(strAmount);
        resServ.consumeResource(res, amount, log);
        debug ("resource after consuming: {}", res);
        out.output("=== consumeResource: ");
        out.output(res);
        return res;
    }
    //TODO:  not all, only available
    private void listAllAvailableResources(UserActionLogRecord log) {
        List<Resource> lst = resServ.listAllAvailableResources(log);
        out.output("=== listAllAvailableResources: ");
        lst.forEach((r) -> out.output(r));
    }
    
    private void checkoutResource(String resourceName, UserActionLogRecord log) {
        RentableResource rr = getRentableResource(resourceName, false);
        if (rr == null) throw new DemoException("Resource is not found");
        resServ.checkinResource(rr, log);
        out.output("=== checkoutResource: Done");
    }

    private void checkinResource(String resourceName, UserActionLogRecord log) {
        RentableResource rr = getRentableResource(resourceName, false);
        if (rr == null) throw new DemoException("Resource is not found");
        Rent r =resServ.checkinResource(rr, log);
        out.output("=== checkinResource: => ");
        out.output(r);
    }

    private void listResourcesForRent(UserActionLogRecord log) {
        List<RentableResource> lst = resServ.listResourcesForRent(log);
        out.output("=== listResourcesForRent: ");
        lst.forEach((r) -> out.output(r));
    }
}
