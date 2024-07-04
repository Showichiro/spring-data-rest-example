package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Pokemon;
import com.example.demo.repository.PokemonListRepository;

@Service
public class PokemonService {
    @Autowired
    private PokemonListRepository repository;

    public List<Pokemon> getPokemonList() {
        return repository.getPokemonList().stream().map(res -> {
            var pokemon = new Pokemon();
            pokemon.setId(res.id());
            pokemon.setName(res.name());
            return pokemon;
        }).toList();
    }
}
