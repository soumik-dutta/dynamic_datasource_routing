package com.omoto.viewaccess.pojomodel;

import com.omoto.vo.PersonalInfoVO;
import com.omoto.vo.QualifyingDataVO;

import java.util.List;

/**
 * Created by omoto on 23/8/16.
 */
public class View {
    //personal info  @see com.omoto.vo.PersonalInfoVO
    private PersonalInfoVO personalinfo;
    //list of qualifing info list
    private List<QualifyingDataVO> qualifyinginfo;

    //set api key
    private String key;


    //getter- setter


    public PersonalInfoVO getPersonalinfo() {
        return personalinfo;
    }

    public void setPersonalinfo(PersonalInfoVO personalinfo) {
        this.personalinfo = personalinfo;
    }

    public List<QualifyingDataVO> getQualifyinginfo() {
        return qualifyinginfo;
    }

    public void setQualifyinginfo(List<QualifyingDataVO> qualifyinginfo) {
        this.qualifyinginfo = qualifyinginfo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
