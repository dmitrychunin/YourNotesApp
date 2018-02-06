package ru.performanceLab.yourNote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.performanceLab.yourNote.model.User;
import ru.performanceLab.yourNote.scope.SessionBean;
import ru.performanceLab.yourNote.service.dao.NoteDao;
import ru.performanceLab.yourNote.service.dao.UserDao;

@RestController
public abstract class NoteController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NoteDao noteDao;

    private SessionBean sessionBean;

    protected abstract SessionBean getSession(String userName);

    @GetMapping("/")
    public ModelAndView showStartPage() {
        return new ModelAndView("index", new ModelMap());
    }

    @GetMapping("/yourNote/{userName}")
    public ModelAndView getNotes(@PathVariable(value = "userName") String userName) {
        sessionBean = getSession(userName);
        if (!sessionBean.isOpen()) {
            return new ModelAndView("index", new ModelMap());
        }

        ModelMap model = new ModelMap();
        User user = userDao.findUserByName(userName);
        if (user == null) {
            user = userDao.create(userName);
        }

        model.addAttribute("noteList", noteDao.findNotesByUserId(user.getId()));
        return new ModelAndView("noteList", model);
    }

    @GetMapping("/yourNote/{userName}/add")
    public ModelAndView createNote(@PathVariable(value = "userName") String userName,
                                   @RequestParam(value = "noteName") String noteName) {
        sessionBean = getSession(userName);
        if (!sessionBean.isOpen()) {
            return new ModelAndView("index", new ModelMap());
        }

        noteDao.createEmptyNote(userName, noteName);
        return getNotes(userName);
    }
    
    /*TODO: знак = добавляется при редактировании*/
    @GetMapping("/yourNote/{userName}/delete")
    public ModelAndView deleteNote(@PathVariable(value = "userName") String userName,
                                   @RequestParam(value = "noteName") String noteName) {
        sessionBean = getSession(userName);
        if (!sessionBean.isOpen()) {
            return new ModelAndView("index", new ModelMap());
        }

        noteDao.deleteNote(userName, noteName);
        return getNotes(userName);
    }

    @PostMapping("/yourNote/{userName}/edit/{noteName}")
    public ModelAndView updateNote(@PathVariable(value = "userName") String userName,
                                   @PathVariable(value = "noteName") String noteName,
                                   @RequestBody String noteText) {
        sessionBean = getSession(userName);
        if (!sessionBean.isOpen()) {
            return new ModelAndView("index", new ModelMap());
        }

        noteDao.updateNote(userName, noteName, noteText);
        return getNotes(userName);
    }
}