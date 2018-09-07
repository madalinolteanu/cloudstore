package org.jhipster.lic.domain;

import java.io.Serializable;

/**
 * Created by Madalin on 9/3/2018.
 */
public class CloudStore implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;

    CloudStore(){}

    public CloudStore(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CloudStore that = (CloudStore) o;

        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CloudStore{" +
            "user=" + user +
            '}';
    }
}
