package model.DAO;

import db.JDBIConnector;
import model.bean.ProductDiscount;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public abstract class DAO {
    protected Jdbi connector;

    public DAO() {
        connector = JDBIConnector.get();
    }

    protected Jdbi getConnector(){
        return connector;
    }
}
