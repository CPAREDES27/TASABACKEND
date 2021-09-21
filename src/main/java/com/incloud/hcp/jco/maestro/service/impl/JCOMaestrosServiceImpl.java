package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.lang.reflect.Array;
import java.util.*;

@Service
public class JCOMaestrosServiceImpl implements JCOMaestrosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public MaestroExport obtenerMaestro (MaestroImports importsParam) throws Exception {

        MaestroExport me=new MaestroExport();
        try {
            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", importsParam.getTabla());
            imports.put("DELIMITER", importsParam.getDelimitador());
            imports.put("NO_DATA", importsParam.getNo_data());
            imports.put("ROWSKIPS", importsParam.getRowskips());
            imports.put("ROWCOUNT", importsParam.getRowcount());
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_ORDER", importsParam.getOrder());
            logger.error("obtenerMaestro_1");
            //setear mapeo de tabla options

            List<MaestroOptions> options = importsParam.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }
            logger.error("obtenerMaestro_2");

            String []fields=importsParam.getFields();
            //ejecutar RFC ZFL_RFC_READ_TABLE
            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }
        return me;
    }


    public MaestroExport obtenerMaestro2 (MaestroImportsKey imports) throws Exception {

        Metodos metodo= new Metodos();
        MaestroExport me=new MaestroExport();
        MaestroOptionsKey me2 = new MaestroOptionsKey();

        List<MaestroOptions> option = imports.getOption();
        logger.error("TAMAÑO import: "+option.size());
        List<MaestroOptionsKey> options = imports.getOptions();
        logger.error("TAMAÑO import: "+options.size());
            /*for(int j =0;j<option.size();j++){
                MaestroOptions mop= option.get(j);
                logger.error("GET IMPORT: "+mop.getWa());
            }*/
        HashMap<String, Object> importz = new HashMap<String, Object>();
        importz.put("QUERY_TABLE", imports.getTabla());
        importz.put("DELIMITER", imports.getDelimitador());
        importz.put("NO_DATA", imports.getNo_data());
        importz.put("ROWSKIPS", imports.getRowskips());
        importz.put("ROWCOUNT", imports.getRowcount());
        importz.put("P_USER", imports.getP_user());
        importz.put("P_ORDER", imports.getOrder());
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodo.ValidarOptions(option,options);
        String []fields=imports.getFields();
        EjecutarRFC exec = new EjecutarRFC();
        me = exec.Execute_ZFL_RFC_READ_TABLE(importz, tmpOptions, fields);
        return me;
    }

    public MensajeDto editarMaestro (MaestroEditImports importsParam) throws Exception{

        //DESPUES
        MensajeDto msj= new MensajeDto();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TABLE", importsParam.getTabla());
            imports.put("P_FLAG", importsParam.getFlag());
            imports.put("P_CASE", importsParam.getP_case());
            imports.put("P_USER", importsParam.getP_user());


            EjecutarRFC exec = new EjecutarRFC();


            msj = exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, importsParam.getData());


        }catch (Exception e){

            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
        }
        return msj;


    }
    public MensajeDto editarMaestro2 (MaestroEditImport importsParam) throws Exception{

        //DESPUES
        MaestroExport me= new MaestroExport();
        MensajeDto msj= new MensajeDto();
        try {

            me= ConsultaReadTable(importsParam.getFieldWhere(),importsParam.getKeyWhere(),importsParam.getTabla(),importsParam.getP_user());

            List<MaestroUpdate> update = importsParam.getOpcion();
            List<HashMap<String, Object>> data= me.getData();
            LinkedHashMap<String,Object> newRecord = new LinkedHashMap<String,Object>();
            LinkedHashMap<String,Object> setDAta = new LinkedHashMap<String,Object>();
            logger.error("SALUDA2");
            for(Map<String,Object> datas: data){
                for(Map.Entry<String,Object> entry: datas.entrySet()){
                    String key= entry.getKey();
                    Object value= entry.getValue();
                    if(!key.equals("MANDT")) {
                        newRecord.put(key, value);
                    }
                }
            }

            String mensajito="";
            String valor="";
           for(int i=0;i<update.size();i++){
               mensajito=update.get(i).getField();
               valor = update.get(i).getValor();
               for(Map.Entry<String,Object> entry:newRecord.entrySet()){
                   if(entry.getKey().contains(mensajito)){
                       entry.setValue(valor);
                   }
               }
           }


            String cadena="|";
            for(Map.Entry<String,Object> entry:newRecord.entrySet()){
                cadena+=entry.getValue();
                cadena+="|";
            }

            cadena = cadena.substring(0,cadena.length()-1);
            me.setMensaje(cadena);


            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TABLE", importsParam.getTabla());
            imports.put("P_FLAG", importsParam.getFlag());
            imports.put("P_CASE", importsParam.getP_case());
            imports.put("P_USER", importsParam.getP_user());


            EjecutarRFC exec = new EjecutarRFC();


            msj = exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, cadena);

        }catch (Exception e){
            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
        }
        return msj;


    }
    public MaestroExport ConsultaReadTable(String key, String value, String table, String usuario) throws Exception{

        String cadena=key+" = "+ "'"+value+"'";
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        JCoRepository repo = destination.getRepository();
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("P_USER", usuario);
        importx.setValue("QUERY_TABLE", table);
        importx.setValue("DELIMITER", "|");
        JCoParameterList tables = stfcConnection.getTableParameterList();

        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();
        tableImport.setValue("WA", key+" = "+"'"+value+"'");


        JCoTable tableExport = tables.getTable("DATA");
        JCoTable FIELDS = tables.getTable("FIELDS");
        stfcConnection.execute(destination);
        List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
        logger.error(tableExport.getString());
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            LinkedHashMap<String, Object> newRecord = new LinkedHashMap<String, Object>();
            for (int j = 0; j < FIELDS.getNumRows(); j++) {
                FIELDS.setRow(j);
                String keys = (String) FIELDS.getValue("FIELDNAME");
                //String contador ="00"+j+"_";
                //String key2 = contador +key;
                Object values = "";
                try {
                    values = ArrayResponse[j].trim();
                } catch (Exception e) {
                    values = "";

                }

                newRecord.put(keys, values);
            }
            datas.add(newRecord);
        }

            MaestroExport me = new MaestroExport();
            me.setData(datas);
        return me;
    }
    public AppMaestrosExports appMaestros(AppMaestrosImports imports) {

        AppMaestrosExports ame=new AppMaestrosExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_APP_MAESTROS);
            logger.error("stfcConnection: "+stfcConnection.toString());
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_ROL", imports.getP_rol());
            importx.setValue("P_APP", imports.getP_app());


            JCoParameterList tables = stfcConnection.getExportParameterList();
            stfcConnection.execute(destination);

            stfcConnection.execute(destination);

            JCoTable t_tabapp = tables.getTable(Tablas.T_TABAPP);
            JCoTable t_tabfield = tables.getTable(Tablas.T_TABFIELD);
            JCoTable t_tabservice = tables.getTable(Tablas.T_TABSERVICE);



            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> List_t_tabapp = metodo.ListarObjetos(t_tabapp);
            List<HashMap<String, Object>> List_t_tabfield = metodo.ListarObjetos(t_tabfield);
            List<HashMap<String, Object>> List_t_tabservice = metodo.ListarObjetos(t_tabservice);

            ame.setT_tabapp(List_t_tabapp);
            ame.setT_tabfield(List_t_tabfield);
            ame.setT_tabservice(List_t_tabservice);
            ame.setMensaje("Ok");

        }catch (Exception e){
            ame.setMensaje(e.getMessage());
        }

        return ame;
    }



}
