package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogImports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaExports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOValeVivereService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOValeViveresImpl implements JCOValeVivereService {

    public ControlLogExports ListarValeViveres(ControlLogImports imports)throws Exception{

        ControlLogExports vve= new ControlLogExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_LECT_MAES_VIVER);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("ROWCOUNT", imports.getRowcount());

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exe = new EjecutarRFC();

            stfcConnection.execute(destination);

            JCoTable tableExport = tables.getTable(Tablas.S_DATA);

             Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> data = metodo.ListarObjetos(tableExport);
            String [] fields=imports.getFields();
            List<HashMap<String, Object>> data = metodo.ObtenerListObjetos(tableExport, fields);


            vve.setData(data);
            vve.setMensaje("Ok");
        }catch (Exception e){
            vve .setMensaje(e.getMessage());
        }

        return vve;
    }
    public VvGuardaExports GuardarValeViveres(VvGuardaImports imports)throws Exception{

        VvGuardaExports vve = new VvGuardaExports();

        try{

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_VALE_VIVER);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoParameterList export = stfcConnection.getExportParameterList();

            EjecutarRFC exe = new EjecutarRFC();
            exe.setTable(tables, Tablas.ST_VVI, imports.getSt_vvi());
            exe.setTable(tables, Tablas.ST_PVA, imports.getSt_pva());

            stfcConnection.execute(destination);

            vve.setP_orden(export.getValue("P_ORDEN").toString());
            vve.setP_merc(export.getValue("P_MERC").toString());
            vve.setP_vale(export.getValue("P_VALE").toString());

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldsT_mensaje());

            vve.setT_mensaje(t_mensaje);
            vve.setMensaje("Ok");

        }catch (Exception e){
        vve .setMensaje(e.getMessage());
        }
        return vve;
    }


}