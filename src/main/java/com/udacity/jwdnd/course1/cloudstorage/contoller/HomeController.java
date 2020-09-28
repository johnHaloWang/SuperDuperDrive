package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "HomeController";
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final FileService fileService;
    private EncryptionService encryptionService;

    @Autowired
    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, FileService fileService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }

    @GetMapping(value = {"/", "/home"})
    public String homeView(Authentication authentication, Model model, HttpSession session){
        LOGGER.debug(TAG_ +  " get method");

        Integer userId = null;
        if(session.getAttribute("userId")==null){
            String username = authentication.getName();
            userId = Integer.valueOf(userService.getUser(username).getUserId());
            session.setAttribute("userId", userId);
        }else{
            userId = Integer.valueOf((int)session.getAttribute("userId"));
        }

        List<Note> notes = noteService.getAllUserNotes(userId.intValue());
        List<Credential> credentials = credentialService.getAllUserCredentials(userId.intValue());
        List<File> files = fileService.getFilesByUserId(userId.intValue());

        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("files", files);

        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("credentialForm", new CredentialForm());
        model.addAttribute("fileForm", new FileForm());
        model.addAttribute("enS", encryptionService);

        return "home";
    }
}
