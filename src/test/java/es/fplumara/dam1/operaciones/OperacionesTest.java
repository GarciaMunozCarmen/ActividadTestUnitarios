package es.fplumara.dam1.operaciones;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

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

}
