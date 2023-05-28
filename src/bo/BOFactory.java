package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PlaceOrderBOImpl;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PLACE_ORDER;
    }

    public SuperBO getBO(BOTypes boType){
        switch (boType) {
            case CUSTOMER: return new CustomerBOImpl();
            case ITEM: return new ItemBOImpl();
            case PLACE_ORDER: return  new PlaceOrderBOImpl();
            default: return null;
        }
    }
}
