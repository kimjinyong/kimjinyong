package egovframwork.kr.go.krms.com.commn.api.coltroller;

import java.io.BufferedReader;

/**
 * @Pakage Name : egovframework.kr.go.krms.com.cmmn.api.controller
 * @Class Name : ApiMngController.java
 * @Description : 외부 API 연결을 위한 controller
 *
 * @Copyright (c) All right reserved.
 *------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------
 * 수정일       / 수정자        / 수정내용
 * 2023.02.01 / 김민구        / 최초 생성
 *------------------------------------------------------------------
/*

@Controller
@EnableWebMvc
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiMngController {
 /** 프로퍼티 서비스*/
 @Resource(name = "propertiesService")
 protected EgovPropertyService propertiesService;

 private static final Logger logger = LoggerFactory.getLogger(ApiMngController.class);

 /**
  * <pre>
  * 1. 개요 : 내부망에서 티맵으로 외부망 API 호출을 위한 API
 */
 @RequestMapping(value = "/api/tmap/callOutApi.do")
 public ModelAndView callOutApi(@RequestParam Map<String, Object>commandMap, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception{
  ModelAndView mav = new ModelAndView();
  
  HttpURLConnection connect = null;
  InputStream instream = null;

  try {
   String urlName = EgovProperties.getProperty("Globals.fileMoveUrl");
   String urlPort = EgovProperties.getProperty("Globals.fileMovePort");
   URL url = new URL(urlName+":"+urlPort+"/com/api/tmap/responseApi.do");
   logger.debug("url connection start");

   connect = (HttpURLConnection)url.openConnection();
   connect.setRequestMethod("POST");
   connect.setUeCaches(false);
   connect.setDoOutput(true);
   connect.setDoInput(true);
   connect.setConnectTimeout(5000);

   htmlOutSender(connect, commandMap);

   instream = connect.getInputStream();

   StringBuilder sbd = htmlReader(instream);

   logger.debug(sbd.toString());
   JSONParser parse = new JSONParser();
   JSONObject jsonResult = (JSONObject)parse.parse(sbd.toString());

   logger.debug(jsonResult.toJSONString());
   Iterator<String> keys = jsonResult.keySet().iterator();
   while(keys.hasNext()){
    String key = (String)keys.next();
    mav.addObject(key,jsonResult.get(key));
   }
   logger.debug("url connection end");

  }catch(NullPointerException e){
   logger.error("nullpointException",e);
   logger.error(e.getMessage());
  }catch(IndexOutOfBoundsException e){
   logger.error("IndexOutOfBoundsException",e);
   logger.error(e.getMessage());
  }catch(Exception e){
   logger.error("Exception",e);
   logger.error(e.getMessage);
  }finally {
   if(connect != null) connect.disconnect();
   if(instream != null) instream.close();
  }

  mav.setViewName(KRMSConst.JSON_VIEW);
  return mav;
 }

 /**
  * <pre>
  * 1. 개요 : 외부망에서 Tmap API를 URL로 호출
  * 2. 처리내용
  *   - 로직 처리 : 외부망에서 Tmap API로 URL로 호출
  * </pre>
  * @Method Name : responseApi
  * @Description : 외부망에서 Tmap API를 URL로 호출
  * @param : @RequestParam 
  * @return : ModelAndView
  * @throws : Exception
 **/
 @RequestMapping(value = "/api/tmap/responseApi.do")
 public ModelAndView responseApi(@RequestParam Map<String, Object>commandMap, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception{
  ModelAndView mav = new ModelAndView();

  HttpURLConnection connect = null;
  InputStream instream = null;

  StringBuilder sbd = new StringBuilder();
  mav.setViewName(KRMSConst.JSON_VIEW);

  try {
   String customUrl = StringUtil.nvl(commandMap.get("url"));

   if("".equals(customUrl)){
    mav.addObject(KRMSConst.RESULT_MSG, KRMSConst.ERROR_999_CODE);
    mav.addObject(KRMSConst.RESULT_CODE, KRMSConst.JSON_SUCCESS_NO);
   }

   StringBuilder bud = new StringBuilder();
   bud.append("format=json");
   Iterator<String> keys = commandMap.keySet().iterator();
   while(keys.hasNext()){
    String key = (String)keys.next();
    bud.append("&"+key+"="+commandMap.get(key));
   }
   
   logger.debug("tmap call Start");
   logger.debug("http://apis.openapi.sk.com/tmap/"+customUrl+"?"+bud.toString());
   connect = (HttpURLConnection)url.openConnection();
   connect.setRequestMethod("POST");
   connect.setUseCaches(false);
   connect.setDoOutput(true);
   connect.setDoInput(true);
   connect.setConnectTimeout(5000);

   htmlOutSender(connect,commandMap);

   instream = connect.getInputStream();
   sbd = htmlReader(instream);
   
   logger.debug(sbd.toString());

   JSONParser parse = new JSONParser();
   JSONObject json = (JSONObject)parse.parse(sbd.toString());

   keys =json.keySet().iterrator();
   while(keys.hasNext()){
    String key = (String)keys.next();
    mav.addObject(key, json.get(key));
   }
  }catch(NullPointerException e){
   logger.error("nullpointException",e);
  }catch(IndexOutOfBoundsException e){
   logger.error("IndexOutOfBoundsEexception",e);
   logger.error(e.getMessage());
  }catch(Exception e){
   logger.error("Exception",e);
   logger.error(e.getMessage());
  }finally {
   if(connect != null) connect.disconnect();
   if(instream != null) instream.close();
  }

  mav.addObject(KRMSConst.RESULT_MSG, KRMSCcnst.JSON_SUCCESS);
  mav.addObject(KRMSConst.RESULT_CODE, KRMSConst.JSON_SUCCESS_YES);

  return mav;
 }

 private StringBuilder htmlReader(InputStream instream){
  StringBuilder sbd - new StringBuilder();
  try(BufferredReader reader - new BufferredReader(new InputStreamReader(instream));)
  {
   String line = "";

   for(int i=1; (ilne = reader.readLine())!=null; i++)
   {
    sbd.append(line);
   }
  }catch(IOException e){
   logger.error("htmlReader error",e);
  }
  return sbd;
 }

 private void htmlOutSender(HttpURLConnect connect, Map<String, Object>commandMap){
  try(OutputStreamWriter outWrite = new OutputStreamWriter(connect.getOutputStream());)
  {
   outWrite.write("format=json");
   Iterrator<String> keys = commandMap.keySet().iterrator();
   while(keys.hasNext()){
    String key = (String)keys.next();
    outWrite.append("&"+key+"="+commandMap.get(key));
   }
   outWrite.flush();
  }catch(Exception e){
   logger.error("htmlWriter error",e);
  }
 }
}
