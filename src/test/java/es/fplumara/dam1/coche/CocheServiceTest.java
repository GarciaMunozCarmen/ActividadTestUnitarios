package es.fplumara.dam1.coche;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CocheServiceTest {

    @Mock
    private CocheRepository cocheRepository;
    private Coche coche;
    private CocheService cocheService;

    @BeforeEach
    public void setUp(){
        cocheService = new CocheService(cocheRepository);
        coche = new Coche();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234ABC", "0000ZZZ", "9876QWE"})
    public void isValidMatriculaTest(String matricula){
        assertTrue(cocheService.validaMatricula(matricula));
    }
    @ParameterizedTest
    @CsvSource({
            "123ABC",
            "12345ABC",
            "1234AB",
            "1234A1C",
            "1234-ABC",
            "1234 ABC",
            "1234abc"
    })
    public void isInvalidMatriculaTest(String matricula){
        assertFalse(cocheService.validaMatricula(matricula));
    }

    @Nested
    class matriculasValidasTests{
        @BeforeEach
        public void setUp(){
            coche.setMatricula("1234ABC");
        }

        @Test
        public void comprarCoche(){
            cocheService.comprarCoche(coche);

            verify(cocheRepository).save(coche);
        }
        @Test
        public void buscarCocheMatriculaValida(){
            cocheService.buscarCoche(coche.getMatricula());

            verify(cocheRepository).findByMatricula(coche.getMatricula());
        }
    }

    @Nested
    class matricluasInvalidasTests{
        @BeforeEach
        public void setUp(){
            coche.setMatricula("1234abc");
        }

        @Test
        public void comprarCoche() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> cocheService.comprarCoche(coche));

            verifyNoInteractions(cocheRepository);
        }
        @Test
        public void buscarCoche() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> cocheService.buscarCoche(coche.getMatricula()));

            verifyNoInteractions(cocheRepository);
        }

    }
}
