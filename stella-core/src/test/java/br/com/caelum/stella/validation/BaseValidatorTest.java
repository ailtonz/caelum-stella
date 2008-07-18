package br.com.caelum.stella.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import br.com.caelum.stella.MessageProducer;
import br.com.caelum.stella.ValidationMessage;

public class BaseValidatorTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testGetValidationMessagesT() {
        Mockery mockery = new Mockery();
        final MessageProducer messageProducer = mockery
                .mock(MessageProducer.class);
        final InvalidValue invalidValue = mockery.mock(InvalidValue.class);
        final ValidationMessage validationMessage = mockery
                .mock(ValidationMessage.class);
        final Object any = new Object();

        mockery.checking(new Expectations() {
            {
                exactly(1).of(messageProducer).getMessage(invalidValue);
                will(returnValue(validationMessage));
            }
        });
        Validator validator = new BaseValidator(messageProducer) {

            @Override
            protected List<InvalidValue> getInvalidValues(Object value) {
                assertEquals(value, any);
                ArrayList<InvalidValue> invalidValues = new ArrayList<InvalidValue>();
                invalidValues.add(invalidValue);
                return invalidValues;
            }

            public boolean isEligible(Object object) {
                return false;
            }

        };
        List<ValidationMessage> messages0 = validator.invalidMessagesFor(any);
        List<ValidationMessage> messages1 = new ArrayList<ValidationMessage>();
        messages1.add(validationMessage);
        assertEquals(messages0, messages1);

        mockery.assertIsSatisfied();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAssertValidShouldThrowInvalidStateExpectionWhenComesAnInvalidValue() {
        Mockery mockery = new Mockery();
        final MessageProducer messageProducer = mockery
                .mock(MessageProducer.class);
        final InvalidValue invalidValue = mockery.mock(InvalidValue.class);
        final ValidationMessage validationMessage = mockery
                .mock(ValidationMessage.class);
        final Object any = new Object();

        mockery.checking(new Expectations() {
            {
                exactly(1).of(messageProducer).getMessage(invalidValue);
                will(returnValue(validationMessage));
            }
        });
        Validator validator = new BaseValidator(messageProducer) {

            @Override
            protected List<InvalidValue> getInvalidValues(Object value) {
                assertEquals(value, any);
                ArrayList<InvalidValue> invalidValues = new ArrayList<InvalidValue>();
                invalidValues.add(invalidValue);
                return invalidValues;
            }

            public boolean isEligible(Object object) {
                return false;
            }

        };
        try {
            validator.assertValid(any);
            fail();
        } catch (InvalidStateException e) {
            List<ValidationMessage> messages0 = e.getInvalidMessages();
            List<ValidationMessage> messages1 = new ArrayList<ValidationMessage>();
            messages1.add(validationMessage);
            assertEquals(messages0, messages1);
        } catch (Exception e) {
            fail();
        }

        mockery.assertIsSatisfied();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAssertValidShouldNotThrowInvalidStateExpectionWhenValueIsValid() {
        Mockery mockery = new Mockery();
        final MessageProducer messageProducer = mockery
                .mock(MessageProducer.class);
        final Object any = new Object();

        mockery.checking(new Expectations());
        Validator validator = new BaseValidator(messageProducer) {

            @Override
            protected List<InvalidValue> getInvalidValues(Object value) {
                assertEquals(value, any);
                ArrayList<InvalidValue> empty = new ArrayList<InvalidValue>();
                return empty;
            }

            public boolean isEligible(Object object) {
                return false;
            }

        };
        try {
            validator.assertValid(any);
        } catch (InvalidStateException e) {
            fail();
        }

        mockery.assertIsSatisfied();
    }

}
