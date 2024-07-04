package com.example.demo.repository;

import java.util.List;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx3.Rx3Apollo;

import pokemon.PokemonListQuery.Pokemon_v2_pokemon;

@Repository
@RepositoryRestResource(collectionResourceRel = "pokemon", path = "pokemons",  exported = true)
public class PokemonListRepository {
    public List<Pokemon_v2_pokemon> getPokemonList() {
        final var apolloClient = ApolloClient.builder()
                .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
                .build();
        var query = pokemon.PokemonListQuery.builder().build();
        final var apolloQueryCall = apolloClient.query(query);
        return Rx3Apollo.from(apolloQueryCall)
                .map(Response::getData)
                .map(pokemon.PokemonListQuery.Data::pokemon_v2_pokemon)
                .blockingFirst();
    }
}
