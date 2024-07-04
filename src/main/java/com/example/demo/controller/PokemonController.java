package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PokemonService;

@RestController
public class PokemonController {
    @Autowired
    private PokemonService service;

    @GetMapping("/pokemons")
    public List<?> getPokemonList(){
        return service.getPokemonList();
    }
}
