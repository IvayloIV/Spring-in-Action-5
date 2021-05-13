package sia.tacocloud.config.transformers;

import java.io.IOException;
import java.util.UUID;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import sia.tacocloud.domain.Ingredient;

@Component
public class EmailToIngredientTransformer extends AbstractMailMessageTransformer<Ingredient> {

    @Override
    protected AbstractIntegrationMessageBuilder<Ingredient> doTransform(Message message) throws Exception {
        Ingredient ingredient = this.processIngredient(message);
        return MessageBuilder.withPayload(ingredient);
    }

    private Ingredient processIngredient(Message message) throws MessagingException, IOException {
        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        String[] elements = this.getTextFromMimeMultipart(mimeMultipart).split(" ");

        Ingredient ingredient = new Ingredient();
        ingredient.setId(UUID.randomUUID().toString().substring(0, 4));
        ingredient.setName(elements[0]);
        ingredient.setType(Ingredient.Type.valueOf(elements[1]));

        return ingredient;
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();

        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n" + bodyPart.getContent());
            }
        }

        return result.toString().trim();
    }
}
