package com.omoto.viewaccess.dao;

import com.omoto.constants.Constants;
import com.omoto.utils.StrUtil;
import com.omoto.viewaccess.ViewAccessUtil;
import com.omoto.viewaccess.pojomodel.View;
import com.omoto.vo.PersonalInfoVO;
import com.omoto.vo.QualifyingDataVO;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omoto on 30/8/16.
 */
public class ViewDAO extends SimpleJdbcDaoSupport implements Constants {

    public List<View> getItems() {
        System.out.println("Inside ViewDAO ....");
        StringBuilder fromDateTime = new StringBuilder();
        StringBuilder toDateTime = new StringBuilder();
        new ViewAccessUtil().getDateForView(fromDateTime, toDateTime);

        String query = "SELECT * FROM view_name";
        System.out.println(query);
        return getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<View>() {
            public View mapRow(ResultSet rs, int rows) throws SQLException {
                System.out.println("Inside map row ...");
                // create view reference
                View view = new View();

                //column ids  that are qualifing data
                List<Integer> columnids = new ArrayList<Integer>();
                columnids.add(3);
                columnids.add(5);
                columnids.add(13);
                columnids.add(22);
                columnids.add(23);
                columnids.add(29);

                //creating the personal information
                PersonalInfoVO personalInfoVO = new PersonalInfoVO();
                personalInfoVO.setEmail(rs.getString(26));
                personalInfoVO.setRegid(rs.getString(4));
                personalInfoVO.setFirstname(rs.getString(10) + " " + rs.getString(11));
                personalInfoVO.setLastname(rs.getString(12));
//                personalInfoVO.setMobileno(rs.getString(24));
                personalInfoVO.setMobileno("7761844042");

                //choose sms type
                view.setKey(rs.getString(3) != "COSMETOLOGY AND DERMATOLOGY" ? ESTHETICSKEY : HEALTHKEY);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();

                //creating qualifying information
                List<QualifyingDataVO> qualifyingDataVOList = new ArrayList<QualifyingDataVO>();

                for (Integer columns : columnids) {

                    System.out.println("column type : " + resultSetMetaData.getColumnType(columns) + " ; column name : " + resultSetMetaData.getColumnName(columns));

                    //create qualifying
                    QualifyingDataVO qualifyingDataVO = new QualifyingDataVO();
                    qualifyingDataVO.setName(resultSetMetaData.getColumnName(columns));
                    //get the column type
                    if (resultSetMetaData.getColumnType(columns) == Types.VARCHAR)
                        qualifyingDataVO.setValue(StrUtil.nonNull(rs.getString(columns)));
                    if (resultSetMetaData.getColumnType(columns) == Types.DATE) {
                        qualifyingDataVO.setValue(rs.getDate(columns).toString());
                    }
                    if (resultSetMetaData.getColumnType(columns) == Types.INTEGER) {
                        qualifyingDataVO.setValue(((Integer) rs.getInt(columns)).toString());
                    }

                    //add the qualifying data to the list
                    qualifyingDataVOList.add(qualifyingDataVO);
                }

                view.setPersonalinfo(personalInfoVO);
                view.setQualifyinginfo(qualifyingDataVOList);


                return view;
            }
        });
    }
}
