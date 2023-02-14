package com.msa.example.chapter06.controller;

public class ClientInfo {
    private final String channel;
    private final String clientAddress;

    public ClientInfo(String channel, String clientAddress) {
        this.channel = channel;
        this.clientAddress = clientAddress;
    }
}
