package utils;

public class Messages {

	public String getInvoiceGeneratedMessage(String invoiceNumber){
		return "Faktura 'faktura_" + invoiceNumber + ".pdf' została pobrana!";
	}

	public String getFillAllFieldsMessage(){
		return "Najpierw uzupełnij wszystkie pola!";
	}

}
