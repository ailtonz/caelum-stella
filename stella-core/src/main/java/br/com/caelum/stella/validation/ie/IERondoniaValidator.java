package br.com.caelum.stella.validation.ie;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.stella.MessageProducer;
import br.com.caelum.stella.SimpleMessageProducer;
import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.LogicOrComposedValidator;
import br.com.caelum.stella.validation.Validator;
import br.com.caelum.stella.validation.error.IEError;

public class IERondoniaValidator implements Validator<String> {

    private final LogicOrComposedValidator<String> baseValidator;

    /**
     * Este considera, por padrão, que as cadeias estão formatadas e utiliza um
     * {@linkplain SimpleMessageProducer} para geração de mensagens.
     */
    public IERondoniaValidator() {
        this(true);
    }

    /**
     * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
     * mensagens.
     * 
     * @param isFormatted
     *                considerar cadeia formatada quando <code>true</code>
     */
    public IERondoniaValidator(boolean isFormatted) {
        this(new SimpleMessageProducer(), isFormatted);
    }

    public IERondoniaValidator(MessageProducer messageProducer,
            boolean isFormatted) {
        Class[] validatorClasses = { IERondoniaCasoUmValidator.class,
                IERondoniaCasoDoisValidator.class };
        this.baseValidator = new LogicOrComposedValidator<String>(
                messageProducer, isFormatted, validatorClasses);
        this.baseValidator.setInvalidFormat(IEError.INVALID_FORMAT);
    }

    public void assertValid(String value) {
        if (value != null) {
            baseValidator.assertValid(value);
        }
    }

    public List<ValidationMessage> invalidMessagesFor(String value) {
        List<ValidationMessage> result;
        if (value != null) {
            result = baseValidator.invalidMessagesFor(value);
        } else {
            result = new ArrayList<ValidationMessage>();
        }
        return result;
    }

    public boolean isEligible(String object) {
        return baseValidator.isEligible(object);
    }

}
