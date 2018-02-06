package ru.performanceLab.yourNote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
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

    protected abstract SessionBean getSession();
//    @Autowired
//    private SessionBean sessionBean;
//
//    @Autowired
//    private ApplicationContext ctx;

//    private static HardcodedDB hardcodedDB = new HardcodedDB();
//    @RequestMapping("/yourNote")
//    public ModelAndView greeting(@RequestParam(value="name") String name, Map<String, Object> model) {
//        ModelAndView mav = new ModelAndView("noteList");
//        HardcodedDB hardcodedDB = new HardcodedDB();
//        List<Note> list = hardcodedDB.getMap().get(name);
//        mav.addObject("noteList", list);
//        return mav;
//    }

//    @RequestMapping("/yourNote")
//    public String greeting(@RequestParam(value="name") String name, Map<String, Object> model) {
//        HardcodedDB hardcodedDB = new HardcodedDB();
//        List<Note> list = hardcodedDB.getMap().get(name);
//        model.put("noteList", list);
//        return "ru.performanceLab.yourNote.controller";
//    }

    @GetMapping("/")
    public ModelAndView showStartPage() {
        return new ModelAndView("index", new ModelMap());
    }
    @GetMapping("/yourNote/{userName}")
    public ModelAndView getNotes(@PathVariable(value = "userName") String userName) {
        sessionBean = getSession();
        if (sessionBean == null) {
            return new ModelAndView("index", new ModelMap());
        }


        ModelMap model = new ModelMap();
        User user = userDao.findUserByName(userName);
        if (user == null) {
            user = userDao.create(userName);
        }
        System.out.println(sessionBean.getMd5());
        model.addAttribute("noteList", noteDao.findNotesByUserId(user.getId()));
        return new ModelAndView("noteList", model);
    }

    @GetMapping("/yourNote/{userName}/add")
    public ModelAndView createNote(@PathVariable(value = "userName") String userName,
                                   @RequestParam(value = "noteName") String noteName) {
        sessionBean = getSession();
        if (sessionBean == null) {
            return new ModelAndView("index", new ModelMap());
        }

        System.out.println(getSession().getMd5());
        noteDao.createEmptyNote(userName, noteName);
        return getNotes(userName);
    }
/*TODO: NON UNIQUE NOTE IN ONE USER*/
/*TODO: знак = добавляется при редактировании*/
/*TODO: многопользовательский сервис = у каждого SessionBean уникальное имя(userName?) или переменная sessionBean должна быть не глобальная для всего сервиса*/
    @GetMapping("/yourNote/{userName}/delete")
    public ModelAndView deleteNote(@PathVariable(value = "userName") String userName,
                                   @RequestParam(value = "noteName") String noteName) {
        sessionBean = getSession();
        if (sessionBean == null) {
            return new ModelAndView("index", new ModelMap());
        }

        System.out.println(getSession().getMd5());
        noteDao.deleteNote(userName, noteName);
        return getNotes(userName);
    }

    @PostMapping("/yourNote/{userName}/edit/{noteName}")
    public ModelAndView updateNote(@PathVariable(value = "userName") String userName,
                                   @PathVariable(value = "noteName") String noteName,
                                   @RequestBody String noteText) {
        sessionBean = getSession();
        if (sessionBean == null) {
            return new ModelAndView("index", new ModelMap());
        }

        System.out.println(getSession().getMd5());
        noteDao.updateNote(userName, noteName, noteText);
        return getNotes(userName);
    }
}