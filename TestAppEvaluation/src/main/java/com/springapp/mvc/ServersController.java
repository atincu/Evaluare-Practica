package com.springapp.mvc;

import Model.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapp.mvc.service.impl.ModelMainService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/")
public class ServersController{

    private String urlSyncServer;
    private String urlImdbServer;

    private SyncServer syncServer;
    private List<LinkedHashMap> contentPool;
    private List <FinalModel> finalModelList = new ArrayList<FinalModel>();

    @RequestMapping(method = RequestMethod.GET)
     public String getName (ModelMap model) {
        model.addAttribute("messsage", "Add a keyword:");
        return "movieName";
    }

    @RequestMapping(value = "/displayResult", method = RequestMethod.POST)
    public String doPost(@RequestParam String movieName, ModelMap model) {
        finalModelList.clear();

        ApplicationContext context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
        ModelMainService modelMainService = (ModelMainService) context.getBean("ModelMainService");

        //verificare timestamp
        List <FinalModel> toDelete = modelMainService.findAllModels();
        for (int i = 0; i < modelMainService.findAllModels().size(); i++) {
            //mai mare de 15 minute
            if ((System.currentTimeMillis() - toDelete.get(i).getTime().getTime()) > 900000) {
                modelMainService.deleteModel(toDelete.get(i));
                System.out.println("Modelul " + toDelete.get(i).getTitle() + " a fost sters!");
            }
        }

        List <FinalModel> toCheck = modelMainService.findModelByName(movieName);
        if (toCheck.isEmpty()){
           //apeleaza servere
           urlSyncServer = "http://localhost:8081/tracktv?query=" + movieName;
           testGetDataSyncServer();
           urlImdbServer= "http://localhost:8082/movies/" + movieName;
           testGetDataPoolServer();
           addAllData();
           //salveaza in baza de date
           for (int i = 0; i < finalModelList.size(); i++){
               modelMainService.insertModel(finalModelList.get(i));
           }
       }
       else {
           for (int i = 0; i < modelMainService.findModelByName(movieName).size(); i++)
               finalModelList.add(modelMainService.findModelByName(movieName).get(i));
       }

        model.addAttribute("list", finalModelList);
        return "displayMovie";
    }


    public void testGetDataSyncServer(){
       RestTemplate restTemplate = new RestTemplate();
       ObjectMapper mapper = new ObjectMapper();

       //Salvare Json
        String jsonRight = restTemplate.getForObject(urlSyncServer, String.class);
        //pentru caracterele speciale
        byte jsonRightInByte[] = jsonRight.getBytes();
        String dataString = null;
        try {
            dataString = new String(jsonRightInByte, "UTF-8");
        }   catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            syncServer = mapper.readValue(dataString, SyncServer.class);
        }   catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < syncServer.getNoEntries(); i++){
            FinalModel finalModelObject = new FinalModel();
            finalModelObject.setTitle(syncServer.getContent().get(i).getTitle());
            finalModelObject.setPoster(syncServer.getContent().get(i).getPoster());
            finalModelObject.setYear(syncServer.getContent().get(i).getYear());
            finalModelObject.setOverview(syncServer.getContent().get(i).getOverview());
            finalModelObject.setUrl(syncServer.getContent().get(i).getUrl());
            finalModelObject.setActors("n/a");
            finalModelObject.setPlot("n/a");
            finalModelObject.setGenre("n/a");
            finalModelList.add(finalModelObject);
        }
    }

    public void testGetDataPoolServer() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            ServerMessage serverMessage = restTemplate.getForObject(urlImdbServer, ServerMessage.class);
            String url = serverMessage.getDetail();
            //refresh, gen
            Thread.sleep(5000);

            String jsonRight = restTemplate.getForObject(url, String.class);
            byte jsonRightInByte[] = jsonRight.getBytes();
            String dataString = null;
            try {
              dataString = new String(jsonRightInByte, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            contentPool = mapper.readValue(dataString, List.class);

        }
        catch (NullPointerException npe){
            System.out.println("Valoare null!");
        } catch (Exception e){
            System.out.println("Orice ar mai aparea");
        }
    }

    public void addAllData (){
        for (int i = 0; i < finalModelList.size(); i++ ){
            for (int j = 0; j < contentPool.size(); j++){
                if (finalModelList.get(i).getTitle().equals(contentPool.get(j).get("Title"))){

                    finalModelList.get(i).setActors((String) contentPool.get(j).get("Actors"));
                    finalModelList.get(i).setPlot((String) contentPool.get(j).get("Plot"));
                    finalModelList.get(i).setGenre((String) contentPool.get(j).get("Genre"));
                }
            }
        }
    }
}