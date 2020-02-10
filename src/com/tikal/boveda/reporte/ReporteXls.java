package com.tikal.boveda.reporte;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.tikal.boveda.modelo.BovedaRenglon;




public class ReporteXls {
	
	
	private List<BovedaRenglon> renglones;

	public ReporteXls() {}
	
	public void setRenglones(List<BovedaRenglon> renglones) {
		this.renglones = renglones;
	}
	
	public List<BovedaRenglon> getRenglones() {
		return renglones;
	}
	@SuppressWarnings("deprecation")
	
	public HSSFWorkbook GetReporteXls(String inicio, String fin,String username) throws IOException  { //, OutputStream ops

		HSSFFont headerFont;
		HSSFFont contentFont;
		HSSFWorkbook workbook= new HSSFWorkbook();
	    
				int renglon=0;
			  	int columna=0; 	
				System.out.println("ya esta escribiendo..........:");
	    		// 	FileInputStream file = new FileInputStream(new File(archivo));
	    		 	
	    		 	
	    		 //	HSSFWorkbook workbook = new HSSFWorkbook();
	    		 	workbook = new HSSFWorkbook();
	    		 	
	    		 	
	    		  	HSSFSheet sheet = workbook.createSheet();
	    		  	
	    		  	workbook.setSheetName(0, "Concentrado de Facturas");
	    		  	System.out.println("nombre de hoja:"+ 	sheet.getSheetName());
	    		  	
	    		  	
	    		  	
	    		     
	    		  	
	    		//  	InputStream inputStream = new FileInputStream("img/LogoMervel.png");
//	    		        System.out.println("insertaré la imagen");
//
//	    		        byte[] imageBytes = IOUtils.toByteArray(inputStream);
//
//	    		        int pictureureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
//
//	    		        inputStream.close();
//
//	    		        CreationHelper helper = workbook.getCreationHelper();
//
//	    		        Drawing drawing = sheet.createDrawingPatriarch();
//
//	    		        ClientAnchor anchor = helper.createClientAnchor();
//
//	    		        anchor.setCol1(1);
//	    		        anchor.setRow1(1);
//
//	    		        drawing.createPicture(anchor, pictureureIdx);
//
//
//
//	    		        Picture pict = drawing.createPicture(anchor, pictureureIdx);
//	    		        Cell cell = sheet.createRow(2).createCell(1);

	    			////////// fuentes
    		             
	    		  	 HSSFFont font = workbook.createFont();
	    		  	HSSFFont font2 = workbook.createFont();   
	    		  	HSSFFont font3 = workbook.createFont();    
	    	            
	    	            
	    		  	 CellStyle style = workbook.createCellStyle();
	    	          //  style.setBorderBottom(CellStyle.BORDER_THIN);
	    	            style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
	    	         //   style.setAlignment(VerticalAlignment.CENTER);
	    	            font.setBold(true);
	    	            font.setFontHeightInPoints((short)11);
	    	            font.setColor(HSSFColor.BLACK.index);
	    	            style.setFont(font);
	    	            
	    	            CellStyle styleTotal = workbook.createCellStyle();
		    	          //  style.setBorderBottom(CellStyle.BORDER_THIN);
	    	            styleTotal.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
	    	           // styleTotal.setAlignment(HorizontalAlignment.RIGHT);
		    	            font.setBold(true);
		    	            font.setFontHeightInPoints((short)11);
		    	            font.setColor(HSSFColor.BLACK.index);
		    	            styleTotal.setFont(font);
	    	            
	    	            CellStyle style2 = workbook.createCellStyle();
	    	      //      style2.setBorderBottom(CellStyle.BORDER_THIN);
	    	       //     style2.setAlignment(HorizontalAlignment.CENTER);
	    	            style2.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
	    	            font2.setBold(false);
	    	            font2.setFontHeightInPoints((short)10);
	    	            font2.setColor(HSSFColor.BLACK.index);
	    	            style2.setFont(font2);
	    	            
	    	            CellStyle style3 = workbook.createCellStyle();
	    	         //   style3.setBorderBottom(CellStyle.BORDER_THIN);
	    	          //  style3.setAlignment(HorizontalAlignment.RIGHT);
	    	            style3.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
	    	            font3.setBold(false);
	    	            font3.setFontHeightInPoints((short)10);
	    	            font3.setColor(HSSFColor.BLACK.index);
	    	            style3.setFont(font3);
	    	            ///////////////////////////
	    	            
	    	
//	    		        Row fila1 = sheet.createRow(0);
//	    		        sheet.addMergedRegion(new CellRangeAddress(0,0,2,3));
//    		            
//	    		            Cell celda1 = fila1.createCell(3);
//	    		           
//	    		            	          
//	    		            celda1.setCellValue("CORTE DE CAJA");
//	    		       
//	    		           
//
//	    		            celda1.setCellStyle(style);
//	 
	    	            
	    	            
	    	            
	    	            
///////////////////////////////////////////	    	            
	    		 
	    		        String[] titulos = {"Nombre del Proveedor", "Rfc del proveedor", "Fecha Programada",
	                            "Num Factura", "Fecha Factura", "Importe", "Iva", "Total", "Moneda","Uuid", "Estatus" };
	    		        
	    		        
	    		       Integer[] wd = {256*40, 256*20,256*20, 256*10, 256*20, 256*15,256*15,256*20,256*10,256*35,256*20};        
	    		        // Creamos una fila en la hoja en la posicion 0
	    		        Row fila = sheet.createRow(4);
	    		        
	    		        
	    		        // Creamos el encabezado
	    		        for(int i = 0; i < titulos.length; i++) {
	    		            // Creamos una celda en esa fila, en la posicion 
	    		            // indicada por el contador del ciclo
	    		        	//System.out.println("IIIIIIIII numero de registros:"+regs.size());
	    		            Cell celda = fila.createCell(i);
	    		            celda.setCellValue(titulos[i]);
	    		         //   sheet.autoSizeColumn(i);
	    		            sheet.setColumnWidth(i,wd[i]);
	    		            celda.setCellStyle(style);
	    		           
	    		            //celda.setCellStyle(headerStyle);
	    		        }
	    		     
	    		   	     
		    		        
		    		       
	    		      //  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    		        for(int i=0; i<this.renglones.size();i++){
	    		        	
	    		        	
	    		        	BovedaRenglon reng= this.renglones.get(i); //TODO terminar para el reporte con la columna fecha Correctamente
	    		    		
	    		        	HSSFRow dataRow = sheet.createRow(i + 5);
	    		        	 
	    		        	reng.llenarRenglon(dataRow);
	    		        	
	    		        }

//	    		        Row tot = sheet.createRow(renglones.size()+5);
//	    		        
//	    		        Cell celdaTot = tot.createCell(11);
//	    		        celdaTot.setCellValue("99999.99");
//	    		        celdaTot.setCellStyle(styleTotal);
	    		        
	    		        
	    		        
	    		        //	HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		    		     //  HSSFClientAnchor anchor;
		    		       //anchor = new HSSFClientAnchor(500,100,600,200,(short)0,0,(short)1,0);
		    		      //anchor.setAnchorType( 2 );
		    		 //     anchor.setRow2(2);
		    		      // alto= new HSSFCientA
		    		   //    File file= new File("img/LogoMervel.png");
		    		       
		    		  //     HSSFPicture picture =  patriarch.createPicture(anchor, loadPicture(file, workbook ));
		    		       
		    		       Row fil = sheet.createRow(1);
		    		       Cell celda = fil.createCell(0);
		    		       celda.setCellValue("Concentrado de Facturas del Periodo");
		    		       celda.setCellStyle(style);
		    		       
		    		       Cell celda11 = fil.createCell(1);
		    		       celda11.setCellValue(": "+inicio.substring(0,11)+" al "+fin.substring(0, 11));
		    		       celda11.setCellStyle(style);
		    		       
		    		      
		    		      
		    		       
		    		       
		    		       
		    		       
		    		       
		    		       
	    		        
	    		        return workbook;
	    	          ////  FileOutputStream fileOut = new FileOutputStream(archivo); //Doy la ruta y el nombre del archivo nuevo que se generará
	    	         //   workbook.write(fileOut); //Escribo el nuevo archivo
	    	          //  fileOut.close(); //Cierro el archivo
	    		
	}
	
	
	private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {
		 
		HSSFWorkbook workbook =new HSSFWorkbook();
		HSSFFont font = workbook .createFont();
		font.setBold(fontBold);
		font.setColor(fontColor);
		font.setFontName("Arial");
		font.setFontHeightInPoints(fontHeight);
 
		return font;
	}
	
	private static int loadPicture( File path, HSSFWorkbook wb ) throws IOException
	       {
	       int pictureIndex;
	      FileInputStream fis = null;
	       ByteArrayOutputStream bos = null;
	       try
	       {
	          // read in the image file
	           fis = new FileInputStream(path);
	           bos = new ByteArrayOutputStream( );
	           int c;
	          // copy the image bytes into the ByteArrayOutputStream
	           while ( (c = fis.read()) != -1)
	               bos.write( c );
	       
	          // add the image bytes to the workbook
	           pictureIndex = wb.addPicture(bos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG );
	    
	       }
	       finally
	       {
	           if (fis != null)
	               fis.close();
	           if (bos != null)
	               bos.close();
	       }
	       return pictureIndex;
	       }
}