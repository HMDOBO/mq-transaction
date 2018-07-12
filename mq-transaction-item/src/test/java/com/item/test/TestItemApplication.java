package com.item.test;

import com.common.domain.OrderItemRecordDO;
import com.item.api.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestItemApplication {

    @Autowired
    private ItemService itemService;

    @Test
    public void method() {
        List<OrderItemRecordDO> list = new ArrayList<>();
        OrderItemRecordDO oird = new OrderItemRecordDO();
        oird.setItem_id(1L);
        oird.setOrder_id(1016991606412214274L);
        oird.setPhone("13333333333");
        oird.setUsername("张三");

        list.add(oird);

        itemService.recordOrderItem(list);
    }

}

