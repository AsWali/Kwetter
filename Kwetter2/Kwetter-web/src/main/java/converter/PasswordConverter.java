package converter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.AttributeConverter;
/**
 * Created by Asror on 19-3-2017.
 */
public class PasswordConverter implements AttributeConverter<String, String> {

    /**
     * Methode om een string te converten naar een base64 String.
     * @param password
     * @return base64 String
     */
    @Override
    public String convertToDatabaseColumn(String password) {

        byte[] hash = DigestUtils.sha256(password.getBytes());

        String base64String = Base64.encodeBase64String(hash);

        return base64String;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
