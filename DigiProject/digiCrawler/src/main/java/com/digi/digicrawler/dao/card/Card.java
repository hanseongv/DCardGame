package com.digi.digicrawler.dao.card;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "Card")
public class Card {
    @JsonProperty("cardId")
    @Field("cardId")
    private String cardId;
    @JsonProperty("name")
    @Field("name")
    private String name;
    @JsonProperty("form")
    @Field("form")
    private String form;
    @JsonProperty("properties")
    @Field("properties")
    private String properties;
    @JsonProperty("type")
    @Field("type")
    private String type;
    @JsonProperty("dp")
    @Field("dp")
    private String dp;
    @JsonProperty("appearanceCost")
    @Field("appearanceCost")
    private String appearanceCost;
    @JsonProperty("evolutionCost1")
    @Field("evolutionCost1")
    private String evolutionCost1;
    @JsonProperty("evolutionCost2")
    @Field("evolutionCost2")
    private String evolutionCost2;
    @JsonProperty("effect")
    @Field("effect")
    private String effect;
    @JsonProperty("sourceEffect")
    @Field("sourceEffect")
    private String sourceEffect;
    @JsonProperty("securityEffect")
    @Field("securityEffect")
    private String securityEffect;
    @JsonProperty("waterInformation")
    @Field("waterInformation")
    private String waterInformation;
    @JsonProperty("imgUrl")
    @Field("imgUrl")
    private String imgUrl;
}

