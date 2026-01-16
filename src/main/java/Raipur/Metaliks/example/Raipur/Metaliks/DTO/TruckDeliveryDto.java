package Raipur.Metaliks.example.Raipur.Metaliks.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TruckDeliveryDto {
    private Long id;
    private String truckNumber;
    private String deliveryDate;
    private Integer quantity;
}
