package com.example.digiproducer.repository.impl;

import com.example.digiproducer.entity.Card;
import com.example.digiproducer.repository.DigiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Repository
//@RequiredArgsConstructor
public class DigiRepositoryImpl implements DigiRepository {
    private static final int limit = 15;
    final MongoTemplate mongoTemplate;

    public DigiRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //    @Override
//    public CompletableFuture<Card> findCardByCardId(String cardName) {
//         Query query = new Query();
//        query.addCriteria(Criteria.where("name").is(cardName));
//        query.fields().exclude("_id" );
//
//        Card findNews = mongoTemplate.findOne(query, Card.class);
//
//        return CompletableFuture.completedFuture(findNews);
//    }
    @Override
    public CompletableFuture<List<Card>> findCardByCardName(String cardName) {

        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("name").regex(cardName)
        );
        ProjectionOperation projectionOperation = Aggregation.project().andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation,projectionOperation);

        List<Card> filteredNews = mongoTemplate.aggregate(aggregation, "Card", Card.class).getMappedResults();

        return CompletableFuture.completedFuture(filteredNews);

    }
}
