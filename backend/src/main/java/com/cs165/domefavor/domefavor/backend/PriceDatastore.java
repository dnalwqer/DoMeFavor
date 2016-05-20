package com.cs165.domefavor.domefavor.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex on 16/4/17.
 */
public class PriceDatastore {

    private static final Logger mLogger = Logger
            .getLogger(PriceDatastore.class.getName());
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    private static Key getKey() {
        return KeyFactory.createKey(Price.Price_PARENT_ENTITY_NAME,
                Price.Price_PARENT_KEY_NAME);
    }


    public static boolean add(Price price) {
//        if (getPriceByName(price.id, null) != null) {
//            mLogger.log(Level.INFO, "Price exists");
//            return false;
//        }

        Key parentKey = getKey();


        Entity entity = new Entity(Price.Price_ENTITY_NAME, price.id,
                parentKey);
        entity.setProperty(Price.FIELD_NAME_id, price.id);
        entity.setProperty(Price.FIELD_NAME_taker, price.taker);
        entity.setProperty(Price.FIELD_NAME_price, price.price);

        mDatastore.put(entity);

        return true;
    }

    public static boolean deleteid(String name) {
        // you can also use name to get key, then use the key to delete the
        // entity from datastore directly
        // because name is also the entity's key

        // query
        Query.Filter filter = new Query.FilterPredicate(Price.FIELD_NAME_id,
                Query.FilterOperator.EQUAL, name);

        Query query = new Query(Price.Price_ENTITY_NAME);
        query.setFilter(filter);

        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = mDatastore.prepare(query);

        Entity result = pq.asSingleEntity();
        boolean ret = false;
        if (result != null) {
            // delete
            mDatastore.delete(result.getKey());
            ret = true;
        }

        return ret;
    }

    public static void deleteAll(){
        ArrayList<Price> list = query("");
        for(Price cur : list){
            deleteid(cur.id);
        }
    }

    public static ArrayList<Price> query(String name) {
        ArrayList<Price> resultList = new ArrayList<Price>();
        if (name != null && !name.equals("")) {
            Price Price = getPriceByName(name, null);
            if (Price != null) {
                resultList.add(Price);
            }
        } else {
            Query query = new Query(Price.Price_ENTITY_NAME);
            // get every record from datastore, no filter
            query.setFilter(null);
            // set query's ancestor to get strong consistency
            query.setAncestor(getKey());

            PreparedQuery pq = mDatastore.prepare(query);

            for (Entity entity : pq.asIterable()) {
                Price price = getPriceFromEntity(entity);
                if (price != null) {
                    resultList.add(price);
                }
            }
        }
        return resultList;
    }

//    public static ArrayList<Price> queryEmail(String email) {
//        ArrayList<Price> resultList = new ArrayList<Price>();
//
//            Query query = new Query(Price.Price_ENTITY_NAME);
//            // get every record from datastore, no filter
//            query.setFilter(null);
//            // set query's ancestor to get strong consistency
//            query.setAncestor(getKey());
//
//            PreparedQuery pq = mDatastore.prepare(query);
//
//            for (Entity entity : pq.asIterable()) {
//                Price price = getPriceFromEntity(entity);
//                if (price.taker.equals(email)) {
//                    resultList.add(price);
//                }
//            }
//
//        return resultList;
//    }

    public static Price getPriceByName(String name, Transaction txn) {
        Entity result = null;
        try {
            result = mDatastore.get(KeyFactory.createKey(getKey(),
                    Price.Price_ENTITY_NAME, name));
        } catch (Exception ex) {

        }

        return getPriceFromEntity(result);
    }

    private static Price getPriceFromEntity(Entity entity) {
        if (entity == null) {
            return null;
        }

        return new Price(
                (String) entity.getProperty(Price.FIELD_NAME_price),
                (String) entity.getProperty(Price.FIELD_NAME_id),
                (String) entity.getProperty(Price.FIELD_NAME_taker)
        );
    }
}
