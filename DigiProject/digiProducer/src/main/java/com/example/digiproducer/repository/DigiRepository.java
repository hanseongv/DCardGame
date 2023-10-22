package com.example.digiproducer.repository;

import com.example.digiproducer.entity.Card;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DigiRepository {

   CompletableFuture<List<Card>>findCardByCardName(String cardId);

}
