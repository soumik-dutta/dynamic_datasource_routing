package com.omoto.viewaccess.helper;

import com.google.gson.Gson;
import com.omoto.constants.Constants;
import com.omoto.controllers.smsController.SMSDataHelper;
import com.omoto.dao.implementation.CampaignDAO;
import com.omoto.dao.smsListImplementation.SmsListDAO;
import com.omoto.model.Campaign;
import com.omoto.utils.HttpUtils;
import com.omoto.viewaccess.pojomodel.View;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to create the HTTP request to CRMApiController
 * Created by soumik on 30/8/16.
 */
public class CallCRMApiHelper implements Constants {
    Gson gson = new Gson();


    /**
     * process view object and create a json that will be send to CRMApiController
     *
     * @param viewList :  view object
     *                 created by soumik 31/08/2016
     */
    public void sendCrmApiRequest(List<View> viewList, CampaignDAO campaignDAO, SmsListDAO smsListDAO) throws Exception {
        Map<String, Long> hashMap = new HashMap<String, Long>();
        Campaign campaign;
        Long listId;

        //creating a list id for each campaign type
        campaign = (Campaign) campaignDAO.getObjectByApikey(ESTHETICSKEY);
        listId = SMSDataHelper.createNewSmsList(campaign.getCustomerid(), campaign.getId(), smsListDAO);
        hashMap.put(ESTHETICS, listId);

        campaign = (Campaign) campaignDAO.getObjectByApikey(HEALTHKEY);
        listId = SMSDataHelper.createNewSmsList(campaign.getCustomerid(), campaign.getId(), smsListDAO);
        hashMap.put(HEALTH, listId);


        for (View view : viewList) {
            List<View> viewListForSendingJson = new ArrayList<View>();
            String requestJson = "";
            String encodedRequestJson = "";


            //String jsonBodyRawData = URLEncoder.encode(requestJson, "UTF-8");
            System.out.println("send sms calling");
            //creating the url for excel processing..
            String callsendemailurl = "";

            //set the listid to personal info
            if (view.getKey().equalsIgnoreCase(ESTHETICSKEY)) {
                view.getPersonalinfo().setListid(hashMap.get(ESTHETICS));
                view.getPersonalinfo().setUnitname("Femiint Esthetics");
            }
            if (view.getKey().equalsIgnoreCase(HEALTHKEY)) {
                view.getPersonalinfo().setListid(hashMap.get(HEALTH));
                view.getPersonalinfo().setUnitname("Femiint Health");
            }


            //add the view to an arraylist
            viewListForSendingJson.add(view);
            //jsonify the view object
            requestJson = gson.toJson(viewListForSendingJson);
            //encode the json to UTF-8 format
            encodedRequestJson = URLEncoder.encode(requestJson, "UTF-8");
            //create the usrl call
            callsendemailurl = CRM_EXCEL_PROCESSING_URL + "json=" + encodedRequestJson + "&key=" + view.getKey();

            System.out.println(callsendemailurl);
            //send a http call to the url
            HttpUtils.runRequest(callsendemailurl);
        }
    }
}
