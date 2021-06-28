package com.serlew.app.controller;

import com.serlew.app.models.entity.Heroe;
import com.serlew.app.models.service.IHeroeService;
import com.serlew.app.util.PageRender;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author "Lewczuk, Sergio Daniel"
 */
@Controller
@Slf4j
public class HeroeController {

    @Autowired
    private IHeroeService heroeService;

    @GetMapping(value = {"/listar", "/", "/index"})
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageRequest = PageRequest.of(page, 5);

        Page<Heroe> heroes = heroeService.listarHeroesPaginable(pageRequest);
        PageRender<Heroe> pageRender = new PageRender<>("/listar", heroes);
        model.addAttribute("titulo", "Listado de Heroes");
        model.addAttribute("heroes", heroes);
        model.addAttribute("page", pageRender);

        return "listar";
    }

    @GetMapping("/agregar")
    public String agregar(Heroe heroe) {
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Heroe heroe, @RequestParam("file") MultipartFile imagen, Errors errores, RedirectAttributes flash) {
        if (errores.hasErrors()) {
            return "modificar";
        }
        if (!imagen.isEmpty()) {

            String nombreUnicoArchivo = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();

            Path directorioImagenes = Paths.get("uploads").resolve(nombreUnicoArchivo);
            Path directorioRaiz = directorioImagenes.toAbsolutePath();

            log.info("rootPath: " + directorioImagenes);
            log.info("rootAbsolutPath: " + directorioRaiz);

            try {
                Files.copy(imagen.getInputStream(), directorioRaiz);

                heroe.setImagen(nombreUnicoArchivo);

            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                Logger.getLogger(HeroeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String mensajeFlash = (heroe.getIdHeroe() != null) ? "Heroe editado con Éxito!" : "Heroe creado con Éxito!";

        heroeService.guardarHeroe(heroe);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/";
    }

    @GetMapping("/editar/{idHeroe}")
    public String editar(Heroe heroe, Model model) {
        heroe = heroeService.encontrarHeroePorId(heroe);
        model.addAttribute("heroe", heroe);

        return "modificar";
    }

    @GetMapping("/eliminar")
    public String eliminar(Heroe heroe) {
        heroeService.eliminarHeroe(heroe);
        return "redirect:/";
    }

    @GetMapping(value = "/ver/{idHeroe}")
    public String ver(Heroe heroe, Model model, RedirectAttributes flash) {
        heroe = heroeService.encontrarHeroePorId(heroe);

        if (heroe == null) {
            flash.addFlashAttribute("error", "El Heroe no existe.");
            return "redirect:/listar";
        }
        model.addAttribute("heroe", heroe);
        model.addAttribute("titulo", "Detalle del Heroe: " + heroe.getNombre());

        return "ver";
    }

}
