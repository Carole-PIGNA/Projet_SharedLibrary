package com.example.monappliCaro.livre;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    @Autowired
    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public List<Livre> getLivres() {
        return livreRepository.findAll();

    }

    public void addNewLivre(Livre livre) {
        Optional<Livre> livreOptional = livreRepository.findLivreBytitre(livre.getTitre());
        if (livreOptional.isPresent()){
            throw new IllegalStateException("Livre déjà dans la bibliothèque.");
        }
        livreRepository.save(livre);
    }


    public void deleteLivre(Long livreId) {
        boolean exists = livreRepository.existsById(livreId);
        if (!exists){
            throw new IllegalStateException("Le livre dont l'ID est " + livreId + " n'existe pas");
        }
        livreRepository.deleteById(livreId);
    }
    @Transactional
    public void updateLivre(Long livreId, String proprio) {
        Livre livre = livreRepository.findById(livreId).orElseThrow(() -> new IllegalStateException(
                "Le livre dont l'ID est" + livreId + " n'existe pas"));
                if (proprio != null && proprio.length() > 0 && !Objects.equals(livre.getProprio(), proprio)){
                    livre.setProprio(proprio);
        }
    }
}
