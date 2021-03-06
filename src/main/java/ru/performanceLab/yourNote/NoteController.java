package ru.performanceLab.yourNote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.performanceLab.yourNote.db.model.Note;
import ru.performanceLab.yourNote.db.model.User;
import ru.performanceLab.yourNote.session.SessionBean;
import ru.performanceLab.yourNote.db.dao.NoteDao;
import ru.performanceLab.yourNote.db.dao.UserDao;

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
        /*TODO: убрать дублируемую проверку на закрытие сессии*/
        sessionBean = getSession(userName);
        if (!sessionBean.isOpen()) {
            return showStartPage();
        }

        ModelMap model = new ModelMap();
        User user = userDao.findUserByName(userName);
        if (user == null) {
            user = userDao.create(userName);
        }

        model.addAttribute("noteList", noteDao.findNotesByUser(user));
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

        Note deletedNote = noteDao.getNote(userName, noteName);
        noteDao.deleteNote(deletedNote);
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

        Note updatedNote = noteDao.getNote(userName, noteName);
        noteDao.updateNote(updatedNote, noteText);
        return getNotes(userName);
    }
}