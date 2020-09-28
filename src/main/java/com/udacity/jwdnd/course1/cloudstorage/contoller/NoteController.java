package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "NoteController";
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PutMapping("/add")
    public String postNote(NoteForm noteForm, Model model, HttpSession session){
        LOGGER.debug(TAG_ + " add method");

        int userId = (int)session.getAttribute("userId");
        String errorMsgstr = "";
        if(noteForm.getNoteId()==null || noteForm.getNoteId().equals("")){
            LOGGER.debug(TAG_ + " add new note method");
            Note note = new Note(noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);
            int addRow = this.noteService.addNote(note);
            if(addRow == 1){
                LOGGER.debug(TAG_ +  " add new note success");
                model.addAttribute("successResult", true);
            }else{
                LOGGER.debug(TAG_ + " add new note failed");
                model.addAttribute("errorResult", true);
                errorMsgstr = "New note failed to add";
                model.addAttribute("errorResultMessage", errorMsgstr);
            }
        }else{
            LOGGER.debug(TAG_ + " update/edit note method");
            Note note = new Note(Integer.parseInt(noteForm.getNoteId()), noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);
            int updateRow = this.noteService.editNoteByNoteObject(note);
            if(updateRow == 1){
                model.addAttribute("successResult", true);
                LOGGER.debug(TAG_ + " update/edit note success");
            }else{
                model.addAttribute("errorResult", true);
                LOGGER.debug(TAG_ + " update/edit note failed");
                errorMsgstr = "Note failed to update/edit";
                model.addAttribute("errorResultMessage", errorMsgstr);
            }
        }
        return "result";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam(name="noteId") String noteId, Model model){
        LOGGER.debug(TAG_ + " delete method with noteId: " + noteId);
        int delRow = this.noteService.deleteNote(Integer.parseInt(noteId));
        if(delRow == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
        }
        return "result";
    }




}
