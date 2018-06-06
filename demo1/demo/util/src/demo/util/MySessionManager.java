package demo.util;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;
import nl.justobjects.pushlet.util.PushletException;

/**
 * Created by Administrator on 2017-1-18.
 */
public class MySessionManager extends SessionManager {

    protected MySessionManager() {
    }

    @Override
    public Session createSession(Event anEvent) throws PushletException {
        return Session.create("jg" + anEvent.getField("ssjg") + "_" + this.createSessionId());
    }
}