package com.pdf.api.services;

import java.io.File;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.pdf.api.models.Doc;
import com.pdf.api.models.OrderItem;

@Path ("/pdf")
public class CreatePdfService {
	
	private static final File PdfsFolder;

    static {
		System.setProperty ("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    	
    	PdfsFolder = new File (System.getProperty ("user.home") + File.separator + "pdfs");
        if (!PdfsFolder.exists () || !PdfsFolder.isDirectory ()) {
        	PdfsFolder.mkdirs ();
        }
    }

	@POST
    @Consumes ({"application/json"})
    @Produces ({"application/pdf"})
    public Response postPayload (Doc inputDoc) {
		
		try (PDDocument document = new PDDocument ()) {
			
			PDPage page = new PDPage ();
	        document.addPage (page);

	        try (PDPageContentStream contentStream = new PDPageContentStream (document, page)) {

	            PDFont font = PDType1Font.HELVETICA; // TODO need to install on the server
	
	            contentStream.setFont (font, 14);
	
	            contentStream.beginText ();
	
	            contentStream.newLineAtOffset (100, 150); // x, y
	            contentStream.showText (inputDoc.getCustomer ().getName ());
	
	            for (OrderItem item : inputDoc.getOrderItems ()) {
	                contentStream.newLineAtOffset (100, 150); // x, y
	                
	                contentStream.showText (item.getName () + " ---- " + item.getUnitCost () + "x"+ item.getCartons ()+ " ---- " + item.getTotalCost ());
	            }
	
	            contentStream.endText ();
	        }

	        File pdfFile = new File (PdfsFolder + File.separator + "receipt-" + UUID.randomUUID ().toString () + ".pdf");
	        
            document.save (pdfFile);
            
            return Response.ok ((Object) pdfFile)
				.header ("Content-Disposition", "attachment; filename='receipt.pdf'")
				.build ();
            
		} catch (Exception e) {
			e.printStackTrace ();
		}
		
		return null;
    }
}