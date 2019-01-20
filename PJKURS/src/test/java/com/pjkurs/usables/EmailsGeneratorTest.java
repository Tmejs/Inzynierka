package com.pjkurs.usables;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pjkurs.domain.Appusers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EmailsGeneratorTest {
    private String name = "testName";
    private String email = "email@email.com";

    @Test
    public void testGetRegistrationMessage() {
        Appusers user = mock(Appusers.class);
        when(user.getName()).thenReturn(name);
        when(user.getEmail()).thenReturn(email);

        MailObject result = EmailsGenerator.getRegistrationMessage(user);

        assertThat(result.getReciever(), is("email@email.com"));
        assertThat(result.getMessageBody(), is("<html>Witaj " + name + "<br>" +
                "Dziekujemy za rejestrację w naszym serwisie"
                + "<html>"));
        assertThat(result.getTitle(), is("Witamy w serwisie PJKURS "));
    }

    @Test
    public void testGetAddedToCourseMessage() {
    }

}
