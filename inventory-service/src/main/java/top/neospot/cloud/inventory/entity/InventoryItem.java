package top.neospot.cloud.inventory.entity;

import lombok.Data;
import top.neospot.cloud.common.model.BaseModel;

@Data
public class InventoryItem extends BaseModel {
    private String productCode;
    private Integer availableQuantity = 0;
}