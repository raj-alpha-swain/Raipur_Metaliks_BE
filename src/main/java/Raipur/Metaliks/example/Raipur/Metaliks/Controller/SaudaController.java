package Raipur.Metaliks.example.Raipur.Metaliks.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Raipur.Metaliks.example.Raipur.Metaliks.DTO.SaudaDto;
import Raipur.Metaliks.example.Raipur.Metaliks.Service.SaudaService;

import java.util.List;

@RestController
@RequestMapping("/api/sauda")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" })
public class SaudaController {

    @Autowired
    private SaudaService saudaService;

    @PostMapping
    public ResponseEntity<SaudaDto> createSauda(@RequestBody SaudaDto saudaDto) {
        try {
            SaudaDto createdSauda = saudaService.createSauda(saudaDto);
            return new ResponseEntity<>(createdSauda, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<SaudaDto>> getAllSaudas() {
        try {
            List<SaudaDto> saudas = saudaService.getAllSaudas();
            return new ResponseEntity<>(saudas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaudaDto> getSaudaById(@PathVariable Long id) {
        try {
            SaudaDto sauda = saudaService.getSaudaById(id);
            return new ResponseEntity<>(sauda, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaudaDto> updateSauda(@PathVariable Long id, @RequestBody SaudaDto saudaDto) {
        try {
            SaudaDto updatedSauda = saudaService.updateSauda(id, saudaDto);
            return new ResponseEntity<>(updatedSauda, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSauda(@PathVariable Long id) {
        try {
            saudaService.deleteSauda(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sellers")
    public ResponseEntity<List<String>> getUniqueSellers() {
        try {
            List<String> sellers = saudaService.getUniqueSellers();
            return new ResponseEntity<>(sellers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SaudaDto>> getFilteredSaudas(
            @RequestParam(required = false) String seller,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        try {
            List<SaudaDto> saudas = saudaService.getFilteredSaudas(seller, year, month);
            return new ResponseEntity<>(saudas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
