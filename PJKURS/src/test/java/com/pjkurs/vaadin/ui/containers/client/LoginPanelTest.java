package com.pjkurs.vaadin.ui.containers.client;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import com.pjkurs.domain.Appusers;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.ui.containers.menu.LoginPanel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LoginPanelTest {

    @Test
    public void testLoginPanelContet_givenLoggedClient() {
        Appusers user = mock(Appusers.class);
        String logedName = "pawel@pawel.com";
        MainViewModel model = mock(MainViewModel.class);
        when(user.getEmail()).thenReturn(logedName);
        NavigatorUI.setLoggeddUser(user);

        LoginPanel loginPanel = new LoginPanel(model);
        com.vaadin.ui.Component resultComponent = loginPanel.getContent();

        assertThat(resultComponent, instanceOf(VerticalLayout.class));
        Component nameLabele = ((VerticalLayout) resultComponent).getComponent(0);
        assertThat(nameLabele, instanceOf(Label.class));
        String resultcaption = ((Label)nameLabele).getCaption();
        String resultValue = ((Label)nameLabele).getValue();
        assertThat(resultcaption, is("Zalogowany jako"));
        assertThat(resultValue, is(logedName));
    }



}
