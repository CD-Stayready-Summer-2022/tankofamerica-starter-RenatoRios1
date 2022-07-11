package com.codedifferently.tankofamerica.shellhelper;

import com.codedifferently.tankofamerica.user.serviceandrepo.UserServiceImpl;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CLIPromptProvider implements PromptProvider {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public AttributedString getPrompt() {
        if(userServiceImpl.userIsLoggedIn(userServiceImpl.getCurrentUser())){
            return new AttributedString(String.format("Tank Of America:> [%s] :>", userServiceImpl.getUsernameFromUser(userServiceImpl.getCurrentUser()).toUpperCase(Locale.ROOT)),
                    AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE)
            );
        }
        return new AttributedString("Tank Of America:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE)
        );
    }
}
