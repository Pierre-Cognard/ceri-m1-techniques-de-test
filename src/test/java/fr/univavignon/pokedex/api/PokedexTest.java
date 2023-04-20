package fr.univavignon.pokedex.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PokedexTest {

    private IPokemonMetadataProvider metadataProvider;
    private IPokemonFactory pokemonFactory;
    private Pokedex pokedex;

    @BeforeEach
    public void setUp() {
        metadataProvider = mock(IPokemonMetadataProvider.class);
        pokemonFactory = mock(IPokemonFactory.class);
        pokedex = new Pokedex(metadataProvider, pokemonFactory);
    }

    @Test
    public void getPokemonMetadataTest() throws PokedexException {
        PokemonMetadata metadata = new PokemonMetadata(1, "Bulbasaur", 126, 126, 90);
        when(metadataProvider.getPokemonMetadata(1)).thenReturn(metadata);
        Assertions.assertEquals(metadata, pokedex.getPokemonMetadata(1));
    }
    @Test
    public void createPokemonTest() {
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 49, 49, 90, 110, 60, 400, 4, 56.0);
        when(pokemonFactory.createPokemon(0, 110, 60, 400, 4)).thenReturn(pokemon);
        Assertions.assertEquals(pokemon, pokedex.createPokemon(0, 110, 60, 400, 4));
    }
    @Test
    public void sizeTest() {
        Assertions.assertEquals(0, pokedex.size());
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 49, 49, 90, 110, 60, 400, 4, 56.0);
        when(pokemonFactory.createPokemon(1, 500, 600, 4500, 30)).thenReturn(pokemon);
        pokedex.addPokemon(pokemon);
        Assertions.assertEquals(1, pokedex.size());
    }
    @Test
    public void addAndGetPokemonTest() throws PokedexException {
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 49, 49, 90, 110, 60, 400, 4, 56.0);
        when(pokemonFactory.createPokemon(1, 500, 600, 4500, 30)).thenReturn(pokemon);
        int id = pokedex.addPokemon(pokemon);
        Assertions.assertEquals(pokemon, pokedex.getPokemon(id));
    }
    @Test
    public void getPokemonsTest() {
        Pokemon pokemon1 = new Pokemon(0, "Bulbasaur", 49, 49, 90, 110, 60, 400, 4, 56.0);
        Pokemon pokemon2 = new Pokemon(1, "Tortank",79,83,100,78,800,3000,10,98.0);
        when(pokemonFactory.createPokemon(1, 500, 600, 4500, 30)).thenReturn(pokemon1);
        when(pokemonFactory.createPokemon(2, 600, 700, 5500, 40)).thenReturn(pokemon2);
        pokedex.addPokemon(pokemon1);
        pokedex.addPokemon(pokemon2);

        List<Pokemon> pokemons = pokedex.getPokemons();
        Assertions.assertEquals(2, pokemons.size());
        Assertions.assertTrue(pokemons.contains(pokemon1));
        Assertions.assertTrue(pokemons.contains(pokemon2));
    }
}
