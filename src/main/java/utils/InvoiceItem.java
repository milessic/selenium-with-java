package utils;
import java.util.Map;

public class InvoiceItem {
	public String itemDescription;
	public String pkwiuCode;
	public String quantity;
	public String unit;
	public String unitNetPrice;
	public String vatRatePercent;

	public InvoiceItem(
			String itemDescription,
			String pkwiuCode, 
			String quantity,
			String unit,
			String unitNetPrice,
			String vatRatePercent
			){
		this.itemDescription = itemDescription;
		this.pkwiuCode = pkwiuCode;
		this.quantity = quantity;
		this.unit = unit;
		this.unitNetPrice = unitNetPrice;
		this.vatRatePercent = vatRatePercent;
	}

	public Map<String, String> getAsMap(){
		return Map.of(
			"itemDescription", this.itemDescription,
			"pkwiuCode", this.pkwiuCode,
			"quantity", this.quantity,
			"unit", this.unit,
			"unitNetPrice", this.unitNetPrice,
			"vatRatePercent", this.vatRatePercent
			);
	}
}
