package com.example.digiproducer.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "Card")
public class Card {

    private String cardId;
    private String name;
    private String form;
    private String properties;
    private String type;
    private String dp;
    private String appearanceCost;
    private String evolutionCost1;
    private String evolutionCost2;
    private String effect;
    private String sourceEffect;
    private String securityEffect;
    private String waterInformation;
    private String imgUrl;
}

