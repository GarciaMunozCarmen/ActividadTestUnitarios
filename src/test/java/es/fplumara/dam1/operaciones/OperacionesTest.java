package es.fplumara.dam1.operaciones;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OperacionesTest {

    // TESTS DE CALIFICACIONES

    @ParameterizedTest
    @CsvSource({
            "0,INSUFICIENTE",
            "4.99,INSUFICIENTE",
            "5, APROBADO",
            "6.99, APROBADO",
            "7, NOTABLE",
            "8.99, NOTABLE",
            "9, SOBRESALIENTE",
            "10, SOBRESALIENTE"
    })
    @DisplayName("Comprueba las notas válidas")
    public void clasificacionNotasValidas(double nota, String resultadoEsperado) {
        String resultado = Operaciones.calificacion(nota);
        assertEquals(resultadoEsperado,resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "10.01, Nota fuera de rango",
            "-0.01, Nota fuera de rango"
    })
    @DisplayName("Comprueba las notas inválidas")
    public void clasificacionNotasInvalidas(double nota, String resultadoEsperado){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Operaciones.calificacion(nota);
        });
        assertEquals(resultadoEsperado, ex.getMessage());
    }

    //TESTS DE MEDIA

    @ParameterizedTest
    @MethodSource("mediaNormal")
    @DisplayName("Comprueba las medias")
    public void mediaTestNormal (double resultadoEsperado, double... notas){
        assertEquals(resultadoEsperado, Operaciones.media(notas), 0.0001);
    }
    public static Stream<Arguments> mediaNormal() {
        return Stream.of(
                Arguments.of(6.0, new double[]{5.0, 7.0}),
                Arguments.of(10.0, new double[]{10.0}),
                Arguments.of(0.0, new double[]{0.0, 0.0, 0.0, 0.0})
        );
    }

    @Nested
    class CasosInvalidos { //Esta clase engloba al test mediaTestNula y mediaTestVacia
        @ParameterizedTest
        @MethodSource("mediaNula")
        @DisplayName("Comprueba medias vacías")
        public void mediaTestNula(double... notas) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> Operaciones.media(notas));
            assertEquals("No hay notas", ex.getMessage());
        }

        public static Stream<Arguments> mediaNula() {
            return Stream.of(
                    Arguments.of(new double[]{}) // Array vacio
            );
        }

        @Test
        @DisplayName("Comprueba media sin notas")
        public void mediaTestVacia() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> Operaciones.media()); //No das notas
            assertEquals("No hay notas", ex.getMessage());
        }
    }
}
