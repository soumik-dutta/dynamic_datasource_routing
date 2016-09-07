package com.omoto.viewaccess;

import com.omoto.constants.Constants;
import com.omoto.dao.implementation.CampaignDAO;
import com.omoto.dao.smsListImplementation.SmsListDAO;
import com.omoto.viewaccess.dao.ViewDAO;
import com.omoto.viewaccess.pojomodel.View;
import com.omoto.viewaccess.service.FemiintViewAccessService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller to initiate the view access with Dynamic Switching of Datasource
 * Created by Soumik on 22/8/16.
 */
public class DynamicDatasourceRoutingController implements Controller, Constants {
    private View view;
    private ViewDAO viewDAO;
    private DbContextHolder dbContextHolder;
    private CampaignDAO campaignDAO;
    private SmsListDAO smsListDAO;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //get the client from url parameters
        String client = httpServletRequest.getParameter("client");
        System.out.println("Inside DynamicDatasourceRoutingController....");

        //check whether the messaging service is UP/Down
        if (new ViewAccessUtil().isMessageServiceAvailable()) {
//        if(true) {
            //call the service methods
            if (client.equalsIgnoreCase(CON1)) {
                //calling the service class of Femiint
                new FemiintViewAccessService(dbContextHolder).getViewAccessData(viewDAO, campaignDAO, smsListDAO);
            }
        } else {
            System.out.println("Sorry! Messaging  service is currently down...");
        }
        httpServletResponse.setStatus(200);
        return null;
    }


    // getter-setter


    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public ViewDAO getViewDAO() {
        return viewDAO;
    }

    public void setViewDAO(ViewDAO viewDAO) {
        this.viewDAO = viewDAO;
    }

    public DbContextHolder getDbContextHolder() {
        return dbContextHolder;
    }

    public void setDbContextHolder(DbContextHolder dbContextHolder) {
        this.dbContextHolder = dbContextHolder;
    }

    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    public CampaignDAO getCampaignDAO() {
        return campaignDAO;
    }

    public void setSmsListDAO(SmsListDAO smsListDAO) {
        this.smsListDAO = smsListDAO;
    }

    public SmsListDAO getSmsListDAO() {
        return smsListDAO;
    }
}
