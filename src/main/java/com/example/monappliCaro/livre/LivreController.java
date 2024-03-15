package com.example.monappliCaro.livre;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/livre")
public class LivreController {
    private final LivreService livreService;

    @Autowired
    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public List<Livre> getLivres() {
        return livreService.getLivres();

    }
    @PostMapping
    public void registerNewLivre (@RequestBody Livre livre){
        livreService.addNewLivre(livre);
    }
    @DeleteMapping(path = "{livreId}")
    public void deleteLivre(@PathVariable("livreId") Long livreId){
        livreService.deleteLivre(livreId);
    }

    @PutMapping(path = "{livreId}")
    public void updateLivre(
            @PathVariable("livreId") Long livreId,
            @RequestParam(required = false) String proprio ){
        livreService.updateLivre(livreId, proprio);
    }
}
