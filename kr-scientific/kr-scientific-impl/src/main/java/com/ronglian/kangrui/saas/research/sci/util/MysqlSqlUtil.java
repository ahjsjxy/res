package com.ronglian.kangrui.saas.research.sci.util;


import com.ronglian.kangrui.saas.research.sci.vo.CrfFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.FormConfigVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class MysqlSqlUtil {

    public static  final  String idColumn = "id";
    public static  final  String objectIdColumn = "objectId";
    public static  final  String deletedColumn = "deleted";
    public static  final  String statusColumn = "status" ;


    /**
     *
     * @param formVo
     * @return
     */
    /*****************************************************
     CREATE TABLE `attachment` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `path` varchar(400) DEFAULT NULL COMMENT '文件保存相对路径',
     `name` varchar(200) DEFAULT NULL COMMENT '文件名称',
     `type` int(11) DEFAULT NULL COMMENT '1:study;2:subject;3:biomaterial;4;表单内部',
     `belong_id` bigint(20) DEFAULT NULL COMMENT '根据type挂在ssbd数据id',
     `create_time` datetime DEFAULT NULL,
     `create_user_name` varchar(100) DEFAULT NULL,
     `create_user_id` bigint(20) DEFAULT NULL,
     PRIMARY KEY (`id`)
     ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统附件记录'
     ******************************************************/
    public static String generateCreateTableSql(FormConfigVo formVo){
        StringBuilder sb = new StringBuilder(" CREATE TABLE ");
        List<CrfFieldVo> crfFieldVoList = formVo.getFieldVoList() ;
        sb.append(formVo.getTableName());
        sb.append(" ( ");
        sb.append(" `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID', ");
        sb.append(" `objectId` bigint(20) NOT NULL COMMENT 'Object Id', ");
        sb.append(" `"+deletedColumn+"` INT(11) NOT NULL DEFAULT '0' COMMENT '是否删除: [ 0:未删除 1:已删除 ]', ");
        sb.append(" `"+statusColumn+"` INT(11) NOT NULL DEFAULT '3' COMMENT 'CRF的状态: [ 3：填写中  4:待审核  5:已审核  6：待修改 ]', ");
        if (crfFieldVoList != null && crfFieldVoList.size()>0) {
            for (CrfFieldVo crfFieldVo : crfFieldVoList) {
                sb.append(generateCreateFieldSql(crfFieldVo));
            }
        }
        sb.append(" PRIMARY KEY (id) ");
        sb.append(" ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ");

        return sb.toString();
    }



    private static String generateCreateFieldSql(CrfFieldVo crfFieldVo){
        StringBuilder sb = new StringBuilder();
        sb.append(" `");
        sb.append(crfFieldVo.getFieldName());
        sb.append("` ");
        sb.append(" nvarchar(255)  NULL DEFAULT NULL ,");
        return sb.toString();
    }



    public static String generateAddColumnSql(String tableName, String columnName){
        StringBuilder sb = new StringBuilder("ALTER TABLE ");
        sb.append(" `");
        sb.append(tableName);
        sb.append("`");
        sb.append(" ADD ") ;
        sb.append("`");
        sb.append(columnName);
        sb.append("`");
        sb.append(" varchar(255) NULL ") ;

        return sb.toString();
    }



    public static String generateSelectDataSql(FormConfigVo formVo, Long objectId){
        List<CrfFieldVo> fields = formVo.getFieldVoList() ;

        StringBuilder sb = new StringBuilder("SELECT "+idColumn+",");
        for (int i = 0; i < fields.size(); i++) {
            CrfFieldVo fieldVo = fields.get(i);
            sb.append(" `");
            sb.append(fieldVo.getFieldName());
            sb.append("`,");

        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" FROM ");
        sb.append(formVo.getTableName());
        sb.append(" WHERE "+deletedColumn+"=0 ");
        if(objectId != null){
            sb.append(" AND "+objectIdColumn+"=");
            sb.append(objectId);
        }
        return sb.toString();
    }



    /**
     *@MethodName:generateUpdateSql
     * @param tableName
     * @param fields
     *@return:java.lang.String
     *@Desciption:生成更新sql
     *@author:cdchen
     *@date:2018-07-04 9:30
     */
    public static String generateUpdateDataSql(String tableName, Map<String,Object> fields, Long recorId){
        if(isOnlyId(fields)){
            return "";
        }

        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(" `");
        sb.append(tableName);
        sb.append("` SET ");


        for (String key : fields.keySet()) {
            if(key.equals(idColumn)){
                continue;
            }
            sb.append(" `");
            sb.append(key);
            sb.append("` ");
            sb.append("=");
            sb.append("?");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);

        sb.append(" WHERE id =  ");
        sb.append(recorId);
        return sb.toString();
    }




    private static boolean isOnlyId(Map<String,Object>   fields){
        boolean flag = true;
        for (String key : fields.keySet()) {
            if(!key.equals(idColumn)){
                flag = false;
                break;
            }
        }
        return flag;
    }



    /**
     *
     * @param tableName
     * @param fields
     * @param objectId
     * @return
     */
    public static String generateInsertDataSql(String tableName, Map<String,Object> fields, Long objectId){
        StringBuilder sb = new StringBuilder("INSERT INTO ");

        sb.append(" `");
        sb.append(tableName);
        sb.append("` ");

        StringBuilder columns = new StringBuilder(" ( `"+objectIdColumn+"`,");
        StringBuilder values = new StringBuilder(" ( ");
        values.append(objectId);
        values.append(",");


        for (String key : fields.keySet()) {
            columns.append(" `");
            columns.append(key);
            columns.append("` ");
            columns.append(",");

            values.append("?");
            values.append(",");
        }

        values.deleteCharAt(values.length()-1);
        columns.deleteCharAt(columns.length()-1);

        values.append(" ) ");
        columns.append(" ) ");

        return sb.append(columns).append(" VALUES  ").append(values).toString();
    }



    public static String generateDelRecordSql(String tableName, Long id){
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(" `");
        sb.append(tableName);
        sb.append("` SET ");
        sb.append(deletedColumn);
        sb.append("=1");
        sb.append(" WHERE "+idColumn+" =  ");
        sb.append(id);
        return sb.toString();
    }

}
