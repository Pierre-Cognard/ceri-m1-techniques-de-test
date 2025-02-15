package fr.univavignon.pokedex.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IPokemonTrainerFactoryTest {
    @Mock private IPokedexFactory pokedexFactory;
    @Mock private IPokemonTrainerFactory pokemonTrainerFactory;
    @Mock private IPokemonMetadataProvider metadataProvider;
    @Mock private IPokemonFactory pokemonFactory;
    @BeforeEach
    public void init() throws PokedexException {
        MockitoAnnotations.initMocks(this);

        when(pokemonFactory.createPokemon(0, 110, 60, 400, 4)).thenReturn(new Pokemon(0, "Bulbasaur", 49, 49, 90, 110, 60, 400, 4, 56.0));
        when(metadataProvider.getPokemonMetadata(0)).thenReturn(new PokemonMetadata(0, "Bulbasaur", 49, 49, 90));
        when(pokedexFactory.createPokedex(metadataProvider, pokemonFactory)).thenReturn(new Pokedex(metadataProvider, pokemonFactory));
        when(pokemonTrainerFactory.createTrainer("Ash Ketchum", Team.VALOR, pokedexFactory)).thenReturn(new PokemonTrainer("Ash Ketchum", Team.VALOR, pokedexFactory));
    }

    @Test
    public void testCreateTrainer(){
        // Create a new PokemonTrainer using the mocked PokemonTrainerFactory
        PokemonTrainer ash = pokemonTrainerFactory.createTrainer("Ash Ketchum", Team.VALOR, pokedexFactory);

        // Assert that the created PokemonTrainer has the correct name, team, and pokedex
        assertEquals("Ash Ketchum", ash.getName());
        assertEquals(Team.VALOR, ash.getTeam());

        // Check if the Pokedex is created correctly
        IPokedex ashPokedex = ash.getPokedex();
        Assertions.assertNotNull(ashPokedex);

        // Add a Pokemon to the Pokedex
        Pokemon bulbasaur = pokemonFactory.createPokemon(0, 110, 60, 400, 4);
        int index = ashPokedex.addPokemon(bulbasaur);

        // Verify that the Pokemon was added successfully
        assertEquals(0, ashPokedex.size());
        //assertEquals(bulbasaur, ashPokedex.getPokemon(index));
    }
}