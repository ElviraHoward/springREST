package com.springRest.demo.controller;

import com.springRest.demo.controller.xsltSettings.Directors;
import com.springRest.demo.controller.xsltSettings.Films;
import com.springRest.demo.service.DirectorService;
import com.springRest.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

/**
 * Created by Elvira on 03.06.2018.
 */
@Controller
@RequestMapping("/xslt")
public class XsltController {

    private DirectorService directorService;

    private FilmService filmService;

    @Autowired
    public XsltController(DirectorService directorService, FilmService filmService) {
        this.directorService = directorService;
        this.filmService = filmService;
    }

    private Source createXsltSource(Object obj) throws Exception {
        final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        final Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(obj, document);
        return new DOMSource(document);
    }

    @RequestMapping(value = "/directors")
    public ModelAndView findOwners() throws Exception {
        final ModelAndView modelAndView = new ModelAndView("lists");
        modelAndView.addObject("xmlSource", createXsltSource(
                new Directors(directorService.findAll())
        ));
        return modelAndView;
    }

    @RequestMapping(value = "/films")
    public ModelAndView findVehicles() throws Exception {
        final ModelAndView modelAndView = new ModelAndView("lists");
        modelAndView.addObject("xmlSource", createXsltSource(
                new Films(filmService.findAll()))
        );
        return modelAndView;
    }
}
