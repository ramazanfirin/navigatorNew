package com.navigator.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nagivator.model.KeyValueDTO;


public class CityCurfUtil {

	public static  List<NameValuePair> getInformaitonOfCoordinates(String x,String y) throws Exception{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		String url = "http://cbs.kayseri.bel.tr/Rehber.aspx/JSHaritadanBilgiAl";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		StringEntity input = new StringEntity("{\"x\":"+x+",\"y\":"+y+"}");
		input.setContentType("application/json");
		post.setEntity(input);
 
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip,deflate");
		post.setHeader("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.6,en;q=0.4");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		post.setHeader("Host", "cbs.kayseri.bel.tr");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Referer", "http://cbs.kayseri.bel.tr/Rehber.aspx");
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println("result for "+x +" and "+y+"="+result.toString()) ;
		
		String returnValue = result.toString(); 
		returnValue = returnValue.replace("{\"d\":\"", "");
		returnValue = returnValue.replace("\"}", "");
		returnValue = returnValue.replace("||", "&");
		returnValue = returnValue.replace("|", "@");
		String[] values= returnValue.split("&");

		String finalReturn="<div id=\"content\"><div id=\"siteNotice\"></div><h1 id=\"firstHeading\" class=\"firstHeading\">Aciklama</h1><div id=\"bodyContent\">";
		;
	
		for (int i = 0; i < values.length; i++) {
			String[] temp  =values[i].split("@");
			if(!temp[0].equals("--") && temp.length>1){
				//System.out.println(temp[0]+"->"+temp[1]);
//				String value1=new String(temp[0].getBytes("ISO-8859-9"),"UTF-8");
//				String value2=new String(temp[1].getBytes("ISO-8859-9"),"UTF-8");
//				finalReturn = finalReturn+""+value1+":"+value2+"<br/>";
				list.add(new NameValuePair(new String(temp[0].getBytes("ISO-8859-9"),"UTF-8"), new String(temp[1].getBytes("ISO-8859-9"),"UTF-8")));
			}else
				break;
		}
		finalReturn = finalReturn +"</div></div>";
		return list;
	}
	
	
	public static  List<NameValuePair> getInformaitonOfCoordinates2(String x,String y) throws Exception{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		String url = "http://cbs.kayseri.bel.tr/Rehber.aspx/JSHaritadanBilgiAl";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		StringEntity input = new StringEntity("{\"islem\":"+2+",\"kordinatlar\":\""+x+"|"+y+"\"}");
		input.setContentType("application/json");
		post.setEntity(input);
 
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip,deflate");
		post.setHeader("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.6,en;q=0.4");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		post.setHeader("Host", "cbs.kayseri.bel.tr");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Referer", "http://cbs.kayseri.bel.tr/Rehber.aspx");
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println("result for "+x +" and "+y+"="+result.toString()) ;
		
		String returnValue = result.toString(); 
		returnValue = returnValue.replace("{\"d\":\"", "");
		returnValue = returnValue.replace("\"}", "");
		returnValue = returnValue.replace("||", "&");
		returnValue = returnValue.replace("|", "@");
		String[] values= returnValue.split("&");

		String finalReturn="<div id=\"content\"><div id=\"siteNotice\"></div><h1 id=\"firstHeading\" class=\"firstHeading\">Aciklama</h1><div id=\"bodyContent\">";
		;
	
		for (int i = 0; i < values.length; i++) {
			String[] temp  =values[i].split("@");
			if(!temp[0].equals("--") && temp.length>1){
				//System.out.println(temp[0]+"->"+temp[1]);
//				String value1=new String(temp[0].getBytes("ISO-8859-9"),"UTF-8");
//				String value2=new String(temp[1].getBytes("ISO-8859-9"),"UTF-8");
//				finalReturn = finalReturn+""+value1+":"+value2+"<br/>";
				list.add(new NameValuePair(new String(temp[0].getBytes("ISO-8859-9"),"UTF-8"), new String(temp[1].getBytes("ISO-8859-9"),"UTF-8")));
			}else
				break;
		}
		finalReturn = finalReturn +"</div></div>";
		return list;
	}
	
	
	public static List<SelectItem> parseHtml(String html) throws Exception{
		List<SelectItem> list = new ArrayList<SelectItem>();
		Document doc = Jsoup.parse(html.toString());
		Element impress = doc.getElementById("pageKentrehberiGenelAramaSonucContentListView");

		Elements elements = impress.children();
		for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if(element.attributes().size()>0)
				continue;

			Element one=element.child(0);
			SelectItem item = new SelectItem(one.attr("href"),new String(one.ownText().getBytes(),"UTF-8"));
			list.add(item);

		}
		return list;
		 
	}
	
	public static List<SelectItem> getIlceList() throws Exception{
		String url = "http://cbs.kayseri.bel.tr/MobilIlceler.aspx?P9=I";
		
		String result= Util.getUrl(url);
		System.out.println(result);
		return parseHtml(result);
	
	
	}
	
	public static List<SelectItem> getMahalleList(String url) throws Exception{
		String result = Util.getUrl("http://cbs.kayseri.bel.tr/"+url);
		return parseHtml(result);

	}
	
	public static List<SelectItem> getSokakList(String url) throws Exception{
		String result = Util.getUrl("http://cbs.kayseri.bel.tr/"+url);
		return parseHtml(result);

	}
	
	public static List<SelectItem> getBinaList(String url) throws Exception{
		String result = Util.getUrl("http://cbs.kayseri.bel.tr/"+url);
		return parseHtml(result);

	}
	
	public static List<String> getKapiNo(String binaNo) throws Exception{
		List<String> returnList= new ArrayList<String>();
		
		String url = "http://cbs.kayseri.bel.tr/Rehber.aspx/GetKapiBina";
		//url = "http://localhost/Rehber.aspx/GetKapiBina";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		StringEntity input = new StringEntity("{\"binaNO\":"+binaNo+"}");
		input.setContentType("application/json");
		post.setEntity(input);
		
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip,deflate");
		post.setHeader("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.6,en;q=0.4");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		post.setHeader("Host", "cbs.kayseri.bel.tr");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Referer", "http://cbs.kayseri.bel.tr/Rehber.aspx");
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		String returnValue="";
		returnValue = result.toString().replace("{\"d\":{\"__type\":\"HaritaCografiArama+sobje\",\"deger\":\"", "");
		returnValue = returnValue.replace("\"}}", "");
		returnValue = returnValue.replace("||", "&");
		returnValue = returnValue.replace("|", "@");
		
		String[] values= returnValue.split("&");
		
		for (int i = 0; i < values.length; i++) {
			String[] temp  =values[i].split("@");
			returnList.add(temp[0]);
			returnList.add(temp[1]);
		}
		
		return returnList;
	}
	
	public static List<KeyValueDTO> genelArama(String string) throws Exception{
		List<KeyValueDTO> returnList= new ArrayList<KeyValueDTO>();
		
		String url = "http://cbs.kayseri.bel.tr/Rehber.aspx/JSGenelArama";
		//url = "http://localhost/Rehber.aspx/JSGenelArama";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		String query = "{\"val\":\""+string+"\"}";
		StringEntity input = new StringEntity(query,"UTF-8");
		input.setContentType("application/json; charset=UTF-8");
		post.setEntity(input);
		
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip,deflate");
		post.setHeader("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.6,en;q=0.4");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		post.setHeader("Host", "cbs.kayseri.bel.tr");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Referer", "http://cbs.kayseri.bel.tr/Rehber.aspx");
		
		
		
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(),"UTF-8"));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		byte[] utfString = result.toString().getBytes("US-ASCII") ;
		String stringaaa = new String(utfString, "UTF-8");
		
		String returnValue="";
		returnValue = result.toString().replace("\\u003ca href=\\u0027javascript:JSAramaGetirByNumber(", "");
		returnValue = returnValue.replace(" /\\u003e\\u003c/a\\u003e|\\u003ca href=\\u0027javascript:JSAramaGetirByNumber(", "-");
		returnValue = returnValue.replace(" style=\\u0027border-width:0\\u0027 ","");
		returnValue = returnValue.replace(")\\u0027\\u003e\\u003cimg src=\\u0027leaflet-0.7.2/images/marker-icon.png\\u0027","");
		returnValue = returnValue.replace(")\\u0027 class=\\u0027linknone\\u0027\\u003e", "-");
		
		returnValue = returnValue.replace("\\u003c/a\\u003e", "");
		returnValue = returnValue.replace("/\\u003e|", "-");
		returnValue = returnValue.replace("££", "&");
		
				
		returnValue = returnValue.replace("{\"d\":\"", "");
		returnValue = returnValue.replace("\"}", "&");
		
		String[] values= returnValue.split("&");
		
		for (int i = 0; i < values.length; i++) {
			String[] temp  =values[i].split("@");
			
			String[] a = temp[0].split("-");
			
			returnList.add(new KeyValueDTO(a[1], a[2]));
			
		}
		
		return returnList;
	}
	
	public static List<KeyValueDTO> genelAramaByNumber(String string) throws Exception{
		List<KeyValueDTO> returnList= new ArrayList<KeyValueDTO>();
		
		String url = "http://cbs.kayseri.bel.tr/Rehber.aspx/JSGenelAramaByNumber";
		//url = "http://localhost/Rehber.aspx/JSGenelArama";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		String query = "{\"number\":"+string+"}";
		StringEntity input = new StringEntity(query,"UTF-8");
		input.setContentType("application/json; charset=UTF-8");
		post.setEntity(input);
		
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip,deflate");
		post.setHeader("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.6,en;q=0.4");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		post.setHeader("Host", "cbs.kayseri.bel.tr");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Referer", "http://cbs.kayseri.bel.tr/Rehber.aspx");
		
		
		
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(),"UTF-8"));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println(result.toString());
		
		String returnValue=result.toString();
//		returnValue = result.toString().replace("\\u003ca href=\\u0027javascript:JSAramaGetirByNumber(", "");
//		returnValue = returnValue.replace(" /\\u003e\\u003c/a\\u003e|\\u003ca href=\\u0027javascript:JSAramaGetirByNumber(", "-");
//		returnValue = returnValue.replace(" style=\\u0027border-width:0\\u0027 ","");
//		returnValue = returnValue.replace(")\\u0027\\u003e\\u003cimg src=\\u0027leaflet-0.7.2/images/marker-icon.png\\u0027","");
//		returnValue = returnValue.replace(")\\u0027 class=\\u0027linknone\\u0027\\u003e", "-");
//		
//		returnValue = returnValue.replace("\\u003c/a\\u003e", "");
//		returnValue = returnValue.replace("/\\u003e|", "-");
//		returnValue = returnValue.replace("££", "&");
//		
//				
//		returnValue = returnValue.replace("{\"d\":\"", "");
//		returnValue = returnValue.replace("\"}", "&");
		
		returnValue = returnValue.replace("|", "&");
		String[] values= returnValue.split("&");
		String a  =values[0];
		a=a.replace("{\"d\":\"", "");
		String b = values[1];
		
		returnList.add(new KeyValueDTO("lat", a));
		returnList.add(new KeyValueDTO("lng", b));	
		
		for (int i = 2; i < values.length; i++) {
			returnList.add(new KeyValueDTO("", values[i]));
			
			
			
			//returnList.add(new KeyValueDTO(a[1], a[2]));
			
		}
		
		return returnList;
	}
	
	public static void main(String[] args) throws Exception{
		//System.out.println(getInformaitonOfCoordinates("458745.6525627385", "4278203.998566265"));
		//System.out.println(getInformaitonOfCoordinates("38.73504480508859", "35.48951983451843"));
		//System.out.println(getIlceList());
//		for (Iterator iterator = getIlceList().iterator(); iterator.hasNext();) {
//			SelectItem type = (SelectItem) iterator.next();
//			System.out.println(type.getLabel());
//			
//		}
		//System.out.println(getMahalleList("MobilMahalleler.aspx?P1=12&P9=I"));
		
		//System.out.println(getKapiNo("225962"));
		
		
//		String url = "http://cbs.kayseri.bel.tr/Mobil.aspx";
//		String result= Util.getUrl(url);
//		
//		url = "http://cbs.kayseri.bel.tr/Mobil.aspx#pageKentRehberi";
//		result= Util.getUrl(url);
//		
//		url ="http://cbs.kayseri.bel.tr/MobilIlceler.aspx?P9=I";
//		result= Util.getUrl(url);;
//		System.out.println(result);
//		parseHtml(result);
//		System.out.println("bitti");
		
		
		//List<String> a =getKapiNo("11111111111");
		//List<String> a =getKapiNo("225962");
		//System.out.println("ddd");
		
		//List<NameValuePair> list = getInformaitonOfCoordinates2("35.5063445890609", "38.7264522982779");
		//List<KeyValueDTO> list =genelArama("kıranardı");
		genelAramaByNumber("13840");
		System.out.println("ddd");
	}
}
