/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package USUARIOS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.BeanProcessor;

/**
 *
 * @author Genner
 */
public class usuarioDAL extends uqroo.conexion.Jdbc {

    public usuarioDAL() {
    }

    public List<Usuario> lista() throws Exception {
        Connection con = null;
        PreparedStatement psc = null;
        ResultSet rsc = null;
        List<Usuario> lis = new ArrayList<>();
        try {

            con = this.getDataSources().getConnection();

            BeanProcessor bp = new BeanProcessor();
            psc = con.prepareStatement("SELECT ID, NOMBRE FROM ALUMNO ");

            rsc = psc.executeQuery(); 
            while (rsc.next()) {

                lis.add((Usuario) bp.toBean(rsc, Usuario.class));
            }

        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (rsc != null) {
                    rsc.close();
                }
                if (psc != null) {
                    psc.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return lis;

    }

}
