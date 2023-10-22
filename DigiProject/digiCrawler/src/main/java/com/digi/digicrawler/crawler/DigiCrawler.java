package com.digi.digicrawler.crawler;


import com.digi.digicrawler.common.JsonUtil;
import com.digi.digicrawler.dao.card.Card;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;


@Component
public class DigiCrawler {
    private final MongoTemplate mongoTemplate;

    public DigiCrawler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public void Crawler() throws IOException {
        String url = "https://digimoncard.co.kr/index.php?mid=cardlist";
        String defaultUrl = "https://digimoncard.co.kr";

        List<String> categoryUrl = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("div #snaviList ul li a");
        elements.forEach(x -> categoryUrl.add(x.attr("href")));

//        System.out.println(doc.toString());
//        doc = Jsoup.connect(categoryUrl.get(0)).get();
//        Elements a = doc.select("div .paging ul li a");
//        Elements e1 = doc.getElementsByAttributeValue("class", "cardlistCol");
//        Element e2 = e1.get(0);
//        Elements card_name = e2.getElementsByAttributeValue("class", "card_name");
//        Elements card_img = e2.getElementsByAttributeValue("class", "card_img");
//        System.out.println(card_name.toString());
//        System.out.println(card_img.toString());

        for (var cUrl : categoryUrl) {

            doc = Jsoup.connect(cUrl).get();
            Elements a = doc.select("div .paging ul li a");
            int page = a.size() == 0 ? 1 : a.size() / 2;
            for (int i = 1; i <= page; i++) {

                doc = Jsoup.connect(cUrl + "&page=" + i).get();

                Elements e1 = doc.getElementsByAttributeValue("class", "cardlistCol");
                Element e2 = e1.get(0);

                Elements card_name = e2.getElementsByAttributeValue("class", "card_name");
                Elements cardinfo_top = e2.getElementsByAttributeValue("class", "cardinfo_top");
                Elements cardinfo_bottom = e2.getElementsByAttributeValue("class", "cardinfo_bottom");

                List<Map> mapArr = new ArrayList<>();

                for (int j = 0; j < card_name.size(); j++) {
                    Map map = new LinkedHashMap<String, Object>();
                    String[] str;

                    //card_name
                    str = card_name.get(j).text().split(" ", 2);
                    map.put("cardId", str[0].trim());
                    map.put("name", str[1].trim());

                    //card_img
                    Elements card_img = cardinfo_top.select("div .card_img img");
                    map.put("imgUrl", defaultUrl + card_img.get(j).attr("src"));

                    //cardinfo_top_body
                    Elements cardinfo_top_body =cardinfo_top.select("div .cardinfo_top_body");

                    str = cardinfo_top_body.get(j).text().replaceAll(" ", "").replaceFirst("형태", "").split("속성", 2);
                    map.put("form", ConvertNullValue(str[0]));
                    str = str[1].split("유형", 2);
                    map.put("properties", ConvertNullValue(str[0]));
                    str = str[1].split("DP", 2);
                    map.put("type", ConvertNullValue(str[0]));
                    str = str[1].split("등장비용", 2);
                    map.put("dp", ConvertNullValue(str[0]));
                    str = str[1].split("진화비용1", 2);
                    map.put("appearanceCost", ConvertNullValue(str[0]));
                    str = str[1].split("진화비용2", 2);
                    map.put("evolutionCost1", ConvertNullValue(str[0]));
                    map.put("evolutionCost2", ConvertNullValue(str[1]));

                    //cardinfo_bottom
                    String[] str2 = cardinfo_bottom.get(j).text().replaceAll(" ", "").replaceFirst("효과", "").split("진화원효과", 2);
                    map.put("effect", ConvertNullValue(str2[0]));
                    str2 = str2[1].split("시큐리티효과", 2);
                    map.put("sourceEffect", ConvertNullValue(str2[0]));
                    str2 = str2[1].split("입수정보", 2);
                    map.put("securityEffect", ConvertNullValue(str2[0]));
                    map.put("waterInformation", ConvertNullValue(str2[1]));

                    mapArr.add(map);
                }

                var json = JsonUtil.toJson(new ObjectMapper(), mapArr);
                List<Card> save = new ObjectMapper().readValue(json, new TypeReference<ArrayList<Card>>() {
                });

                UpdateCardData(save);

            }
        }

    }

    void UpdateCardData(List<Card> save) {
        for (var n : save) {
            // upsert 내용이 있다면 수정하고, 없다면 인서트함
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("cardId").is(n.getCardId()));

            Query query = new Query(criteria);

            // 인서트할 필드를 정의
            Update update = new Update();
            update.set("imgUrl", n.getImgUrl());
            update.set("name", n.getName());
            update.set("form", n.getForm());
            update.set("properties", n.getProperties());
            update.set("type", n.getType());
            update.set("dp", n.getDp());
            update.set("appearanceCost", n.getAppearanceCost());
            update.set("evolutionCost1", n.getEvolutionCost1());
            update.set("evolutionCost2", n.getEvolutionCost2());
            update.set("effect", n.getEffect());
            update.set("sourceEffect", n.getSourceEffect());
            update.set("securityEffect", n.getSecurityEffect());
            update.set("waterInformation", n.getWaterInformation());

            FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions().returnNew(true).upsert(true);

            mongoTemplate.findAndModify(query, update, findAndModifyOptions, Card.class);
        }
    }

    Object ConvertNullValue(String str) {
        switch (str) {
            case "-":
                return null;
            default:
                return str;
        }
    }
}
