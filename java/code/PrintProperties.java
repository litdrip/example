package net.xuele.notify2.properties;

import com.github.diamond.client.PropertiesConfigurationFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * 月氏琅琊 2016/10/15 0015.
 */
@Component
public class PrintProperties {

    private static final Logger logger = LoggerFactory.getLogger(PrintProperties.class);
    @Autowired
    private PropertiesConfigurationFactoryBean propertiesConfigurationFactoryBean;

    @PostConstruct
    private void print(){
        try {
            logger.info("|-----------------------------------------------------------");
            logger.info("|输出配置:");
            Properties object = propertiesConfigurationFactoryBean.getObject();
            List<String> list = new ArrayList<>();
            for(Map.Entry<Object, Object> e :object.entrySet()){
                list.add(e.getKey()+"\t =["+e.getValue()+"]");
            }
            Collections.sort(list);
            for (String s : list){
                logger.info("|"+s);
            }
            logger.info("|-----------------------------------------------------------");
        } catch (Exception e) {}
    }
}
