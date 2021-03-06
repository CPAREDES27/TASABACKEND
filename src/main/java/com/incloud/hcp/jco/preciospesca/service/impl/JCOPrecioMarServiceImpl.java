package com.incloud.hcp.jco.preciospesca.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.preciospesca.dto.MaestroOptionsPrecioMar;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarImports;
import com.incloud.hcp.jco.preciospesca.service.JCOPrecioMarService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOPrecioMarServiceImpl implements JCOPrecioMarService {
    @Override
    public PrecioMarExports ObtenerPrecioMar(PrecioMarImports imports) throws Exception {
        Metodos metodos = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_INDPR", imports.getP_indpr());
        importParams.put("P_ROWS", imports.getP_rows());
        importParams.put("P_CALIDAD", imports.getP_calidad());
        importParams.put("P_FLAG", imports.getP_flag());

        List<MaestroOptions> option = imports.getP_option();
        List<MaestroOptionsKey> options2 = imports.getP_options();

        /**
         * Añadir el options de acuerdo a la aplicación
         */
        String optionWaApplications=null;
        switch (imports.getNum_application()){
            case 1: //Aprobacion Castigos Propuestos
                if(imports.getId_estado().equalsIgnoreCase("PC")){
                    optionWaApplications="(USCPP NE '' OR USCPP IS NOT NULL) AND ESCSG EQ 'N'";
                }
                break;
            case 2: //Aprobacion Precios
                optionWaApplications="(ESPRC EQ 'N' AND (ESCSG EQ 'L' OR ESCSG EQ '' OR ESCSG EQ ' ' OR ESCSG IS NULL)) AND PRCOM > '0.00'";
                break;
            case 3: //Castigos Propuestos
                if(imports.getId_estado().equalsIgnoreCase("PC")){
                    optionWaApplications="(USCPP EQ '' OR USCPP IS NULL) AND ESCSG NE 'L'";
                }
                break;
            case 4: //Precios acopio
                switch (imports.getId_estado()){
                    case "C":
                        optionWaApplications="(PRCOM IS NOT NULL AND PRCOM > 0)";
                        break;
                    case "S":
                        optionWaApplications="(PRCOM IS NULL OR PRCOM = 0)";
                        break;
                }
                break;
        }

        if(optionWaApplications!=null){
            MaestroOptions optionApplication=new MaestroOptions();
            optionApplication.setWa(optionWaApplications);
            option.add(optionApplication);
        }


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodos.ValidarOptions(option,options2);


        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_LECT_PRECIO_MAR);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", tmpOptions);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblT_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
        JCoTable tblSTR_PM = tables.getTable(Tablas.STR_PM);


        List<HashMap<String, Object>> listSTR_PM = metodos.ListarObjetos(tblSTR_PM);
        List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetos(tblT_MENSAJE);

        PrecioMarExports dto = new PrecioMarExports();
        dto.setStr_pm(listSTR_PM);
        dto.setT_mensaje(listT_MENSAJE);
        dto.setMensaje("OK");

        return dto;
    }
}
