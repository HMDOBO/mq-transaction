package com.order.service;

public interface MessageService {

    void saveLocalMessageToDB(String msg);

    void sendLocalBuyRecordMessage(String msg);

}
