package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utilidad para cifrar contraseñas usando SHA-256 y codificarlas en Base64.
 */
public class SeguridadUtil {

    /**
     * Cifra un texto plano usando SHA-256 y lo devuelve en formato Base64.
     *
     * @param textoPlano La contraseña o texto a cifrar.
     * @return Cadena cifrada en Base64.
     */
    public static String cifrarSHA256(String textoPlano) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(textoPlano.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // Esto no debería ocurrir, ya que SHA-256 es estándar
            throw new RuntimeException("Error al cifrar la contraseña", e);
        }
    }
}