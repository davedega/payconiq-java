package com.dega.payconiq.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by davedega on 12/04/18.
 */

public class User extends RealmObject {

    private RealmList<RealmRepo> reposList;

    public RealmList<RealmRepo> getReposList() {
        return reposList;
    }
}

