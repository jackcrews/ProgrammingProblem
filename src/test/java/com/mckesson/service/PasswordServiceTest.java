package com.mckesson.service;

import static com.agileworksllc.sample.validation.service.PasswordService.ERROR_LETTER_AND_DIGIT;
import static com.agileworksllc.sample.validation.service.PasswordService.ERROR_PASSWORD_CASE;
import static com.agileworksllc.sample.validation.service.PasswordService.ERROR_PASSWORD_LENGTH;
import static com.agileworksllc.sample.validation.service.PasswordService.ERROR_PASSWORD_SEQUENCE_REPEATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.agileworksllc.sample.validation.config.AppConfig;
import com.agileworksllc.sample.validation.service.PasswordService;

public class PasswordServiceTest {
    private PasswordService passwordService;

    @Before
    public void getServiceFromIOC() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        passwordService = ctx.getBean(PasswordService.class);
    }

    @Test
    public void testContainsOnlyLowercaseLetters() {
        // act
        Set<String> result = passwordService.validate("abcde");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_CASE));
    }

    @Test
    public void testContainsUppercaseLetters() {
        // act
        Set<String> result = passwordService.validate("Abcde");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_CASE));
    }

    @Test
    public void testContainsBothLetterAndDigit() {
        // act
        Set<String> result = passwordService.validate("a0");

        // verify
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsBothDigitAndLetter() {
        // act
        Set<String> result = passwordService.validate("0a");

        // verify
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsOnlyLetters() {
        // act
        Set<String> result = passwordService.validate("a");

        // verify
        assertThat(result, hasItem(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsOnlyDigits() {
        // act
        Set<String> result = passwordService.validate("0");

        // verify
        assertThat(result, hasItem(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testSize5orMore() {

        // act
        Set<String> result = passwordService.validate("12345");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSizeLessThan5() {

        // act
        Set<String> result = passwordService.validate("1234");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSize12orLess() {

        // act
        Set<String> result = passwordService.validate("123456789112");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSizeMoreThan12() {

        // act
        Set<String> result = passwordService.validate("1234567891123");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSequenceNotViolated() {

        // act
        Set<String> result = passwordService.validate("abcde12345");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLetters() {

        // act
        Set<String> result = passwordService.validate("abab");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatSingleLetter() {

        // act
        Set<String> result = passwordService.validate("aa");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersAndDigits() {

        // act
        Set<String> result = passwordService.validate("ab1ab1");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersNotAtFront() {

        // act
        Set<String> result = passwordService.validate("prefixabab");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersNotAtBack() {

        // act
        Set<String> result = passwordService.validate("ababpostfix");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

}
