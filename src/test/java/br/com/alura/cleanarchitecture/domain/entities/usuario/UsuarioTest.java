package br.com.alura.cleanarchitecture.domain.entities.usuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UsuarioTest {

    @Test
    public void naoDeveCadastrarUsuarioComCpfInvalido() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Usuario("123456789-00", "Fulano", LocalDate.parse("2006-09-19"), "email@.com"));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Usuario("12345678999", "Fulano", LocalDate.parse("2012-03-19"), "email@.com"));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Usuario("", "Fulano", LocalDate.parse("2012-03-19"), "email@.com"));
    }

    @Test
    public void deveCadastrarUsuarioUsandoFabricaDeUsuario() {
        FabricaDeUsuario fabrica = new FabricaDeUsuario();
        Usuario usuario = fabrica.comNomeCpfNascimentoEmail("Fulano", "123.456.789-00", LocalDate.parse("2006-09-19"));

        Assertions.assertEquals("Fulano", usuario.getNome());

        usuario = fabrica.incluiEndereco("12345-678", "123", "Casa");
        Assertions.assertEquals("12345-678", usuario.getEndereco().getCep());
    }

    @Test
    public void naoDeveCadastrarUsuarioComMenosDe18Anos() {
        LocalDate dataNascimento = LocalDate.now().minusYears(17);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("123.456.789-00", "Fulano", dataNascimento, "fulano@example.com");
        });

        Assertions.assertEquals("Usuário deve ter pelo menos 18 anos de idade!", exception.getMessage());
    }
}
