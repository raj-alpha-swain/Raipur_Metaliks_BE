package Raipur.Metaliks.example.Raipur.Metaliks.DTO;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SaudaDto {
    private Long id;
    private LocalDate saudaDate;
    private String sellerName;
    private String buyerName;
    private String material;
    private Double price;
    private Integer saudaQuantity;
    private Integer difference;
    private Integer actualQuantity;
    private List<TruckDeliveryDto> truckDeliveries;
}
