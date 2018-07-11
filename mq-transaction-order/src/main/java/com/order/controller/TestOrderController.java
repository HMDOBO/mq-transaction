package com.order.controller;

import com.common.common.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestOrderController {

    @RequestMapping(value = "testOrder")
    public ResponseMessage<String> test() {
        return ResponseMessage.success("testOrder success");
    }

}
