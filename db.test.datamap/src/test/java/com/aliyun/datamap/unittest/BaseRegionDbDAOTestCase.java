package com.aliyun.datamap.unittest;

import com.aliyun.jtester.module.ICore;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * Created by chao.qianc on 2016/5/27.
 */
@SpringApplicationContext("classpath:spring-test-regiondb-dao-context.xml")
public abstract class BaseRegionDbDAOTestCase extends UnitilsJUnit4 implements ICore {

}
