package com.r.boda.uploadservice.upload.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

/**
 * 
  * <附件处理类>
  * 
  * @author  bd02
  * @version  [版本号, 2014-2-3]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class AttachmentUtils {
	
	/**
	 * 
	  *<将多个pdf文件进行合并>
	  * @param files  文件集合
	  * @param newfile
	  * @return [参数说明]
	  * 
	  * @return boolean [返回类型说明]
	  * @exception throws [异常类型] [异常说明]
	  * @see [类、类#方法、类#成员]
	 */
	public static boolean mergePdfFiles(String[] files, String newfile){
		 boolean retValue = false;  
	        Document document = null;  
	        try {  
	            document = new Document(new PdfReader(files[0]).getPageSize(1));  
	            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));  
	            document.open();  
	            for (int i = 0; i < files.length; i++) { 
	            	if(StringUtils.isEmpty(files[i])){
	            		continue;
	            	}
	                PdfReader reader = new PdfReader(files[i]);  
	                int n = reader.getNumberOfPages();  
	                for (int j = 1; j <= n; j++) {  
	                    document.newPage();  
	                    PdfImportedPage page = copy.getImportedPage(reader, j);  
	                    copy.addPage(page);  
	                }  
	            }  
	            retValue = true;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            document.close();  
	        }  
	        return retValue;  
	   } 
	
	/**
	 * 
	  *<进行pdf编辑操作,并将新生成的pdf路径返回>
	  * @param pdfFile     需要操作的pdf文件
	  * @param insertFile  在路径之间插入的pdf,若pdf为空则不插入，直接删除之间的页面
	  * @param startPage   起始删除页
	  * @param endPage     结束删除页
	  * @return [参数说明]
	  * 
	  * @return String [返回类型说明]
	 * @throws IOException 
	  * @exception throws [异常类型] [异常说明]
	  * @see [类、类#方法、类#成员]
	 */
	 public static String modefyPdfFile(String pdfFile,String newpdfFile,String insertFile,int startPage,int endPage) {
			//生成A4类型的PDF
		 
		  Document document = null;  
		  try {
			  if(startPage == 1 && !StringUtils.isEmpty(insertFile)){  //其实页从1开始
				  document = new Document(new PdfReader(insertFile).getPageSize(1));
			  }else{
				  int newFileStartPage = getFirstPage(startPage,endPage);
				  document = new Document(new PdfReader(pdfFile).getPageSize(newFileStartPage));
			  }
			  
			  
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(newpdfFile));  
	        document.open();  
			
			PdfReader reader = new PdfReader(pdfFile);  
			int pageNumber = reader.getNumberOfPages();
			for(int i = 1; i <= pageNumber; i++){
				
				if(!StringUtils.isEmpty(insertFile) && i== startPage ){  //插入时
					PdfReader insertReader = new PdfReader(insertFile);
					int insertPages = insertReader.getNumberOfPages();
					 for(int j=1;j<=insertPages ;j++){
						 document.newPage();  
		                 PdfImportedPage page = copy.getImportedPage(insertReader, j);  
		                 copy.addPage(page);  
					 }
				}else if(i>=startPage && i<= endPage){
					continue;
				}
				 document.newPage();  
                 PdfImportedPage page = copy.getImportedPage(reader, i);  
                 copy.addPage(page);  
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}  finally{
			  document.close();  
		}
		 return newpdfFile;
	 }
	 /**
	  * 
	   *<获取新创建pdf的起始页>
	   * @param startPage 
	   * @param endPage
	   * @return [参数说明]
	   * 
	   * @return int [返回类型说明]
	   * @exception throws [异常类型] [异常说明]
	   * @see [类、类#方法、类#成员]
	  */
	private static int getFirstPage(int startPage,int endPage){
		int retuenValue = 1;
		if(startPage == 1){
			retuenValue = endPage+1;
		}
		return retuenValue;
		
	}
}
