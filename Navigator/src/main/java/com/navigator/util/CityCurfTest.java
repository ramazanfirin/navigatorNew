package com.navigator.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CityCurfTest {

	public static void main(String[] args) {
		try {
			//parseIlce();
			//getMahalleList("NO=124&AD=KEYKUBAT MAH.");
			//parseSokak();
			getBinaList("NO=251807&AD=");
			//getKapiNo();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<SelectItem> getIlceList() throws Exception{
		String url = "http://cbs.kayseri.bel.tr/KIlce.aspx";
		url ="http://cbs.kayseri.bel.tr/GenelSayfalar/AdresBilesenleri/KIlce.aspx";
		List  <SelectItem> selectItemList = new ArrayList<SelectItem>();
		try {
			String result= Util.getUrl(url);
			
			Document doc = Jsoup.parse(result);
			Elements impress = doc.getElementsByClass("imgcursor");
			
			
			for (Iterator iterator = impress.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
//			if(element.attributes().size()>0)
//				continue;

				String ilce  = new String(element.attr("title").getBytes(), "UTF-8");
				String onclick = element.attr("onclick");
				onclick = onclick.replace("JsMahGoster", "");
				onclick = onclick.replace("(", "");
				onclick = onclick.replace(")", "");
				onclick = onclick.replace("this.id,", "");
				onclick = onclick.replace(",this.title", "");
				onclick = onclick.replace(";", "");
			    
				//System.console().writer().println(ilce + " "+onclick);
				SelectItem item = new SelectItem("NO="+onclick+"&AD=",ilce);
				selectItemList.add(item);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return selectItemList;
	}
	
	public static List <SelectItem>  getMahalleList(String url1) throws Exception{
		String urlEncoded = URLEncoder.encode(url1, "UTF-8");
		String url = "http://cbs.kayseri.bel.tr/KMahalle.aspx?"+url1;
		url="http://cbs.kayseri.bel.tr/GenelSayfalar/AdresBilesenleri/KMahalle.aspx?"+url1;
		List  <SelectItem> selectItemList = new ArrayList<SelectItem>();
		try {
			String result= Util.getUrl(url);
			
			Document doc = Jsoup.parse(result);
			Elements impress = doc.getElementsByAttributeValue("style", "border-width: 0px");
			for (Iterator iterator = impress.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				String mahalle  = new String(element.attr("title").getBytes(), "UTF-8");
				
				
			
				String onclick = element.attr("onclick");
				onclick = onclick.replace("JScsbmGetir", "");
				onclick = onclick.replace("(", "");
				onclick = onclick.replace(")", "");
				onclick = onclick.replace("this.id,", "");
				onclick = onclick.replace(",this.title", "");
				onclick = onclick.replace(";", "");
				
				System.out.println(mahalle+" "+onclick);
				SelectItem item = new SelectItem("NO="+onclick+"&AD=",mahalle);
				selectItemList.add(item);
			}
			
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return selectItemList;
	}
	

	public static List <SelectItem>  getSokakList(String url1) throws Exception{
		List  <SelectItem> selectItemList = new ArrayList<SelectItem>();
		String urlEncoded = URLEncoder.encode(url1, "UTF-8");
		String url = "http://cbs.kayseri.bel.tr/Kcsbm.aspx?"+url1;
		url="http://cbs.kayseri.bel.tr/GenelSayfalar/AdresBilesenleri/Kcsbm.aspx?"+url1;
		try {
			String result= Util.getUrl(url);
			
			Document doc = Jsoup.parse(result);
			Elements impress = doc.getElementsByClass("cursorhand");
			for (Iterator iterator = impress.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				String sokak  = new String(element.attr("title").getBytes(), "UTF-8");
				
				
			
				String onclick = element.attr("onclick");
				onclick = onclick.replace("JSKapiGetir", "");
				onclick = onclick.replace("(", "");
				onclick = onclick.replace(")", "");
				onclick = onclick.replace("this.id,", "");
				onclick = onclick.replace(",this.title", "");
				onclick = onclick.replace(";", "");
				
				if(!sokak.equals("") && !(sokak==null)){
					SelectItem item = new SelectItem("NO="+onclick+"&AD=",sokak);
					selectItemList.add(item);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return selectItemList;
	}
	
	public static List <SelectItem> getBinaList(String url1) throws Exception{
		String urlEncoded = URLEncoder.encode(url1, "UTF-8");
		String url = "http://cbs.kayseri.bel.tr/KBina.aspx?"+url1;
		url="http://cbs.kayseri.bel.tr/GenelSayfalar/AdresBilesenleri/KBina.aspx?"+url1;
		List  <SelectItem> selectItemList = new ArrayList<SelectItem>();
		try {
			String result= Util.getUrl(url);
			 result=new String(result.getBytes("UTF-8"), "UTF-8");
			Document doc = Jsoup.parse(result);
			Elements impress = doc.getElementsByClass("trAlternative");
			Elements impress2 = doc.getElementsByClass("trNormal");
			impress.addAll(impress2);
			
			for (Iterator iterator = impress.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				Elements elementList  =element.getElementsByAttributeValue("align", "left");
				
				//System.out.println(elementList.get(0).html());
				
				Elements onclickList =elementList.get(3).getElementsByAttribute("onclick");
				if(onclickList.size()==0)
					continue ;
							
				String onclik=onclickList.get(0).attr("onclick");
				
				onclik = onclik.replace("JSBinaCiz", "");
				onclik = onclik.replace("(", "");
				onclik = onclik.replace(")", "");
				onclik = onclik.replace("\"", "");
				
				String bina = elementList.get(0).html();
				if(bina.equals("96"))
					System.out.println("geldi");
				String acs  = elementList.get(2).html();
				String newString ="";
				
				newString = StringEscapeUtils.unescapeHtml4(acs);
				//newString = StringEscapeUtils.escapeHtml4(newString);
				newString = new String(newString.getBytes(),"UTF-8");
				bina=bina+"-"+newString;
				
				
				SelectItem item = new SelectItem(onclik,bina);
				selectItemList.add(item);
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(selectItemList.size());
		return selectItemList;
	}
	
	public static List<String> getKapiNo(String kapiNo) throws Exception{
		
		List<String> a =CityCurfUtil.getKapiNo(kapiNo);
		System.out.println("bitti");
		return a;
	}
	
	public static final String escapeHTML(String s){
		   StringBuffer sb = new StringBuffer();
		   int n = s.length();
		   for (int i = 0; i < n; i++) {
		      char c = s.charAt(i);
		      switch (c) {
		         case '<': sb.append("&lt;"); break;
		         case '>': sb.append("&gt;"); break;
		         case '&': sb.append("&amp;"); break;
		         case '"': sb.append("&quot;"); break;
		         case 'à': sb.append("&agrave;");break;
		         case 'À': sb.append("&Agrave;");break;
		         case 'â': sb.append("&acirc;");break;
		         case 'Â': sb.append("&Acirc;");break;
		         case 'ä': sb.append("&auml;");break;
		         case 'Ä': sb.append("&Auml;");break;
		         case 'å': sb.append("&aring;");break;
		         case 'Å': sb.append("&Aring;");break;
		         case 'æ': sb.append("&aelig;");break;
		         case 'Æ': sb.append("&AElig;");break;
		         case 'ç': sb.append("&ccedil;");break;
		         case 'Ç': sb.append("&Ccedil;");break;
		         case 'é': sb.append("&eacute;");break;
		         case 'É': sb.append("&Eacute;");break;
		         case 'è': sb.append("&egrave;");break;
		         case 'È': sb.append("&Egrave;");break;
		         case 'ê': sb.append("&ecirc;");break;
		         case 'Ê': sb.append("&Ecirc;");break;
		         case 'ë': sb.append("&euml;");break;
		         case 'Ë': sb.append("&Euml;");break;
		         case 'ï': sb.append("&iuml;");break;
		         case 'Ï': sb.append("&Iuml;");break;
		         case 'ô': sb.append("&ocirc;");break;
		         case 'Ô': sb.append("&Ocirc;");break;
		         case 'ö': sb.append("&ouml;");break;
		         case 'Ö': sb.append("&Ouml;");break;
		         case 'ø': sb.append("&oslash;");break;
		         case 'Ø': sb.append("&Oslash;");break;
		         case 'ß': sb.append("&szlig;");break;
		         case 'ù': sb.append("&ugrave;");break;
		         case 'Ù': sb.append("&Ugrave;");break;         
		         case 'û': sb.append("&ucirc;");break;         
		         case 'Û': sb.append("&Ucirc;");break;
		         case 'ü': sb.append("&uuml;");break;
		         case 'Ü': sb.append("&Uuml;");break;
		         case '®': sb.append("&reg;");break;         
		         case '©': sb.append("&copy;");break;   
		         case '€': sb.append("&euro;"); break;
		         // be carefull with this one (non-breaking whitee space)
		         case ' ': sb.append("&nbsp;");break;         
		         
		         default:  sb.append(c); break;
		      }
		   }
		   return sb.toString();
		}
}
