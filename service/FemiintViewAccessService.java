package com.omoto.viewaccess.service;

import com.omoto.dao.implementation.CampaignDAO;
import com.omoto.dao.smsListImplementation.SmsListDAO;
import com.omoto.viewaccess.DbContextHolder;
import com.omoto.viewaccess.DbType;
import com.omoto.viewaccess.dao.ViewDAO;
import com.omoto.viewaccess.helper.CallCRMApiHelper;
import com.omoto.viewaccess.pojomodel.View;

import java.util.List;

/**
 * Created by omoto on 30/8/16.
 */
public class FemiintViewAccessService implements ClientViewAccessService {

    private DbContextHolder dbContextHolder;

    //constructor
    public FemiintViewAccessService(DbContextHolder dbContextHolder) {
        this.dbContextHolder = dbContextHolder;

    }

    @Override
    public void getViewAccessData(ViewDAO viewDAO, CampaignDAO campaignDAO, SmsListDAO smsListDAO) {
        try {
            //get datasource connection
            dbContextHolder.setCustomer(DbType.FEMINT);
            //get the Items
            List<View> viewList = viewDAO.getItems();
            //call CRM API Controller
            new CallCRMApiHelper().sendCrmApiRequest(viewList, campaignDAO, smsListDAO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //clearDbHolder
            clearDbHolder();
        }
    }


    @Override
    public void clearDbHolder() {
        dbContextHolder.clearCustomer();
    }
}
