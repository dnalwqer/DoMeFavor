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
import java.util.logging.Logger;
/**
 * Created by Alex on 16/4/17.
 */
public class ContactDatastore {

    private static final Logger mLogger = Logger
            .getLogger(ContactDatastore.class.getName());
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    private static Key getKey() {
        return KeyFactory.createKey(Contact.CONTACT_PARENT_ENTITY_NAME,
                Contact.CONTACT_PARENT_KEY_NAME);
    }


    public static boolean add(Contact contact) {
//        if (getContactByName(contact.id, null) != null) {
//            mLogger.log(Level.INFO, "contact exists");
//            return false;
//        }

        Key parentKey = getKey();


        Entity entity = new Entity(Contact.CONTACT_ENTITY_NAME, contact.id,
                parentKey);
        entity.setProperty(Contact.FIELD_NAME_id, contact.id);
        entity.setProperty(Contact.FIELD_NAME_taskName, contact.taskName);
        entity.setProperty(Contact.FIELD_NAME_lat, contact.lat);
        entity.setProperty(Contact.FIELD_NAME_time, contact.time);
        entity.setProperty(Contact.FIELD_NAME_lng, contact.lng);
        entity.setProperty(Contact.FIELD_NAME_content, contact.content);
        entity.setProperty(Contact.FIELD_NAME_poster, contact.poster);
        entity.setProperty(Contact.FIELD_NAME_price, contact.price);

        mDatastore.put(entity);

        return true;
    }

    public static boolean delete(String name) {
        // you can also use name to get key, then use the key to delete the
        // entity from datastore directly
        // because name is also the entity's key

        // query
        Query.Filter filter = new Query.FilterPredicate(Contact.FIELD_NAME_id,
                Query.FilterOperator.EQUAL, name);

        Query query = new Query(Contact.CONTACT_ENTITY_NAME);
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
        ArrayList<Contact> list = query("");
        for(Contact cur : list){
            delete(cur.id);
        }
    }

    public static ArrayList<Contact> query(String name) {
        ArrayList<Contact> resultList = new ArrayList<Contact>();
        if (name != null && !name.equals("")) {
            Contact contact = getContactByName(name, null);
            if (contact != null) {
                resultList.add(contact);
            }
        } else {
            Query query = new Query(Contact.CONTACT_ENTITY_NAME);
            // get every record from datastore, no filter
            query.setFilter(null);
            // set query's ancestor to get strong consistency
            query.setAncestor(getKey());

            PreparedQuery pq = mDatastore.prepare(query);

            for (Entity entity : pq.asIterable()) {
                Contact contact = getContactFromEntity(entity);
                if (contact != null) {
                    resultList.add(contact);
                }
            }
        }
        return resultList;
    }

    public static Contact getContactByName(String name, Transaction txn) {
        Entity result = null;
        try {
            result = mDatastore.get(KeyFactory.createKey(getKey(),
                    Contact.CONTACT_ENTITY_NAME, name));
        } catch (Exception ex) {

        }

        return getContactFromEntity(result);
    }

    private static Contact getContactFromEntity(Entity entity) {
        if (entity == null) {
            return null;
        }

        return new Contact(
                (String) entity.getProperty(Contact.FIELD_NAME_id),
                (String) entity.getProperty(Contact.FIELD_NAME_taskName),
                (String) entity.getProperty(Contact.FIELD_NAME_lat),
                (String) entity.getProperty(Contact.FIELD_NAME_lng),
                (String) entity.getProperty(Contact.FIELD_NAME_time),
                (String) entity.getProperty(Contact.FIELD_NAME_content),
                (String) entity.getProperty(Contact.FIELD_NAME_price),
                (String) entity.getProperty(Contact.FIELD_NAME_poster)
                );
    }

    public static ArrayList<Contact> queryloca(String lat, String lng){
        ArrayList<Contact> resultList = new ArrayList<Contact>();
        double lng1 = Double.parseDouble(lng);
        double lat1 = Double.parseDouble(lat);
            Query query = new Query(Contact.CONTACT_ENTITY_NAME);
            // get every record from datastore, no filter
            query.setFilter(null);
            // set query's ancestor to get strong consistency
            query.setAncestor(getKey());

            PreparedQuery pq = mDatastore.prepare(query);

            for (Entity entity : pq.asIterable()) {
                Contact contact = getContactFromEntity(entity);

                if (contact != null
                        && distance(lng1, lat1, Double.parseDouble(contact.lng), Double.parseDouble(contact.lat))
                        < 2000
                        ) {
                    resultList.add(contact);
                }
            }

        return resultList;
    }

    public static double distance(double long1, double lat1, double long2,
                                  double lat2) {
        double a, b, R;
        R = 6378137; // earth radius
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    public static ArrayList<Contact> queryname(String name){
        ArrayList<Contact> resultList = new ArrayList<Contact>();

            Query query = new Query(Contact.CONTACT_ENTITY_NAME);
            // get every record from datastore, no filter

            query.setFilter(null);
            // set query's ancestor to get strong consistency
            query.setAncestor(getKey());

            PreparedQuery pq = mDatastore.prepare(query);

            for (Entity entity : pq.asIterable()) {
                Contact contact = getContactFromEntity(entity);
                if (contact.poster.equals(name)) {
                    resultList.add(contact);
                }
            }
        return resultList;
    }

    public static ArrayList<Contact> queryid(String name) {
        ArrayList<Contact> resultList = new ArrayList<Contact>();

            Query query = new Query(Contact.CONTACT_ENTITY_NAME);
            // get every record from datastore, no filter
            query.setFilter(null);
            // set query's ancestor to get strong consistency
            query.setAncestor(getKey());

            PreparedQuery pq = mDatastore.prepare(query);

            for (Entity entity : pq.asIterable()) {
                Contact contact = getContactFromEntity(entity);
                if (contact != null && contact.id.equals(name)) {
                    resultList.add(contact);
                }
            }

        return resultList;
    }
}
