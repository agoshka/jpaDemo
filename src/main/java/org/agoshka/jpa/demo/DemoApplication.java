package org.agoshka.jpa.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import org.agoshka.jpa.demo.data.UserInfo;
import org.agoshka.jpa.demo.serv.ExecutorService;
import org.agoshka.jpa.demo.serv.UsersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * это консольное приложение, параметры передаются через командную строку
 * Например:
 * 
 * java -jar DemoApplication user=guest ip=127.0.0.1 replenishResource amount=15 resource=resource1
 * 
 * Параметры user и ip должны быть указаны вначале
 * дальше указывается action (в примере replenishResource)  и параметры для этого действия 
 * (в данном случае количество и имя ресурса)
 * В командной строке можно указывать несколько действий
 * 
 * Actions&Parameters:
 * replenishResource  - пополняет расходный ресурс
 *      amount=<resource income>
 *      resource=<resourceName>
 * addResourceToInventory - добавляет ресурс для аренды
 *      resource=<resourceName>
 * listResourcesForConsumption  - список доступных расходных ресурсов 
 * 
 * listAllAvailableResources - список всех доступных ресурсов
 * 
 * consumeResource  - пополняет расходный ресурс
 *      amount=<resource quantity>
 *      resource=<resourceName>
 * listConsumedResources  - список расходных ресурсов которые закончились на данный момент
 * checkoutResource - возвращает ресурс
 *      resource=<resourceName>
 * checkinResource - берет в пользование ресурс
 *      resource=<resourceName>
 * listResourcesForRent - список доступных ресурсов для аренды
 * 
 * 
 * @author go
 */
@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {
    final static Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    @Autowired
    UsersService userServ;
    
    @Autowired
    ExecutorService executor;
    
    static {
        PropertyConfigurator.configure(DemoApplication.class.getResource("/log4j.properties"));
    }
    
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Queue<String> q = new LinkedList<>(Arrays.asList(args));
        q.add("end");
        UserInfo user = null;
        String ip = null;
        String action = null;
        Map<String,String> prop = new HashMap<>();
        //parse  command line arguments
        while (!q.isEmpty()) {
            String s = q.poll();
            debug("Params {}", s);
            Entry<String, String> ent = Common.strToPair(s);
            if (ent == null) {
                continue;
            }
            debug("Key={} value={}", ent.getKey(), ent.getValue());
            switch (ent.getKey()) {
                case "user":
                    user = userServ.findOrAddUser(ent.getValue());
                    break;
                case "ip":
                    ip = ent.getValue();
                    break;
                default:
                    //is new action?
                    if (ent.getValue() == null) {
                        //execute current action
                        executeAction(action, prop, user, ip);
                        //start new action
                        prop.clear();
                        action = ent.getKey();
                        debug("== new action {}", action);
                    } else {
                        //collect params for current action
                        if (action != null) {
                            prop.put(ent.getKey(), ent.getValue());
                        }
                    }
            }
        }
    }

    private void executeAction(String action, Map<String, String> prop, UserInfo user, String ip) {
        if (action == null) return;
        debug("== execute action {}", action);
        if (user == null) throw new DemoException("User is not defined");
        if (ip == null) throw new DemoException("IpAddress is not defined");
        prop.put(Common.ACTION, action);
        executor.executeAction( user, ip, prop);
        debug("user action={} is finished \n",  action);
    }
    
    protected void debug(String format, Object...args) {
        logger.debug(format, args);
    }
    
    
    
}
