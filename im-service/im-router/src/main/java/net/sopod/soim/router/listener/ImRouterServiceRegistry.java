package net.sopod.soim.router.listener;

import java.rmi.*;
import java.rmi.registry.Registry;

/**
 * ImRouterServiceRegistry
 *
 * @author tmy
 * @date 2022-05-01 16:34
 */
public class ImRouterServiceRegistry implements Registry {

    @Override
    public Remote lookup(String s) throws RemoteException, NotBoundException, AccessException {
        return null;
    }

    @Override
    public void bind(String s, Remote remote) throws RemoteException, AlreadyBoundException, AccessException {

    }

    @Override
    public void unbind(String s) throws RemoteException, NotBoundException, AccessException {

    }

    @Override
    public void rebind(String s, Remote remote) throws RemoteException, AccessException {

    }

    @Override
    public String[] list() throws RemoteException, AccessException {
        return new String[0];
    }

}
