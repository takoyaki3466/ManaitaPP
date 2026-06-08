package com.takoy3466.manaitapp.dataComponent;

import com.takoy3466.manaitapp.core.interfaces.IMsg;

import java.util.Objects;

public abstract class AbstractManaitaData<T> implements IMsg<T> {
    protected T tMsg;

    public AbstractManaitaData(T tMsg) {
        this.tMsg = tMsg;
    }

    @Override
    public T getMsg() {
        return tMsg;
    }

    public void setMsg(T msg) {
        this.tMsg = msg;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AbstractManaitaData<?> that)) return false;
        return Objects.equals(tMsg, that.tMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tMsg);
    }
}
