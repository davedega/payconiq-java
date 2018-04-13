package com.dega.payconiq.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by davedega on 12/04/18.
 */

public class DataHelper {

    private Realm realm;

    public DataHelper(Realm realm) {
        this.realm = realm;
    }

    public ArrayList respositories;

    public ArrayList getRespositories() {
        return new ArrayList(realm.where(RealmRepo.class).findAll());
    }

    // Create 3 counters and insert them into random place of the list.
    public void createRepo(final Repository repository) {
        realm.beginTransaction();
        RealmRepo newRepo = realm.createObject(RealmRepo.class, UUID.randomUUID().toString());
        newRepo.setName(repository.getName());
        newRepo.setForks(repository.getForks());
        newRepo.setStargazers(repository.getStargazersCount());
        newRepo.setWatchersCount(repository.getWatchersCount());
        realm.commitTransaction();
    }

    public void saveRepositories(List<Repository> repositoriesResponse) {
        realm.beginTransaction();

        for (Repository repository : repositoriesResponse) {
            RealmRepo newRepo = realm.createObject(RealmRepo.class, UUID.randomUUID().toString());
            newRepo.setName(repository.getName());
            newRepo.setForks(repository.getForks());
            newRepo.setStargazers(repository.getStargazersCount());
            newRepo.setWatchersCount(repository.getWatchersCount());
        }
        realm.commitTransaction();
    }

}
