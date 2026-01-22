package es.fplumara.dam1.facturacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceTest {

    private FacturaService factura;

    @Mock
    private Calculadora calc;

    @BeforeEach
    public void setUp(){
        factura = new FacturaService(calc);
    }

    @Test
    public void compruebaIVA100(){
        // Calculadora no devuelve nd así q aquí le decimos que tiene que devolver cuando le damos 100 y 21
        when(calc.sumar(100, 21)).thenReturn(121);
        int resultado = factura.totalConIva(100);
        assertEquals(121, resultado);

        verify(calc, times(1)).sumar(100, 21);
        verifyNoMoreInteractions(calc);
    }

    @Test
    public void compruebaIVA0(){
        when(calc.sumar(0,21)).thenReturn(21);
        int resultado = factura.totalConIva(0);
        assertEquals(21, resultado);

        verify(calc, times(1)).sumar(0,21);
        verifyNoMoreInteractions(calc);
    }
}
