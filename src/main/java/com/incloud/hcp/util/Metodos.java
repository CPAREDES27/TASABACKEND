package com.incloud.hcp.util;

import com.incloud.hcp.jco.maestro.dto.MaestroImports;
import com.incloud.hcp.jco.maestro.dto.MaestroImportsKey;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Metodos {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<HashMap<String, Object>> ListarObjetos(JCoTable tableExport) throws Exception {

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            JCoFieldIterator iter = tableExport.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();
                String key = (String) field.getName();
                Object value = tableExport.getValue(key);

                if(key.equals("ESPMR")){
                    if(value.equals("L")){
                        value="LIBERADO";
                    }else{
                        value="NO LIBERADO";
                    }

                }
                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    value = dateFormat.format(value);
                }


                try {
                    if (field.getTypeAsString().equals("DATE")) {

                        String date = String.valueOf(value);
                        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = dia.format(value);
                        value = fecha;
                    }
                }catch (Exception e){
                    value=String.valueOf(value);
                }
                newRecord.put(key, value);
            }
            data.add(newRecord);
        }

        return data;
    }

    public List<HashMap<String, Object>> ListarObjetosDIR(JCoTable tableExport) throws Exception {

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            JCoFieldIterator iter = tableExport.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();

                String key = (String) field.getName();
                Object value = tableExport.getValue(key);

                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    value = dateFormat.format(value);
                }

                if (field.getTypeAsString().equals("DATE") && key.equals("FEARR")) {
                    try {
                        String date = String.valueOf(value);
                        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = dia.format(value);
                        value = fecha;
                    } catch (Exception e) {
                        value = "";
                    }

                }


                newRecord.put(key, value);
            }
            data.add(newRecord);
            if (newRecord.containsKey("ESMAR")) {
                if (newRecord.containsValue("C") || newRecord.containsValue("A")) {

                    data.add(newRecord);
                }
            }


        }

        return data;
    }

    public String returnTable(String table) {
        String tablita = "";
        if (table.equals("MONEDA")) {
            tablita = "ZFLMND";
        }
        if (table.equals("UBICPLANTA")) {
            tablita = "ZFLUPT";
        }
        if (table.equals("LITORAL")) {
            tablita = "ZFLZLT";
        }else if (table.equals("GRUPOEMPRESA")) {
            tablita = "ZFLGRE";

        } else if (table.equals("INCIDENTE")) {
            tablita = "ZFLINC";

        } else if (table.equals("LITORAL")) {
            tablita = "ZFLZLT";

        } else if (table.equals("CLASEAVISOMANT")) {
            tablita = "TQ80_T";

        } else if (table.equals("ORGCOMPRAS")) {
            tablita = "T024E";

        } else if (table.equals("UNIDADMEDIDA")) {
            tablita = "ZFLUMD";

        } else if (table.equals("ORGVENTAS")) {
            tablita = "TVKOT";

        } else if (table.equals("CANALDISTRIBUCION")) {
            tablita = "TVTWT";

        } else if (table.equals("ORIGENDATOS")) {
            tablita = "ZFLORD";

        } else if (table.equals("SISTPESCA")) {
            tablita = "ZFLSPE";
        } else if (table.equals("GRUPFLOTA")) {
            tablita = "ZFLGFL";
        } else if (table.equals("GRUPCAPACIDAD")) {
            tablita = "ZFLGCP";
         } else if (table.equals("BODEGA")) {
            tablita = "ZFLBOD";
        } else if (table.equals("TEMPORADA")) {
            tablita = "ZFLTPO";

        } else if (table.equals("SUMINISTRO")) {

            tablita = "ZFLSUM";

        } else if (table.equals("ZONAPESCA")) {
            tablita = "ZFLZPC";

        } else if (table.equals("REPERCUSION")) {
            tablita = "ZV_FLRE";

        } else if (table.equals("SINIESTRO")) {
            tablita = "ZFLINC";

        } else if (table.equals("ACCIDENTE")) {
            tablita = "ZFLINC";

        } else if (table.equals("ENCUBICACION")) {
            tablita = "ZFLUBI";

        } else if (table.equals("TIPOEMBARCACION")) {
            tablita = "ZFLTEM";

        } else if (table.equals("ZONALITORAL")) {
            tablita = "ZFLZLT";

        } else if (table.equals("PLANTAPROPIA")) {
            tablita = "ZV_FLPA";

        } else if (table.equals("UBICPLANTA")) {
            tablita = "ZFLUPT";

        } else if (table.equals("MONEDASAP")) {
            tablita = "ZFLMND";

        } else if (table.equals("ESPECIE")) {
            tablita = "ZFLSPC";

        } else if (table.equals("CATEGORIA")) {
            tablita = "ZFLCNS";

        } else if (table.equals("ZONAAREA")) {
            tablita = "ZFLZAR";

        }
        else if (table.equals("SISTVIRADO")) {
            tablita = "ZTBC_DATA";

        }
        return tablita;
    }

    public String returnWA(String table) {
        String wa = "";
        if (table.equals("MONEDA") || table.equals("ESPECIE") || table.equals("UBICPLANTA") || table.equals("MONEDASAP") || table.equals("ZONALITORAL") || table.equals("TIPOEMBARCACION") || table.equals("GRUPCAPACIDAD") || table.equals("BODEGA") || table.equals("TEMPORADA") || table.equals("SUMINISTRO") || table.equals("ZONAPESCA") || table.equals("ORIGENDATOS") || table.equals("SISTPESCA") || table.equals("GRUPFLOTA") || table.equals("UBICPLANTA") || table.equals("LITORAL") || table.equals("INCIDENTE") ||table.equals("UNIDADMEDIDA")) {
            wa = "ESREG = 'S'";
        }else if(table.equals("CLASEAVISOMANT") || table.equals("REPERCUSION") ||table.equals("ORGVENTAS") || table.equals("CANALDISTRIBUCION")){
            wa= "SPRAS EQ 'S'";
        }else if(table.equals("ORGVENTAS")){
            wa= "SPRAS EQ 'S'";
        }else if(table.equals("SINIESTRO")){
            wa = "ESREG = 'S' AND CDTIN = 'S'";
        }else if(table.equals("ACCIDENTE")){
            wa = "ESREG = 'S' AND CDTIN = 'A' ";
        }else if(table.equals("ENCUBICACION")){
            wa = "CDUBI BETWEEN '1' AND '999'";
        }else if(table.equals("PLANTAPROPIA")){
            wa= "ESREG = 'S' AND (WERKS <> 'FP09') AND (INPRP = 'P')";
        }else if(table.equals("CATEGORIA")){
            wa= "DESCR = 'CATEGORIA PESCA COMPETENCIA'";
        }else if(table.equals("ZONAAREA")){
            wa = "ZESZAR = 'S'";
        }else if(table.equals("SISTVIRADO")){
            wa= "CODIG EQ 'SH' AND (STATU EQ '1')";
        }

        return wa;
    }

    public String[] returnField(String table) {

        String[] fields = null;
        if (table.equals("UBICPLANTA")) {
            fields = new String[]{"CDUPT", "DSUPT"};
        }else if (table.equals("MONEDA")) {
            fields = new String[]{"CDMND", "DSMND"};
        }else if (table.equals("LITORAL")) {
            fields = new String[]{"CDZLT", "DSZLT"};
        }else if ( table .equals("GRUPOEMPRESA")){
            fields= new String[]{"CDGRE","DSGRE"};
        }
        else if ( table .equals("INCIDENTE")){
            fields= new String[]{"CDINC", "DSINC"};
        }
        else if ( table .equals("CLASEAVISOMANT")){
            fields= new String[]{"CDGRE","DSGRE"};
        }
        else if ( table .equals("GRUPOEMPRESA")){
            fields= new String[]{"QMART", "QMARTX"};
        }
        else if ( table .equals("ORGCOMPRAS")){
            fields= new String[]{"EKORG", "EKOTX"};
        }
        else if ( table .equals("UNIDADMEDIDA")){
            fields= new String[]{"CDUMD", "DSUMD"};
        }
        else if ( table .equals("ORGVENTAS")){
            fields= new String[]{"VKORG", "VTEXT"};
        }
        else if ( table .equals("CANALDISTRIBUCION")){
            fields= new String[]{"VTWEG", "VTEXT"};
        }
        else if ( table .equals("ORIGENDATOS")){
            fields= new String[]{"CDORD", "DSCRP"};
        }
        else if ( table .equals("SISTPESCA")){
            fields= new String[]{"CDSPE", "DSSPE"};
        }
        else if ( table .equals("GRUPFLOTA")){
            fields= new String[]{"CDGFL", "DSGFL"};
        }
        else if ( table .equals("GRUPCAPACIDAD")){
            fields= new String[]{"CDGCP", "DSGCP"};
        } else if (table.equals("BODEGA")) {
            fields = new String[] {"CDBOD", "DSBOD"};
        }else if (table.equals("TEMPORADA")) {
            fields = new String[] {"CDTPO", "DSTPO"};
        } else if (table.equals("SUMINISTRO")) {
            fields = new String[] {"CDSUM", "DSSUM"};
        } else if (table.equals("ZONAPESCA")) {
            fields = new String[] {"CDZPC", "DSZPC"};
        } else if (table.equals("REPERCUSION")) {
            fields = new String[] {"AUSWK", "AUSWKT"};
        } else if (table.equals("SINIESTRO")) {
            fields = new String[] {"CDINC", "DSINC"};
        } else if (table.equals("ACCIDENTE")) {
            fields = new String[] {"CDINC", "DSINC"};
        } else if (table.equals("ENCUBICACION")) {
            fields = new String[] {"CDUBI", "DSUBI"};
        } else if (table.equals("TIPOEMBARCACION")) {
            fields = new String[] {"CDTEM", "DESCR"};
        } else if (table.equals("ZONALITORAL")) {
            fields = new String[] {"CDZLT", "DSZLT"};
        } else if (table.equals("PLANTAPROPIA")) {
            fields = new String[] {"CDPTA", "DESCR"};
        } else if (table.equals("UBICPLANTA")) {
            fields = new String[] {"CDUPT", "DSUPT"};
        } else if (table.equals("MONEDASAP")) {
            fields = new String[] {"MDEXT", "DSMND"};
        } else if (table.equals("ESPECIE")) {
            fields = new String[] {"CDSPC", "DSSPC"};
        } else if (table.equals("CATEGORIA")) {
            fields = new String[] {"VAL01", "VAL02"};
        } else if (table.equals("ZONAAREA")) {
            fields = new String[] {"ZCDZAR", "ZDSZAR"};
        }
        else if (table.equals("SISTVIRADO")) {
            fields = new String[] {"ARGUM", "DESCR"};
        }

        return fields;
    }


    public List<HashMap<String, Object>> ValidarOptions(List<MaestroOptions> option ,List<MaestroOptionsKey> options){



        String control="";


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

        if(option.size()>0 && options.size()==0){
            for(int j=0;j<option.size();j++){
                MaestroOptions mop = option.get(j);
                HashMap<String, Object> record2 = new HashMap<String, Object>();
                record2.put("WA",mop.getWa());
                tmpOptions.add(record2);
            }
        }

        if(option.size() >0 && options.size()>0){
            for(int j=0;j<option.size();j++){
                MaestroOptions mop = option.get(j);
                HashMap<String, Object> record2 = new HashMap<String, Object>();
                record2.put("WA",mop.getWa());
                tmpOptions.add(record2);
            }

            for (int i = 0; i < options.size(); i++) {
                MaestroOptionsKey mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                if(mo.getControl().equals("INPUT"))
                {
                    control="LIKE";
                }
                if (mo.getControl().equals("COMBOBOX")) {
                    control="=";
                }
                if(mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals("") )){
                    control="BETWEEN";
                }else if(mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    control="=";
                }

                if(mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put("WA","AND"+" "+ mo.getKey() +" "+ control+ " "+ "'%"+mo.getValueLow()+"%'");
                }else if(mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put("WA","AND"+" "+ mo.getKey() +" "+ control+ " "+ "'"+mo.getValueLow()+"'");
                }else if(mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))){
                    record.put("WA","AND"+" "+ mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'" +" AND "+ "'"+mo.getValueHigh()+"'");
                }else if(mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))){
                    record.put("WA","AND"+" "+ mo.getKey()+" "+ control+ " "+ "'"+mo.getValueLow()+"'" );
                }
                tmpOptions.add(record);

            }

        }

        if(options.size()>0 && option.size()==0) {
            for (int i = 0; i < options.size(); i++) {
                MaestroOptionsKey mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                if (mo.getControl().equals("INPUT")) {
                    control = "LIKE";
                }
                if (mo.getControl().equals("COMBOBOX")) {
                    control = "=";
                }
                if (mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))) {
                    control = "BETWEEN";
                } else if (mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    control = "=";
                }

                if (mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    record.put("WA", mo.getKey() + " " + control + " " + "'%" + mo.getValueLow() + "%'");
                } else if (mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    record.put("WA", mo.getKey() + " " + control + " " + "'" + mo.getValueLow() + "'");
                } else if (mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))) {
                    record.put("WA", mo.getKey() + " " + control + " " + "'" + mo.getValueLow() + "'" + " AND " + "'" + mo.getValueHigh() + "'");
                } else if (mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                    record.put("WA", mo.getKey() + " " + control + " " + "'" + mo.getValueLow() + "'");
                }


                if (i > 0) {
                    if (mo.getControl().equals("INPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                        record.put("WA", "AND" + " " + mo.getKey() + " " + control + " " + "'%" + mo.getValueLow() + "%'");
                    } else if (mo.getControl().equals("COMBOBOX") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                        record.put("WA", "AND" + " " + mo.getKey() + " " + control + " " + "'" + mo.getValueLow() + "'");
                    } else if (mo.getControl().equals("MULTIINPUT") && (!mo.getValueLow().equals("") && !mo.getValueHigh().equals(""))) {
                        record.put("WA", "AND" + " " + mo.getKey() + " " + control + " " + "'" + mo.getValueLow() + "'" + " AND " + "'" + mo.getValueHigh() + "'");
                    } else if (mo.getControl().equals("MULTIINPUT") && (mo.getValueHigh().equals("") || mo.getValueHigh().equals(null))) {
                        record.put("WA", "AND" + " " + mo.getKey() + " " + control + " " + "'" + mo.getValueLow() + "'");
                    }

                }
                tmpOptions.add(record);

            }
        }

        return tmpOptions;
    }
    public List<HashMap<String, Object>> ObtenerListObjetos(JCoTable jcoTable,  String[] fields)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        if(fields.length>=1){
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                JCoFieldIterator iter = jcoTable.getFieldIterator();
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                while (iter.hasNextField()) {
                    JCoField field = iter.nextField();
                    String key = (String) field.getName();
                    Object value = jcoTable.getValue(key);

                    for (int k = 0; k < fields.length; k++) {
                        logger.error("key: " + key + " k: " + fields[k]);
                        ;

                        if (fields[k].trim().equals(key.trim())) {

                            if (field.getTypeAsString().equals("TIME")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                                value = dateFormat.format(value);
                            }
                            try {
                                if (field.getTypeAsString().equals("DATE")) {

                                    String date = String.valueOf(value);
                                    SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                                    String fecha = dia.format(value);
                                    value = fecha;
                                }
                            }catch (Exception e){
                                value=String.valueOf(value);
                            }

                            newRecord.put(key, value);

                        }
                    }


                }
                data.add(newRecord);
            }
        }else {
            data=ObtenerListObjetosSinField(jcoTable);
        }
        return data;

    }

    public List<HashMap<String, Object>> ObtenerListObjetosSinField(JCoTable jcoTable)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < jcoTable.getNumRows(); i++) {
            jcoTable.setRow(i);
            JCoFieldIterator iter = jcoTable.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();
                String key = (String) field.getName();
                Object value = jcoTable.getValue(key);

                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    value = dateFormat.format(value);
                }
                try {
                    if (field.getTypeAsString().equals("DATE")) {

                        String date = String.valueOf(value);
                        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = dia.format(value);
                        value = fecha;
                    }
                }catch (Exception e){
                    value=String.valueOf(value);
                }

                newRecord.put(key, value);
            }
            data.add(newRecord);
        }
        return data;

    }

    public String ObtenerCampo(JCoTable tableExport, JCoTable FIELDS){
        String campo="";
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");
                if(key.equals("CDPTA")){
                    value=ArrayResponse[j].trim();
                    campo=value.toString();
                    logger.error("CDPTA: "+campo);
                }
            }

        }
        return campo;
    }

    public String ConvertirABase64(String fileName)throws IOException {
        File file = new File(fileName);
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
