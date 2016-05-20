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
public class ProfileDatastore {

    private static final Logger mLogger = Logger
            .getLogger(ProfileDatastore.class.getName());
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    private static Key getKey() {
        return KeyFactory.createKey(Profile.Profile_PARENT_ENTITY_NAME,
                Profile.Profile_PARENT_KEY_NAME);
    }


    public static boolean add(Profile profile) {
//        if (getprofileByName(profile.id, null) != null) {
//            mLogger.log(Level.INFO, "profile exists");
//            return false;
//        }

        Key parentKey = getKey();


        Entity entity = new Entity(Profile.Profile_ENTITY_NAME, profile.email,
                parentKey);
        entity.setProperty(Profile.FIELD_NAME_age, profile.age);
        entity.setProperty(Profile.FIELD_NAME_email, profile.email);
        entity.setProperty(Profile.FIELD_NAME_gender, profile.gender);

        mDatastore.put(entity);

        return true;
    }

    public static boolean delete(String name) {
        // you can also use name to get key, then use the key to delete the
        // entity from datastore directly
        // because name is also the entity's key

        // query
        Query.Filter filter = new Query.FilterPredicate(Profile.FIELD_NAME_email,
                Query.FilterOperator.EQUAL, name);

        Query query = new Query(Profile.Profile_ENTITY_NAME);
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
        ArrayList<Profile> list = query("");
        for(Profile cur : list){
            delete(cur.email);
        }
    }

    public static ArrayList<Profile> query(String name) {
        ArrayList<Profile> resultList = new ArrayList<Profile>();
        if (name != null && !name.equals("")) {
            Profile profile = getProfileByName(name, null);
            if (profile != null) {
                resultList.add(profile);
            }
        } else {
            Query query = new Query(Profile.Profile_ENTITY_NAME);
            // get every record from datastore, no filter
            query.setFilter(null);
            // set query's ancestor to get strong consistency
            query.setAncestor(getKey());

            PreparedQuery pq = mDatastore.prepare(query);

            for (Entity entity : pq.asIterable()) {
                Profile profile = getProfileFromEntity(entity);
                if (profile != null) {
                    resultList.add(profile);
                }
            }
        }
        return resultList;
    }

    public static Profile getProfileByName(String name, Transaction txn) {
        Entity result = null;
        try {
            result = mDatastore.get(KeyFactory.createKey(getKey(),
                    Profile.Profile_ENTITY_NAME, name));
        } catch (Exception ex) {

        }

        return getProfileFromEntity(result);
    }

    private static Profile getProfileFromEntity(Entity entity) {
        if (entity == null) {
            return null;
        }

        return new Profile(
                (String) entity.getProperty(Profile.FIELD_NAME_age),
                (String) entity.getProperty(Profile.FIELD_NAME_email),
                (String) entity.getProperty(Profile.FIELD_NAME_gender)
        );
    }
}
