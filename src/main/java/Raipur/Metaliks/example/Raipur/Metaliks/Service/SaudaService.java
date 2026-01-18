package Raipur.Metaliks.example.Raipur.Metaliks.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Raipur.Metaliks.example.Raipur.Metaliks.DTO.SaudaDto;
import Raipur.Metaliks.example.Raipur.Metaliks.DTO.TruckDeliveryDto;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.Sauda;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.TruckDelivery;
import Raipur.Metaliks.example.Raipur.Metaliks.Entity.Buyer;
import Raipur.Metaliks.example.Raipur.Metaliks.Repository.SaudaRepository;
import Raipur.Metaliks.example.Raipur.Metaliks.Repository.TruckDeliveryRepository;
import Raipur.Metaliks.example.Raipur.Metaliks.Repository.BuyerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaudaService {

    @Autowired
    private SaudaRepository saudaRepository;

    @Autowired
    private TruckDeliveryRepository truckDeliveryRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Transactional
    public SaudaDto createSauda(SaudaDto saudaDto) {
        Sauda sauda = new Sauda();
        sauda.setSaudaDate(saudaDto.getSaudaDate());
        sauda.setSellerName(saudaDto.getSellerName());
        sauda.setBuyerName(saudaDto.getBuyerName());
        sauda.setMaterial(saudaDto.getMaterial());
        sauda.setPrice(saudaDto.getPrice());
        sauda.setSaudaQuantity(saudaDto.getSaudaQuantity());

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

        // Calculate difference automatically
        sauda.updateDifference();

        Sauda savedSauda = saudaRepository.save(sauda);

        // Sync buyer data
        syncBuyerFromDeal(savedSauda);

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

        // Calculate difference automatically
        sauda.updateDifference();

        Sauda updatedSauda = saudaRepository.save(sauda);

        // Sync buyer data
        syncBuyerFromDeal(updatedSauda);

        return convertToDto(updatedSauda);
    }

    @Transactional
    public void deleteSauda(Long id) {
        saudaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<String> getUniqueSellers() {
        return saudaRepository.findDistinctSellerNames();
    }

    @Transactional(readOnly = true)
    public List<SaudaDto> getFilteredSaudas(String seller, Integer year, Integer month) {
        List<Sauda> saudas;

        if (seller != null && !seller.isEmpty() && year != null && month != null) {
            // Filter by both seller and date
            java.time.LocalDate startDate = java.time.LocalDate.of(year, month, 1);
            java.time.LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            saudas = saudaRepository.findBySellerNameAndDateBetween(seller, startDate, endDate);
        } else if (seller != null && !seller.isEmpty()) {
            // Filter by seller only
            saudas = saudaRepository.findBySellerName(seller);
        } else if (year != null && month != null) {
            // Filter by date only
            java.time.LocalDate startDate = java.time.LocalDate.of(year, month, 1);
            java.time.LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            saudas = saudaRepository.findByDateBetween(startDate, endDate);
        } else {
            // No filters, return all
            saudas = saudaRepository.findAll();
        }

        return saudas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    private void syncBuyerFromDeal(Sauda sauda) {
        try {
            // Find or create buyer by name
            Buyer buyer = buyerRepository.findByName(sauda.getBuyerName())
                    .orElse(new Buyer());

            buyer.setName(sauda.getBuyerName());
            buyer.setProduct(sauda.getMaterial());

            // Aggregate total quantity from all deals for this buyer
            List<Sauda> buyerDeals = saudaRepository.findByBuyerName(sauda.getBuyerName());
            long totalQuantity = buyerDeals.stream()
                    .mapToLong(s -> s.getSaudaQuantity() != null ? s.getSaudaQuantity() : 0)
                    .sum();

            buyer.setQuantity(totalQuantity);
            buyer.setPrice(sauda.getPrice() != null ? sauda.getPrice().floatValue() : 0f);
            buyer.setStatus("Active");

            buyerRepository.save(buyer);
        } catch (Exception e) {
            // Log error but don't fail the deal creation/update
            System.err.println("Error syncing buyer: " + e.getMessage());
        }
    }
}
