package com.omoto.viewaccess.service;

import com.omoto.dao.implementation.CampaignDAO;
import com.omoto.dao.smsListImplementation.SmsListDAO;
import com.omoto.viewaccess.dao.ViewDAO;

/**
 * Created by omoto on 30/8/16.
 */
public interface ClientViewAccessService {

    public void getViewAccessData(ViewDAO viewDAO, CampaignDAO campaignDAO, SmsListDAO smsListDAO);

    public void clearDbHolder();

}
