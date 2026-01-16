package Raipur.Metaliks.example.Raipur.Metaliks.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Raipur.Metaliks.example.Raipur.Metaliks.DTO.SaudaDto;
import Raipur.Metaliks.example.Raipur.Metaliks.DTO.TruckDeliveryDto;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.Sauda;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.TruckDelivery;
import Raipur.Metaliks.example.Raipur.Metaliks.Repository.SaudaRepository;
import Raipur.Metaliks.example.Raipur.Metaliks.Repository.TruckDeliveryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaudaService {

    @Autowired
    private SaudaRepository saudaRepository;

    @Autowired
    private TruckDeliveryRepository truckDeliveryRepository;

    @Transactional
    public SaudaDto createSauda(SaudaDto saudaDto) {
        Sauda sauda = new Sauda();
        sauda.setSaudaDate(saudaDto.getSaudaDate());
        sauda.setSellerName(saudaDto.getSellerName());
        sauda.setBuyerName(saudaDto.getBuyerName());
        sauda.setMaterial(saudaDto.getMaterial());
        sauda.setPrice(saudaDto.getPrice());
        sauda.setSaudaQuantity(saudaDto.getSaudaQuantity());
        sauda.setDifference(saudaDto.getDifference());
        sauda.setActualQuantity(saudaDto.getActualQuantity());

        // Add truck deliveries
        if (saudaDto.getTruckDeliveries() != null) {
            for (TruckDeliveryDto truckDto : saudaDto.getTruckDeliveries()) {
                TruckDelivery truck = new TruckDelivery();
                truck.setTruckNumber(truckDto.getTruckNumber());
                truck.setDeliveryDate(truckDto.getDeliveryDate());
                truck.setQuantity(truckDto.getQuantity());
                sauda.addTruckDelivery(truck);
            }
        }

        Sauda savedSauda = saudaRepository.save(sauda);
        return convertToDto(savedSauda);
    }

    @Transactional(readOnly = true)
    public List<SaudaDto> getAllSaudas() {
        return saudaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SaudaDto getSaudaById(Long id) {
        Sauda sauda = saudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sauda not found with id: " + id));
        return convertToDto(sauda);
    }

    @Transactional
    public SaudaDto updateSauda(Long id, SaudaDto saudaDto) {
        Sauda sauda = saudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sauda not found with id: " + id));

        sauda.setSaudaDate(saudaDto.getSaudaDate());
        sauda.setSellerName(saudaDto.getSellerName());
        sauda.setBuyerName(saudaDto.getBuyerName());
        sauda.setMaterial(saudaDto.getMaterial());
        sauda.setPrice(saudaDto.getPrice());
        sauda.setSaudaQuantity(saudaDto.getSaudaQuantity());
        sauda.setDifference(saudaDto.getDifference());
        sauda.setActualQuantity(saudaDto.getActualQuantity());

        // Update truck deliveries
        sauda.getTruckDeliveries().clear();
        if (saudaDto.getTruckDeliveries() != null) {
            for (TruckDeliveryDto truckDto : saudaDto.getTruckDeliveries()) {
                TruckDelivery truck = new TruckDelivery();
                truck.setTruckNumber(truckDto.getTruckNumber());
                truck.setDeliveryDate(truckDto.getDeliveryDate());
                truck.setQuantity(truckDto.getQuantity());
                sauda.addTruckDelivery(truck);
            }
        }

        Sauda updatedSauda = saudaRepository.save(sauda);
        return convertToDto(updatedSauda);
    }

    @Transactional
    public void deleteSauda(Long id) {
        saudaRepository.deleteById(id);
    }

    private SaudaDto convertToDto(Sauda sauda) {
        SaudaDto dto = new SaudaDto();
        dto.setId(sauda.getId());
        dto.setSaudaDate(sauda.getSaudaDate());
        dto.setSellerName(sauda.getSellerName());
        dto.setBuyerName(sauda.getBuyerName());
        dto.setMaterial(sauda.getMaterial());
        dto.setPrice(sauda.getPrice());
        dto.setSaudaQuantity(sauda.getSaudaQuantity());
        dto.setDifference(sauda.getDifference());
        dto.setActualQuantity(sauda.getActualQuantity());

        List<TruckDeliveryDto> truckDtos = sauda.getTruckDeliveries().stream()
                .map(truck -> {
                    TruckDeliveryDto truckDto = new TruckDeliveryDto();
                    truckDto.setId(truck.getId());
                    truckDto.setTruckNumber(truck.getTruckNumber());
                    truckDto.setDeliveryDate(truck.getDeliveryDate());
                    truckDto.setQuantity(truck.getQuantity());
                    return truckDto;
                })
                .collect(Collectors.toList());

        dto.setTruckDeliveries(truckDtos);
        return dto;
    }
}
