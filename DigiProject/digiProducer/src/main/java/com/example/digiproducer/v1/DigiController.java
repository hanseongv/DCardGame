package com.example.digiproducer.v1;

import com.example.digiproducer.entity.Card;
import com.example.digiproducer.repository.DigiRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("v1")
public class DigiController {
    final DigiRepository digiRepository;

    public DigiController(DigiRepository digiRepository) {
        this.digiRepository = digiRepository;
    }

    @Async
    @GetMapping("/card/{cardName}/list")
    @CrossOrigin(origins = "http://localhost:5173/")
    public CompletableFuture<List<Card>> findCardByCardName(@PathVariable String cardName) {
        return digiRepository.findCardByCardName(cardName);
    }
}
